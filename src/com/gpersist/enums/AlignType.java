package com.gpersist.enums;

public enum AlignType {
    Left("1"), Center("2"), Right("3");

    private final String vligntype;

    private AlignType(String vligntype) {
        this.vligntype = vligntype;
    }

    public String getAligntype() {
        return this.vligntype;
    }

    public static AlignType parse(String vligntype) {
        if (vligntype != null) {
            for (AlignType type : AlignType.values()) {
                if (vligntype.equalsIgnoreCase(type.vligntype))
                    return type;
            }
        }

        return AlignType.Left;
    }
}