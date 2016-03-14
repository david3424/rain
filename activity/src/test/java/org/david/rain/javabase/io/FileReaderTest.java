package org.david.rain.javabase.io;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by mac on 15/12/21.
 * 文件读取demo*
 */
public class FileReaderTest {


    private static final String FILE_PATH = "/Users/mac/Documents/test11.txt";
    private static final String TO_FILE_PATH = "/Users/mac/Documents/test12.txt";

    /**
     * 以字节为单位读写文件内容
     *
     * @param filePath ：需要读取的文件路径
     */
    public static void readFileByByte(String filePath) {
        File file = new File(filePath);
        // InputStream:此抽象类是表示字节输入流的所有类的超类。
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // FileInputStream:从文件系统中的某个文件中获得输入字节。
            ins = new FileInputStream(file);
            outs = new FileOutputStream(TO_FILE_PATH);
            int temp;
            // read():从输入流中读取数据的下一个字节。
            while ((temp = ins.read()) != -1) {
                outs.write(temp);
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (ins != null && outs != null) {
                try {
                    outs.close();
                    ins.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    /**
     * 以字符为单位读写文件内容
     *
     * @param filePath
     */
    public static void readFileByCharacter(String filePath) {
        File file = new File(filePath);
        // FileReader:用来读取字符文件的便捷类。
        FileReader reader = null;
        FileWriter writer = null;
        try {
            reader = new FileReader(file);
            writer = new FileWriter(TO_FILE_PATH);
            int temp;
            while ((temp = reader.read()) != -1) {
                writer.write((char) temp);
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            if (reader != null && writer != null) {
                try {
                    reader.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 以行为单位读写文件内容
     *
     * @param filePath
     */
    public static void readFileByLine(String filePath) {
        File file = new File(filePath);
        // BufferedReader:从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
        BufferedReader bufReader = null;
        BufferedWriter bufWriter = null;
        try {
            // FileReader:用来读取字符文件的便捷类。
            bufReader = new BufferedReader(new FileReader(file));
            bufWriter = new BufferedWriter(new FileWriter(TO_FILE_PATH));
            // buf = new BufferedReader(new InputStreamReader(new
            // FileInputStream(file)));
            String temp = null;
            while ((temp = bufReader.readLine()) != null) {
                bufWriter.write(temp + "\n");
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (bufReader != null && bufWriter != null) {
                try {
                    bufReader.close();
                    bufWriter.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    /**
     * 使用Java.nio ByteBuffer字节将一个文件输出至另一文件
     *
     * @param filePath 文件路径
     */
    public static void readFileByBybeBuffer(String filePath) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            // 获取源文件和目标文件的输入输出流  
            in = new FileInputStream(filePath);
            out = new FileOutputStream(TO_FILE_PATH);
            // 获取输入输出通道
            FileChannel fcIn = in.getChannel();
            FileChannel fcOut = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                // clear方法重设缓冲区，使它可以接受读入的数据
                buffer.clear();
                // 从输入通道中将数据读到缓冲区
                int r = fcIn.read(buffer);
                if (r == -1) {
                    break;
                }
                // flip方法让缓冲区可以将新读入的数据写入另一个通道  
                buffer.flip();
                fcOut.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null && out != null) {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }

   /* public static void main(String args[]) {
        long time1 = getTime();
        // readFileByByte(FILE_PATH);// 8734,8281,8000,7781,8047
        // readFileByCharacter(FILE_PATH);// 734, 437, 437, 438, 422
        // readFileByLine(FILE_PATH);// 110, 94,  94,  110, 93
        readFileByBybeBuffer(FILE_PATH);// 125, 78,  62,  78, 62
        long time2 = getTime();
        System.out.println(time2 - time1);
    }*/

    @Test
    public void testResult() throws Exception {
        long time1 = getTime();
//         readFileByByte(FILE_PATH);// 8734,8281,8000,7781,8047
//         readFileByCharacter(FILE_PATH);// 734, 437, 437, 438, 422
//        readFileByLine(FILE_PATH);// 110, 94,  94,  110, 93
//        long time2 = getTime();
//        System.out.println("testLine:" + (time2 - time1));
        readFileByBybeBuffer(FILE_PATH);// 125, 78,  62,  78, 62
        long time3 = getTime();
        System.out.println("testBybe:" + (time3 - time1));

    }
}
