package com.gpersist.enums;

public enum ColumnType {
    String("string"), Short("int"), Int("int"), Boolean("bool"), Date("date"), Double("float");

    private final String columntype;

    private ColumnType(String columntype) {
        this.columntype = columntype;
    }

    public String getColumntype() {
        return this.columntype;
    }

    public static ColumnType parse(String columntype) {
        if (columntype != null) {
            for (ColumnType type : ColumnType.values()) {
                if (columntype.equalsIgnoreCase(type.columntype))
                    return type;
            }
        }

        return ColumnType.String;
    }
}
