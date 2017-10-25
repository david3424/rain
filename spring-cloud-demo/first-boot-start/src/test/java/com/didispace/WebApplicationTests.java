package com.didispace;

import org.david.rain.cloud.start.Application;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = MockServletContext.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class WebApplicationTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
//        mvc = MockMvcBuilders.standaloneSetup(new TaskController()).build();
        mvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种
    }

    @Test
    public void getHello() throws Exception {

        // 测试UserController
        RequestBuilder request = get("/tasks/");
        // 1、get查一下user列表，应该为空

        mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(Matchers.containsString("32")));

    }

}
