package com.alms.enums;

public enum FlowDataAuth {
    // 无限制
    None("00"),

    // 按可操作部门
    Dept("01"),

    // 按程序设定
    Program("02"),

    // 按可操作部门和程序设定
    DeptProgram("03");

    private final String flowdataauth;

    private FlowDataAuth(String flowdataauth) {
        this.flowdataauth = flowdataauth;
    }

    public String getFlowdataauth() {
        return this.flowdataauth;
    }

    public static FlowDataAuth parse(String flowdataauth) {
        if (flowdataauth != null) {
            for (FlowDataAuth type : FlowDataAuth.values()) {
                if (flowdataauth.equalsIgnoreCase(type.flowdataauth))
                    return type;
            }
        }

        return FlowDataAuth.None;
    }
}