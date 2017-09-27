package org.david.rain.common.mapper;


import org.junit.Test;


public class JsonMapperTest {


    @Test
    public void fromJson() throws Exception {

        //language=JSON
        String datas = "{\"name\":\"david\",\"sex\":\"boy\"}";
        ContractData map = JsonMapper.INSTANCE.fromJson(datas, ContractData.class);
        System.out.println(map);
    }

}
