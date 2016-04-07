package org.david.rain.wmproxy.module.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @ClassName FileUploadUtil
 * @date 2010-9-6 下午04:45:44
 */
public class FileUploadUtil {
	public static final int BUFFER_SIZE = 1024;
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	 // 上传任意文件
    public static void upload(File source, String destDir, String destFileName) throws Exception{
        InputStream in = null;
        OutputStream out = null;

        try {
        	if(!destDir.endsWith(FILE_SEPARATOR))
        		destDir += FILE_SEPARATOR;
            File rootDir = new File(destDir);
            if (!rootDir.exists()) {
                rootDir.mkdirs();
            }
            in = new BufferedInputStream(new FileInputStream(source));
            out = new BufferedOutputStream(new FileOutputStream(destDir + destFileName), BUFFER_SIZE);

            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception ex) {
            throw ex;

        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw e;
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }
    
    public static String getFileExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if(pos < 0)
            return "";
        return fileName.substring(pos);
    }
}
