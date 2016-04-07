package org.david.rain.wmeovg.callback.servlet;

import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.david.rain.wmeovg.request.util.SignatureUtil;
import org.david.rain.wmeovg.request.util.WmeovgProperties;
import sun.misc.BASE64Decoder;

/**
 * @ClassName CallbackServlet
 * @Description 客户端回调接口
 * @date 2010-8-6 上午10:45:39
 */
public class CallbackServlet extends HttpServlet {

	// protected static Logger log =
	// LoggerFactory.getLogger(CallbackServlet.class);
	private static final long serialVersionUID = 1L;
	public static ICallbackService callbackService;

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

				System.err.println("虚拟物品兑换回调接口实例化失败：没有继承ICallbackService接口");
				throw new ServletException(
						"虚拟物品兑换回调接口没有继承ICallbackService接口");
			}

			callbackService = (ICallbackService) callbackObject;
			callbackService.init(config);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){

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
		String callback;
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
			if (null != sign
					&& !sign.equals(SignatureUtil.sign(gid.trim() + status + callback.trim() + WmeovgProperties.getPrivateKey().trim()))) {

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
			callbackService.receive(gid, cid, appid, status, count, callback);
		} catch (Exception e) {
			
			System.err.println("虚拟物品兑换回调接口：数据接收处理异常");
			e.printStackTrace();
			resp.setStatus(1004);
			return ;
		}
	}
}
