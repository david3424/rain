package org.david.rain.monitor.hdinterface;

/**
 * Created by czw on 14-1-7.
 */
public interface ShortMessageInterface {

    /**
     * 短信发送接口
     *
     * @param huodongId 活动id，一般传活动名
     * @param phone     手机号码
     * @param content   短信内容
     * @return 成功or失败
     */
    public boolean sendMessageWithType(String huodongId, String phone, String content);
}
