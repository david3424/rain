package org.david.rain.monitor.monitor.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.david.rain.monitor.dbutils.repository.impl.CommonDao;
import org.david.rain.monitor.monitor.domain.DataItem;
import org.david.rain.monitor.monitor.domain.OscillationLog;
import org.david.rain.monitor.monitor.domain.ServerItem;
import org.david.rain.monitor.monitor.service.data.DataItemService;
import org.david.rain.monitor.monitor.service.data.DataOscillationService;
import org.david.rain.monitor.monitor.service.server.ItemService;
import org.david.rain.monitor.monitor.util.DataSourceContext;
import org.david.rain.monitor.monitor.util.JsonUtil;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by czw on 14-3-18.
 */

@Controller
@RequestMapping("/api/")
public class InterfaceController {

    @Autowired
    DataItemService dataItemService;

    @Autowired
    ItemService itemService;

    @Autowired
    DataSourceContext dataSourceContext;

    @Autowired
    DataOscillationService dataOscillationService;

    @RequestMapping("data/list/exception")
    public
    @ResponseBody
    List<ApiDataItem> getExceptionList() {
        List<DataItem> list = dataItemService.getExceptionList();
        List<ApiDataItem> list_new = new ArrayList<>();
        if(list!=null && list.size()>0){
            for(DataItem dataItem : list){
                ApiDataItem apiDataItem =new ApiDataItem();
                apiDataItem.setId(dataItem.getId());
                apiDataItem.setChangeTime(dataItem.getChangeTime());
                apiDataItem.setDataSource(dataItem.getDataSource());
                apiDataItem.setItemName(dataItem.getItemName());
                apiDataItem.setItemNameCh(dataItem.getItemNameCh());
                apiDataItem.setUser(new ApiUser(dataItem.getUser().getChName()));
                list_new.add(apiDataItem);
            }
            return list_new;
        }else{
            return null;
        }
    }

    @RequestMapping("server/list/exception")
    @ResponseBody
    public List<ApiServerItem> getServerItemExceptionList() {
        List<ServerItem> list = itemService.getExceptionList();
        List<ApiServerItem> list_new = new ArrayList<>();
        if(list!=null && list.size()>0){
            for(ServerItem serverItem : list){
                ApiServerItem apiServerItem = new ApiServerItem();
                apiServerItem.setChangeTime(serverItem.getChangeTime());
                apiServerItem.setId(serverItem.getId());
                apiServerItem.setItemNameCh(serverItem.getItemNameCh());
                apiServerItem.setCreator(new ApiUser(serverItem.getCreator().getChName()));
                list_new.add(apiServerItem);
            }
            return list_new;
        }else{
            return  null;
        }
    }

    @RequestMapping("data/list")
    public
    @ResponseBody
    List<DataItem> getDateItemList() {
        return dataItemService.getDateItemList();
    }

    @RequestMapping("data/oscillation/log/exception")
    @ResponseBody
    public List<ApiOscillItemLog> getLatestNErrorLog(Integer n) {
        List<OscillationLog> temp = dataOscillationService.getLatestNErrorLog(n);
        List<ApiOscillItemLog> itemLogList = new ArrayList<>(temp.size());
        for (OscillationLog log : temp) {
            DataItem item = dataItemService.getDataItemById(log.getItemId());
            ApiOscillDateItem apiOscillDateItem = new ApiOscillDateItem();
            apiOscillDateItem.setItemNameCh(item.getItemNameCh());
            apiOscillDateItem.setUser(new ApiUser(item.getUser().getChName()));
            //封装新的bean  字段更少 只要需要的
            ApiOscillItemLog log_bean = new ApiOscillItemLog();
            log_bean.setAttrName(log.getAttrName());log_bean.setId(log.getId());
            log_bean.setItemId(log.getItemId());
            log_bean.setTimes(log.getTimes()); log_bean.setDataItem(apiOscillDateItem);
            itemLogList.add(log_bean);
        }
        return itemLogList;
    }


