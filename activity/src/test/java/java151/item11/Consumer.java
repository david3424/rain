package java151.item11;

import org.apache.commons.lang3.SerializationUtils;
import org.david.rain.tools.logback.LoggerUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by xdw9486 on 2017/7/25.
 */

class Consumer {
    public static final String FILE_NAME = "E:/obj.bin";

    public static void main(String[] args) {

//反序列化
        Person p;
        try {
            p = (Person) SerializationUtils.deserialize(new FileInputStream(FILE_NAME));
            LoggerUtil.info("name={}", p.getName());
        } catch (FileNotFoundException e) {
            LoggerUtil.error(String.format("deserialization [%s] exception", FILE_NAME), e);
        }
    }
}
