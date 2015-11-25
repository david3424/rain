/*
* 时间： 2007-5-18 16:37:42
*  北京完美时空网络技术有限公司
*/
package com.david.web.pppppp.common.search;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 李亮阳
 * Date: 2007-5-18
 * Time: 11:01:49
 * 定义新的search对象；
 */
public class Search {
    //操作类型
    public final static String SEARCH_EQUAL = " = ";
    public final static String SEARCH_LIKE = " like ";
    public final static String SEARCH_BIG = " > ";
    public final static String SEARCH_BIGEQUAL = " >= ";
    public final static String SEARCH_SIALL = " < ";
    public final static String SEARCH_SIALLEQUAL = " <= ";

    //字段的类型
    public final static int SEARCH_TYPE_STRING = 1;
    public final static int SEARCH_TYPE_INT = 2;
    public final static int SEARCH_TYPE_LIKE = 3;

    //关系符号
    public final static String SEARCH_OR = " or ";
    public final static String SEARCH_AND = " and ";


    //排序方式
    public final static String SEARCH_DESC = " desc ";
    public final static String SEARCH_ASC = " asc ";


    // 用来接受页面传过来的参数；
    private HttpServletRequest request;

    //from前面的sql语句，得到查询的数量；
    private String selectCountSql;

    //fromq前面的sql语句，得到程序的结果集；
    private String selectSql;

    // 当前表中一个查询的where部分的集合；
    public Hashtable<String, Where> whereMap = new Hashtable();

    // 当前表中一个查询的order部分集合；
    public Hashtable<String, Order> orderMap = new Hashtable();

    Where where = null;
    Order order = null;
   private   boolean flag = false;

   public boolean isFlag() {
        return flag;
    }//是否第一次调用addWhere方法；

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

		

    //是否第一次调用addWhere方法；
    private boolean addWhereFirst = true;
    //是否第一次调用addOrder方法；
    private boolean addOrderFirst = true;

    //存放where后面的条件；
    private List<String> whereSql = new ArrayList();

    //存放order后面的条件；
    private List<String> orderSql = new ArrayList();


    //存放?对应的参数。
    private List<Object> whereParas = new ArrayList<Object>();


    // 生产的sql语句；
    private String sqlStr;

    //	 每页显示的记录数
    private int pageSize = 10;
    //	当前页数 用于分页；
    private int pageNo = 0;
    //	 查询记录的开始位置；用于分页；
    private int preRec = 0;

// 是否分页；
    private boolean whetherPage;

// 记录查询条件组成的字符串；
    private String searchStr = "";


    /**
     * 添加select count(*) 形式的sql语句；
     */
    public void addSelectCountSql(String contSql) {
        selectCountSql = contSql + " ";
    }

    /**
     * 添加select count(*) 形式的sql语句；
     */
    public void addSelectSql(String Sql) {
        selectSql = Sql + " ";
    }


    /**
     * 添加Where后面的条件；
     * 从request中取参数；
     * @param whereOrAnd  添加 or 或者 and
     * @param property    要添加的查询字段名
     * @param propertyType   要查询的字段的类型；
     *                         public final static int SEARCH_TYPE_STRING = 1; 字符串
     *                         public final static int SEARCH_TYPE_INT = 2;     整型
     *                         public final static int SEARCH_TYPE_LIKE = 3;    字符型，like，
     * @param operation       比较符号：
     *                          public final static String SEARCH_EQUAL = " = ";
     *                          public final static String SEARCH_LIKE = " like ";
     *                          public final static String SEARCH_BIG = " > ";
     *                          public final static String SEARCH_BIGEQUAL = " >= ";
     *                          public final static String SEARCH_SIALL = " < ";
     *                          public final static String SEARCH_SIALLEQUAL = " <= ";
     * @param defaultValue     默认值，当添加了要查询的字段，而request中没有此字段名的值，就用此值
     * @throws Exception
     */
    public void addWhere(String whereOrAnd, String property, int propertyType, String operation, String defaultValue) throws Exception {


        String[] InvalidParamers = {"insert", "update", "delete", "select","1=1","1=2"};
        String whereSqlStr = "";
        if (request != null) {
            if (propertyType == SEARCH_TYPE_STRING) {
                if (addWhereFirst) {
                    addWhereFirst = false;
                    searchStr += "&" + property + "=" + request.getParameter(property);
                    whereSqlStr = property + operation + "'" + request.getParameter(property) + "'";
                } else {
                    searchStr += "&" + property + "=" + request.getParameter(property);
                    whereSqlStr = whereOrAnd + property + operation + "'" + request.getParameter(property) + "'";
                }
            } else if (propertyType == SEARCH_TYPE_LIKE) {
                if (addWhereFirst) {
                    searchStr += "&" + property + "=" + request.getParameter(property);
                    addWhereFirst = false;
                    whereSqlStr = property + operation + "'%" + request.getParameter(property) + "%'";
                } else {
                    searchStr += "&" + property + "=" + request.getParameter(property);
                    whereSqlStr = whereOrAnd + property + operation + "'%" + request.getParameter(property) + "%'";
                }
            }  else {
                if (addWhereFirst) {
                    searchStr += "&" + property + "=" + request.getParameter(property);
                    addWhereFirst = false;
                    whereSqlStr = property + operation + request.getParameter(property);
                } else {
                    searchStr += "&" + property + "=" + request.getParameter(property);
                    whereSqlStr = whereOrAnd + property + operation + request.getParameter(property);
                }
            }
        } else {
            //request 为空；添加 defaulstValue
            if (propertyType == SEARCH_TYPE_STRING) {
                if (addWhereFirst) {
                    addWhereFirst = false;
                    searchStr += "&" + property + "=" + defaultValue;
                    whereSqlStr = property + operation + "'" + defaultValue + "'";
                } else {
                    searchStr += "&" + property + "=" + defaultValue;
                    whereSqlStr = whereOrAnd + property + operation + "'" + defaultValue + "'";
                }
            } else if (propertyType == SEARCH_TYPE_LIKE) {
                if (addWhereFirst) {
                    searchStr += "&" + property + "=" + defaultValue;
                    addWhereFirst = false;
                    whereSqlStr = property + operation + "'%" + defaultValue + "%'";
                } else {
                    searchStr += "&" + property + "=" + defaultValue;
                    whereSqlStr = whereOrAnd + property + operation + "'%" + defaultValue + "%'";
                }
            } else {
                if (addWhereFirst) {
                    searchStr += "&" + property + "=" + defaultValue;
                    addWhereFirst = false;
                    whereSqlStr = property + operation + defaultValue;
                } else {
                    searchStr += "&" + property + "=" + defaultValue;
                    whereSqlStr = whereOrAnd + property + operation + defaultValue;
                }
            }

        }




        System.out.println("添加的where = " + whereSqlStr);
        whereSql.add(whereSqlStr);
    }


