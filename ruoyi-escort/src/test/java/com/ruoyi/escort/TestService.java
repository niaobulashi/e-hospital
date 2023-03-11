package com.ruoyi.escort;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.escort.service.IEscortOrderService;
import com.ruoyi.escort.service.impl.EscortOrderServiceImpl;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;

public class TestService {
    public String helloTest() {
        return "hello";
    }

    @Autowired
    IEscortOrderService iEscortOrderService;

    @Autowired
    ISysConfigService iSysConfigService;

    public static void main(String[] args) {

//        String startTime = iSysConfigService.selectConfigByKey("create.order.time.start");
//        String endTime = iSysConfigService.selectConfigByKey("create.order.time.end");
//        int count = Integer.parseInt(iSysConfigService.selectConfigByKey("create.order.count"));
//        String startDateStr = DateUtils.getDate() + " " + startTime;
//        String endDateStr = DateUtils.getDate() + " " + endTime;
    }
}
