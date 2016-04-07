package org.david.rain.wmproxy.module.sys.manager;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManager;
import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.Model;

import java.util.Set;


public interface FunctionMng extends CoreManager<Function> {


	/**
	 * 得到功能集合所属的模块集合
	 * @param functions
	 * @return Set<Model>
	 */	
	public Set<Model> getFunctionSetModels(Set<Function> functions);
	
	/**
	 * 分页查询
	 * @param name
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findByName(String query, int start, int limit, String sort, String dir);
}