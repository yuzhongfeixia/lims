package com.alms.enums;

public enum FlowAction {
    None("000"), Apply("001"), CHECK("002"), Aduit("003"), Notice("101"), Download("102"), GetSample("103");

    private final String flowaction;

    private FlowAction(String flowaction) {
        this.flowaction = flowaction;
    }

    public String getFlowaction() {
        return this.flowaction;
    }

    public static FlowAction parse(String flowaction) {
        if (flowaction != null) {
            for (FlowAction type : FlowAction.values()) {
                if (flowaction.equalsIgnoreCase(type.flowaction))
                    return type;
            }
        }

        return FlowAction.None;
    }
}