package com.ruoyi.quartz.task;

import com.ruoyi.escort.service.IEscortOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单定时任务
 *
 * @author zhangqi
 */
@Component("orderDataTask")
public class OrderDataTask {

    public static final Logger log = LoggerFactory.getLogger(OrderDataTask.class);

    @Autowired
    IEscortOrderService iEscortOrderService;

    /**
     * 自动生成会员、订单(已付款)、流水数据
     */
    public void createOrders() {
        log.info("定时任务:自动生成会员、订单(已付款)、流水数据-start");
        iEscortOrderService.createRandomOrder();
        log.info("定时任务:自动生成会员、订单(已付款)、流水数据-end");
    }

    /**
     * 根据预约日期改变订单状态为服务中
     */
    public void changeOrderStatusService() {
        log.info("定时任务:根据预约日期改变订单状态为服务中-start");
        iEscortOrderService.changeOrderStatusService();
        log.info("定时任务:根据预约日期改变订单状态为服务中-end");
    }

    /**
     * 根据计划完成时间日期改变订单状态为已完成
     */
    public void changeOrderStatusFinish() {
        log.info("定时任务:根据计划完成时间日期改变订单状态为已完成-start");
        iEscortOrderService.changeOrderStatusFinish();
        log.info("定时任务:根据计划完成时间日期改变订单状态为已完成-end");
    }
}
