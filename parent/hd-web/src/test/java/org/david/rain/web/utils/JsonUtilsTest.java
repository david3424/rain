package org.david.rain.web.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mac on 15-9-2.
 */
public class JsonUtilsTest {


    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String TREE_MODEL_BINDING = "{\"treekey1\":\"treevalue1\",\"treekey2\":\"treevalue2\",\"children\":[{\"childkey1\":\"childkey1\"},{\"childkey2\":\"childkey1\"},{\"childkey3\":\"childkey1\"}]}";
    String str = "{data=[{\"roleId\":\"4307\",\"roleLevel\":\"71\",\"roleName\":\"S16.杀毒用瑞星\",\"serverId\":\"16\",\"serverName\":\"双线16区\"},{\"roleId\":\"8135\",\"roleLevel\":\"48\",\"roleName\":\"S16.我是小号\",\"serverId\":\"16\",\"serverName\":\"双线16区\"}], code=0, success=true, msg=成功}" ;


    /**
     * 利用ObjectNode分解json格式的串，得到array、object等* 
     * @throws IOException
     */
    @Test
    public  void jsonObjectTest() throws IOException {

        ObjectNode objectNode = objectMapper.readValue(TREE_MODEL_BINDING,ObjectNode.class);
        ArrayNode arrayNode = (ArrayNode) objectNode.path("children");
        System.out.println(arrayNode.toString());
    }
    
    @Test
    public  void jsonMapTest() throws IOException {

        // use properties to restore the map
        Properties props = new Properties();
        props.load(new StringReader(str.substring(1, str.length() - 1).replace(", ", "\n")));
       /*data=[{"roleId":"4307","roleLevel":"71","roleName":"S16.杀毒用瑞星","serverId":"16","serverName":"双线16区"},{"roleId":"8135","roleLevel":"48","roleName":"S16.我是小号","serverId":"16","serverName":"双线16区"}]
        code=0
        success=true
        msg=成功*/
              HashMap<String,Object> map2 = new HashMap();
        //迭代props
        for (Map.Entry<Object, Object> e : props.entrySet()) {
            map2.put((String) e.getKey(), e.getValue());
        }
        System.out.println(map2);
        System.out.println(objectMapper.writeValueAsString(map2));
    }
    
    
    @Test
    public void MapAndJson() throws IOException{
            // 读取JSON数据
            Map<String, Object> userData = objectMapper.readValue(new File("user.json"), Map.class);
            System.out.println(userData);
            // 写入JSON数据
            userData = new HashMap<> ();
            Map<String, String> nameStruct = new HashMap<> ();
            nameStruct.put("first", "Joe");
            nameStruct.put("last", "Hankcs");
            userData.put("name", nameStruct);
            userData.put("gender", "MALE");
            userData.put("verified", Boolean.FALSE);
            userData.put("userImage", "Rm9vYmFyIQ==");
            objectMapper.writeValue(new File("user-modified.json"), userData);
        }
}
