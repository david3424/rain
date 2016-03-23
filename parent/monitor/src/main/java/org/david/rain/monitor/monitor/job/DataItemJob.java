package org.david.rain.monitor.monitor.job;

import org.david.rain.monitor.dbutils.repository.impl.CommonDao;
import org.david.rain.monitor.hdinterface.ShortMessageInterface;
import org.david.rain.monitor.lang.Tuple;
import org.david.rain.monitor.monitor.util.DataSourceContext;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.david.rain.monitor.monitor.util.SpringContextSupport;
import org.david.rain.monitor.monitor.domain.*;
import org.david.rain.monitor.monitor.service.data.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p/>
 * 目前数据监控是直接读取数据库，这样子好像是不能设置超时的，也就是说如果访问有问题job就会出问题
 * 如果是用接口的话，吧sql传过去，那边来查询数据库，接口返回，这样子可以控制超时，感觉还不错，
 * 后面写程序的人可以考虑下,修改成那样子，这样的化起始这个监控就可以和服务器监控统一起来，恩，感觉良好
 * <p/>
 * 当前数据波动检查 没有单独的定时器，起始可以单独用定时器，这样会比较好，比如定点每天整点跑，或者每隔10分钟跑一次
 * 这样子更为准确， 我的建议是后期独立出来比较靠谱，由于时间有线，不能大动刀，留下这个注释，希望后续修改者可以参考
 * <p/>
 * 三个要是能大统一就能省不少代码了。服务监控，规则监控，波动监控，现在后面两个是合到一期的，
 * 起始也是为了少复制代码的权衡设计，但是这个设计不够精准，后面都快做好了才发现。
 */
public class DataItemJob implements Job {

    static Logger logger = LoggerFactory.getLogger(DataItemJob.class);

    public static final String JOB_DATA_MAP_KEY = "dataItemId";

    private Integer dataItemId;

    private volatile DataSourceContext dataSourceContext;

    private volatile DataAttrService attrSettingService;


    private volatile DataItemService dataItemService;

    private volatile DataAttrService dataAttrService;

    private volatile DataOscillationService dataOscillationService;

    private volatile DataRemindService dataRemindService;

    private volatile ShortMessageInterface shortMessageInterface;

    private volatile DataCheckService dataCheckService;


    private void init() {

        if (dataSourceContext == null) {
            dataSourceContext = SpringContextSupport.getSpringBean(DataSourceContext.class);
        }
        if (attrSettingService == null) {
            attrSettingService = SpringContextSupport.getSpringBean(DataAttrService.class);
        }
        if (dataAttrService == null) {
            dataAttrService = SpringContextSupport.getSpringBean("dataAttrService", DataAttrService.class);
        }
        if (dataItemService == null) {
            dataItemService = SpringContextSupport.getSpringBean("dataItemService", DataItemService.class);
        }
        if (dataOscillationService == null) {
            dataOscillationService = SpringContextSupport.getSpringBean(DataOscillationService.class);
        }
        if (dataRemindService == null) {
            dataRemindService = SpringContextSupport.getSpringBean(DataRemindService.class);
        }
        if (shortMessageInterface == null) {
            shortMessageInterface = SpringContextSupport.getSpringBean(ShortMessageInterface.class);
        }

        if (dataCheckService == null) {
            dataCheckService = SpringContextSupport.getSpringBean(DataCheckService.class);
        }
    }


