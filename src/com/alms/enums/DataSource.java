package com.alms.enums;

public enum DataSource {
    BusData("01"), RelaData("02"), EnterData("03"), FuncData("04"), FormulaData("05"), AveData("06"), FmtData("07"),
    JudgeData("08"), JudgeFmt("09"), DifferData("10"), AveNotJudge("11"), TwoJudgment("12"), FLJudgment("13");

    private final String datasource;

    private DataSource(String datasource) {
        this.datasource = datasource;
    }

    public String getDatasource() {
        return this.datasource;
    }

    public static DataSource parse(String datasource) {
        if (datasource != null) {
            for (DataSource type : DataSource.values()) {
                if (datasource.equalsIgnoreCase(type.datasource))
                    return type;
            }
        }

        return DataSource.BusData;
    }
}