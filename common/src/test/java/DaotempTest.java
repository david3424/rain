import bean.WorkLog;
import org.david.rain.beta.dao.BaseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by david on 2014/10/30.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-application.xml"})
public class DaotempTest {

    @Resource
    BaseDao baseDao;
    @Test
    public void testVoid(){
        WorkLog workLog = new WorkLog("logid","projectid",new Date(),"emploee","archivestate");
        boolean bb = baseDao.baseSave(workLog);
        System.out.println(bb);

}



}
