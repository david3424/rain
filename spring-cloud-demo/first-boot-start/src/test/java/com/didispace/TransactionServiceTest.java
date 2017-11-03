package com.didispace;

import org.david.rain.boot.start.Application;
import org.david.rain.boot.start.service.TransactionTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionServiceTest {


    @Autowired
    TransactionTestService transactionTestService;


    @Test
    public void testT1() throws Exception {
        transactionTestService.updateAndSave();
    }
}
