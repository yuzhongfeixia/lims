package com.alms.enums;

public enum FlowNodeType {
    // 开始
    Create("create"),

    // 批准
    Approve("approve"),

    // 实现
    Realize("realize"),

    // 归档
    Finish("finish");

    private final String flownodetype;

    private FlowNodeType(String flownodetype) {
        this.flownodetype = flownodetype;
    }

    public String getFlownodetype() {
        return this.flownodetype;
    }

    public static FlowNodeType parse(String flownodetype) {
        if (flownodetype != null) {
            for (FlowNodeType type : FlowNodeType.values()) {
                if (flownodetype.equalsIgnoreCase(type.flownodetype))
                    return type;
            }
        }

        return FlowNodeType.Create;
    }
}