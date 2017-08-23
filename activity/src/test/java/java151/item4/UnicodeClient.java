package java151.item4;

/**
 *utf-8 6个字节 gbk4个字节
 */
public class UnicodeClient {


    public static void main(String[] args) throws Exception {
        String str = "测试";
        //读取字节
        byte[] b = str.getBytes("gbk");
        //重新生成一个新的字符串
        System.out.println(new String(b,"gbk"));
    }

}

