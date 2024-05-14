package com.gpersist.enums;

public enum ActionOutType {
    Bean(1), Array(2), Text(3), Save(4);

    private final int outtype;

    private ActionOutType(int outtype) {
        this.outtype = outtype;
    }

    public int getOuttype() {
        return this.outtype;
    }
}
