package com.wanmei.controller.resendprize;

import com.google.common.collect.Maps;
import com.wanmei.common.CommonList;
import com.wanmei.common.Constant;
import com.wanmei.common.search.Search;
import com.wanmei.entity.*;
import com.wanmei.service.ServiceException;
import com.wanmei.service.resendprize.ResendPrizeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * <p/>
 * List page     : GET /resend/
 * Create page   : GET /resend/create
 * Create action : POST /resend/create
 * check logsList: get /resend/logs
 */
@Controller
@RequestMapping(value = "/resend")
public class ResendPrizeController {

    private static final int PAGE_SIZE = 50;
    private static Logger logger = LoggerFactory.getLogger(ResendPrizeController.class);
    private static Map<String, String> sortTypes = Maps.newLinkedHashMap();

    static {
        sortTypes.put("username", "账号");
        sortTypes.put("server", "服务器");
    }

    @Qualifier("resendPrizeService")
    @Autowired
    private ResendPrizeService resendPrizeService;


    /**
     * 补发导入的数据列表
     *
     * @param sortType
     * @param tablename
     * @param pageNumber
     * @param model
     * @param request
     * @return
     */
    //    @RequestMapping(value = "")
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "sortType", defaultValue = "username") String sortType, @RequestParam(value = "tablename", defaultValue = "kefu_sendprize_temp") String tablename,
                       @RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {
        /* if (StringUtils.isEmpty(tablename) || !tablename.startsWith("kefu_sendprize_temp")) {
            tablename = "kefu_sendprize_temp";//设置默认表
        }*/
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Search search = new Search();
        search.setPageNo(pageNumber);
        search.setPageSize(PAGE_SIZE);
        search.addOrder(sortType, Search.SEARCH_DESC);
        Iterator it = searchParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            search.addWhere(Search.SEARCH_AND, e.getKey() + " like '%" + e.getValue() + "%' ");
        }
        search.addSelectSql("SELECT username,roleid,server,prize ,status,rid from " + tablename + "");
        search.addSelectCountSql("SELECT count(*) from  " + tablename);
        CommonList results = resendPrizeService.pagination(search, ReSendPrizeBean.class);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        model.addAttribute("searchParams", "tablename=" + tablename);
        model.addAttribute("list", results);
        return "resendprize/resendList";
    }

    /**
     * 所有失败的数据列表
     *
     * @param sortType
     * @param pageNumber
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "failed", method = RequestMethod.GET)
    public String failed(@RequestParam(value = "sortType", defaultValue = "username") String sortType,
                         @RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Search search = new Search();
        search.setPageNo(pageNumber);
        search.setPageSize(PAGE_SIZE);
        search.addOrder(sortType, Search.SEARCH_DESC);
        Iterator it = searchParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            search.addWhere(Search.SEARCH_AND, e.getKey() + " like '%" + e.getValue() + "%' ");
        }
        search.addSelectSql("SELECT username,roleid,server,prize ,status,token,rid from " + Constant.TABLE_GAME_RESEND_PRIZE_FAILED);
        search.addSelectCountSql("SELECT count(*) from  " + Constant.TABLE_GAME_RESEND_PRIZE_FAILED);
        CommonList results = resendPrizeService.paginationFailed(search, FailedSendPrizeBean.class);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        model.addAttribute("list", results);
        return "resendprize/failedList";
    }


    @RequestMapping(value = "logs")
    public String logs(@RequestParam(value = "sortType", defaultValue = "id") String sortType,
                       @RequestParam(value = "tablename", defaultValue = "kefu_sendprize_temp") String tablename,
                       @RequestParam(value = "status", defaultValue = "-1") Integer flag,
                       @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                       String datasource,
                       Model model, ServletRequest request) {


        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Search search = new Search();
        search.setPageNo(pageNumber);
        search.setPageSize(PAGE_SIZE);
        search.addOrder(sortType, Search.SEARCH_DESC);
        CommonList results = new CommonList();
        Iterator it = searchParams.entrySet().iterator();
        if (!it.hasNext()) {

        } else {
            while (it.hasNext()) {
                Map.Entry e = (Map.Entry) it.next();
                if (e.getValue() != null && !((String) e.getValue()).isEmpty()) {
                    search.addWhere(Search.SEARCH_AND, e.getKey() + " = '" + e.getValue() + "' ");
                }
            }
            String sql_l = null;
            if (flag != null && flag >= 0) {
                switch (flag) {
                    case 0:
                        sql_l = "flag=0 and status = 0 ";
                        break;
                    case 1:
                        sql_l = "flag=0 and status <> 0 ";
                        break;
                    case 2:
                        sql_l = "flag=1 and status = 0 ";
                        break;
                    case 3:
                        sql_l = "flag=1 and status <>0 ";
                        break;
                    default:
                        sql_l = "";
                        break;
                }

                search.addWhere(Search.SEARCH_AND, sql_l);
            }
            search.addWhere(Search.SEARCH_AND, " tablename = '" + tablename + "'");
            search.addSelectSql("SELECT flag,createdate,username,roleid,server,prize ,pid,gid,status from " + Constant.TABLE_GAME_PRIZE_LOGS_NEW);
            search.addSelectCountSql("SELECT count(*) from  " + Constant.TABLE_GAME_PRIZE_LOGS_NEW);
            results = resendPrizeService.paginationLog(search, PrizeLogBean.class, datasource);
        }
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        flag = flag == null ? -1 : flag;
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_") + "&tablename="
                + tablename + "&status=" + flag + "&datasource=" + datasource);
        model.addAttribute("list", results);
        return "resendprize/logList";
    }


    /**
     * 获取发奖情况。
     */
    @RequestMapping(value = "report", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> report(@RequestParam("tablename") String tablename, String datasource) throws Exception {

        List list = resendPrizeService.gainStatusMap(tablename, datasource);
        /*for(Object o:list){
            Map map = (Map) o;
        }*/
        return list;
    }

    /**
     * Ajax请求校验TaskName是否唯一。
     */
    @RequestMapping(value = "resendall", method = RequestMethod.GET)
    @ResponseBody
    public int resendAll(@RequestParam("tablename") String tablename, @RequestParam(value = "datasource", defaultValue = "huodong161") String datasource) {
        try {
            resendPrizeService.reSendAll(tablename, datasource);
        } catch (ServiceException ser) {
            return 0;
        }
        return 1;
    }

    @RequestMapping(value = "resendUnknown", method = RequestMethod.GET)
    @ResponseBody
    public int resendUnknown(@RequestParam("tablename") String tablename, @RequestParam(value = "datasource", defaultValue = "huodong161") String datasource) {
        try {
            resendPrizeService.reSendUnknown(tablename, datasource);
        } catch (ServiceException ser) {
            return 0;
        }
        return 1;
    }



    @RequestMapping(value = "upload", method = RequestMethod.GET)
    public String createForm() {
        return "resendprize/upload";
    }

    /**
     * 可以将request 去掉，直接用@RequestParam转化
     * 文件名重复会被覆盖
     *
     * @param request
     * @param modal
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
//    public String upload(HttpServletRequest request, ModelMap modal) {
    public String upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request, ModelMap modal) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
//        /files2013/01/05/17
        String docPathDir = "/files" + simpleDateFormat.format(new Date());
//        E:\svn\hdauto\trunk\resources\files2013\01\05\17
        String docRealPathDir = request.getSession().getServletContext().getRealPath(docPathDir);
        File docSaveFile = new File(docRealPathDir);
        if (!docSaveFile.exists()) {
            docSaveFile.mkdirs();
        }
//        org.springframework.web.multipart.commons.CommonsMultipartFile@157f347
//        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
//        文件后缀
//        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));、
//        文件名
        String docImageName = multipartFile.getOriginalFilename();
//        E:\svn\hdauto\trunk\resources\files2013\01\05\17\2.jpg
        String fileName = docRealPathDir + File.separator + docImageName;
        File file = new File(fileName);
        modal.put("fileName", fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IllegalStateException e) {
            logger.error("上传失败");
            modal.put("message", "上传失败");
            return "resendprize/upload";
        } catch (IOException e) {
            logger.error("上传失败");
            modal.put("message", "上传失败");
            return "resendprize/upload";
        }
        modal.put("message", "上传成功");
        return "resendprize/upload";
    }

    @RequestMapping(value = "uploadexcel", method = RequestMethod.POST)
    @ResponseBody
    public Map uploadExcel(@RequestParam("file") MultipartFile multipartFile,
                           @RequestParam("tablename") String tablename,
                           @RequestParam(value = "orderid", defaultValue = "1") Integer orderid, ModelMap modal,
                           MultipartHttpServletRequest request) {
        modal.put("tablename", tablename);
        Map map = new HashMap();
        if (StringUtils.isEmpty(tablename) || !tablename.startsWith("kefu_sendprize_temp")) {

            map.put("success", false);
            map.put("message", "表名错误。");
            return map;
        }

        String fileType = multipartFile.getContentType();


        if (!Constant.SUPPORT_IMPORT_FILE_TYPE.contains(fileType)) {
            map.put("success", false);
            map.put("message", "不支持的文件类型，目前支持 txt, excel(2007)。");
            return map;
        }

        try {

            String path = resendPrizeService.uploadImportPrizeFile(multipartFile);

            if (fileType.equals("text/plain")) {
                resendPrizeService.importToDBInTextType(path, tablename);
                map.put("success", true);
                map.put("message", "上传成功");
                resendPrizeService.updateOrderBeanById(tablename, orderid);
            } else if (fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                List<ExcelParseExceptInfo> resultInfo = resendPrizeService.importToDBInXLXSType(path, tablename);
                map.put("success", true);
                map.put("message", formatExcelUploadInfo(resultInfo));
                resendPrizeService.updateOrderBeanById(tablename, orderid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", e.getMessage());
        } finally {
            return map;
        }

    }

    private String formatExcelUploadInfo(List<ExcelParseExceptInfo> resultInfo) {
        StringBuilder stringBuilder = new StringBuilder("第：");
        if (resultInfo.size() == 0) {
            return "上传成功！";
        } else {
            for (ExcelParseExceptInfo info : resultInfo) {
                stringBuilder.append(info.getRow()).append(",");
            }
            stringBuilder.append("行存在空单元，被忽略。其他导入成功！");
            return stringBuilder.toString();
        }
    }


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addForm() {
        return "resendprize/resendAdd";
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(OrderBean orderBean, RedirectAttributes redirectAttributes, Model model) {

        try {
            resendPrizeService.addResend(orderBean);
            redirectAttributes.addFlashAttribute("message", "添加成功");
        } catch (ServiceException se) {
            redirectAttributes.addFlashAttribute("message", "添加失败");
        }
        return "redirect:orderlist";
    }


    @RequestMapping(value = "orderlist")
    public String orderlist(@RequestParam(value = "sortType", defaultValue = "createtime") String sortType,
                            @RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model, ServletRequest request) {

        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        Search search = new Search();
        search.setPageNo(pageNumber);
        search.setPageSize(PAGE_SIZE);
        search.addOrder(sortType, Search.SEARCH_DESC);
        Iterator it = searchParams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            search.addWhere(Search.SEARCH_AND, e.getKey() + " like '%" + e.getValue() + "%' ");
        }
        search.addSelectSql("select id, actname,gamename,description,ordertype,totalnum,createtime,status,tablename,baktablename from " + Constant.TABLE_GAME_RESEND_PRIZE_ORDER);
        search.addSelectCountSql("SELECT count(*) from  " + Constant.TABLE_GAME_RESEND_PRIZE_ORDER);
        CommonList results = resendPrizeService.paginationOrder(search, OrderBean.class);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypes", sortTypes);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("list", results);
        return "resendprize/orderList";
    }

    @RequestMapping(value = "complete")
    @ResponseBody
    public int complete(@RequestParam("orderid") Integer orderid) {

        return resendPrizeService.completeOrder(orderid);
    }

    @RequestMapping(value = "del")
    @ResponseBody
    public int del(@RequestParam("orderid") Integer orderid) throws SQLException {

        return resendPrizeService.delOrder(orderid);
    }

}
