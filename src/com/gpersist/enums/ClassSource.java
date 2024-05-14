package com.gpersist.enums;

public enum ClassSource {
    Consign("consign"), Task("task"), TaskSample("tasksample"), Data("data"), Sample("sample"), Stand("stand"),
    Variety("variety"), TestDept("testdept");

    private final String classsource;

    private ClassSource(String classsource) {
        this.classsource = classsource;
    }

    public String getClasssource() {
        return this.classsource;
    }

    public static ClassSource parse(String classsource) {
        if (classsource != null) {
            for (ClassSource type : ClassSource.values()) {
                if (classsource.equalsIgnoreCase(type.classsource))
                    return type;
            }
        }

        return ClassSource.Data;
    }
}