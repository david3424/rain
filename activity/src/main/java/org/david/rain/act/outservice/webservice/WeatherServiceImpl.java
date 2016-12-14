package org.david.rain.act.outservice.webservice;

import javax.jws.WebService;

/**
 * Created by xdw9486 on 2016/12/13.
 * implement of weather api
 */


@WebService(endpointInterface = "org.david.rain.act.outservice.webservice.WeatherService", serviceName = "WeatherService")
public class WeatherServiceImpl implements WeatherService {
    @Override
    public String getWeather(String city) {
        //fake weather data:
        String shanghai = "12C,almost sunny";
        String beijing = "7C,most part fog";
        String guangzhou = "20C,a little rain";
        if (city.equals("shanghai"))
            return shanghai;
        else if (city.equals("beijing"))
            return beijing;
        else if (city.equals("guangzhou"))
            return guangzhou;
        else
            return "no data";
    }
}
