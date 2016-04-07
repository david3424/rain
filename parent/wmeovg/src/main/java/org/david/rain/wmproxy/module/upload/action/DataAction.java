package org.david.rain.wmproxy.module.upload.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.david.rain.wmproxy.common.base.struts2.BaseAction;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.config.entity.Game;
import org.david.rain.wmproxy.module.config.entity.WhiteList;
import org.david.rain.wmproxy.module.config.manager.ClientInfoMng;
import org.david.rain.wmproxy.module.config.manager.GameMng;
import org.david.rain.wmproxy.module.config.manager.WhiteListMng;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.david.rain.wmproxy.module.upload.entity.ResultBean;
import org.david.rain.wmproxy.module.upload.entity.UploadPrizeBean;
import org.david.rain.wmproxy.module.util.ContentUtils;
import org.david.rain.wmproxy.module.util.FileUploadUtil;
import org.david.rain.wmproxy.module.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;


public class DataAction extends BaseAction {

    public String preview() throws Exception {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String loginName = (String) request.getSession().getAttribute(
                User.LOGIN_NAME_KEY);
        String rootPath = request.getSession().getServletContext().getRealPath(
                "/")
                + "upload"
                + FileUploadUtil.FILE_SEPARATOR
                + "whitelist"
                + FileUploadUtil.FILE_SEPARATOR
                + loginName
                + FileUploadUtil.FILE_SEPARATOR;

        String filePath = rootPath + filename;

        try {
            ResultBean beanFromXLSX = ContentUtils.getResultBeanFromXLSX(filePath);
            JSONObject jo = JSONObject.fromObject(beanFromXLSX);
            jo.put("success", true);
            out.write(jo.toString());
            return null;
        } catch (Exception e) {

            out.write("{success:false,msg:\"" + HtmlUtils.htmlEscape(e.getMessage()) + "\"}");
            return null;
        }

    }

    public String check() throws Exception {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String loginName = (String) request.getSession().getAttribute(
                User.LOGIN_NAME_KEY);
        String rootPath = request.getSession().getServletContext().getRealPath(
                "/")
                + "upload"
                + FileUploadUtil.FILE_SEPARATOR
                + "whitelist"
                + FileUploadUtil.FILE_SEPARATOR
                + loginName
                + FileUploadUtil.FILE_SEPARATOR;

        String filePath = rootPath + filename;

        ResultBean beanFromXLSX = ContentUtils.getResultBeanFromXLSX(filePath);

        String clinetInfoId = request.getParameter("entity.clientInfo.id");
        ClientInfo clientInfo = clientInfoMng.findById(Integer.valueOf(clinetInfoId));
        String gameId = request.getParameter("entity.game.id");
        Game game = gameMng.findById(Integer.valueOf(gameId));

        if (clientInfo == null || game == null || entity == null) {

            out.write("{success:false, msg:'必填默认参数有误，请重试。'}");
            return null;
        }
        entity.setGame(game);
        entity.setClientInfo(clientInfo);

        User user = userMng.getByLoginName(loginName);
        entity.setUser(user);

        if (beanFromXLSX.getAppid() > 0 && StringUtils.isEmpty(entity.getAppid())) {

            out.write("{success:false, msg:'应用标识不能为空。'}");
            return null;
        }
        Integer count = entity.getCount();
        if (beanFromXLSX.getZeroCount() > 0 && (count == null || count <= 0)) {

            out.write("{success:false, msg:'发送总量必须大于0。'}");
            return null;
        }

        Set<UploadPrizeBean> newUploadPrizeBeans = new TreeSet<UploadPrizeBean>();

        int repeatCount = 0;
        int successCount = 0;

        int clientid = Integer.parseInt(
                request.getParameter("entity.clientInfo.id"));
        for (UploadPrizeBean bean : beanFromXLSX.getBeans()) {

            if (StringUtils.isEmpty(bean.getAppid()))
                bean.setAppid(entity.getAppid());
            if (bean.getCount() == null || bean.getCount() <= 0)
                bean.setCount(count);

            int whiteListId = 0;
            try {
                whiteListId = entityMng.getWhiteListIdByPrizeLog(clientid,
                        bean.getAppid(), bean.getPrizeid());
            } catch (Exception e) {
                e.printStackTrace();
                repeatCount++;
                continue;
            }

            if (whiteListId != 0) {

                repeatCount++;
                continue;
            }

            newUploadPrizeBeans.add(bean);
            successCount++;
        }

        beanFromXLSX.setBeans(newUploadPrizeBeans);
        JSONObject jo = JSONObject.fromObject(beanFromXLSX);

        jo.put("success", true);
        jo.put("repeatCount", repeatCount);
        jo.put("successCount", successCount);
        out.write(jo.toString());
        return null;
    }

