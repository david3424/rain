package org.david.rain.tooltest;

import org.apache.poi.ss.usermodel.Cell;
import org.david.rain.act.entity.Task;
import org.david.rain.tools.ExcelTool;
import org.david.rain.tools.excel.export.ExcelColumn;
import org.david.rain.tools.excel.export.ExcelHead;
import org.david.rain.tools.excel.export.ExcelHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * todo 类型匹配、时间格式 大数据怎么办
     * @throws Exception
     */
    @Test
    public void testExportXls() throws Exception {
        List<Task> agentSalesDetails = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Task asd = new Task();
            asd.setId(i+1l);
            asd.setTitle("title"+i);
            asd.setDescription("desc"+i);
            agentSalesDetails.add(asd);
        }
        // excel结构
        List<ExcelColumn> excelColumns = new ArrayList<>();
        excelColumns.add(new ExcelColumn(0, "id", "序号"));
        excelColumns.add(new ExcelColumn(1, "title", "标题", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(2, "description", "描述", Cell.CELL_TYPE_STRING));
        // 需要特殊转换的单元
       Map<String, Map> excelHeadConvertMap = new HashMap<>();
       /*  Map<Integer,String> isReceive = new HashMap<>();
        isReceive.put(1, "是");
        isReceive.put(0, "否");
        excelHeadConvertMap.put("isReceive", isReceive);
        Map<Integer,String> orderType = new HashMap<>();
        orderType.put(1, "新订单");
        orderType.put(2, "续订订单");
        excelHeadConvertMap.put("orderType", orderType);*/
        File modelFile = new File("C:\\Users\\david\\Desktop\\role2role.xlsx");
        File outputFile = new File("C:\\Users\\david\\Desktop\\role2role_end.xlsx");
        ExcelHead head = new ExcelHead();
        head.setRowCount(2); // 模板中头部所占行数
        head.setColumns(excelColumns);  // 列的定义
        head.setColumnsConvertMap(excelHeadConvertMap); // 列的转换
        ExcelHelper.getInstanse().exportExcelFile(head, modelFile, outputFile, agentSalesDetails);
    }

      @Test
    public void excelHelperExcelFileConvertToList() throws Exception {
        FileInputStream fis = new FileInputStream("./xls/upload_4df3a05f_136cdc915cf__7ffd_00000000.tmp");
        List<List> dataList = ExcelHelper.getInstanse().excelFileConvertToList(fis);
        for (List list : dataList) {
            for (Object object : list) {
                System.out.print(object);
                System.out.print("\t\t\t");
            }
            System.out.println();
        }
    }
}
