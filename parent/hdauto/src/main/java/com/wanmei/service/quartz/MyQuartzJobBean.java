package com.david.web.wanmei.service.quartz;

import com.david.web.wanmei.util.SpringContextSupport;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MyQuartzJobBean extends QuartzJobBean {

//    private SimpleService simpleService;
//
//    public void setSimpleService(SimpleService simpleService) {
//        this.simpleService = simpleService;
//    }

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        Trigger trigger = jobexecutioncontext.getTrigger();
        String triggerName = trigger.getName();
        String group = trigger.getGroup();
        SimpleService simpleService = SpringContextSupport.getSpringBean(SimpleService.class);

        //根据Trigger组别调用不同的业务逻辑方法
        /*if (StringUtils.equals(group, Scheduler.DEFAULT_GROUP)) {
              simpleService.testMethod(triggerName, group);
          } else {
              simpleService.testMethod2(triggerName, group);
          }

      }*/


        try {
            simpleService.testService(triggerName, group);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        /*     switch (Integer.parseInt(group)) {
            case 1:
                System.out.println("this is roleid prize--1111111--");
                try {
                    simpleService.testMethod(triggerName, "group1");
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;
            case 2:
                System.out.println("this is roleid_sm prize--2222222--");
                try {
                    simpleService.testMethod(triggerName, "group2");
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;
            case 3:
                System.out.println("this is coupon prize-33333---");
                try {
                    simpleService.testMethod(triggerName, "group3");
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;
            case 4:
                System.out.println("this is rolename prize--444444--");
                try {
                    simpleService.testMethod(triggerName, "group4");
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;

            case 5:
                System.out.println("this is rolename_sm prize--5555--");
                try {
                    simpleService.testMethod(triggerName, "group5");
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                break;
        }*/
    }

}
