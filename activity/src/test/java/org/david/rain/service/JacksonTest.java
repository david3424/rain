package org.david.rain.service;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.david.rain.entity.AccountBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 2014/8/21.
 * jackson test
 */
public class JacksonTest {

    private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;
    private AccountBean bean = null;

    @Before
    public void init() {
        bean = new AccountBean();
        bean.setAddress("china-Guangzhou");
        bean.setEmail("hoojo_@126.com");
        bean.setId(1);
        bean.setName("hoojo");
        objectMapper = new ObjectMapper();
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void destory() {
        try {
            if (jsonGenerator != null) {
                jsonGenerator.flush();
            }
            assert jsonGenerator != null;
            if (!jsonGenerator.isClosed()) {
                jsonGenerator.close();
            }
            jsonGenerator = null;
            objectMapper = null;
            bean = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将java对象转换成json字符串
     */
    @Test
    public void writeEntityJSON() {

        try {
            System.out.println("jsonGenerator");
            //writeObject可以转换java对象，eg:JavaBean/Map/List/Array等
            jsonGenerator.writeObject(bean);
            System.out.println();

            System.out.println("ObjectMapper");
            //writeValue具有和writeObject相同的功能
            objectMapper.writeValue(System.out, bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *将map转换成json字符串
     */
    @Test
    public void writeMapJSON() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("name", bean.getName());
            map.put("account", bean);
            bean = new AccountBean();
            bean.setAddress("china-Beijin");
            bean.setEmail("hoojo@qq.com");
            map.put("account2", bean);
            System.out.println("jsonGenerator");
            jsonGenerator.writeObject(map);
            System.out.println("objectMapper");
            objectMapper.writeValue(System.out, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将list集合转换成json字符串
     */
    @Test
    public void writeListJSON() {
        try {
            List<AccountBean> list = new ArrayList<AccountBean>();
            list.add(bean);

            bean = new AccountBean();
            bean.setId(2);
            bean.setAddress("address2");
            bean.setEmail("email2");
            bean.setName("haha2");
            list.add(bean);

            System.out.println("jsonGenerator");
            //list转换成JSON字符串
            jsonGenerator.writeObject(list);
            System.out.println();
            System.out.println("ObjectMapper");
            //用objectMapper直接返回list转换成的JSON字符串
            System.out.println("1###" + objectMapper.writeValueAsString(list));
            System.out.print("2###");
            //objectMapper list转换成JSON字符串
            objectMapper.writeValue(System.out, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将json串转为Map
     */
    @Test
    public void readAsMap(){

 /*       String json = "{\"gender\" : \"MALE\",\n" +
                "  \"verified\" : false,\n" +
                "  \"age\" : \"aa\",\n" +
                "  \"userImage\" : \"Rm9vYmFyIQ==\"}";*/
        String json = "{\"jsonrpc\":\"2.0\",\"id\":854800568,\"error\":null,\"result\":{\"status\":\"queued\",\"request_type\":\"GameSendMailRequest\",\"request_id\":4548}}";
       /* Map testM = new HashMap();
        testM.put("gender","MALE");
        testM.put("age","14");
        try {
            json = objectMapper.writeValueAsString(testM);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            Map map = objectMapper.readValue(json, Map.class);
            System.out.println(StringUtils.isEmpty((String) map.get("error")));
            map = (Map) map.get("result");
            String result = map.get("request_id") + "";

            System.out.println(result);
//            Integer age = Integer.valueOf((String) map.get("age"));
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
