package org.david.rain.web.controller.test.hdinterface;

import org.david.rain.web.service.webservice.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Created by IDEA.
 * Date: 13-11-13
 * Time: 下午3:58
 */



@Controller
public class HdController {


    @Autowired
    private ServiceManager serviceManager;

    @RequestMapping(value = "combinedservicetest")
    public String combinedServiceTest(Model model) throws IOException {
        model.addAttribute("combinedServiceInterface", serviceManager.combinedServiceInterface);
        return "test/hdinterface/combinedservice";
    }

    @RequestMapping(value = "hdimageservicetest")
    public String hdImageServiceTest(Model model) throws IOException {
        model.addAttribute("hdongImageService", serviceManager.hdongImageService);
        return "test/hdinterface/hdongimageservice";
    }



}
