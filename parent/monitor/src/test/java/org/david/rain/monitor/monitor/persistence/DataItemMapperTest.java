package org.david.rain.monitor.monitor.persistence;

import org.david.rain.monitor.monitor.domain.DataItem;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by czw on 14-2-24.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class DataItemMapperTest {

    @Autowired
    DataItemMapper dataItemMapper;


    @Test
    public void selectAllDataItem()
    {
        List<DataItem> itemList = dataItemMapper.selectAllDataItem();
        Assert.assertTrue(itemList.size() > 0);
        Assert.assertEquals(itemList.get(0).getUser().getChName(),"程志旺");
    }

}
