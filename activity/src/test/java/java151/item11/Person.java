package java151.item11;

import java.io.Serializable;

/**
 *
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 154400741251028858L;
    private String name;
    /*
    *Caused by: java.io.InvalidClassException:
    * java151.item11.Person; local class incompatible:
    * stream classdesc serialVersionUID = -1459564616834487190, local class serialVersionUID = 154400741251028858
    * */
    private String testName;//测试不指定序列号反序列化失败情况

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}

