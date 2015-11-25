package com.david.web.pppppp.service.resendprize;

import com.david.web.pppppp.entity.ExcelParseExceptInfo;
import com.david.web.pppppp.entity.ReSendPrizeBean;
import com.david.web.pppppp.service.ServiceException;
import com.david.web.pppppp.util.excel.parser.DataProcesser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-5-30
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
public class KefuExcelDataProcesser implements DataProcesser {

    private static int ROW_CELL_NUM = 4;

    private int nowCell;

    private int buffer = 4096;

    private int nowRow;

    private Map<String, Object> params;

    private List<ReSendPrizeBean> prizeList = new ArrayList<ReSendPrizeBean>();


    private final ResendPrizeService resendPrizeService;

    private final String tableName;


    List<ExcelParseExceptInfo> emptyRowList = new ArrayList<ExcelParseExceptInfo>();


    public KefuExcelDataProcesser(ResendPrizeService resendPrizeService, String tableName, int buffer) {
        this.resendPrizeService = resendPrizeService;
        this.tableName = tableName;
        this.buffer = buffer;
    }

    public KefuExcelDataProcesser(ResendPrizeService resendPrizeService, String tableName) {
        this(resendPrizeService, tableName, 4096);
    }

    public List<ExcelParseExceptInfo> getEmptyRowList() {
        return emptyRowList;
    }

    @Override
    public void doSheetDataBegin() {
        nowRow = 0;
    }

    @Override
    public void doRowBegin() {
        nowCell = 0;
        nowRow++;
        params = new HashMap<String, Object>();
    }

    @Override
    public void doRowEnd() {
        try {
            ReSendPrizeBean prize = new ReSendPrizeBean(params);
            prizeList.add(prize);
        } catch (ServiceException e) {
            emptyRowList.add(new ExcelParseExceptInfo(nowRow, nowCell + 1, "空单元格", ExcelParseExceptInfo.SKIP_HANDLER));
        }

        if (nowRow % buffer == 0) {
            flush();
        }
    }

    @Override
    public void doCellBegin() {
        nowCell++;
    }

    @Override
    public void doCellEnd(String data) {
        String _data = data.trim();

        /** 空cell 目前的规则是跳过，应为这种比较少，跳过继续，通知丹丹就行 **/
        if (_data.isEmpty()) {
            return;
        }
        String columnName = ReSendPrizeBean.IDX_COLUMN_MAP.get(nowCell - 1);
        String columnType = ReSendPrizeBean.COLUMN_TYPE_MAP.get(columnName);
        if (columnType.equals(ReSendPrizeBean.NUMBER_COLUMN)) {
            try {
                params.put(columnName, Long.parseLong(_data));
            } catch (NumberFormatException e) {
                int haveDoneNum = (nowRow / buffer + (nowRow % buffer > 0 ? 1 : 0) - 1) * buffer;
                throw new ServiceException("第" + nowRow + "行，第" + (nowCell) +
                        "栏转换为数字失败，请检查文件格式,已经成功处理" + haveDoneNum + "行数据。");
            }
        } else {
            params.put(columnName, _data);
        }
    }

    @Override
    public void doSheetDataEnd() {
        flush();
    }

    private void flush() {
        resendPrizeService.saveFromList(prizeList, tableName);
        prizeList.clear();
    }
}
