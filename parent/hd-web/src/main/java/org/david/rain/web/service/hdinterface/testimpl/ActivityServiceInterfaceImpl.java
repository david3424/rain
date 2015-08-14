package org.david.rain.web.service.hdinterface.testimpl;

import common.HdUser;
import common.UserBean;
import org.david.rain.web.service.hdinterface.ActivityServiceInterface;


/**
 * 活动相关接口
 */
public class ActivityServiceInterfaceImpl implements ActivityServiceInterface {


    /***********************************一般活动接口***********************************/

    /**
     * method getUserInfoByName
     * 获取用户信息。不返回生日、真实姓名，身份证合法则idnumber返回'11',不合法则idnumber返回' ',邮箱合法则email返回'11',不合法则email返回' '。 null.参数错误
     *
     * @param hdUser name : 用户名    userinfo : 请求用户信息
     */
    public UserBean getUserInfoByName(HdUser hdUser) {
        return new UserBean();
    }

    /**
     * method verifyRoleExists2
     * 判断角色名是否存在.返回: >0.成功,角色ID -1.其他错误 -2.帐号不存在 -3.角色不熟于该帐号 -4.角色不存在 -10001.参数错误
     *
     * @param hdUser username : 用户名 zoneid : 服务器ID rolename : 角色名 userinfo : 请求用户信息
     */
    public Long verifyRoleExists(HdUser hdUser) {

        return (long) Math.abs((hdUser.getUsername().hashCode() << 20) + (hdUser.getRolename().hashCode() << 10) + hdUser.getServer());
    }

    /**
     * method verifyFactionMaster2
     * 判断用户是否帮主.返回: >0.成功,帮派ID -1.网络通信错误 -2.帐号不存在 -3.角色不存在 -4.角色不是帮主 -5.其他错误 -10001.参数错误
     *
     * @param hdUser username : 用户名  zoneid : 服务器ID rolename : 角色名  loginfo  : 调用者信息
     */
    public Integer verifyFactionMaster2(HdUser hdUser) {
        return 1000000;
    }


    /**
     * method activeUser
     * 激活用户，返回 0.成功; 1.用户不存在; 2.用户已被激活; 3.激活码不存在; -1.其他错误. 20110720 判断的条件是取code前两位和GMServer.conf配置中activeUser中的字段匹配，不根据aid判断
     *
     * @param hdUser username : 用户名 code : 激活码，16位字节 type : 激活类型 userinfo : 请求用户信息
     */
    @Override
    public int activeUser(HdUser hdUser) {
        return 0;
    }

    @Override
    public int getUserType(HdUser hdUser) {
        return 0;
    }

    /***********************************积分查询接口***********************************/

    /**
     * method queryChargeSumByName
     * 根据帐号查询某时间段内某游戏的充值总和，单位：分
     *
     * @param hdUser 所需参数对象;
     * @return 查询成功返回该账号某个时间段下积分数;
     * 起始时间格式转换异常 -1L；
     * 起始时间为空 -2L；
     */
    public Long queryChargeSumByName(HdUser hdUser) {
        return 100000L;
    }

    @Override
    public Integer addMentorNumber(HdUser hdUser) {
        return null;
    }


    @Override
    public int presentGoods(HdUser hdUser) {
        return 0;
    }
    
    /**
     * 根据用户ID获取对应的用户名
     * @return String: 正确的用户名  null: 用户名不存在
     */
    @Override
    public String getNameById(HdUser hdUser) {
    	return null;
    }
}
