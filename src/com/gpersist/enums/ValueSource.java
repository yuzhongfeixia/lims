package com.gpersist.enums;

public enum ValueSource {
    Text("01"), Data("02"), Empty("03"), Image("04");

    private final String valuesource;

    private ValueSource(String valuesource) {
        this.valuesource = valuesource;
    }

    public String getValuesource() {
        return this.valuesource;
    }

    public static ValueSource parse(String valuesource) {
        if (valuesource != null) {
            for (ValueSource type : ValueSource.values()) {
                if (valuesource.equalsIgnoreCase(type.valuesource))
                    return type;
            }
        }

        return ValueSource.Text;
    }
}