    public String commit() throws Exception {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String loginName = (String) request.getSession().getAttribute(
                User.LOGIN_NAME_KEY);
        String rootPath = request.getSession().getServletContext().getRealPath(
                "/")
                + "upload"
                + FileUploadUtil.FILE_SEPARATOR
                + "whitelist"
                + FileUploadUtil.FILE_SEPARATOR
                + loginName
                + FileUploadUtil.FILE_SEPARATOR;

        String filePath = rootPath + filename;

        ResultBean beanFromXLSX = ContentUtils.getResultBeanFromXLSX(filePath);

        String clinetInfoId = request.getParameter("entity.clientInfo.id");
        ClientInfo clientInfo = clientInfoMng.findById(Integer.valueOf(clinetInfoId));
        String gameId = request.getParameter("entity.game.id");
        Game game = gameMng.findById(Integer.valueOf(gameId));

        if (clientInfo == null || game == null || entity == null) {

            out.write("{success:false, msg:'必填默认参数有误，请重试。'}");
            return null;
        }
        //entity.setGame(game);
        //entity.setClientInfo(clientInfo);

        User user = userMng.getByLoginName(loginName);
        //entity.setUser(user);

        if (beanFromXLSX.getAppid() > 0 && StringUtils.isEmpty(entity.getAppid())) {

            out.write("{success:false, msg:'应用标识不能为空。'}");
            return null;
        }
        Integer count = entity.getCount();
        if (beanFromXLSX.getZeroCount() > 0 && (count == null || count <= 0)) {

            out.write("{success:false, msg:'发送总量必须大于0。'}");
            return null;
        }

        int updateCount = 0;
        int successCount = 0;
        int failCount = 0;

        int clientid = Integer.parseInt(
                request.getParameter("entity.clientInfo.id"));
        for (UploadPrizeBean bean : beanFromXLSX.getBeans()) {

            if (StringUtils.isEmpty(bean.getAppid()))
                bean.setAppid(entity.getAppid());
            if (bean.getCount() == null || bean.getCount() <= 0)
                bean.setCount(count);

            WhiteList whiteList = new WhiteList();

            whiteList.setAppid(bean.getAppid().trim());
            whiteList.setPrizeid(bean.getPrizeid());
            whiteList.setPrizename(bean.getPrizename());
            //fhz    增加邮件正文列表 20141030
            whiteList.setTitle(bean.getTitle());
            whiteList.setText(bean.getText());
            whiteList.setGame(game);
            whiteList.setClientInfo(clientInfo);
            whiteList.setCount(bean.getCount());
            whiteList.setSendCount(0);
            whiteList.setFailCount(0);
            whiteList.setCreatetime(new Date());
            whiteList.setStatus((byte) -1); // 默认拒绝
            whiteList.setUser(user);

            int whiteListId = 0;
            try {
                whiteListId = entityMng.getWhiteListIdByPrizeLog(clientid,
                        bean.getAppid(), bean.getPrizeid());
            } catch (Exception e) {
                e.printStackTrace();
                failCount++;
                String operatorContent = "批量添加白名单失败(" + failCount + ")(" + whiteList.toString() + ")";
                LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
                continue;
            }

            if (whiteListId != 0) {
                whiteList = entityMng.findById(whiteListId);
                whiteList.setTitle(bean.getTitle());
                whiteList.setText(bean.getText());
                whiteList.setCount(whiteList.getCount() + bean.getCount());
                try {
                    entityMng.update(whiteList);
                } catch (Exception e) {
                    e.printStackTrace();
                    failCount++;
                    String operatorContent = "批量添加白名单失败(" + failCount + ")(" + whiteList.toString() + ")";
                    LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
                    continue;
                }
                updateCount++;
                String operatorContent = "批量添加白名单更新(" + failCount + ")(" + whiteList.toString() + ")";
                LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
                continue;
            }

            try {
                entityMng.save(whiteList);
            } catch (Exception e) {
                e.printStackTrace();
                failCount++;
                String operatorContent = "批量添加白名单失败(" + failCount + ")(" + whiteList.toString() + ")";
                LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
                continue;
            }

            successCount++;

            String operatorContent = "批量添加白名单成功(" + successCount + ")(" + whiteList.toString() + ")";
            LogUtil.saveToLog(request, userMng, logMng, operatorContent, logger);
        }
        logger.info(loginName + "成功导入物品白名单：" + filePath + " 其中新增" + successCount + "条成功，失败 " + failCount + " 条，更新" + updateCount + " 条");
        out.write("{success:true, repeatCount:" + updateCount + ", failCount:" + failCount + ", successCount:" + successCount + "}");
        return null;
    }

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMng userMng;
    @Autowired
    private GameMng gameMng;
    @Autowired
    private ClientInfoMng clientInfoMng;
    @Autowired
    private LogMng logMng;

    @Autowired
    private WhiteListMng entityMng;

    public String filename;

    private WhiteList entity;


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public WhiteList getEntity() {
        return entity;
    }

    public void setEntity(WhiteList entity) {
        this.entity = entity;
    }


}
