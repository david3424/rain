package org.david.rain.common.components.lottery.probability;


import org.david.rain.common.components.exception.ComponentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class ProbabilityAlgorithm {
    private final static Random RANDOM = new Random();
    private final static Logger LOG = LoggerFactory.getLogger(ProbabilityAlgorithm.class);

    /**
     * 设置抽奖区间  左闭右开就行[1,1000),[1000,2000)
     */
    private static Map<Integer, Integer[]> getRandomMap(List<ProbabilityLotteryPrize> prizeInfos) {
        Map<Integer, Integer[]> map = new ConcurrentHashMap<Integer, Integer[]>();
        int temp = 1;
        for (ProbabilityLotteryPrize proInfo : prizeInfos) {
            Integer[] randomArray = {0, 0};
            if (proInfo.getProbability() > 0) {
                randomArray[0] = temp;
                temp += proInfo.getProbability();
                randomArray[1] = temp;
            }
            map.put(prizeInfos.indexOf(proInfo), randomArray);
            LOG.debug(" 抽奖区间为：{} ",  Arrays.toString(randomArray));
        }
        return map;
    }

    public static ProbabilityLotteryPrize drawPrize(List<ProbabilityLotteryPrize> prizeInfos, int accuracy) throws ComponentsException {
        if (accuracy <= 0) {
            throw new ComponentsException("奖品概率配置精度错误。");
        }
        int randomCode = RANDOM.nextInt(accuracy) + 1;
        Map<Integer, Integer[]> randomMap = getRandomMap(prizeInfos);
        for (Map.Entry<Integer, Integer[]> proInfo : randomMap.entrySet()) {
            if (randomCode >= proInfo.getValue()[0] && randomCode < proInfo.getValue()[1]) {
                return prizeInfos.get(proInfo.getKey());
            }
        }
        return null;
    }
}
