package com.david.web.wanmei.controller.account;

import com.david.web.wanmei.entity.User;
import com.david.web.wanmei.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

/**
 * 用户注册的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	private AccountService accountService;

    @Autowired
    public void setAccountService(@Qualifier("AccountService")AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.GET)
	public String registerForm() {
		return "account/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register( User user, RedirectAttributes redirectAttributes) throws SQLException {
		accountService.registerUser(user);
		redirectAttributes.addFlashAttribute("username", user.getLogin_name());
		return "redirect:/login";
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName",method = RequestMethod.GET)
	@ResponseBody
	public String checkLoginName(@RequestParam("login_name") String loginName) throws Exception {
		if (accountService.findUserByLoginName(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}
}
