package com.alms.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import com.gpersist.utils.ToolUtils;

public class MathService {

    // region 平均值函数

    // 平均值（保留为三位小数）
    public static String Ave(List<String> datas, String defvalue) {
        if (datas.size() <= 0)
            return defvalue;

        try {
            String sumvalue = "0";
            BigDecimal newsumvalue = new BigDecimal(sumvalue);
            int count = datas.size();
            String result = "ND";

            for (String data : datas) {
                if (data.equals("ND")) {
                    newsumvalue = newsumvalue.add(new BigDecimal(0));
                    count = count - 1;
                } else {
                    newsumvalue = newsumvalue.add(new BigDecimal(data));
                }
            }

            if (count == 0) {
                return result;
            } else {
                // 字符串之间的除法运算
                BigDecimal newcount = new BigDecimal(count);
                return String.valueOf((newsumvalue.divide(newcount, 3, BigDecimal.ROUND_HALF_EVEN)));
            }
        } catch (Exception e) {
            return defvalue;
        }
    }

    // 平均值（保留为参数标准多一位小数）
    public static String Ave(List<String> datas, String defvalue, int dcimalDigits) {
        if (datas.size() <= 0)
            return defvalue;

        try {
            String sumvalue = "0";
            BigDecimal newsumvalue = new BigDecimal(sumvalue);
            int count = datas.size();
            String result = "ND";

            for (String data : datas) {
                if (data.equals("ND")) {
                    newsumvalue = newsumvalue.add(new BigDecimal(0));
                    count = count - 1;
                } else {
                    newsumvalue = newsumvalue.add(new BigDecimal(data));
                }
            }

            if (count == 0) {
                return result;
            } else {
                // 字符串之间的除法运算
                BigDecimal newcount = new BigDecimal(count);
                // ROUND_HALF_DOWN五舍六入 ROUND_HALF_UP四舍五入
                // ROUND_HALF_EVEN 银行家舍入法”，主要在美国使用。四舍六入，五分两种情况。如果前一位为奇数，则入位，否则舍去。

                return String.valueOf((newsumvalue.divide(newcount, dcimalDigits, BigDecimal.ROUND_HALF_EVEN)));
            }
        } catch (Exception e) {
            return defvalue;
        }
    }

    // 相对相差
    public static String Differ(List<String> datas, String defvalue) {
        if (datas.size() <= 0)
            return defvalue;

        try {
            String sumvalue = "0";
            BigDecimal newsumvalue = new BigDecimal(sumvalue);
            BigDecimal addsumvalue = new BigDecimal(sumvalue);
            int count = datas.size();
            String result = "ND";

            for (String data : datas) {
                if (data.equals("ND")) {
                    newsumvalue = newsumvalue.add(new BigDecimal(0));
                    addsumvalue = addsumvalue.add(new BigDecimal(0));
                    count = count - 1;
                } else {
                    newsumvalue = new BigDecimal(data).subtract(newsumvalue);
                    addsumvalue = new BigDecimal(data).add(addsumvalue);
                }
            }

            if (count == 0) {
                return result;
            } else {
                BigDecimal newcount = new BigDecimal(count);
                BigDecimal rr = addsumvalue.divide(newcount, 2, BigDecimal.ROUND_HALF_EVEN);

                // 小数格式替换成百分号
                NumberFormat nt = NumberFormat.getPercentInstance();
                nt.setMinimumFractionDigits(2);
                nt.format(newsumvalue.divide(rr, 2, BigDecimal.ROUND_HALF_EVEN));

                return String.valueOf(nt.format(newsumvalue.divide(rr, 2, BigDecimal.ROUND_HALF_EVEN))).replace("-",
                        "");

            }
        } catch (Exception e) {
            return defvalue;
        }
    }

    // 牛顿迭代开平方（因为BigDecimal类型精度较高，Java没有提供封装好的有效的方法）
    public static BigDecimal sqrt(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal x = num.divide(new BigDecimal("2"), MathContext.DECIMAL128);
        while (x.subtract(x = sqrtIteration(x, num)).abs().compareTo(new BigDecimal("0.0000000000000000000001")) > 0)
            ;
        return x;
    }

