package org.david.java.javabase.reflect;

import org.david.rain.act.base.reflect.ReflectTask;
import org.david.rain.act.entity.Task;
import org.junit.Test;

/**
 * Created by mac on 14-12-1.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class ReflectTaskTest {

    @Test
    public void testReflect() throws Exception {
        Task task = ReflectTask.initByDefaultConst();
        System.out.println(task);
    }
}
