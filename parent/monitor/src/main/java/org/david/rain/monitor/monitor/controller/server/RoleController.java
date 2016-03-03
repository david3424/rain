package org.david.rain.monitor.monitor.controller.server;

import org.david.rain.monitor.monitor.domain.Role;
import org.david.rain.monitor.monitor.service.RoleService;
import org.david.rain.monitor.monitor.service.UserService;
import org.david.rain.monitor.monitor.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */


@Controller
@RequestMapping("/role")
public class RoleController {


    static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;


    @RequestMapping("manage")
    public String typeManagePage() {
        return "rolemanager";
    }


    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public List<Role> list() {
        return roleService.getAllRoles();
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addRole(Role role) {
        try {
            roleService.saveRole(role);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "添加失败");
        }
        return JsonUtil.commonResponse(true, "添加成功");
    }


    @RequestMapping(value = "update", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectRoleUsers(Long id) {

        Role role = roleService.selectRoleUsers(id);

        Map<String, Object> result = new HashMap<>(3);
        result.put("rusers", role.getUserList());
        result.put("allusers", userService.getUserList());
        result.put("success", true);
        return result;

    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateRole(Role role, String users) {

        int re = 0;
        try {
            /*调用两次插入ss_user_role,有么有更好的办法？*/
//            先删除roleid对应的所有users
            roleService.deleteRoleUsers(role.getId());
            if (StringUtils.isNotEmpty(users)) {
                for (String id : users.split(",")) {
                    if (StringUtils.isEmpty(id)) {//roles 为空
                        break;
                    }
                    userService.saveUserRoles(Integer.parseInt(id), role.getId().toString());
                    re = roleService.updateRole(role);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "修改失败,系统异常。");
        }
        if (re >= 0) {
            return JsonUtil.commonResponse(true, "修改成功。");
        } else {
            return JsonUtil.commonResponse(false, "修改失败。");
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteRole(Long id) {

        int re = roleService.deleteRole(id);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "删除成功。");
        } else {
            return JsonUtil.commonResponse(false, "删除失败。");
        }
    }
}
