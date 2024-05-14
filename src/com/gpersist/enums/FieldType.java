package com.gpersist.enums;

public enum FieldType {
    Number("01"), Select("02"), Text("03");

    private final String fieldtype;

    private FieldType(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public String getFieldtype() {
        return this.fieldtype;
    }

    public static FieldType parse(String fieldtype) {
        if (fieldtype != null) {
            for (FieldType type : FieldType.values()) {
                if (fieldtype.equalsIgnoreCase(type.fieldtype))
                    return type;
            }
        }

        return FieldType.Text;
    }
}