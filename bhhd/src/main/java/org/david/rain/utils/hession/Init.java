package org.david.rain.utils.hession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.david.rain.utils.ConfigUtil;

public class Init {

	private static final Log logger = LogFactory.getLog(Init.class);
	// ssl env
	private static String javaProtocolHandlerPkgs = null;
	private static String javaxNetSslKeyStoreType = null;
	private static String javaxNetSslKeyStore = null;
	private static String javaxNetSslKeyStorePassword = null;

	public static void init() {

		javaProtocolHandlerPkgs = "javax.net.ssl";

		javaxNetSslKeyStoreType = "PKCS12";

		javaxNetSslKeyStore = ConfigUtil.defaultConfigPath() + "/serverXml/"
				+ "webgame.report.sys.wanmei.com.p12";
		
		javaxNetSslKeyStorePassword = "webgame.report.sys.wanmei.com";

		logger.info("init parameter 'java.protocol.handler.pkgs' is : "
				+ javaProtocolHandlerPkgs);
		logger.info("init parameter 'javax.net.ssl.keyStoreType' is : "
				+ javaxNetSslKeyStoreType);
		logger.info("init parameter 'javax.net.ssl.keyStore' is :"
				+ javaxNetSslKeyStore);
		logger.info("init parameter 'javax.net.ssl.keyStorePassword' is : "
				+ javaxNetSslKeyStorePassword);
		{
			// init ssl env
			System.setProperty("java.protocol.handler.pkgs",
					javaProtocolHandlerPkgs);
			System.setProperty("javax.net.ssl.keyStoreType",
					javaxNetSslKeyStoreType);
			System.setProperty("javax.net.ssl.keyStore", javaxNetSslKeyStore);
			System.setProperty("javax.net.ssl.keyStorePassword",
					javaxNetSslKeyStorePassword);

			java.security.Security
					.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

			javax.net.ssl.HostnameVerifier hv = new javax.net.ssl.HostnameVerifier() {
				public boolean verify(String urlHostName,
						javax.net.ssl.SSLSession session) {
					logger.info("Warning:URL Host: " + urlHostName + " vs. "
							+ session.getPeerHost());
					return true;
				}
			};

			javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hv);

			logger.info("init ssl env succeed.");
		}
	}
}
