package org.david.rain.wmproxy.common.base.struts2;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * User: liaom
 * Date: 2010-4-7
 * 
 * 所有Action的基类 .
 */
public class BaseAction implements ServletRequestAware, ServletResponseAware {
	
	protected Integer[] ids;
	protected Integer id;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
    
    // ExtJs GridPanel参数
    protected Integer start;
    protected Integer limit;
    protected String sort;
    protected String dir;
    protected String callback;
    protected String query;

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setServletRequest(HttpServletRequest req) {
        request = req;
    }

    public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setServletResponse(HttpServletResponse httpServletResponse) {
        response = httpServletResponse;
        // 让所有的action禁止缓存
        //response.setHeader("Pragma", "No-cache");
        //response.setHeader("Cache-Control", "no-cache");
    }
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
