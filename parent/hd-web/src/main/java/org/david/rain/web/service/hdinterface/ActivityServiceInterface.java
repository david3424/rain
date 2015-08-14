package org.david.rain.web.service.hdinterface;

import common.HdUser;
import common.UserBean;

import java.util.List;
import java.util.Vector;

/**
 * 活动相关接口
 */
public interface ActivityServiceInterface {


    /***********************************一般活动接口***********************************/

    /**
     * method getUserInfoByName
     * 获取用户信息。不返回生日、真实姓名，身份证合法则idnumber返回'11',不合法则idnumber返回' ',邮箱合法则email返回'11',不合法则email返回' '。 null.参数错误
     *
     * @param hdUser name : 用户名    userinfo : 请求用户信息
     */
    public UserBean getUserInfoByName(HdUser hdUser);

    /**
     * method verifyRoleExists2
     * 判断角色名是否存在.返回: >0.成功,角色ID -1.其他错误 -2.帐号不存在 -3.角色不熟于该帐号 -4.角色不存在 -10001.参数错误
     *
     * @param hdUser username : 用户名 zoneid : 服务器ID rolename : 角色名 userinfo : 请求用户信息
     */
    public Long verifyRoleExists(HdUser hdUser);

    /**
     * method verifyFactionMaster2
     * 判断用户是否帮主.返回: >0.成功,帮派ID -1.网络通信错误 -2.帐号不存在 -3.角色不存在 -4.角色不是帮主 -5.其他错误 -10001.参数错误
     *
     * @param hdUser username : 用户名  zoneid : 服务器ID rolename : 角色名  loginfo  : 调用者信息
     */
    public Integer verifyFactionMaster2(HdUser hdUser);


    /**
     * method activeUser
     * 激活用户，返回 0.成功; 1.用户不存在; 2.用户已被激活; 3.激活码不存在; -1.其他错误. 20110720 判断的条件是取code前两位和GMServer.conf配置中activeUser中的字段匹配，不根据aid判断
     *
     * @param hdUser username : 用户名 code : 激活码，16位字节 type : 激活类型 userinfo : 请求用户信息
     */
    public int activeUser(HdUser hdUser);

    /**
     * method getUserType
     * 返回用户类型。-1.其他错误.
     * boolean isZhuxianActive(int usertype) { return (0x01 == (usertype & 0x01)); }  // 诛仙内测激活
     * boolean isChibiActive(int usertype) { return (0x02 == (usertype & 0x02)); }   // 赤壁内侧激活
     * boolean isRewuActive(int usertype) { return (0x04 == (usertype & 0x04)); }   // 热舞内侧激活
     * boolean isWulinActive(int usertype) { return (0x08 == (usertype & 0x08)); }   // 武林测试服内侧激活
     * boolean isMatrixProtect(int usertype) { return (0x10 == (usertype & 0xF0)); }  // 绑定密保卡
     * boolean isMobilekeyProtect(int usertype) { return (0x20 == (usertype & 0xF0)); } // 绑定手机
     * boolean isUsb_keyProtect(int usertype) { return (0x30 == (usertype & 0xF0)); }  // 绑定USB KEY
     * boolean isPhoneProtect(int usertype) { return (0x40 == (usertype & 0xF0)); }  // 绑定电话
     * boolean isIPLimit(int usertype) { return (0x10000 == (usertype & 0x10000)); }  // 是否有IP限制状态
     * boolean isAuth(int usertype) { return (0x20000 == (usertype & 0x20000)); }   // 是否GM
     * boolean isForbid(int usertype) { return (0x40000 == (usertype & 0x40000)); }  // 是否被封禁
     * boolean isIndulge(int usertype) { return (0x80000 == (usertype & 0x80000)); }  // 是否注册了防沉迷信息
     * boolean isAntiWallow(int usertype) { return (0x100000 == (usertype & 0x100000)); } // 是否纳入防沉迷
     * boolean isZongheng(int usertype) { return (0x80000000 == (usertype & 0x80000000)); }// 是否纵横中文网独立用户
     *
     * @param hdUser username : 用户名
     */
    public int getUserType(HdUser hdUser);





    /***********************************积分查询接口***********************************/

    /**
     * method queryChargeSumByName
     * 根据帐号查询某时间段内某游戏的充值总和，单位：分
     *
     * @param hdUser 所需参数对象 username  begin end gameid server;
     * @return 查询成功返回该账号某个时间段下积分数;
     *         起始时间格式转换异常 -1L；
     *         起始时间为空 -2L；
     */
    public Long queryChargeSumByName(HdUser hdUser);





    /***********************************推广码绑定接口***********************************/


    /**
     * method addMentorNumber
     * 填写老带新导师号码
     *
     * @param hdUser username gameid server sn：导师号 status:功能号，每个游戏不同
     * @return 0 成功
     *         -1 失败
     *         null参数为空
     */
    public Integer addMentorNumber(HdUser hdUser);







    /**
     *发送奖品 默认返回结果（目前发送非神魔的游戏奖品）
     * 免费发送奖品. 返回:0.成功 1.帐号不存在 2.密码错误 3.服务器不存在 4.奖品不存在 5.积分不够 6.角色名不存在 7.角色不属于该帐号 8.给角色发送奖品时失败 -1.其他错误，很可能是网络通讯错误
     * @return
     */
    public int presentGoods(HdUser hdUser);
    
    /**
     * 根据用户ID获取对应的用户名
     * @return String: 正确的用户名  null: 用户名不存在
     */
    public String getNameById(HdUser hdUser);
}
