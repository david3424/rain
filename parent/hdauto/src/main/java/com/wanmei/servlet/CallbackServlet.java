package com.david.web.pppppp.init.servlet;

import javax.servlet.http.HttpServlet;

/**
 * Created by IntelliJ IDEA.
 * User: gameuser
 * Date: 12-12-27
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public class CallbackServlet extends HttpServlet {

    /*// protected static Logger log =
    // LoggerFactory.getLogger(CallbackServlet.class);
    private static final long serialVersionUID = 1L;
    public static ICallbackService callbackService;
    private ApplicationContext ctx;
    private AwardPrizesBean awardPrizesBean;

    @SuppressWarnings("unchecked")
    @Override
    public void init(ServletConfig config) throws ServletException {

        String callback = config.getInitParameter("callback");
        Object callbackObject = null;

        if (null == callbackService) {

            try {
                Class callbackClass = Class.forName(callback);
                callbackObject = callbackClass.newInstance();
            } catch (Exception e) {
                System.err.println("虚拟物品兑换系统回调接口实例化失败：类" + callback
                        + "不存在或不能被实例化");
                throw new ServletException("虚拟物品兑换系统回调接口实例化失败：类" + callback
                        + "不存在或不能被实例化");
                // log.error("虚拟物品兑换系统：回调接口实例化错误", gid);
            }
            if (!(callbackObject instanceof ICallbackService)) {

                System.err.println("虚拟物品兑换回调接口实例化失败：没有继承com.pppppp.wmeovg.callback.servlet.ICallbackService接口");
                throw new ServletException(
                        "虚拟物品兑换回调接口没有继承com.pppppp.wmeovg.callback.servlet.ICallbackService接口");
            }

            callbackService = (ICallbackService) callbackObject;
            callbackService.init(config);
        }
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        awardPrizesBean = (AwardPrizesBean) ctx.getBean("awardPrizesBean");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String gid = req.getParameter("gid");
        String cid = req.getParameter("cid");
        String appid = req.getParameter("appid");

        int status = -1;
        try {
            status = Integer.parseInt(req.getParameter("status"));
        } catch (Exception e) {

            // log.error("虚拟物品兑换系统：流水号[{}]返回状态错误", gid);
            resp.setStatus(1001);
            return;
        }
        int count = Integer.parseInt(req.getParameter("count"));
        String callback = req.getParameter("callback");
        try {
            callback = new String((new BASE64Decoder()).decodeBuffer(req
                    .getParameter("callback")), "UTF-8");
            // log.info("+++++++++============callback ======" + callback);
        } catch (Exception e1) {

            // log.error("虚拟物品兑换系统：流水号[{}]回调参数解码错误", gid);
            System.err.println("虚拟物品兑换回调接口：回调参数解码错误");
            resp.setStatus(1002);
            return;
        }
        String sign = req.getParameter("sign");
        // 验证签名
        try {
            if (null != sign&& !sign.equals(SignatureUtil.sign(gid.trim() + status + callback.trim() + WmeovgProperties.getPrivateKey().trim()))) {

                // log.info("虚拟物品兑换系统：流水号[{}]客户端回调消息不合法", gid);
                System.err.println("虚拟物品兑换回调接口：客户端回调参数不合法");
                resp.setStatus(1001);
                return;
            }
        } catch (NoSuchAlgorithmException e) {

            System.err.println("虚拟物品兑换回调接口：数据签名失败");
            resp.setStatus(1003);
            return;
        }

        try {
//             awardPrizesBean.updateStatus(11,status);
            receive(gid, cid, appid, status, count, callback);

        } catch (Exception e) {

            System.err.println("虚拟物品兑换回调接口：数据接收处理异常");
            e.printStackTrace();
            resp.setStatus(1004);
            return;
        }
    }


    public void receive(String gid, String cid, String appid, int status, int count, String callback) throws Exception {
        // 此处代码由客户端完成
        System.out.println("-----流水号：" + gid + "------结果：" + status + " -------回调参数：" + callback);
        String[] params = callback.split("&");
        String tbName = params[0];
        if (!tbName.equals("kefu_sendprize_temp")) {//不是大批量补发
            //   System.out.println("---------------------------------不是大批量补发-------------------------------------");
            String note = params[2];
            if (note.equals("ROLEID")) {//新roleid发奖
                System.out.println("---------------------------------NEWCallBack平台roleid发奖-------------------------------------");
                awardPrizesBean.setTableName(tbName);
                //id 有可能不为int 需检查
                int id = Integer.parseInt(params[1]);
                if (status == 0) {
                    // 发送成功
                    System.out.println("---------------------------------NEWCallBack平台roleid---发送成功-------------------------------------");
//                    awardPrizesBean.updateStatus(id, 1);
                    //   System.out.println("表" + tbName + "  发奖成功id==" + id);
                } else {
                    // 发送失败
                    System.out.println("---------------------------------NEWCallBack平台roleid---发送失败-------------------------------------");
//                    awardPrizesBean.updateStatus(id, 3);
                }
                PrizeTableBean prizeTableBean = awardPrizesBean.gainPrizeBean(tbName, id);
                System.out.println("-------------prizeTableBean is ----------------" + prizeTableBean);
                awardPrizesBean.addLog(prizeTableBean.getUsername(), prizeTableBean.getServer(), Long.parseLong(prizeTableBean.getRoleid()), prizeTableBean.getPrize(), status, id, 1, gid);
            }
        }
    }*/

}