    /**
     * 直接添加where后面的参数
     * 例如：" and status=1 ";
     * @param sqlStr 要查询的字段  status=1
     * @param andOr  字段前面的链接符 and or or
     */
    public void addWhere(String andOr, String sqlStr){



       if(whereSql.size()>0){
           whereSql.add(" "+andOr+" "+sqlStr+" ");
       }else{
           whereSql.add(" "+sqlStr+" ");
       }
    }


    public void addWhere(String andOr, String sqlStr,Object obj){
        addWhere(andOr, sqlStr);
        whereParas.add(obj);
    }




    /**
     * 添加order的条件；
     * @param property
     */
    public void addOrder(String property, String descOrAsc) {
        String orderSqlStr = "";
        if (addOrderFirst) {
            orderSqlStr = property + " " + descOrAsc;
            addOrderFirst = false;
        } else {
            orderSqlStr = " , " + property + " " + descOrAsc;
        }
        orderSql.add(orderSqlStr);
    }

    /**
     * 产生 select * from where .....语句；
     *
     * @return
     * @throws Exception
     */
    public String builderSearchSql() throws Exception {
        sqlStr = selectSql;
        if (whereSql.size() > 0) {
            for (int i = 0; i < whereSql.size(); i++) {
                if (i == 0) {
                    sqlStr += " where " + whereSql.get(0);
                } else {
                    sqlStr += whereSql.get(i);
                }
            }
        }

        if (orderSql.size() > 0) {
            sqlStr += " order by ";
            for (int i = 0; i < orderSql.size(); i++) {
                sqlStr += orderSql.get(i);
            }
        }

        if (whetherPage && pageNo > 0) sqlStr += " limit " + getPreRec() + "," + pageSize;
//        System.out.println("--------生成查询结果集Str = " + sqlStr);
//        if (request != null) request.setAttribute("search", this);
        return sqlStr;

    }

    /**
     * 生产select count(*) from where .....语句；
     *
     * @return
     * @throws Exception
     */
    public String builderCountSql() throws Exception {
        sqlStr = selectCountSql;

        if (whereSql.size() > 0) {
            for (int i = 0; i < whereSql.size(); i++) {
                if (i == 0) {
                    sqlStr += " where " + whereSql.get(0);
                } else {
                    sqlStr += whereSql.get(i);
                }
            }
        }


//        System.out.println("生成得到数量的 Str = " + sqlStr);
//        if (request != null) request.setAttribute("search", this);
        return sqlStr;
    }


    public Where getWhere(String property) {
        return whereMap.get(property);
    }

    public void setWhere(Where where) {
        this.where = where;
    }

    public boolean isAddWhereFirst() {
        return addWhereFirst;
    }

    public void setAddWhereFirst(boolean addWhereFirst) {
        this.addWhereFirst = addWhereFirst;
    }

    public boolean isAddOrderFirst() {
        return addOrderFirst;
    }

    public void setAddOrderFirst(boolean addOrderFirst) {
        this.addOrderFirst = addOrderFirst;
    }

    public List<String> getWhereSql() {
        return whereSql;
    }

    public void setWhereSql(List<String> whereSql) {
        this.whereSql = whereSql;
    }

    public List<String> getOrderSql() {
        return orderSql;
    }

    public void setOrderSql(List<String> orderSql) {
        this.orderSql = orderSql;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPreRec() {
        preRec = pageSize * (pageNo - 1);
        return preRec;
    }

    public void setPreRec(int preRec) {
        this.preRec = preRec;
    }

    public Hashtable<String, Where> getWhereMap() {
        return whereMap;
    }

    public void setWhereMap(Hashtable<String, Where> whereMap) {
        this.whereMap = whereMap;
    }

    public Hashtable<String, Order> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Hashtable<String, Order> orderMap) {
        this.orderMap = orderMap;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isWhetherPage() {
        return whetherPage;
    }

    public void setWhetherPage(boolean whetherPage) {
        this.whetherPage = whetherPage;
    }

    public String getSearchStr() {
        return searchStr;
    }

    /**
     * 向翻页的连接中填参数；格式如下："&a=1&b=0"
     *
     * @param searchStr
     */
    public void addToSearchStr(String searchStr) {
        this.searchStr = this.searchStr + " " + searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }


	public List<Object> getWhereParas() {
		return whereParas;
	}

	public void setWhereParas(List<Object> whereParas) {
		this.whereParas = whereParas;
	}

}
