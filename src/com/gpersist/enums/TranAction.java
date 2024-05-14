package com.gpersist.enums;

public enum TranAction {
    None("00"), Search("01"), New("02"), Edit("03"), Delete("04"), Deal("07"), Valid("08"), InValid("09"), Check("10"),
    UnCheck("11"), Upload("12"), Special01("13"), Special02("14"), Special03("15"), Submit("16");

    private final String tranaction;

    private TranAction(String tranaction) {
        this.tranaction = tranaction;
    }

    public String getTranaction() {
        return this.tranaction;
    }

    public static TranAction parse(String tranaction) {
        if (tranaction != null) {
            for (TranAction type : TranAction.values()) {
                if (tranaction.equalsIgnoreCase(type.tranaction))
                    return type;
            }
        }

        return TranAction.None;
    }

    public static TranAction parse(int tranaction) {
        String value = "";

        if (tranaction > 9)
            value = String.valueOf(tranaction);
        else
            value = '0' + String.valueOf(tranaction);

        return parse(value);
    }
}
