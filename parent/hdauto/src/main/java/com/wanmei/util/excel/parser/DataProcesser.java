package com.david.web.wanmei.util.excel.parser;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-5-29
 * Time: 下午7:39
 * To change this template use File | Settings | File Templates.
 */
public interface DataProcesser {


    public void doSheetDataBegin();
    public void doRowBegin();

    public void doRowEnd();

    /**
     * 对有用的单元开始时做操作
     */
    public void doCellBegin();

    /**
     * 对有用的单元结束做操作
     *
     * @param data cell 的 data，业务上有用的数据
     */
    public void doCellEnd(String data);

    public void doSheetDataEnd();
}
