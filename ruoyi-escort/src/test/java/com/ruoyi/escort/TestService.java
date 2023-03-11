package com.ruoyi.escort;


import com.ruoyi.escort.service.IEscortOrderService;
import com.ruoyi.escort.service.impl.EscortOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TestService {
    public String helloTest() {
        return "hello";
    }

    @Autowired
    IEscortOrderService iEscortOrderService;

    public static void main(String[] args) {

        new EscortOrderServiceImpl().randomAssemblyOrder();
    }
}
