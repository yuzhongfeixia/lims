package com.alms.enums;

public enum TodoType {
    Bus("1"), Ok("2"), NotOk("3"), File("4"), Glp("5"), Notice("6"), TaskAcc("7"), LabReady("8"), StartLab("9"),
    GetAcc("10");

    private final String todotype;

    private TodoType(String todotype) {
        this.todotype = todotype;
    }

    public String getTodotype() {
        return this.todotype;
    }

    public static TodoType parse(String todotype) {
        if (todotype != null) {
            for (TodoType type : TodoType.values()) {
                if (todotype.equalsIgnoreCase(type.todotype))
                    return type;
            }
        }

        return TodoType.Bus;
    }
}