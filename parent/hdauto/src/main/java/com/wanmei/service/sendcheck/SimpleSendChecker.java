package com.david.web.wanmei.service.sendcheck;

import com.david.web.wanmei.entity.Task;
import com.david.web.wanmei.service.task.TaskService;
import com.david.web.wanmei.service.test.MessageInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-4-17
 * Time: 下午5:22
 * To change this template use File | Settings | File Templates.
 */
public class SimpleSendChecker {
    @Autowired
    private TaskService taskService;

    private List<String> dataSourceList;//没有什么用

    @Autowired
    private CheckService checkService;

    @Autowired
    private MessageInterface messageInterface;

    public void execute() throws Exception {

        for (String dataSource : dataSourceList) {
            List<String> tables = checkService.getIllegalPrizeTableList(dataSource);
            for (String table : tables) {
                Task task = checkService.getTask(table);
                if (task == null || task.getTrigger_state().equals("PAUSED")) {
                    continue;
                }
                String msg = "虽然不可能，但是发奖出问题了，数据源:" + dataSource + " 表名：" + task.getTrigger_name() + ",发奖已经关闭！";
                messageInterface.sendMessage(msg, "13693293051", "13671245979", "18611195426");
            }
        }

    }

    public List<String> getDataSourceList() {
        return dataSourceList;
    }

    public void setDataSourceList(List<String> dataSourceList) {
        this.dataSourceList = dataSourceList;
    }
}
