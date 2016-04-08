package org.david.rain.wmproxy.common.base.hibernate3;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.EmptyInterceptor;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class TreeIntercptor extends EmptyInterceptor {
	protected SessionFactory sessionFactory;

	protected SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(ServletActionContext
							.getServletContext());
			sessionFactory = (SessionFactory) wac.getBean("sessionFactory",
					SessionFactory.class);
		}
		return sessionFactory;
	}

	protected Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		if (entity instanceof HibernateTree) {
			HibernateTree tree = (HibernateTree) entity;
			Long parentId = tree.getParentId();
			String beanName = tree.getClass().getName();
			Session session = getSession();
			FlushMode model = session.getFlushMode();
			session.setFlushMode(FlushMode.MANUAL);
			Integer myPosition = new Integer(0);
			if (parentId != null) {
				String hql = "select b.lft from " + beanName
						+ " b where b.id=:pid";
				myPosition = (Integer) session.createQuery(hql).setLong("pid",
						parentId).uniqueResult();
			}
			String hql1 = "update " + beanName
					+ " b set b.rgt = b.rgt + 2 WHERE b.rgt > :myPosition";
			String hql2 = "update " + beanName
					+ " b set b.lft = b.lft + 2 WHERE b.lft > :myPosition";
			if (!StringUtils.isBlank(tree.getTreeCondition())) {
				hql1 += " and (" + tree.getTreeCondition() + ")";
				hql2 += " and (" + tree.getTreeCondition() + ")";
			}
			session.createQuery(hql1).setInteger("myPosition", myPosition)
					.executeUpdate();
			session.createQuery(hql2).setInteger("myPosition", myPosition)
					.executeUpdate();
			session.setFlushMode(model);
			for (int i = 0; i < propertyNames.length; i++) {
				if (propertyNames[i].equals(HibernateTree.LFT)) {
					state[i] = myPosition + 1;
				}
				if (propertyNames[i].equals(HibernateTree.RGT)) {
					state[i] = myPosition + 2;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		if (!(entity instanceof HibernateTree)) {
			return false;
		}
		HibernateTree tree = (HibernateTree) entity;
		for (int i = 0; i < propertyNames.length; i++) {
			if (propertyNames[i].equals(HibernateTree.PARENT)) {
				HibernateTree preParent = (HibernateTree) previousState[i];
				HibernateTree curParent = (HibernateTree) currentState[i];
				updateParent(tree, preParent, curParent);
				return true;
			}
		}
		return false;
	}

	private void updateParent(HibernateTree tree, HibernateTree preParent,
			HibernateTree curParent) {
		if (preParent != null && preParent != null
				&& !preParent.equals(curParent)) {
			String beanName = tree.getClass().getName();
			Session session = getSession();
			FlushMode model = session.getFlushMode();
			session.setFlushMode(FlushMode.MANUAL);

			// 获得节点位置
			String hql = "select b.lft,b.rgt from " + beanName
					+ " b where b.id=:id";
			Object[] position = (Object[]) session.createQuery(hql).setLong(
					"id", tree.getId()).uniqueResult();
			int nodeLft = ((Number) position[0]).intValue();
			int nodeRgt = ((Number) position[1]).intValue();
			int span = nodeRgt - nodeLft + 1;
			// 获得当前父节点左位置
			hql = "select b.lft from " + beanName + " b where b.id=:id";
			int parentLft = ((Number) session.createQuery(hql).setLong("id",
					curParent.getId()).uniqueResult()).intValue();

			// 先空出位置
			String hql1 = "update " + beanName + " b set b.rgt = b.rgt + "
					+ span + " WHERE b.rgt > :parentLft";
			String hql2 = "update " + beanName + " b set b.lft = b.lft + "
					+ span + " WHERE b.lft > :parentLft";
			if (!StringUtils.isBlank(tree.getTreeCondition())) {
				hql1 += " and (" + tree.getTreeCondition() + ")";
				hql2 += " and (" + tree.getTreeCondition() + ")";
			}
			session.createQuery(hql1).setInteger("parentLft", parentLft)
					.executeUpdate();
			session.createQuery(hql2).setInteger("parentLft", parentLft)
					.executeUpdate();

			// 再调整自己
			hql = "select b.lft,b.rgt from " + beanName + " b where b.id=:id";
			position = (Object[]) session.createQuery(hql).setLong("id",
					tree.getId()).uniqueResult();
			nodeLft = ((Number) position[0]).intValue();
			nodeRgt = ((Number) position[1]).intValue();
			int offset = parentLft - nodeLft + 1;
			hql = "update "
					+ beanName
					+ " b set b.lft=b.lft+:offset, b.rgt=b.rgt+:offset WHERE b.lft between :nodeLft and :nodeRgt";
			if (!StringUtils.isBlank(tree.getTreeCondition())) {
				hql += " and (" + tree.getTreeCondition() + ")";
			}
			session.createQuery(hql).setParameter("offset", offset)
					.setParameter("nodeLft", nodeLft).setParameter("nodeRgt",
							nodeRgt).executeUpdate();

			// 最后删除（清空位置）
			hql1 = "update " + beanName + " b set b.rgt = b.rgt - " + span
					+ " WHERE b.rgt > :nodeRgt";
			hql2 = "update " + beanName + " b set b.lft = b.lft - " + span
					+ " WHERE b.lft > :nodeRgt";
			if (tree.getTreeCondition() != null) {
				hql1 += " and (" + tree.getTreeCondition() + ")";
				hql2 += " and (" + tree.getTreeCondition() + ")";
			}
			session.createQuery(hql1).setParameter("nodeRgt", nodeRgt)
					.executeUpdate();
			session.createQuery(hql2).setParameter("nodeRgt", nodeRgt)
					.executeUpdate();

			session.setFlushMode(model);
		}
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		if (entity instanceof HibernateTree) {
			HibernateTree tree = (HibernateTree) entity;
			String beanName = tree.getClass().getName();
			Session session = getSession();
			FlushMode model = session.getFlushMode();
			session.setFlushMode(FlushMode.MANUAL);
			String hql = "select b.lft from " + beanName + " b where b.id=:id";
			Integer myPosition = (Integer) session.createQuery(hql).setLong(
					"id", tree.getId()).uniqueResult();
			String hql1 = "update " + beanName
					+ " b set b.rgt = b.rgt - 2 WHERE b.rgt > :myPosition";
			String hql2 = "update " + beanName
					+ " b set b.lft = b.lft - 2 WHERE b.lft > :myPosition";
			if (tree.getTreeCondition() != null) {
				hql1 += " and (" + tree.getTreeCondition() + ")";
				hql2 += " and (" + tree.getTreeCondition() + ")";
			}
			session.createQuery(hql1).setInteger("myPosition", myPosition)
					.executeUpdate();
			session.createQuery(hql2).setInteger("myPosition", myPosition)
					.executeUpdate();
			session.setFlushMode(model);
		}
	}
}
