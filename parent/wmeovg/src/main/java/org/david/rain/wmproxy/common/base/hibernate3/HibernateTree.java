package org.david.rain.wmproxy.common.base.hibernate3;

/**
 * Hibernate实现父子结构tree，使用The Nested Set Model
 * 
 *
 */
public interface HibernateTree {
	public static final String LFT = "lft";
	public static final String RGT = "rgt";
	public static final String PARENT = "parent";

	public Integer getLft();

	public void setLft(Integer lft);

	public Integer getRgt();

	public void setRgt(Integer rgt);

	public Long getParentId();

	public Long getId();

	public String getTreeCondition();
}
