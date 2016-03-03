package org.david.rain.web.controller.commet;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class SendMessageAutoUtil {
	public static void sendMessageAuto(String userid, String message,  String showScriptMethod) {
		final String yhId = userid;
		final String autoMessage = message;
		final String autoShowScriptMethod = showScriptMethod;
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession scriptSession) { //迭代所有的scriptSession，判断是否需要推送
				String goodsId = (String) scriptSession.getAttribute("goodsId");
				if (goodsId != null) {
					return yhId.equals(goodsId);
				} else {
					return false;
				}
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			public void run() {
				script.appendCall(autoShowScriptMethod, autoMessage);
				Collection<ScriptSession> sessions = Browser
						.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}


    public static void testSendAll(String message, String showScriptMethod){
        final String autoMessage = message;
        final String autoShowScriptMethod = showScriptMethod;
        Browser.withAllSessions(
                new Runnable() {
                    private ScriptBuffer script = new ScriptBuffer();
                    public void run() {
                        script.appendCall(autoShowScriptMethod, autoMessage);
                        Collection<ScriptSession> sessions = Browser
                                .getTargetSessions();
                        for (ScriptSession scriptSession : sessions) {
                            scriptSession.addScript(script);
                        }
                    }
                });
    }

	public static void sendMessageListAuto(List targetYhidList, Object stuxx,
			String showScriptMethod) {
		final List targetIdList = targetYhidList;
		final Object autoMessage = stuxx;
		final String autoShowScriptMethod = showScriptMethod;

		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession scriptSession) {
				String yhid = (String) scriptSession.getAttribute("yhId");
				if (yhid != null && targetIdList.contains(yhid)) {
					// return yhid.equals(yhId);
//					targetIdList.remove(yhid);
					return true;
				} else {
					return false;
				}
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			public void run() {
				script.appendCall(autoShowScriptMethod, autoMessage);
				Collection<ScriptSession> sessions = Browser
						.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}
