package org.david.rain.wmproxy.common.base.page;

import java.util.List;

@SuppressWarnings("serial")
public class Pagination extends SimplePage implements java.io.Serializable,
		Paginable {

	public Pagination() {
	}

	public Pagination(int start, int pageSize, int totalCount) {
		super(start, pageSize, totalCount);
	}

	@SuppressWarnings("unchecked")
	public Pagination(int start, int pageSize, int totalCount, List list) {
		super(start, pageSize, totalCount);
		this.list = list;
	}

	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 当前页的数据
	 */
	@SuppressWarnings("unchecked")
	private List list;

	@SuppressWarnings("unchecked")
	public List getList() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public void setList(List list) {
		this.list = list;
	}
}
