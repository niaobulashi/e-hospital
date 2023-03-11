package com.ruoyi.quartz.task;

import com.ruoyi.escort.service.IEscortOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务:自动生成会员、订单(已付款)、流水数据
 *
 * @author zhangqi
 */
@Component("orderDataTask")
public class OrderDataTask {

    public static final Logger log = LoggerFactory.getLogger(OrderDataTask.class);

    @Autowired
    IEscortOrderService iEscortOrderService;

    public void createOrders() {
        log.info("定时任务:自动生成会员、订单(已付款)、流水数据-start");
        iEscortOrderService.createRandomOrder();
        log.info("定时任务:自动生成会员、订单(已付款)、流水数据-end");
    }
}
