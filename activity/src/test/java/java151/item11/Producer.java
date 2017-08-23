package java151.item11;

import org.apache.commons.lang3.SerializationUtils;

import java.io.FileOutputStream;

/**
 * Created by xdw9486 on 2017/7/25.
 */
public class Producer {
    public static final String FILE_NAME = "E:/obj.bin";

    public static void main(String[] args) throws Exception {
        Person person = new Person();
        person.setName("混世魔王");

        //序列化，保存到磁盘上
        new FileOutputStream(FILE_NAME).write(SerializationUtils.serialize(person));


    }
}
