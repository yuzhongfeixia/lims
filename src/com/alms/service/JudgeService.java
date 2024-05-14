package com.alms.service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JudgeService {

    public static final String JUDGE_NO = "未检出";
    public static final String JUDGE_OK = "符合";
    public static final String JUDGE_STRENGTH = "超强";
    public static final String JUDGE_NOTOK = "不符合";
    public static final String JUDGE_NOJUDGE = "不能判定";
    public static final String JUDGE_INVALID = "无效";
    public static final String JUDGE_REVIEW = "不合格需复检";
    public static final String REQUEST_NO = "无";
    public static final String DEF_VALUE = "/";

    public static final String CN_LEVEL_1 = "Ⅰ级";
    public static final String CN_LEVEL_2 = "Ⅱ级";
    public static final String CN_LEVEL_3 = "Ⅲ级";

    static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    public static String judgeOne(Object[] str, String standvalue, String testjudge) {
        try {
            double dv = Double.parseDouble(str[0].toString());
            return (dv <= Double.parseDouble(standvalue)) ? JUDGE_OK : JUDGE_NOTOK;
        } catch (Exception e) {
            return JUDGE_NO;
        }
    }

    public static String judgeTwo(Object[] str, String standvalue, String testjudge) {
        try {
            double dv = Double.parseDouble(str[0].toString());
            return (dv < Double.parseDouble(standvalue)) ? JUDGE_OK : JUDGE_NOTOK;
        } catch (Exception e) {
            return JUDGE_NO;
        }
    }

    public static String judgeThree(Object[] str, String standvalue, String testjudge) {
        try {
            double dv = Double.parseDouble(str[0].toString());
            return (dv >= Double.parseDouble(standvalue)) ? JUDGE_OK : JUDGE_NOTOK;
        } catch (Exception e) {
            return JUDGE_NO;
        }
    }

    public static String judgeFour(Object[] str, String standvalue, String testjudge) {
        try {
            double dv = Double.parseDouble(str[0].toString());
            return (dv > Double.parseDouble(standvalue)) ? JUDGE_OK : JUDGE_NOTOK;
        } catch (Exception e) {
            return JUDGE_NO;
        }
    }

    public static String judgeFive(Object[] str, String standvalue, String testjudge) {
        try {
            String actvalue = "";
            if (standvalue.indexOf("-") >= 0) {
                double dv = Double.parseDouble(str[0].toString());
                String[] standvalues = standvalue.split("-");
                actvalue = (dv >= Double.parseDouble(standvalues[0]) && dv <= Double.parseDouble(standvalues[1]))
                        ? JUDGE_OK
                        : JUDGE_NOTOK;
            } else if (standvalue.indexOf("~") >= 0) {
                double dv = Double.parseDouble(str[0].toString());
                String[] standvalues = standvalue.split("~");
                actvalue = (dv >= Double.parseDouble(standvalues[0]) && dv <= Double.parseDouble(standvalues[1]))
                        ? JUDGE_OK
                        : JUDGE_NOTOK;
            } else {
                String dv = str[0].toString();
                if (dv == "其他") {
                    actvalue = JUDGE_NO;
                } else if (dv.equals("合格")) {
                    actvalue = JUDGE_OK;
                } else if (dv.equals("阴性")) {
                    actvalue = JUDGE_OK;
                } else if (dv.equals("阳性(内标准基因)")) {
                    actvalue = JUDGE_OK;
                } else if (dv.equals("")) {
                    actvalue = JUDGE_NO;
                } else {
                    actvalue = DEF_VALUE;
                }
            }

            return actvalue;
        } catch (Exception e) {
            return JUDGE_NO;
        }
    }

    public static String testjudge(Object[] str, String standvalue, String testjudge, String deteclimit) {
        String actvalue = "";

        if (str[0].equals("ND")) {
            return actvalue = JUDGE_NO;
        } else if (testjudge.equals("≤")) {
            double dv = Double.parseDouble(str[0].toString());
            if (!"".equals(deteclimit) && dv < Double.parseDouble(deteclimit)) {
                return actvalue = JUDGE_NOTOK;
            }
            actvalue = judgeOne(str, standvalue, testjudge);
        } else if (testjudge.equals("＜")) {
            double dv = Double.parseDouble(str[0].toString());
            if (!"".equals(deteclimit) && dv < Double.parseDouble(deteclimit)) {
                return actvalue = JUDGE_NOTOK;
            }
            actvalue = judgeTwo(str, standvalue, testjudge);
        } else if (testjudge.equals("≥")) {
            actvalue = judgeThree(str, standvalue, testjudge);
        } else if (testjudge.equals("＞")) {
            actvalue = judgeFour(str, standvalue, testjudge);
        } else {
            actvalue = judgeFive(str, standvalue, testjudge);
        }

        return actvalue;
    }

}
