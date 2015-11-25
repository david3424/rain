package com.david.web.pppppp.common.page;


import org.json.JSONObject;

/**
 * 消息抽象类
 * User: chenzhiyi
 * Date: 12-9-5
 * Time: 上午11:48
 */
abstract class AbstractMessage implements Message {
    /**
     * JSON对象
     */
    protected JSONObject json;

    /**
     * 获得当前JSON对象，如果为空，则创建一个。
     * @return  JSONObject
     */
    protected JSONObject currentJson() {
        if (json == null) {
            json = new JSONObject();
        }
        return json;
    }
}
