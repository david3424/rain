package org.david.rain.act.outservice.webservice;

import javax.jws.WebService;

/**
 * Created by xdw9486 on 2016/12/13.
 * test webservice of spring
 *
 * 1.添加interface接口
 * 2.实现ws方法，商议协议类型与参数
 * 3.web.xml添加servlet
 * 4.添加ws服务端，定义接口路径
 * 5.添加所需jar包
 * 6.添加测试ws客户端，写单元测试
 */


@WebService
public interface WeatherService {
    String getWeather(String city);
}
