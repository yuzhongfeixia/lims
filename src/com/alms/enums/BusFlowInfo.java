package com.alms.enums;

public enum BusFlowInfo {
    None(""), Submit("提交");

    private final String busflowinfo;

    private BusFlowInfo(String busflowinfo) {
        this.busflowinfo = busflowinfo;
    }

    public String getBusflowinfo() {
        return this.busflowinfo;
    }

    public static BusFlowInfo parse(String busflowinfo) {
        if (busflowinfo != null) {
            for (BusFlowInfo type : BusFlowInfo.values()) {
                if (busflowinfo.equalsIgnoreCase(type.busflowinfo))
                    return type;
            }
        }

        return BusFlowInfo.None;
    }
}