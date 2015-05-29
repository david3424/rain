package com.david.web.wanmei.util.excel.parser;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-5-29
 * Time: 下午7:21
 * To change this template use File | Settings | File Templates.
 */
public class KefuSheetHandler extends DefaultHandler {
    private SharedStringsTable sst;
    private StringBuilder lastContents;
    private boolean nextIsString;


    DataProcesser processer;

    public KefuSheetHandler(SharedStringsTable sst, DataProcesser processer) {
        this.sst = sst;
        this.processer = processer;
    }

    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        // c => cell
        if (name.equals("c")) {
            // Print the cell reference
            System.out.print(attributes.getValue("r") + " - ");
            // Figure out if the value is an index in the SST
            String cellType = attributes.getValue("t");
            if (cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        } else if (name.equals("v")) {
            processer.doCellBegin();
        } else if (name.equals("row")) {
            processer.doRowBegin();
        } else if (name.equals("sheetView")) {
            processer.doSheetDataBegin();
        }
        // Clear contents cache
        lastContents = new StringBuilder();
    }

    public void endElement(String uri, String localName, String name)
            throws SAXException {
        // Process the last contents as required.
        // Do now, as characters() may be called more than once
        if (nextIsString) {
            int idx = Integer.parseInt(lastContents.toString());
            lastContents = new StringBuilder(new XSSFRichTextString(sst.getEntryAt(idx)).toString());
            nextIsString = false;
        }

        // v => contents of a cell
        // Output after we've seen the string contents
        if (name.equals("v")) {
            processer.doCellEnd(lastContents.toString());
        }
        if (name.equals("row")) {
            processer.doRowEnd();
        }
        if (name.equals("sheetData")) {
            processer.doSheetDataEnd();
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        lastContents.append(new String(ch, start, length));
    }
}
