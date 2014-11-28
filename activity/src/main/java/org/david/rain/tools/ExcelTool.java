package org.david.rain.tools;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.david.rain.act.dao.Idao;
import org.david.rain.tools.excel.parser.DataProcesser;
import org.david.rain.tools.excel.parser.KefuSheetHandler;
import org.david.rain.tools.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by david on 2014/11/28.
 *
 */
@Service
public class ExcelTool {

    @Autowired
    Idao idao;



    public int importToDBInTextType(String filePath, String tableName) throws SQLException {
        String nod = "\t";
        String linenod = "\n";
        String sql = "load data local infile '" + (filePath) + "' into table " + tableName + " fields terminated by '" + nod + "' lines terminated by '" + linenod + "' (username,roleid,server,prize);";
        return idao.update(sql);
    }

    public List<ExcelParseExceptInfo> importToDBInXLXSType(String filePath, String tableName) throws Exception {

        OPCPackage pkg = OPCPackage.open(filePath);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = XMLReaderFactory.createXMLReader();
        DataProcesser dataProcesser;
        dataProcesser =  new KefuExcelDataProcesser(this, tableName);
        ContentHandler handler = new KefuSheetHandler(sst, dataProcesser);
        parser.setContentHandler(handler);
        InputStream sheet1 = r.getSheet("rId1");
        InputSource sheetSource = new InputSource(sheet1);
        parser.parse(sheetSource);
        return dataProcesser.getEmptyRowList();

    }


    /**
     * 自己组装sql，做批量插入
     *
     * @param list
     * @param tablename
     * @return
     */
    boolean saveFromList(List<RoleBean> list, String tablename) {
        StringBuilder sql = new StringBuilder("insert into " + tablename + " (account,rolename) values ");
        for (int i = 0; i < list.size(); i++) {
            RoleBean rp = list.get(i);
            sql.append("('").append(rp.getUsername()).append("',");
            sql.append("'").append(rp.getRolename()).append("' )");
            if (i != list.size() - 1) {
                sql.append(",");
            }
        }
        try {
            idao.update(sql.toString());
        } catch (SQLException e) {
            throw new ServiceException("插入数据出错。");
        }
        return true;
    }
}
