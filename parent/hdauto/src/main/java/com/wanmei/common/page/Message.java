package com.david.web.wanmei.common.page;


import org.json.JSONObject;

/**
 * 业务层与控制层传递消息。
 * 方便控制层已JSON格式输出到界面上的接口
 * 工具类ActionUtil.outputMessage()负责输出
 * 工具类MessageUtil.createMessage()可以建造Message
 * 可以扩展自己需要的Message
 */
public interface Message {
    /**
     * 建造Json
     * @return
     */
    JSONObject buildJSON();

    /**
     * 向JSON设置值
     * @param record
     */
    void putInJSON(JSONObject record);
}
