package org.david.rain.monitor.monitor.persistence;

import org.apache.ibatis.annotations.Param;
import org.david.rain.monitor.monitor.domain.SendPrize;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by david
 * * on 13-12-10.
 */
public interface SendPrizeMapper extends Mapper<SendPrize> {


    List<SendPrize> getAllListPage(@Param("page") EasyPageInfo pageInfo, @Param("sendPrize") SendPrize sendPrize);
    
}