    private static BigDecimal sqrtIteration(BigDecimal x, BigDecimal n) {
        return x.add(n.divide(x, MathContext.DECIMAL128)).divide(new BigDecimal("2"), MathContext.DECIMAL128);
    }

    // 相对标准偏差
    public static String RelativeDiffer(List<String> datas, String defvalue) {
        if (datas.size() <= 0)
            return defvalue;

        try {
            String sumvalue = "0";
            BigDecimal newsumvalue = new BigDecimal(sumvalue);
            BigDecimal addsumvalue = new BigDecimal(sumvalue);
            int count = datas.size();
            String result = "ND";

            for (String data : datas) {
                if (data.equals("ND")) {
                    newsumvalue = newsumvalue.add(new BigDecimal(0));
                    addsumvalue = addsumvalue.add(new BigDecimal(0));
                    count = count - 1;
                } else {
                    addsumvalue = new BigDecimal(data).add(addsumvalue);
                }
            }

            if (count == 0) {
                return result;
            } else {
                BigDecimal newcount = new BigDecimal(count);
                // 平均值
                BigDecimal rr = addsumvalue.divide(newcount, 2, BigDecimal.ROUND_HALF_EVEN);
                for (String data : datas) {
                    newsumvalue = new BigDecimal(data).subtract(rr).pow(2).add(newsumvalue);
                    System.out.println(newsumvalue);
                }

                BigDecimal r = sqrt(newsumvalue);

                // 小数格式替换成百分号
                NumberFormat nt = NumberFormat.getPercentInstance();
                nt.setMinimumFractionDigits(2);
                nt.format(r.divide(rr, 4, BigDecimal.ROUND_HALF_EVEN));

                return String.valueOf(nt.format(r.divide(rr, 4, BigDecimal.ROUND_HALF_EVEN))).replace("-", "");

            }
        } catch (Exception e) {
            return defvalue;
        }
    }

    // 选择
    public static String choice(List<String> datas, String defvalue) {
        if (datas.size() <= 0)
            return defvalue;

        try {
            String sumvalue = "";

            for (String data : datas) {
                if (data.equals("01")) {
                    sumvalue = "合格";
                } else if (data.equals("02")) {
                    sumvalue = "不合格";
                } else if (data.equals("03")) {
                    sumvalue = "其他";
                } else if (data.equals("阴性(外标准基因)")) {
                    sumvalue = "阴性";
                } else if (data.equals("阳性(内标准基因)")) {
                    sumvalue = "阳性(内标准基因)";
                } else if (data.equals("阳性(外标准基因)")) {
                    sumvalue = "阳性";
                } else if (data.equals("阴性(内标准基因)")) {
                    sumvalue = "阴性(内标准基因)";
                } else if (data.equals("合格")) {
                    sumvalue = "合格";
                } else if (data.equals("不合格")) {
                    sumvalue = "不合格";
                }
            }

            return sumvalue;

        } catch (Exception e) {
            return defvalue;
        }
    }

    public static String AveMean3(List<String> datas, double diff, String defvalue) {
        if (datas.size() < 3)
            return defvalue;

        try {
            double[] values = { Double.valueOf(datas.get(0)), Double.valueOf(datas.get(1)),
                    Double.valueOf(datas.get(2)) };
            Arrays.sort(values);

            double meanvalue = (values[0] + values[1] + values[2]) / 3;

            if (((values[2] - meanvalue) / meanvalue > diff / 100)
                    && ((meanvalue - values[0]) / meanvalue > diff / 100))
                return defvalue;

            if ((values[2] - meanvalue) / meanvalue > diff / 100)
                return String.valueOf((values[0] + values[1]) / 2);

            if ((meanvalue - values[0]) / meanvalue > diff / 100)
                return String.valueOf((values[1] + values[2]) / 2);

            return String.valueOf(meanvalue);

        } catch (Exception e) {
            return defvalue;
        }
    }

