/* @(#) java 
 * Copyright(c) 2016 NOAH. All Right Reserver.
 * version : 版本管理系统中的文件版本
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 * 序号   时间             作者                   修改内容 
 * 1.    2016-1-9          黄银海        	created this class.
 */
package org.david.rain.common.exception;

public interface IErrorCode {

	/****
	 * 获取异常代码。
	 * @return
	 */
	public int getRespCode();

	/****
	 * 获取异常描述信息。
	 * @return
	 */
	public String getRespMsg();

}
