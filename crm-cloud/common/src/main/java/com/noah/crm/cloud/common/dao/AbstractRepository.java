package com.noah.crm.cloud.common.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

import static com.noah.crm.cloud.utils.database.SQLUtils.*;


/**
 * @author xdw9486
 */
public interface AbstractRepository {

    EntityManager getEm();

    /**
     * 使用jpql进行分页查询
     *
     * @param ql          jpql
     * @param pageable    分页对象
     * @param queryParams 查询参数
     * @param <T>
     * @return
     */
    default <T> Page<T> query(String ql, Pageable pageable, Map<String, Object> queryParams) {

        EntityManager em = getEm();

        Query query = em.createQuery(ql);
        queryParams.forEach(query::setParameter);

        String countQl = " select count(1) " + removeFetchInCountQl(removeSelect(removeOrderBy(ql)));
        Query countQuery = em.createQuery(countQl);
        queryParams.forEach(countQuery::setParameter);

        long total;
        if (hasGroupBy(ql)) {
            List resultList = countQuery.getResultList();
            total = resultList.size();
        } else {
            total = (Long) countQuery.getSingleResult();
        }
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<T> results = query.getResultList();
        return new PageImpl<>(results, pageable, total);
    }

    /**
     * 使用jpql进行查询
     *
     * @param ql          jpql
     * @param queryParams 查询参数
     * @param <T>
     * @return
     */
    default <T> List<T> query(String ql, Map<String, Object> queryParams) {

        EntityManager em = getEm();

        Query query = em.createQuery(ql);
        queryParams.forEach(query::setParameter);

        return query.getResultList();
    }

    /**
     * 使用jpql进行数据更新操作
     *
     * @param ql
     * @param queryParams
     * @return
     */
    default int update(String ql, Map<String, Object> queryParams) {

        Query query = getEm().createQuery(ql);
        queryParams.forEach(query::setParameter);

        return query.executeUpdate();
    }


}
