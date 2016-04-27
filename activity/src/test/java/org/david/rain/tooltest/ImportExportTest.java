package org.david.rain.tooltest;

import org.david.rain.act.entity.Task;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2014/12/3.
 *
 */
public class ImportExportTest {
    public static final String IMPORT_FILE_PATH = ImportExportTest.class.getResource("").getPath() + "upload/";

    @Test
    public void testExport2Txt() throws Exception {

        BufferedOutputStream out = null;
        String fileName = String.valueOf(System.currentTimeMillis());
        String fileFullPath = IMPORT_FILE_PATH + fileName;
        System.out.println("outpath :" + fileFullPath);
        String tab = "\t";
        String enter = "\r\n";
        Task magazine;
        StringBuilder write = new StringBuilder();
        List<Task> list = new ArrayList<>();
        while (list.size() < 100) {
            list.add(new Task("test" + list.size(), "", list.size() + 0l));
        }
        File fileDir = new File(IMPORT_FILE_PATH);
        if (fileDir.exists() || fileDir.mkdir()) {
            try {
                out = new BufferedOutputStream(new FileOutputStream(new File(fileFullPath)), 16 * 1024);
                for (Task task : list) {
                    magazine = task;
                    write.delete(0,write.length());// better than new or setLength(0)
                    write.append("期刊名称：").append(tab).append(magazine.getTitle()).append(enter);
                    out.write(write.toString().getBytes("UTF-8"));
                }
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    assert out != null;
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
