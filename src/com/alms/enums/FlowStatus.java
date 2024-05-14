package com.alms.enums;

public enum FlowStatus {
    None("000"), PreSave("001"), Submit("002"), Dealing("003"), UnAudit("004"), Audit("005"), Finish("999");

    private final String flowstatus;

    private FlowStatus(String flowstatus) {
        this.flowstatus = flowstatus;
    }

    public String getFlowstatus() {
        return this.flowstatus;
    }

    public static FlowStatus parse(String flowstatus) {
        if (flowstatus != null) {
            for (FlowStatus type : FlowStatus.values()) {
                if (flowstatus.equalsIgnoreCase(type.flowstatus))
                    return type;
            }
        }

        return FlowStatus.None;
    }
}