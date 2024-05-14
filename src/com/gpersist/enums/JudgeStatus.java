package com.gpersist.enums;

public enum JudgeStatus {
    NO("1"), OK("2"), NOTOK("3"), REVIEW("4"), INVALID("5");

    private final String judgestatus;

    private JudgeStatus(String judgestatus) {
        this.judgestatus = judgestatus;
    }

    public String getJudgestatus() {
        return this.judgestatus;
    }

    public static JudgeStatus parse(String judgestatus) {
        if (judgestatus != null) {
            for (JudgeStatus type : JudgeStatus.values()) {
                if (judgestatus.equalsIgnoreCase(type.judgestatus))
                    return type;
            }
        }

        return JudgeStatus.NO;
    }
}