    /**
     * 这里的报错最好是在规则上就直接短信提醒
     * <p/>
     * 本方法还需要优化的地方：只有参与规则计算的属性才需要每次都查出来，在查的时候可以先做个统计
     * 被波动检查的属性值可以timestep到了的时候才需要取，后面的筒子们，修改下吧
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("执行job任务");
    /*    init();

        DataItem dataItem = dataItemService.getDataItemById(dataItemId);

        int newStatus = MonitorConst.InfoLevel.HEALTH.value;

        logger.debug(dataItem.toString());

        if (dataItem.getJobStatus() == MonitorConst.JobStatus.STOP.value) {
            return;
        }
        //比较监控项的开始时间  如果没有开始  先不走监控流程
        if(checkTime(new Date().getTime(),dataItem.getBeginTime(),0)){
            return;
        }
        //比较监控项的结束时间  如果已经结束  就停止监控流程
        if(checkTime(new Date().getTime(),dataItem.getEndTime(),1)){
            dataItemService.pauseJob(dataItemId);
            return;
        }
        int sign = dataItemService.updateTurns(dataItem.getId(), dataItem.getTurns());
        if (sign == 0) {
            logger.warn(dataItem.getItemNameCh() + " turns :" + (dataItem.getTurns() + 1) + " is running");
            return;
        }

        dataItem.setTurns(dataItem.getTurns() + 1);

        List<DataAttrSetting> attrSettings = dataAttrService.queryDataAttrSettingByItemId(dataItemId);

        *//** 如果这些属性都在一个数据源比较靠谱，这样子查询在一个事务里面才有用，
         * 表用innodb的 不让两个数据的查询就不一致了，还有就是如果让sql一次性查出两个
         * 值得话，sql写起来就会有点复杂且不是很好写可能 **//*

        Map<String, List<DataAttrSetting>> settingInDiffDataSource = new HashMap<>();
        for (DataAttrSetting attrSetting : attrSettings) {
            List<DataAttrSetting> settingList = settingInDiffDataSource.get(attrSetting.getDataSource());
            if (settingList == null) {
                settingList = new ArrayList<>();
                settingInDiffDataSource.put(attrSetting.getDataSource(), settingList);
            }
            settingList.add(attrSetting);
        }
        //  以下把所有数据源的result value 合成一个map来使用,并且处理一些报错
        Iterator<Map.Entry<String, List<DataAttrSetting>>> itr = settingInDiffDataSource.entrySet().iterator();
        Map<String, Long> valueResult = new HashMap<>();
        boolean isValueResultRight = true;
        while (itr.hasNext()) {
            Map.Entry<String, List<DataAttrSetting>> entry = itr.next();
            Tuple<Integer, Map<String, Long>> result = getAttrValueFromADataSource(entry.getKey(), entry.getValue());
            if (result.l < 0) {
                isValueResultRight = false;
                for (DataAttrSetting dataAttrSetting : entry.getValue()) {
                    valueResult.put(dataAttrSetting.getAttrName(), new Long(result.l));
                }
            } else {
                for (Map.Entry<String, Long> attrItr : result.r.entrySet()) {
                    if (attrItr.getValue() < 0) {
                        isValueResultRight = false;
                    } else {
                        valueResult.put(attrItr.getKey(), attrItr.getValue());
                    }
                }
            }
        }

        int checkSign = 1;//默认是正确
        if (!isValueResultRight) {//如果值都对才计算规则，当然也可以在规则计算的时候检查一下值是不是都合格，目前从简
            newStatus = MonitorConst.InfoLevel.ERROR.value;

            List<DataAttrLog> attrLogs = new ArrayList<>(attrSettings.size());
            for (DataAttrSetting attrSetting : attrSettings) {
                DataAttrLog log = new DataAttrLog(attrSetting, dataItem.getTurns(), valueResult.get(attrSetting.getAttrName()));
                attrLogs.add(log);
            }
            dataAttrService.insertDataAttrLog(attrLogs);
        } else {
            *//**
             * 这里对每一个规则做计
             * **//*
            List<DataCheckSetting> settings = dataAttrService.queryDataCheckSettings(dataItemId);

            checkSign = check(settings, valueResult, dataItem.getTurns());


            if (checkSign == 0) // 检查出错，或者 属性读取出错都要记录log ，由于可能有些数据读取出错但是不影响检查,
            {

                List<DataAttrLog> attrLogs = new ArrayList<>(attrSettings.size());
                for (DataAttrSetting attrSetting : attrSettings) {
                    DataAttrLog log = new DataAttrLog(attrSetting, dataItem.getTurns(), valueResult.get(attrSetting.getAttrName()));
                    attrLogs.add(log);
                }
                dataAttrService.insertDataAttrLog(attrLogs);
                newStatus = MonitorConst.InfoLevel.ERROR.value;//这里默认就是错误级别，当让这个应该在check里面返回
            } else {
                newStatus = MonitorConst.InfoLevel.HEALTH.value;
            }

            *//**
             * 波动的检查不反应到最终监控项的异常上面去。一个监控项很多时候基本就是一个活动,有时候也可以是其他的东西，比如整个活动中的登陆次数
             *//*
            List<OscillationCheckSetting> oscillationCheckSettings = dataOscillationService.queryOscillationList(dataItemId);

            for (OscillationCheckSetting setting : oscillationCheckSettings) {
                int oscillationSign = proccessOneOscillation(setting, valueResult, dataItem.getTurns());
                if (oscillationSign == 6) {
                    continue;//如果周期还没有到，就直接返回
                }
                if (oscillationSign == OscillationLog.EXCEPTION || oscillationSign == OscillationLog.ERROR) {
                    //波动的状态变化需不需在波动中做个记录？我觉得不需要，参生了一次波动就提醒一次应该，2014-03-20
                }
            }

        }
        dataItemService.updateStatus(dataItemId, newStatus, dataItem.getStatus());*/
    }


    /**
     * 如果一个过低过高的波动产生了，这里需要一个报警，
     * 该次参生的值默认记录为异常，不参与计算，可以认为修改为正常
     *
     * @param oscillationCheckSetting
     * @param valueResult
     * @return
     */
    private int checkOneOscillation(OscillationCheckSetting oscillationCheckSetting,
                                    Map<String, Long> valueResult, String phaseDetail, Integer nowTimes) {
        //活动本时段最近两次log
        List<OscillationLog> logList = dataOscillationService.getLatestTwoLog(oscillationCheckSetting.getItemId(),
                oscillationCheckSetting.getAttrName(), phaseDetail, nowTimes - 1);
        //如果本时段到现在log小于两条就直接算正常，这里不做统计
        if (logList.size() < 2) {
            return OscillationLog.NORMAL;
        } else {
            Long first = logList.get(0).getCurrentNum();
            Long second = logList.get(1).getCurrentNum();
            Long three = valueResult.get(oscillationCheckSetting.getAttrName());
            if (three == null || three < 0) {
                dataOscillationService.updateOscillationAttrStatus(oscillationCheckSetting.getItemId(), oscillationCheckSetting.getAttrName()
                        , OscillationCheckSetting.STATUS_ERROR);
                return OscillationLog.ERROR;
            } else {
                long addFirst = second - first;
                long addLater = three - second;

                List<OscillationLog> firstTwoLog = dataOscillationService.
                        getFisrtTwoNormalLog(oscillationCheckSetting.getItemId(), oscillationCheckSetting.getAttrName());

                //这是一个关键值，可以通过数据库配置，计算斜率的底边,这个字以后最好做成基于log来计算吧，隔一定时间更新一次,
                //现在这个算法简直就没有什么用
                long hValue = (firstTwoLog.get(0).getCurrentNum() + firstTwoLog.get(1).getCurrentNum()) / 2;//取最开始两次正常值得平均值

                if (firstTwoLog.get(1).getCurrentNum() > hValue * 5)
                    hValue = firstTwoLog.get(1).getCurrentNum();

                if (hValue == 0) hValue = 100;

                double tan1 = addFirst / hValue;
                double tan2 = addLater / hValue;

                double tanDelta = 0;
                if (tan1 > tan2)
                    tanDelta = tan1 - tan2 / (1 + tan1 * tan2);
                else
                    tanDelta = tan2 - tan1 / (1 + tan1 * tan2);

                logger.warn("itemId:[" + oscillationCheckSetting.getItemId() + "] times:(" + nowTimes + ") tanDelta:" + tanDelta);

                double D20 = 0.36397; //小于20度就算正常

                if (tanDelta < D20) {//TODO 这里加个计算斜率的判断，还有一个标准值得比较
                    return OscillationLog.NORMAL;
                } else
                    return OscillationLog.EXCEPTION;
            }
        }
    }


    /**
     * @param oscillationCheckSetting
     * @param valueResult
     * @param itemTurns
     * @return 6: 表示周期还没有到，其他状态参见OcillationLog 里面定义的常量
     */
    private int proccessOneOscillation(OscillationCheckSetting oscillationCheckSetting, Map<String, Long> valueResult, Long itemTurns) {
        OscillationLog lastLog = dataOscillationService.getLatestLog(oscillationCheckSetting.getItemId(), oscillationCheckSetting.getAttrName());
//        logger.warn("lastLog:" + lastLog);
        long n = 0;
        if (lastLog == null) {
            n = Long.MIN_VALUE;
        } else {
            long logTime = lastLog.getCreateTimeLong();
            long now = System.currentTimeMillis();
            n = (now - logTime) / (oscillationCheckSetting.getTimeStep() * 60 * 1000);
        }

        if (n == 0) {//如果周期还没有到，直接就返回了。
            return 6;
        }
        if (n < 0 && n != Long.MIN_VALUE) {
            logger.error("calc the times error now n:" + n);
            return OscillationLog.ERROR;
        }
        int isNormal = OscillationLog.NORMAL;
        /**
         * 做法1 如果超出了多少倍，就加多少条log，一般不会超出的，超出肯定就报错报警了。如果恢复了，就这么做点假数据，自动做的那种
         * 做法2（现在的做法，不记录缺失，除以多期直接得到本次数量）
         */
        OscillationLog oscillationLog = new OscillationLog(oscillationCheckSetting);
        oscillationLog.setItemTurns(itemTurns);
        String phaseDetail = dataOscillationService.caclPhaseDetail(oscillationCheckSetting);
        oscillationLog.setPhaseDetail(phaseDetail);
        oscillationLog.setTotal(valueResult.get(oscillationCheckSetting.getAttrName()));
        if (n == Long.MIN_VALUE) {//如果是第一次
            oscillationLog.setCurrentNum(oscillationLog.getTotal());
        } else if (n == 1) { //如果中间有间隔就重新开始记录
            isNormal = checkOneOscillation(oscillationCheckSetting, valueResult, phaseDetail, lastLog.getTimes());
            oscillationLog.setCurrentNum(oscillationLog.getTotal() - lastLog.getTotal());
        } else {
            oscillationLog.setCurrentNum((oscillationLog.getTotal() - lastLog.getTotal()) / n);//重新开始后第一个当前周期的数目直接用停用的平均数
        }
        oscillationLog.setStatus(isNormal);
        oscillationLog.setTimes(lastLog == null ? 1 : new Long(n + lastLog.getTimes()).intValue());
        dataOscillationService.addOscillationLog(oscillationLog);
        return isNormal;
    }

    /**
     * 这里检查并且提醒
     *
     * @param checkSettingList
     * @param values
     * @return 0 表示有错误，1 表示正常，0需要记录attr的日志，如果错误的检查项页需要记录日志
     * <p/>
     * 这个返回值可以修改成MonitorConst 的Abn-level,就和数据库里面对上了
     */
    private int check(List<DataCheckSetting> checkSettingList, Map<String, Long> values, Long turns) {
        int re = 1;
        for (DataCheckSetting dataCheckSetting : checkSettingList) {
            int status = DataCheckLog.Status.SYS_ERROR.value;
            try {
                boolean result = ExpressionCheckUtil.isWright(dataCheckSetting, values);
                logger.debug(dataCheckSetting.getChName() + " result:" + result);
                if (result) {
                    status = DataCheckLog.Status.NORMAL.value;
                } else {
                    status = DataCheckLog.Status.EXCEPTION.value;
                    re = 0;//只要有错误 re就是0
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                status = DataCheckLog.Status.SYS_ERROR.value;
                re = 0;//只要有错误 re就是0
            }

            /** 如果状态改变了才记录log **/
            if (status != dataCheckSetting.getStatus()) {
                DataCheckLog log = new DataCheckLog(dataCheckSetting, turns);
                log.setStatus(status);
                attrSettingService.insertDataCheckerLog(log);
                dataCheckService.updateRuleStatus(dataCheckSetting, status);
                sendCheckRemind(dataCheckSetting, status);
            }
        }
        return re;
    }


    private void sendCheckRemind(DataCheckSetting dataCheckSetting, int nowStatus) {
        DataItem item = dataItemService.getDataItemById(dataCheckSetting.getItemId());
        String message = "数据监控项(" + item.getItemNameCh() + ")的规则检查【" + dataCheckSetting.getChName() + "】状态由'" +
                MonitorConst.NORMAL_EXCEPTION.get(dataCheckSetting.getStatus()) + "' 变为'"
                + MonitorConst.NORMAL_EXCEPTION.get(nowStatus) + "'，当前时间：" + DateUtils.getCurrentFormatDateTime();
        List<RemindInfo> remindInfos = dataRemindService.getRemindListByItemId(item.getId());
        for (RemindInfo remindInfo : remindInfos) {
            if (remindInfo.getRemindType() == MonitorConst.REMIND_TYPE_MESSAGE) {
                shortMessageInterface.sendMessageWithType(MonitorConst.MESSAGE_KEY, remindInfo.getPhone(), message);
            }
        }
    }

    /**
     * 这里查询一个数据源里面的属性值
     *
     * @param dataSourceName
     * @param settings
     * @return Tuple.l int 型 1表示正常，< 0 表示数据有问题 Tuple.r Long -1 表示SQL有问题
     */
    private Tuple<Integer, Map<String, Long>> getAttrValueFromADataSource(String dataSourceName, List<DataAttrSetting> settings) {
        Integer status = 1;
        Map<String, Long> valueMap = new HashMap<>();
        DataSource dataSource = dataSourceContext.getDataSource(dataSourceName);
        if (dataSource == null) {
            logger.error("datasource: [" + dataSourceName + " ] is not exist.");
            return new Tuple<>(-101, null);
        }
        CommonDao dao = new CommonDao(dataSource);

        //Connection connection = null;
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            for (DataAttrSetting setting : settings) {
                Long result = -1L;
                try {

                    Object oResult = dao.queryScalar(connection, setting.getSql());
                    if (oResult == null)
                        result = 0L;//这里要注意了，如果没有查到这个东西，该怎么处理
                    else
                        result = Long.parseLong(oResult.toString());
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                    result = -1L;
                }
                logger.debug(setting.getChName() + " result:" + result);
                valueMap.put(setting.getAttrName(), result);
            }
            connection.commit();
            return new Tuple<>(status, valueMap);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return new Tuple<>(-102, null);
        }
    }


    public Integer getDataItemId() {
        return dataItemId;
    }

    public void setDataItemId(Integer dataItemId) {
        this.dataItemId = dataItemId;
    }

    public boolean checkTime(long createtime,String str,Integer type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        if(type==0){ //开始时间判断
            return date.getTime()-createtime >0 ? true:false;
        }else if(type==1){ //结束时间判断
            return createtime-date.getTime() >0 ? true:false;
        }else{
            return false;
        }
    }
}