    /**
     * @param itemId   波动所属Item 的 id
     * @param attrName 波动的检查的属性名
     * @param times    本条记录是第几次检查
     * @param n        一共查多少条历史
     * @param an       本条历史后多少条要显示
     * @return
     */
    @RequestMapping("data/oscillation/log/{itemId}/{attrName}")
    @ResponseBody
    public List<ApiOscillItemLog> getLatestNLogByItemId(@PathVariable Integer itemId, @PathVariable("attrName") String attrName,
                                                        Long times, Integer n, @RequestParam(value = "an", defaultValue = "5") Integer an) {
        List<OscillationLog> temp = dataOscillationService.getLatestNLogByItemId(itemId, attrName, times, n, an);
        DataItem item = dataItemService.getDataItemById(itemId);
        List<ApiOscillItemLog> result = new ArrayList<>(temp.size());
        for (OscillationLog log : temp){
            ApiOscillDateItem dateItem = new ApiOscillDateItem();
            dateItem.setItemNameCh(item.getItemNameCh()); dateItem.setChangeTime(item.getChangeTime());
            dateItem.setUser(new ApiUser(item.getUser().getChName()));
            ApiOscillItemLog log_bean = new ApiOscillItemLog();
            log_bean.setId(log.getId()); log_bean.setTimes(log.getTimes());
            log_bean.setTotal(log.getTotal()); log_bean.setDataItem(dateItem);
            result.add(log_bean);
        }
        return result;
    }


