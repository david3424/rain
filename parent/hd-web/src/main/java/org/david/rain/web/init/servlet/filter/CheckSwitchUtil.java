package org.david.rain.web.init.servlet.filter;

import org.david.rain.common.repository.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Date: 14-2-18
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CheckSwitchUtil {
    @Autowired
    @Qualifier("daoImp")
    private Idao dao;

    public static final String HD_TABLE_SWITCH = "hd_event_switch";

    @Cacheable(value = "cache10sec", key = "'checkSwitch_event'")
    public Integer getSwitchStatus() throws Exception{
        String sql="select status from "+HD_TABLE_SWITCH;
        Integer status = -1;
        try {
            status = dao.queryScalar(sql);
        }catch (Exception e){
            e.printStackTrace();
            status=-1;
        }
        return status;
    }
}
