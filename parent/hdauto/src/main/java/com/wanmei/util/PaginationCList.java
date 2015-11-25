package com.david.web.pppppp.util;

import com.david.web.pppppp.common.CommonList;

import java.util.List;

/**
 * Created by czw on 13-12-19.
 */
public class PaginationCList<T> {
    public int pageNo;
    public int recNum;
    public int pageSize;

    public List<T> list;

    public PaginationCList(CommonList<T> list)
    {
        this.list = list;
        this.recNum = list.getRecNum();
        this.pageNo = list.pageNo;
        this.pageSize = list.pageSize;
    }


}
