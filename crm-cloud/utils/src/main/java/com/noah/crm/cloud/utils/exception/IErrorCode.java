package com.noah.crm.cloud.utils.exception;

/**
 * @author xdw9486
 */
public interface IErrorCode {


    /**
     * 业务代码
     *
     * @return
     */
    String getRespCode();

    /****
     * 获取异常描述信息。
     * @return
     */
    String getRespMsg();

    /****
     * 获取异常代码。
     * @return
     */
    int getStatus();
}
