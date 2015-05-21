package effectivejava.ch2i5;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mac on 15-4-9.
 * entrySet返回的是Map.Entry<String,Object> 时间略快于keySet* 
 */
public class HashMapTest {


    @Test
    public void testEntryKey() throws Exception {

        HashMap<String, String> keySetMap = new HashMap<>();
        HashMap<String, String> entrySetMap = new HashMap<>();

        for (int i = 0; i < 1000; i++) {
            keySetMap.put("" + i, "keySet");
        }
        for (int i = 0; i < 1000; i++) {
            entrySetMap.put("" + i, "entrySet");
        }
        long startTimeOne = System.currentTimeMillis();
//        迭代MAP keyset方式：先得到key，再value keySet()与当前Map对象是对应的，没必要每次都new
        //源码实现：return (ks != null ? ks : (keySet = new KeySet()));
        Iterator<String> keySetIterator = keySetMap.keySet().iterator();
        while (keySetIterator.hasNext()) {
            String key = keySetIterator.next();
            String value = keySetMap.get(key);
            System.out.println(value);
        }
        System.out.println("keyset spent times:"
                + (System.currentTimeMillis() - startTimeOne));

        long startTimeTwo = System.currentTimeMillis();
//        迭代MAP entrySet方式
        Iterator<Map.Entry<String, String>> entryKeyIterator = entrySetMap
                .entrySet().iterator();
        while (entryKeyIterator.hasNext()) {
            Map.Entry<String, String> e = entryKeyIterator.next();
            System.out.println(e.getValue());
        }
        System.out.println("entrySet spent times:"
                + (System.currentTimeMillis() - startTimeTwo));
    }

}


