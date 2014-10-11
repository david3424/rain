package org.david.rain.model;

import java.io.Serializable;
import java.util.List;

public class JqGrid<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<T> gridData;
	private Integer page;
	private Integer total;
	private Integer records;

	public List<T> getGridData() {
		return gridData;
	}

	public void setGridData(List<T> gridData) {
		this.gridData = gridData;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}
}
