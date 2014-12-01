package org.david.rain.javabase.reflect;

import org.david.rain.act.base.reflect.ReflectTask;
import org.david.rain.act.entity.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
