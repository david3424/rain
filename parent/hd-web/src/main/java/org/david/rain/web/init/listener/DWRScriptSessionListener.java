package org.david.rain.web.init.listener;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DWRScriptSessionListener implements ScriptSessionListener {

	// 维护一个Map key为session的Id， value为ScriptSession对象
	public static final Map<String, ScriptSession> SCRIPT_SESSION_MAP = new ConcurrentHashMap<String, ScriptSession>();

	/**
	 * ScriptSession创建事件
	 */
	public void sessionCreated(ScriptSessionEvent event) {
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		ScriptSession scriptSession = event.getSession();
        SCRIPT_SESSION_MAP.put(session.getId(), scriptSession); // 添加scriptSession
		System.out.println("session: " + session.getId() + " scriptSession: "
				+ scriptSession.getId() + "is created!");
	}

	/**
	 * ScriptSession销毁事件
	 */
	public void sessionDestroyed(ScriptSessionEvent event) {
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		ScriptSession scriptSession = SCRIPT_SESSION_MAP.remove(session.getId()); // 移除scriptSession
		System.out.println("session: " + session.getId() + " scriptSession: "
				+ scriptSession.getId() + "is destroyed!");
	}

	/**
	 * 获取所有ScriptSession
	 */
	public static Collection<ScriptSession> getScriptSessions() {
		return SCRIPT_SESSION_MAP.values();
	}
}