    public static String AveMiddle3(List<String> datas, double diff, String defvalue) {
        if (datas.size() < 3)
            return defvalue;

        try {
            double[] values = { Double.valueOf(datas.get(0)), Double.valueOf(datas.get(1)),
                    Double.valueOf(datas.get(2)) };
            Arrays.sort(values);

            double meanvalue = (values[0] + values[1] + values[2]) / 3;

            if (((values[2] - values[1]) / values[1] > diff / 100)
                    && ((values[1] - values[0]) / values[1] > diff / 100))
                return defvalue;

            if ((values[2] - values[1]) / values[1] > diff / 100)
                return String.valueOf((values[0] + values[1]) / 2);

            if ((values[1] - values[0]) / values[1] > diff / 100)
                return String.valueOf((values[1] + values[2]) / 2);

            return String.valueOf(meanvalue);

        } catch (Exception e) {
            return defvalue;
        }
    }

    public static String AveRetain3(List<String> datas, double diff, String defvalue) {
        if (datas.size() < 3)
            return defvalue;

        try {
            double[] values = { Double.valueOf(datas.get(0)), Double.valueOf(datas.get(1)),
                    Double.valueOf(datas.get(2)) };
            Arrays.sort(values);

            double meanvalue = (values[0] + values[1] + values[2]) / 3;

            if (((values[2] - values[1]) / values[1] > diff / 100)
                    && ((values[1] - values[0]) / values[1] > diff / 100))
                return defvalue;

            if ((values[2] - values[1]) / values[1] > diff / 100)
                return String.valueOf(values[1]);

            if ((values[1] - values[0]) / values[1] > diff / 100)
                return String.valueOf(values[1]);

            return String.valueOf(meanvalue);

        } catch (Exception e) {
            return defvalue;
        }
    }

    public static String AveDiff3(List<String> datas, double diff, String defvalue) {
        if (datas.size() < 3)
            return defvalue;

        try {
            double[] values = { Double.valueOf(datas.get(0)), Double.valueOf(datas.get(1)),
                    Double.valueOf(datas.get(2)) };
            Arrays.sort(values);

            double meanvalue = (values[0] + values[1] + values[2]) / 3;

            if (((values[2] - values[1]) / values[1] > diff) && ((values[1] - values[0]) / values[1] > diff))
                return defvalue;

            if ((values[2] - values[1]) / values[1] > diff)
                return String.valueOf(values[1]);

            if ((values[1] - values[0]) / values[1] > diff)
                return String.valueOf(values[1]);

            return String.valueOf(meanvalue);

        } catch (Exception e) {
            return defvalue;
        }
    }

    public static String AveRefre3(List<String> datas, double diff, double refrevalue, String defvalue) {
        if (datas.size() < 3)
            return defvalue;

        try {
            double[] values = { Double.valueOf(datas.get(0)), Double.valueOf(datas.get(1)),
                    Double.valueOf(datas.get(2)) };
            Arrays.sort(values);

            double meanvalue = (values[0] + values[1] + values[2]) / 3;

            int cnt = 0;
            boolean is01 = false;
            boolean is02 = false;
            boolean is03 = false;

            if (Math.abs((values[0] - refrevalue) / refrevalue) > diff / 100) {
                cnt++;
                is01 = true;
            }

            if (Math.abs((values[1] - refrevalue) / refrevalue) > diff / 100) {
                cnt++;
                is02 = true;
            }

            if (Math.abs((values[3] - refrevalue) / refrevalue) > diff / 100) {
                cnt++;
                is03 = true;
            }

            if (cnt >= 2)
                return defvalue;

            if (cnt == 1) {
                if (is01)
                    return String.valueOf((values[1] + values[2]) / 2);

                if (is02)
                    return String.valueOf((values[0] + values[2]) / 2);

                if (is03)
                    return String.valueOf((values[0] + values[1]) / 2);
            }

            return String.valueOf(meanvalue);

        } catch (Exception e) {
            return defvalue;
        }
    }

