package com.ruoyi.common.enums;

/**
 * 用户状态
 *
 * @author zhangqi
 */
public class EscortEnums {

    public enum OrderStatus {
        PAID("0", "已付款"), SERVICE("1", "服务中"), FINISH("2", "已完成");

        private final String code;
        private final String info;

        OrderStatus(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    // 所属商家
    public enum MemberBusiness {
        bus_01("心运树陪诊");

        private final String name;

        MemberBusiness(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // 医院状态
    public enum HospitalStatus {
        // 有效
        status_0("0"),
        // 无效
        status_1("1");


        private final String code;

        HospitalStatus(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
