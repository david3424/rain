package org.david.rain.web.init.listener;

import org.directwebremoting.impl.DefaultScriptSessionManager;

public class DWRScriptSessionManager extends DefaultScriptSessionManager {
	public DWRScriptSessionManager() {
		// ��һ��ScriptSession���������¼��ļ�����
		this.addScriptSessionListener(new DWRScriptSessionListener());
		System.out.println("bind DWRScriptSessionListener");
	}
}
