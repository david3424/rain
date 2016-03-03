package org.david.rain.monitor.monitor.controller.server;

import org.david.rain.monitor.monitor.domain.Role;
import org.david.rain.monitor.monitor.domain.User;
import org.david.rain.monitor.monitor.service.UserService;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.david.rain.monitor.monitor.util.JsonUtil;
import org.david.rain.monitor.monitor.util.PaginationJsonObject;
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
 * Created by czw on 14-1-3.
 */


@Controller
@RequestMapping("/user")
public class UserController {


    static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;


    @RequestMapping("index")
    public String goIndex() {
        return "index";
    }


    @RequestMapping("manage")
    public String typeManagePage() {
        return "usermanager";
    }

    @RequestMapping(value = "list/page", method = RequestMethod.POST)
    @ResponseBody
    public PaginationJsonObject<User> pageList(EasyPageInfo pageInfo) {
        List<User> list = userService.getUserPageList(pageInfo);
        return new PaginationJsonObject<>(list, pageInfo);
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public List<User> list() {
        return userService.getUserList();
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addUser(User user) {
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "添加失败");
        }
        return JsonUtil.commonResponse(true, "添加成功");
    }


    @RequestMapping(value = "update", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectRoles(Integer id) {
        Map<String, Object> result = new HashMap<>(3);
        if(null !=id && id>0){
        User user = userService.selectUserRoles(id);
        result.put("uroles",user.getRoleList());
        }
        result.put("allroles",userService.getAllRoles());
        result.put("success",true);
        return result;

    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateUser(User user) {

        int re = 0;
        try {
            Role r = null;
            StringBuilder sb = new StringBuilder();
            /*调用两次插入ss_user_role,有么有更好的办法？*/
//            先删除userid对应的所有roles
            userService.deleteUserRoles(user.getId());
            if(StringUtils.isNotEmpty(user.getRoles())){
            for(String id:user.getRoles().split(",")){
                if(StringUtils.isEmpty(id)){//roles 为空
                    break;
                }
                userService.saveUserRoles(user.getId(),id);
                r = userService.getRoleById(Integer.parseInt(id));
                sb.append(r.getName()).append(",");
            }
            user.setRoles(StringUtils.stripEnd(sb.toString(), ","));
            }else{
                user.setRoles("");
            }
            re = userService.updateUser(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return JsonUtil.commonResponse(false, "修改失败,系统异常。");
        }
        if (re > 0) {
            return JsonUtil.commonResponse(true, "修改成功。");
        } else {
            return JsonUtil.commonResponse(false, "修改失败。");
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteUser(Integer id) {

        int re = userService.deleteUser(id);
        if (re > 0) {
            return JsonUtil.commonResponse(true, "删除成功。");
        } else {
            return JsonUtil.commonResponse(false, "删除失败。");
        }
    }
}
