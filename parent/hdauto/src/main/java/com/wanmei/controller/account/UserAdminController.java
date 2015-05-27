package com.wanmei.controller.account;

import com.wanmei.entity.User;
import com.wanmei.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

/**
 * 管理员管理用户的Controller.
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserAdminController {

	private AccountService accountService;

    @Autowired
    public void setAccountService(@Qualifier("AccountService")AccountService accountService) {
        this.accountService = accountService;
    }
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		List<User> users = accountService.getAllUser();
		model.addAttribute("users", users);

		return "account/adminUserList";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) throws Exception {
		model.addAttribute("user", accountService.getUser(id));
		return "account/adminUserForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update( @ModelAttribute("preloadUser") User user, RedirectAttributes redirectAttributes) throws SQLException {
		accountService.updateUser(user);
		redirectAttributes.addFlashAttribute("message", "更新用户" + user.getLogin_name() + "成功");
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) throws Exception {
		User user = accountService.getUser(id);
		accountService.deleteUser(id);
		redirectAttributes.addFlashAttribute("message", "删除用户" + user.getLogin_name() + "成功");
		return "redirect:/admin/user";
	}

	/**
	 * 使用@ModelAttribute, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
	 */
	@ModelAttribute("preloadUser")
	public User getUser(@RequestParam(value = "id", required = false) Integer id) throws Exception {
		if (id != null) {
			return accountService.getUser(id);
		}
		return null;
	}
}