    @RequestMapping("data/item/{itemName}")
    public
    @ResponseBody
    String getDateItemByName(@PathVariable String itemName, String jsonpCallBack) {
        DataItem item = dataItemService.getDateItemByName(itemName);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(item);
            return jsonpCallBack + "(" + json + ")";
        } catch (IOException e) {
            e.printStackTrace();
            return jsonpCallBack + "(-1)";
        }
    }

    @RequestMapping("data/item/{itemName}/exist")
    public
    @ResponseBody
    String isItemExist(@PathVariable String itemName, String jsonpCallBack) {
        DataItem item = dataItemService.getDateItemByName(itemName);
        if (item != null) {
            return jsonpCallBack + "(" + 1 + ")";
        } else {
            return jsonpCallBack + "(" + 0 + ")";
        }
    }

    /**
     * 活动开关接口 相关处理
     * dblink 数据源
     * hdstatus  开关表
     * status 状态值  0 关闭 1 开启  -1关闭活动总开关
     */
    public final String HDSwitch = "wm_huodong_switch";
    public final String AllHDSwitch = "hd_event_switch"; //活动总开关
    public final String KEY = "HDAPP!@#HDAPP!@#"; //活动总开关

    @RequestMapping("do/switch")
    public
    @ResponseBody
    Map<String, Object> checkSwitch(String hdencode, Integer status) {
        String dblink = "", hdstatus = "";
//        String str = "";
//        //数据解密
//        //System.out.println(hdencode+"================hdencode");
//        try {
//            str = AES.decrypt(KEY, hdencode);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //System.out.println(str+"================str");
//        if (StringUtils.isEmpty(str)) {
//            return JsonUtil.commonResponse(false, -1, "网络异常，解密信息失败。");
//        }
        String[] ss = hdencode.split("-");
        if (ss.length != 2) {
            return JsonUtil.commonResponse(false, -1, "信息异常，请检查代码。");
        }
        dblink = ss[0];
        hdstatus = ss[1];
        if (StringUtils.isEmpty(dblink)) {
            return JsonUtil.commonResponse(false, -1, "数据异常，DBLink数据源为空。");
        }


        //数据源连接
        DataSource dataSource = dataSourceContext.getDataSource(dblink);
        Connection conn = null;
        CommonDao dao = new CommonDao(dataSource);//dao数据操作
        String sql = "";
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            //总开关状态查询  status=2
            if (hdstatus.equalsIgnoreCase("ALL") && status == 2) {
                Integer checkStatus = -1;
                checkStatus = dao.queryScalar(conn, "select status from " + AllHDSwitch+" limit 1");
                if (checkStatus == 0) {
                    return JsonUtil.commonResponse(false, 0, dblink+"--总开关关闭中。");
                }
                if (checkStatus == 1) {
                    return JsonUtil.commonResponse(true, 1, dblink+"--总开关开启中。");
                }
            }
            if (hdstatus.equalsIgnoreCase("ALL") && status == 0) {//总开关控制
                Integer checkStatus = -1;
                checkStatus = dao.queryScalar(conn, "select status from " + AllHDSwitch+" limit 1");
                if (checkStatus == 0) {
                    return JsonUtil.commonResponse(false, -1, dblink + "--总开关关闭中，无需再次操作。");
                }
                int rtn = dao.update(conn, "update " + AllHDSwitch + " set status = 0");
                if (rtn > 0) {
                    return JsonUtil.commonResponse(true, 1, dblink + "--总开关关闭成功。");
                } else {
                    return JsonUtil.commonResponse(false, -1, dblink + "--总开关关闭失败！！！");
                }
            }
            if (hdstatus.equalsIgnoreCase("ALL") && status == 1) {//总开关控制
                Integer checkStatus = -1;
                checkStatus = dao.queryScalar(conn, "select status from " + AllHDSwitch+" limit 1");
                if (checkStatus == null || checkStatus == -1) {
                    return JsonUtil.commonResponse(false, -1, "请检查您的活动开关标识是否正确。");
                }
                if (checkStatus == 1) {
                    return JsonUtil.commonResponse(false, -1, dblink + "--总开关开启中，无需再次操作。");
                }
                int rtn = dao.update(conn, "update " + AllHDSwitch + " set status = 1");
                if (rtn > 0) {
                    return JsonUtil.commonResponse(true, 1, dblink + "--总开关开启成功。");
                } else {
                    return JsonUtil.commonResponse(false, -1, dblink + "--总开关开启失败！！！");
                }
            }
            //活动状态查询  status=2
            if (!hdstatus.equalsIgnoreCase("ALL") && status == 2) {
                Integer checkStatus = -1;
                checkStatus = dao.queryScalar(conn, "select open from " + HDSwitch + " where tbname = ?", hdstatus);
                if (checkStatus == null || checkStatus == -1) {
                    return JsonUtil.commonResponse(false, -1, "请检查您的活动开关标识是否正确。");
                }
                if (checkStatus == 0) {
                    return JsonUtil.commonResponse(false, 0, hdstatus + "--开关关闭中。");
                }
                if (checkStatus == 1) {
                    return JsonUtil.commonResponse(true, 1, hdstatus + "--开关开启中。");
                }

            }
            //非 总开关处理
            if (!hdstatus.equalsIgnoreCase("ALL") && status == 0) {
                Integer checkStatus = -1;
                checkStatus = dao.queryScalar(conn, "select open from " + HDSwitch + " where tbname = ?", hdstatus);
                if (checkStatus == null || checkStatus == -1) {
                    return JsonUtil.commonResponse(false, -1, "请检查您的活动开关标识是否正确。");
                }
                if (checkStatus == 0) {
                    //System.out.println(checkStatus+"===============rtn=======api===status关闭");
                    return JsonUtil.commonResponse(false, -1, hdstatus + "--开关关闭中，无需再次操作。");
                }
                System.out.println(checkStatus+"===========checkStatus=====关闭");
                int rtn = dao.update(conn, "update " + HDSwitch + " set open = 0 where tbname =? ", hdstatus);
                if (rtn > 0) {
                    return JsonUtil.commonResponse(true, 1, hdstatus + "--开关关闭成功。");
                } else {
                    //System.out.println(rtn+"===============rtn=======api===caca关闭");
                    return JsonUtil.commonResponse(false, -1, hdstatus + "--开关关闭失败！！！");
                }
            }
            if (!hdstatus.equalsIgnoreCase("ALL") && status == 1) {
                Integer checkStatus = -1;
                checkStatus =dao.queryScalar(conn, "select open from " + HDSwitch + " where tbname = ?", hdstatus);
                if (checkStatus == null || checkStatus == -1) {
                    return JsonUtil.commonResponse(false, -1, "请检查您的活动开关标识是否正确。");
                }
                if (checkStatus == 1) {
                    //System.out.println(checkStatus+"===============rtn=======api===status开启");
                    return JsonUtil.commonResponse(false, -1, hdstatus + "--开关开启中，无需再次操作。");
                }
                System.out.println(checkStatus+"===========checkStatus=====开启");
                int rtn = dao.update(conn, "update " + HDSwitch + " set open = 1 where tbname =? ", hdstatus);
                if (rtn > 0) {
                    return JsonUtil.commonResponse(true, 1, hdstatus + "--开关开启成功。");
                } else {
                    //System.out.println(rtn+"===============rtn=======api===caca开启");
                    return JsonUtil.commonResponse(false, -1, hdstatus + "--开关开启失败！！！");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return JsonUtil.commonResponse(false, -1, "网络异常。");
        }finally {
            try {
                DbUtils.commitAndClose(conn);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return JsonUtil.commonResponse(false, -1, "处理异常，请检查逻辑代码。");
    }
}
