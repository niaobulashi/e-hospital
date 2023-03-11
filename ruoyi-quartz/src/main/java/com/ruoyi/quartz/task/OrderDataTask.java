package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 定时任务:自动生成会员、订单、流水数据
 *
 * @author zhangqi
 */
@Component("orderDataTask")
public class OrderDataTask {

    public void createOrderData() {
        Set<Long> dates = DateUtils.randomDateLong("2023-03-08 11:28:00", "2023-03-08 11:29:00", 10);
        DateUtils.scheduledWorkTask(dates);
    }
}
