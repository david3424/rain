package org.david.rain.web.controller.commet;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RemoteProxy(name="directController")
@RequestMapping("msg")
public class MsgController {
	@Autowired
	SendMessageAutoUtil sendMessage;
	
	@RequestMapping("push")
    @ResponseBody
	public Map toPush(int goodsId ) {
        Map map = new HashMap();
        testCount(goodsId);
        map.put("message","successful");
		return map;
	}
	@RequestMapping("find/{id}")
	public String toClient(@PathVariable("id") Integer id, Model model ) {
        model.addAttribute("id",id);
        return "example/commet_test";
	}
	@RemoteMethod
	public void onPageLoad( String yhId) {
		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
        scriptSession.setAttribute("goodsId", yhId); //物品id记录到scriptSession中   每开一个新窗口或者刷新都是一个新的scriptSession对象
//        sendMessage.sendMessageAuto(yhId, yhId, "registerSuccess");
	}
	@RemoteMethod
	public void showMessage() {
        ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
        final String id = scriptSession.getAttribute("goodsId").toString();
        sendMessage.sendMessageAuto(id, "oldValue", "showMessage");
	}



    public void testCount(int goodsId) {
        sendMessage.sendMessageAuto(goodsId + "", "testMSG", "showMessage");
    }
}