    public static String AveMean6(List<String> datas, double diff, String defvalue) {
        if (datas.size() != 6)
            return defvalue;

        try {
            double[] values = { Double.valueOf(datas.get(0)), Double.valueOf(datas.get(1)),
                    Double.valueOf(datas.get(2)), Double.valueOf(datas.get(3)), Double.valueOf(datas.get(4)),
                    Double.valueOf(datas.get(5)) };
            Arrays.sort(values);

            double meanvalue = (values[0] + values[1] + values[2] + values[3] + values[4] + values[5]) / 6;

            int cnt = 0;

            for (int i = 0; i < values.length; i++) {
                if (Math.abs((values[i] - meanvalue) / meanvalue) > diff / 100) {
                    cnt++;
                }
            }

            if (cnt >= 2)
                return defvalue;

            if ((values[5] - meanvalue) / meanvalue > diff / 100)
                return String.valueOf((values[0] + values[1] + values[2] + values[3] + values[4]) / 5);

            if ((meanvalue - values[0]) / meanvalue > diff / 100)
                return String.valueOf((values[1] + values[2] + values[3] + values[4] + values[5]) / 5);

            return String.valueOf(meanvalue);

        } catch (Exception e) {
            return defvalue;
        }
    }

    public static String AveRetain6(List<String> datas, double diff, String defvalue) {
        if (datas.size() != 6)
            return defvalue;

        try {
            double[] values = { Double.valueOf(datas.get(0)), Double.valueOf(datas.get(1)),
                    Double.valueOf(datas.get(2)), Double.valueOf(datas.get(3)), Double.valueOf(datas.get(4)),
                    Double.valueOf(datas.get(5)) };
            Arrays.sort(values);

            double meanvalue = (values[0] + values[1] + values[2] + values[3] + values[4] + values[5]) / 6;

            int cnt = 0;

            for (int i = 0; i < values.length; i++) {
                if (Math.abs((values[i] - meanvalue) / meanvalue) > diff / 100) {
                    cnt++;
                }
            }

            if (cnt >= 2)
                return defvalue;

            if (cnt > 0)
                return String.valueOf((values[1] + values[2] + values[3] + values[4]) / 4);

            return String.valueOf(meanvalue);

        } catch (Exception e) {
            return defvalue;
        }
    }

    // endregion 平均值函数

    // region 统计函数

    public static String statcount(List<String> datas, String defvalue) {
        return String.valueOf(datas.size());
    }

    public static String statsum(List<String> datas, String defvalue) {
        if (datas.size() <= 0)
            return defvalue;

        try {

            double sumvalue = 0;

            for (String data : datas) {
                if (ToolUtils.StringIsEmpty(data))
                    continue;

                sumvalue += Double.valueOf(data);
            }

            return String.valueOf(sumvalue);

        } catch (Exception e) {
            return defvalue;
        }
    }

    public static String statmax(List<String> datas, String defvalue) {
        if (datas.size() <= 0)
            return defvalue;

        try {

            double maxvalue = 0;

            for (String data : datas) {
                maxvalue = Double.valueOf(data) > maxvalue ? Double.valueOf(data) : maxvalue;
            }

            return String.valueOf(maxvalue);

        } catch (Exception e) {
            return defvalue;
        }
    }

    public static String statmin(List<String> datas, String defvalue) {
        if (datas.size() <= 0)
            return defvalue;

        try {

            double minvalue = 0;

            if (datas.size() > 0)
                minvalue = Double.valueOf(datas.get(0));

            for (String data : datas) {
                minvalue = Double.valueOf(data) < minvalue ? Double.valueOf(data) : minvalue;
            }

            return String.valueOf(minvalue);

        } catch (Exception e) {
            return defvalue;
        }
    }

    public static String statmiddle(List<String> datas, String defvalue) {
        if (datas.size() <= 0)
            return defvalue;

        try {
            double[] values = new double[datas.size()];

            for (int i = 0; i < values.length; i++) {
                values[i] = Double.valueOf(datas.get(i));
            }
            Arrays.sort(values);

            if (datas.size() % 2 == 0)
                return String.valueOf((values[datas.size() / 2] + values[datas.size() / 2 + 1]));
            else {
                return String.valueOf(values[(datas.size() + 1) / 2]);
            }

        } catch (Exception e) {
            return defvalue;
        }
    }

    // endregion 统计函数

}
