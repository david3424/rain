package com.wanmei.service.resendprize;

import com.wanmei.common.CommonList;
import com.wanmei.common.Constant;
import com.wanmei.common.DateUtils;
import com.wanmei.common.search.Search;
import com.wanmei.entity.*;
import com.wanmei.logservice.OperationLog;
import com.wanmei.logservice.OperationLogger;
import com.wanmei.logservice.SendPrizeBean;
import com.wanmei.repository.DaoManager;
import com.wanmei.repository.Idao;
import com.wanmei.repository.ResendPrizeIdao;
import com.wanmei.repository.SendPrizeDao;
import com.wanmei.service.ServiceException;
import com.wanmei.util.excel.parser.KefuSheetHandler;
import com.wanmei.wmeovg.request.service.IPrizeService;
import com.wanmei.wmeovg.request.service.PrizeServiceManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: gameuser
 * Date: 13-1-5
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class ResendPrizeService {

    private static Logger logger = LoggerFactory.getLogger(ResendPrizeService.class);

    private ResendPrizeIdao idao;// 补发相关数据目前还是记录在hd161上面再

    private SendPrizeDao sendPrizeDao;

    public ResendPrizeIdao getIdao() {
        return idao;
    }

    @Autowired
    OperationLogger operationLogger;

    @Autowired
    public void setSendPrizeDao(@Qualifier("sendPrizeDao") SendPrizeDao dao) {
        this.sendPrizeDao = dao;
    }

    @Autowired
    public void setIdao(ResendPrizeIdao idao) {
        this.idao = idao;
    }

    /**
     * 限定了 4列 分别为 username roleid server prize
     *
     * @param path
     * @param tablename
     * @return
     */
    @Deprecated  //这个不也用了15M的excel就不行了，就算小也不好，内存占用太大
    public List<ExcelParseExceptInfo> uploadExcelFrom(String path, String tablename) {
        final int bufferSize = 4096;

        List<ExcelParseExceptInfo> result = new ArrayList<ExcelParseExceptInfo>();
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(path);
        } catch (IOException e1) {
            logger.error(e1.getMessage());
            throw new ServiceException("上传Excel时IO错误，可能文件太大，再试一次。");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException("文件转化为excel数据出错，应该是文件格式问题。");
        }
        try {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            XSSFRow xssfRow;

            int itr = xssfSheet.getFirstRowNum();
            int rownum = xssfSheet.getPhysicalNumberOfRows();

            while (itr < rownum) {

                List<ReSendPrizeBean> list = new ArrayList<ReSendPrizeBean>();

                for (int i = 0; itr < rownum && i < bufferSize; itr++, i++) {
                    xssfRow = xssfSheet.getRow(itr);

                    int nullIndex = checkNullCell(xssfRow);
                    if (nullIndex == -1) {
                        Map<String, Object> params = new HashMap<String, Object>();
                        for (int j = 0; j < ReSendPrizeBean.EXCEL_ROW_NUM; j++) {
                            Cell cell = xssfRow.getCell(j);
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            String columnName = ReSendPrizeBean.IDX_COLUMN_MAP.get(j);
                            String columnType = ReSendPrizeBean.COLUMN_TYPE_MAP.get(columnName);
                            if (columnType.equals(ReSendPrizeBean.NUMBER_COLUMN)) {
                                try {
                                    params.put(columnName, Long.parseLong(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                    throw new ServiceException("第" + itr + "行，第" + (j + 1) + "栏数据转化失败，请检查文件格式。");
                                }
                            } else {

                                params.put(columnName, cell.getStringCellValue());
                            }
                        }
                        ReSendPrizeBean rp = new ReSendPrizeBean(params);
                        list.add(rp);
                    } else {
                        result.add(new ExcelParseExceptInfo(itr, nullIndex + 1, "空单元格", ExcelParseExceptInfo.SKIP_HANDLER));
                    }
                }
                saveFromList(list, tablename);
            }

        } catch (Exception se) {
            throw new ServiceException("未知错误：" + se.getMessage());
        }

        return result;
    }

    /**
     * 如果有空白行就忽略掉
     *
     * @param row
     * @return 没有找到返回-1 找到就返回下标
     */
    private int checkNullCell(XSSFRow row) {
        for (int i = 0; i < ReSendPrizeBean.EXCEL_ROW_NUM; i++) {
            Cell c = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
            if (c == null) {
                return i;
            }
        }
        return -1;
    }

    public String uploadImportPrizeFile(MultipartFile multipartFile) throws Exception {

        String fileName = String.valueOf(System.currentTimeMillis());
        String fileFullPath = Constant.IMPORT_FILE_PATH + fileName;

        BufferedInputStream inToSave = null;
        BufferedOutputStream out = null;
        try {
            inToSave = new BufferedInputStream(multipartFile.getInputStream());
            inToSave.mark(1024 * 1024 * 1024);

            inToSave.reset();
            File fileDir = new File(Constant.IMPORT_FILE_PATH);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            out = new BufferedOutputStream(new FileOutputStream(new File(fileFullPath)), 16 * 1024);
            byte[] buffer = new byte[16 * 1024];
            int len = 0;
            while ((len = inToSave.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw new ServiceException("文件上传出错！");
        } finally {
            if (null != inToSave) {
                try {
                    inToSave.close();
                } catch (IOException e) {
                    throw e;
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return fileFullPath;
    }


    public int importToDBInTextType(String filePath, String tableName) throws SQLException {
        String nod = "\t";
        String linenod = "\n";
        String sql = "load data local infile '" + (filePath) + "' into table " + tableName + " fields terminated by '" + nod + "' lines terminated by '" + linenod + "' (username,roleid,server,prize);";
        return idao.update(sql);
    }

    public List<ExcelParseExceptInfo> importToDBInXLXSType(String filePath, String tableName) throws Exception {

        OPCPackage pkg = OPCPackage.open(filePath);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = XMLReaderFactory.createXMLReader();
        KefuExcelDataProcesser dataProcesser = new KefuExcelDataProcesser(this, tableName);
        ContentHandler handler = new KefuSheetHandler(sst, dataProcesser);
        parser.setContentHandler(handler);
        InputStream sheet1 = r.getSheet("rId1");
        InputSource sheetSource = new InputSource(sheet1);
        parser.parse(sheetSource);
        return dataProcesser.getEmptyRowList();
    }

    @Deprecated  //目前这个方法先留着，重构不要了
    public int uploadTextForm(MultipartFile multipartFile, String filePath, String tablename) throws Exception {
        String fileName = String.valueOf(System.currentTimeMillis());
        String fileFullPath = filePath + fileName;
        String nod = "\t";
        String linenod = "\n";
        String expectFileType = "text/plain";
        if (!multipartFile.getContentType().equals(expectFileType)) {
            throw new ServiceException("文件类型不对，只接受纯文本文档");
        }
        BufferedInputStream inToSave = null;
        BufferedOutputStream out = null;
        try {
            inToSave = new BufferedInputStream(multipartFile.getInputStream());
            inToSave.mark(1024 * 1024 * 1024);

            inToSave.reset();
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            out = new BufferedOutputStream(new FileOutputStream(new File(fileFullPath)), 16 * 1024);
            byte[] buffer = new byte[16 * 1024];
            int len = 0;
            while ((len = inToSave.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        } finally {
            if (null != inToSave) {
                try {
                    inToSave.close();
                } catch (IOException e) {
                    throw e;
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        String sql = "load data local infile '" + (fileFullPath) + "' into table " + tablename + " fields terminated by '" + nod + "' lines terminated by '" + linenod + "' (username,roleid,server,prize);";
        return idao.update(sql);
    }

    /**
     * 自己组装sql，做批量插入
     *
     * @param list
     * @param tablename
     * @return
     */
    boolean saveFromList(List<ReSendPrizeBean> list, String tablename) {

        StringBuilder sql = new StringBuilder("insert into " + tablename + " (username,roleid,server,prize) values ");
        for (int i = 0; i < list.size(); i++) {
            ReSendPrizeBean rp = list.get(i);
            sql.append("('").append(rp.getUsername()).append("',");
            sql.append("").append(rp.getRoleid()).append(",");
            sql.append("").append(rp.getServer()).append(",");
            sql.append("").append(rp.getPrize()).append(")");
            if (i != list.size() - 1) {
                sql.append(",");
            }

        }
        try {
            idao.update(sql.toString());
        } catch (SQLException e) {
            throw new ServiceException("插入数据出错。");
        }
        return true;
    }

    public static void main(String args[]) {

        double ss = 2.0;
        System.out.printf("+" + new Integer(String.valueOf(ss)));
    }

    public CommonList pagination(Search search, Class<ReSendPrizeBean> reSendPrizeBeanClass) {

        return idao.pagination(search, reSendPrizeBeanClass);
    }

    public CommonList paginationLog(Search search, Class<PrizeLogBean> prizeLogBeanClass, String dataSource) {
        Idao dao = DaoManager.getDao(dataSource);
        return dao.pagination(search, prizeLogBeanClass);
    }

    public CommonList paginationOrder(Search search, Class<OrderBean> prizeLogBeanClass) {

        return idao.pagination(search, prizeLogBeanClass);
    }

    public List<Map<String, Object>> gainStatusMap(String tablename, String datasource) {
        Idao dao = DaoManager.getDao(datasource);

        QueryRunner runner = dao.getQueryRunner();
        try {
            return runner.query(" select status, count(status) as a  from  " + tablename + " group by status ", new MapListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("查询 发奖状态sql异常");
        }

    }

    public PrizeTableBean gainPrizeBean(String dataSource, String tbName, int id) throws SQLException {
        Idao dao = DaoManager.getDao(dataSource);
        return dao.queryObject(PrizeTableBean.class, "select * from " + tbName + " where id = ?", id);
    }

    public void reSendAll(String tablename, String dataSource) {

        logger.info("resend table:" + tablename + " datasource: " + dataSource + " begin");
        Idao dao = DaoManager.getDao(dataSource);
        try {
            dao.update("update " + tablename + " set status = 0 , gid  = null where status = 4 ");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("更新表" + tablename + "失败");
        }
        String sql = " select id from " + tablename + " where  status in (3, 8)";
        try {

            SendProperty sendProperty = sendPrizeDao.querySendProperty(tablename, dataSource);

            if (sendProperty.getSend_type().equals(SendProperty.SendType.COUPON_MULTI.value)) {
                logger.warn("multi coupon resend only send 8");
                sql = " select gid from " + tablename + " where  status = 8 ";
            }

            List<Integer> gidList = dao.queryOneColumnList(sql);

            logger.warn("---------------resend 3 and 8 list : " + gidList);
            IPrizeService prizeService = null;

            if (sendProperty.getSend_interface().equals(SendProperty.SendInterface.ABROAD_HK.value)) {
                prizeService = PrizeServiceManager.abroadPrizeService;
            } else {
                prizeService = PrizeServiceManager.prizeService;
            }

            for (Integer id : gidList) {
                PrizeTableBean prizeTableBean = gainPrizeBean(dataSource, tablename, id);
                if (sendProperty.getSend_type() == SendProperty.SendType.COUPON.value ||
                        sendProperty.getSend_type() == SendProperty.SendType.COUPON_MULTI.value) {
                    try {
                        int re = prizeService.resendGoods(prizeTableBean.getGid());
                        logger.warn("resend goods return :" + re);

                        logHDBase(sendProperty, prizeTableBean, re);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (sendProperty.getSend_type() == SendProperty.SendType.PRIZE_ID.value) {
                    try {
                        int re = prizeService.resend(prizeTableBean.getGid());
                        logger.warn("resend prize return :" + re);
                        logHDBase(sendProperty, prizeTableBean, re);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.warn("补发中发现未知send_type : " + sendProperty.getSend_type());
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }

        } catch (Exception e) {
            throw new ServiceException("补发表" + tablename + "gid奖品失败");
        }

    }

    private void logHDBase(SendProperty sendProperty, PrizeTableBean prizeTableBean, Integer status) throws Exception {
        Integer resultStatus = 0;
        if (status == 0) {
            resultStatus = 1;
        }
        SendPrizeBean sendPrizeBean = new SendPrizeBean();
        sendPrizeBean.setServer(prizeTableBean.getServer());
        sendPrizeBean.setUserid(prizeTableBean.getUserid() == null ? 0L : prizeTableBean.getUserid().longValue());
        sendPrizeBean.setUsername(prizeTableBean.getUsername());
        sendPrizeBean.setRoleid(Long.parseLong(prizeTableBean.getRoleid()));
        sendPrizeBean.setGid(prizeTableBean.getGid());
        sendPrizeBean.setPrize(prizeTableBean.getPrize());
        sendPrizeBean.setStatus(prizeTableBean.getStatus());
        operationLogger.log(OperationLog.HandleType.RESEND_PRIZE.value, sendProperty, sendPrizeBean, resultStatus, status);
    }

    public void reSendUnknown(String tablename, String dataSource) {
        logger.info("resend table:" + tablename + " datasource: " + dataSource + " begin");
        Idao dao = DaoManager.getDao(dataSource);
        String sql = " select gid from " + tablename + " where  status = 8";
        try {

            SendProperty sendProperty = sendPrizeDao.querySendProperty(tablename, dataSource);
            List<String> gidList = dao.queryOneColumnList(sql);

            logger.warn("---------------resend 8 list : " + gidList);
            IPrizeService prizeService = null;

            if (sendProperty.getSend_interface().equals(SendProperty.SendInterface.ABROAD_HK.value)) {
                prizeService = PrizeServiceManager.abroadPrizeService;
            } else {
                prizeService = PrizeServiceManager.prizeService;
            }

            for (String gid : gidList) {
                if (sendProperty.getSend_type() == SendProperty.SendType.COUPON.value ||
                        sendProperty.getSend_type() == SendProperty.SendType.COUPON_MULTI.value) {
                    try {
                        int re = prizeService.resendGoods(gid);
                        logger.warn("resend goods return :" + re);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (sendProperty.getSend_type() == SendProperty.SendType.PRIZE_ID.value) {
                    try {
                        int re = prizeService.resend(gid);
                        logger.warn("resend prize return :" + re);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.warn("补发中发现未知send_type : " + sendProperty.getSend_type());
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }

        } catch (Exception e) {
            throw new ServiceException("补发表" + tablename + "gid奖品失败");
        }
    }

    public void addResend(OrderBean orderBean) {
        try {
            orderBean.setCreatetime(DateUtils.getCurrentFormatDateTime());
            idao.insert(orderBean);
        } catch (SQLException e) {
            throw new ServiceException("插入数据异常");
        }
    }

    public void updateOrderBeanById(String tablename, Integer orderid) {
        try {
            idao.update("update " + Constant.TABLE_GAME_RESEND_PRIZE_ORDER + " set tablename = ?,status=2 where id = ? ", tablename, orderid);
        } catch (SQLException e) {
            throw new ServiceException("更新数据异常");
        }


    }


    /**
     * @param orderid
     * @return -1 -2 1成功
     */
    public int completeOrder(Integer orderid) {
        try {
            OrderBean orderBean = idao.queryObject(OrderBean.class, orderid);
            if (orderBean == null) {
                return -1;//orderid有误
            }
            if (StringUtils.isNotEmpty(orderBean.getTablename())) {
                String orderTable = orderBean.getTablename();
                if (gainZero(orderTable) > 0) {
                    return -2;//有0值，不能完成
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String tbnameBak = orderTable + "_" + sdf.format(new Date());
                idao.update("create table  " + tbnameBak + "  select * from  " + orderTable);
                idao.update("insert into  " + Constant.TABLE_GAME_RESEND_PRIZE_FAILED + "(id,server,username,rolename,rid,roleid,prize,status,gid,createdate) select id,server,username,rolename,rid,roleid,prize,status,gid,createdate  from kefu_sendprize_temp where status = 3 and rid in (-1,8) ");
                idao.update("update " + Constant.TABLE_GAME_RESEND_PRIZE_FAILED + " set token = ?", orderTable);
                idao.update("update " + Constant.TABLE_GAME_RESEND_PRIZE_ORDER + " set status=3,baktablename=? where id = ? ", tbnameBak, orderid);
                idao.update("delete from  " + orderTable);
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServiceException("获取bean失败");
        }
        return 0;
    }

    private long gainZero(String orderTable) throws SQLException {
        return idao.queryCount("select count(1) from " + orderTable + " where status in (0,2) ");
    }

    public int delOrder(Integer orderid) throws SQLException {
        return idao.update("delete from " + Constant.TABLE_GAME_RESEND_PRIZE_ORDER + " where id = ? ", orderid);
    }

    public CommonList paginationFailed(Search search, Class<FailedSendPrizeBean> failedSendPrizeBeanClass) {
        return idao.pagination(search, failedSendPrizeBeanClass);
    }
}
