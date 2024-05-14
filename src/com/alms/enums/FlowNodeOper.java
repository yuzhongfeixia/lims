package com.alms.enums;

public enum FlowNodeOper {
    // 自动操作
    None("00"),

    // 节点并行且所有人同意
    ParallelAll("11"),

    // 节点并行且单人同意
    ParallelOne("12"),

    // 节点唯一且所有人同意
    SerialAll("21"),

    // 节点唯一且单人同意
    SerialOne("22");

    private final String flownodeoper;

    private FlowNodeOper(String flownodeoper) {
        this.flownodeoper = flownodeoper;
    }

    public String getFlownodeoper() {
        return this.flownodeoper;
    }

    public static FlowNodeOper parse(String flownodeoper) {
        if (flownodeoper != null) {
            for (FlowNodeOper type : FlowNodeOper.values()) {
                if (flownodeoper.equalsIgnoreCase(type.flownodeoper))
                    return type;
            }
        }

        return FlowNodeOper.None;
    }
}