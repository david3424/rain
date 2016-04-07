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
 * @Description �ͻ��˻ص��ӿ�
 * @date 2010-8-6 ����10:45:39
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
				System.err.println("������Ʒ�һ�ϵͳ�ص��ӿ�ʵ����ʧ�ܣ���" + callback
						+ "�����ڻ��ܱ�ʵ����");
				throw new ServletException("������Ʒ�һ�ϵͳ�ص��ӿ�ʵ����ʧ�ܣ���" + callback
						+ "�����ڻ��ܱ�ʵ����");
				// log.error("������Ʒ�һ�ϵͳ���ص��ӿ�ʵ��������", gid);
			}
			if (!(callbackObject instanceof ICallbackService)) {

				System.err.println("������Ʒ�һ��ص��ӿ�ʵ����ʧ�ܣ�û�м̳�ICallbackService�ӿ�");
				throw new ServletException(
						"������Ʒ�һ��ص��ӿ�û�м̳�ICallbackService�ӿ�");
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

			// log.error("������Ʒ�һ�ϵͳ����ˮ��[{}]����״̬����", gid);
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

			// log.error("������Ʒ�һ�ϵͳ����ˮ��[{}]�ص������������", gid);
			System.err.println("������Ʒ�һ��ص��ӿڣ��ص������������");
			resp.setStatus(1002);
			return;
		}

		String sign = req.getParameter("sign");
		// ��֤ǩ��
		try {
			if (null != sign
					&& !sign.equals(SignatureUtil.sign(gid.trim() + status + callback.trim() + WmeovgProperties.getPrivateKey().trim()))) {

				// log.info("������Ʒ�һ�ϵͳ����ˮ��[{}]�ͻ��˻ص���Ϣ���Ϸ�", gid);
				System.err.println("������Ʒ�һ��ص��ӿڣ��ͻ��˻ص��������Ϸ�");
				resp.setStatus(1001);
				return;
			}
		} catch (NoSuchAlgorithmException e) {

			System.err.println("������Ʒ�һ��ص��ӿڣ�����ǩ��ʧ��");
			resp.setStatus(1003);
			return;
		}

		try {
			callbackService.receive(gid, cid, appid, status, count, callback);
		} catch (Exception e) {
			
			System.err.println("������Ʒ�һ��ص��ӿڣ����ݽ��մ����쳣");
			e.printStackTrace();
			resp.setStatus(1004);
			return ;
		}
	}
}
