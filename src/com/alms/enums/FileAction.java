package com.alms.enums;

public enum FileAction {
    Change(1), Destory(2), Leak(3), Loan(4), Secret(5);

    private final int action;

    private FileAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return this.action;
    }
}
