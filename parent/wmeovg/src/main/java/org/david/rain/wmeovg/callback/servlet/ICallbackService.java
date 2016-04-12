package org.david.rain.wmeovg.callback.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * @ClassName ICallBack
 * @Description 回调方法接口
 * @date 2010-8-31 上午11:21:33
 */
public interface ICallbackService {
    /**
     * @param appid    应用标识
     * @param callback 回调参数
     * @Description 结果处理
     */
    void receive(String gid, String cid, String appid, int status, int count, String callback) throws Exception;

    /**
     * @param config
     * @throws ServletException
     * @Description servlet初始化
     */
    void init(ServletConfig config) throws ServletException;
}
