package com.david.web.pppppp.common.page;


import com.david.web.pppppp.common.CommonList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.david.web.pppppp.common.page.MessageConst.RECORDS;

/**
 * 分页查询message
 * User: chenzhiyi
 * Date: 12-8-14
 * Time: 上午11:14
 */
public class PaginationMessage extends AbstractMessage {
    private static final Logger LOG = LoggerFactory.getLogger(PaginationMessage.class);
    private final static String TOTAL_COUNT = "totalCount";
    private int totalCount;
    private List list;

    public PaginationMessage() {
    }

    public PaginationMessage(JSONObject json){
        this.json = json;
    }

    public PaginationMessage(CommonList commonList) {
        totalCount = commonList.getRecNum();
        list = commonList;
    }

    public PaginationMessage(int totalCount, List list) {
        this.totalCount = totalCount;
        this.list = list;
    }

    public JSONObject buildJSON() {
        json = currentJson();
        try {
            json.put(TOTAL_COUNT, totalCount);
            json.put(RECORDS, new JSONArray(list, false));
        } catch (JSONException e) {
            LOG.debug("JSONObject不能写入参数", e);
        }
        return json;
    }

    public void putInJSON(JSONObject record) {
        try {
            record.put(TOTAL_COUNT, totalCount);
            record.put(RECORDS, new JSONArray(list, false));
        } catch (JSONException e) {
            LOG.debug("JSONObject不能写入参数", e);
        }
    }

    public void putMessage(int totalCount, List list) {
        this.totalCount = totalCount;
        this.list = list;
    }

    public void putMessage(CommonList commonList) {
        this.totalCount = commonList.getRecNum();
        this.list = commonList;
    }
}
