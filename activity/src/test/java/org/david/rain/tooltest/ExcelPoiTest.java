package org.david.rain.tooltest;

import org.david.rain.tools.ExcelTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by david on 2014/11/28.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration("classpath:spring/applicationContext.xml")
@Transactional
public class ExcelPoiTest {

       @Autowired
    ExcelTool excelTool;


    @Test
    public void testExcel() throws Exception {
        excelTool.importToDBInXLXSType("C:\\Users\\david\\Desktop\\role2role.xlsx","ss_role2role");
    }


}
