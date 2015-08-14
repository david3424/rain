package org.david.rain.web.service.webservice;


import org.david.rain.web.service.hdinterface.HdIscoreServiceInterface;
import org.david.rain.web.service.hdinterface.HdongImageService;
import org.david.rain.web.service.hdinterface.wrapper.ActivityServiceWrapper;
import org.david.rain.web.service.hdinterface.wrapper.CombinedServiceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by IDEA.
 * Date: 13-10-31
 * Time: 下午3:21
 * 活动开发平台V3
 */
@Component
public class ServiceManager {



    /**
     * 合服服务器名查询接口
     */
    public CombinedServiceWrapper combinedServiceInterface;



    /**
     * 图片上传接口
     */
    public HdongImageService hdongImageService;


    /**
     *  积分接口
     */

    public HdIscoreServiceInterface hdIscoreServiceInterface;

    /**
     * 活动相关接口
     */
    public ActivityServiceWrapper activityServiceInterface;


    @Autowired(required = false)
    @Qualifier(value = "combinedServiceWrapper")
    public void setCombinedServiceInterface(CombinedServiceWrapper combinedServiceInterface) {
        this.combinedServiceInterface = combinedServiceInterface;
    }


    @Autowired(required = false)
    @Qualifier(value = "hdongImageService")
    public void setHdongImageService(HdongImageService hdongImageService) {
        this.hdongImageService = hdongImageService;
    }

    @Autowired(required = false)
    @Qualifier(value = "hdIscoreServiceInterface")
    public void setHdongImageService(HdIscoreServiceInterface hdIscoreServiceInterface) {
        this.hdIscoreServiceInterface = hdIscoreServiceInterface;
    }

}
