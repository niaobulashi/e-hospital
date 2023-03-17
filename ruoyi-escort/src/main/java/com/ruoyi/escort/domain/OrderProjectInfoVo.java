package com.ruoyi.escort.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class OrderProjectInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 项目类型名称
    private String name;

    // 项目类型对应的订单数量
    private int value;
}
