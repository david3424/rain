package com.david.web.wanmei.controller.account;

import com.david.web.wanmei.entity.User;
import com.david.web.wanmei.service.account.AccountService;
import com.david.web.wanmei.service.account.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

//import javax.validation.Valid;

/**
 * 用户修改自己资料的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController {

	@Autowired
	private @Qualifier("AccountService")AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String updateForm(Model model) throws Exception{
		Integer id = getCurrentUserId();
		model.addAttribute("user", accountService.getUser(id));
		return "account/profile";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String update(  User user) throws SQLException {
		accountService.updateUser(user);
		updateCurrentUserName(user.getName());
		return "redirect:/";
	}

	@ModelAttribute("preloadUser")
	public User getUser(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		if (id != null) {
			return accountService.getUser(id);
		}
		return null;
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Integer getCurrentUserId() {
		ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

	/**
	 * 更新Shiro中当前用户的用户名.
	 */
	private void updateCurrentUserName(String userName) {
		ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		user.name = userName;
	}
}
