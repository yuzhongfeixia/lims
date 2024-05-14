package com.gpersist.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 
 * @文件 StringUtils
 * @说明 字符串处理公共函数
 * @公司 南京古方科技有限公司
 * @版权 Copyright (c) 2010-2014
 * 
 * @author cloudy
 * @version 2014-09-20
 *
 */

public class StringUtils {

    /**
     * <p>
     * 将半角的符号转换成全角符号.(即英文字符转中文字符)
     * </p>
     * 
     * @author cloudy
     * @version 2014-09-20
     * 
     * @param in 待处理的字符串
     * @return String 处理后的字符串
     * 
     */

    public static String HalfToFull(String in) {
        char c[] = in.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }

        return new String(c);
    }

    /**
     * <p>
     * 将全角的符号转换成半角符号.(即中文字符转英文字符)
     * </p>
     * 
     * @author cloudy
     * @version 2014-09-20
     * 
     * @param in 待处理的字符串
     * @return String 处理后的字符串
     * 
     */

    public static String FullToHalf(String in) {
        char c[] = in.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }

        return new String(c);
    }

    /**
     * <p>
     * 进行ToString操作，如果传入的是null，返回空字符串
     * </p>
     *
     * <pre>
     * StringUtils.toString(null)         = ""
     * StringUtils.toString("")           = ""
     * StringUtils.toString("test")        = "test"
     * StringUtils.toString(Boolean.TRUE) = "true"
     * </pre>
     *
     * @author cloudy
     * @version 2014-09-20
     * 
     * @param in 待处理的对象
     * @return String 处理后的字符串
     * 
     */

    public static String ToString(Object in) {
        return in == null ? "" : in.toString();
    }

    /**
     * <p>
     * 判断字符串是否为空
     * </p>
     *
     * <pre>
     * StringUtils.StringIsEmpty(null) = true
     * StringUtils.StringIsEmpty("") = true
     * StringUtils.StringIsEmpty("test") = false
     * </pre>
     *
     * @author cloudy
     * @version 2014-09-20
     * 
     * @param in 待处理的字符串
     * @return boolean 为空返回true,不为空返回false
     * 
     */

    public static boolean StringIsEmpty(String in) {
        return (in == null || in.isEmpty());
    }

    /**
     * <p>
     * 获得一个新行
     * </p>
     *
     * @author cloudy
     * @version 2014-09-20
     * 
     * @return String 返回一个空白行
     * 
     */

    public static String GetNewLines() {
        return System.getProperty("line.separator");
    }

    /**
     * <p>
     * 从左边填充指定的字符,并按指定长度扩展字符串长度
     * </p>
     *
     * <pre>
     * StringUtils.LeftFill("test", 6, 'a') = "aatest"
     * StringUtils.LeftFill("test", 4, 'a') = "test"
     * </pre>
     *
     * @see #StringIsEmpty
     * 
     * @author cloudy
     * @version 2014-09-20
     * 
     * @param in   待处理的字符串
     * @param len  左填充后的字符串长度
     * @param fill 填充采用的字符
     * @return String 填充后的字符串
     * 
     */

    public static String LeftFill(String in, int len, char fill) {
        int j = 0;
        if (StringIsEmpty(in))
            j = 0;
        else
            j = in.length();

        for (int i = 0; i < len - j; i++) {
            in = String.valueOf(fill) + in;
        }

        return in;
    }

    /**
     * <p>
     * 从左边填充'0',并按指定长度扩展字符串长度
     * </p>
     *
     * <pre>
     * StringUtils.LeftFill("test", 6) = "00test"
     * </pre>
     * 
     * @author cloudy
     * @version 2014-09-20
     * 
     * @param in  待处理的字符串
     * @param len 左填充后的字符串长度
     * @return String 填充后的字符串
     * 
     */

    public static String LeftFill(String in, int len) {
        return LeftFill(in, len, '0');
    }

    /**
     * <p>
     * 从右边填充指定的字符,并按指定长度扩展字符串长度
     * </p>
     *
     * <pre>
     * StringUtils.RightFill("test", 6, 'a') = "testaa"
     * StringUtils.RightFill("test", 4, 'a') = "test"
     * </pre>
     *
     * @see #StringIsEmpty
     * 
     * @author cloudy
     * @version 2014-09-20
     * 
     * @param in   待处理的字符串
     * @param len  右填充后的字符串长度
     * @param fill 填充采用的字符
     * @return String 填充后的字符串
     * 
     */

    public static String RightFill(String in, int len, char fill) {
        int j = 0;
        if (StringIsEmpty(in))
            j = 0;
        else
            j = in.length();

        for (int i = 0; i < len - j; i++) {
            in += String.valueOf(fill);
        }

        return in;
    }

    /**
     * <p>
     * 从右边填充'0',并按指定长度扩展字符串长度
     * </p>
     *
     * <pre>
     * StringUtils.LeftFill("test", 6) = "test00"
     * </pre>
     * 
     * @author cloudy
     * @version 2014-09-20
     * 
     * @param in  待处理的字符串
     * @param len 右填充后的字符串长度
     * @return String 填充后的字符串
     * 
     */

    public static String RightFill(String in, int len) {
        return RightFill(in, len, '0');
    }

    public static String Decode(String decode) {
        try {
            decode = URLDecoder.decode(decode, "UTF-8");
        } catch (Exception e) {
            // TODO: handle exception
        }

        return decode;
    }

    public static String Encode(String encode) {
        try {
            encode = URLEncoder.encode(encode, "UTF-8");
        } catch (Exception e) {
            // TODO: handle exception
        }

        return encode;
    }

    public static String GetIndentFmt(String value, String fmt, int indent) {
        String rtn = value;

        for (int i = 0; i < indent; i++) {
            rtn = fmt + rtn;
        }

        return rtn;
    }

    public static double GetDoubleByString(String in) {
        double rtn = 0;

        try {
            rtn = Double.valueOf(in);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return rtn;
    }

    public static String GetBeginDate(String begin) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return "to_date('" + begin + " 00:00:00', 'yyyy-mm-dd hh24:mi:ss')";

        default:
            return "'" + begin + " 00:00:00'";
        }
    }

    public static String GetEndDate(String end) {
        switch (ToolUtils.GetDataBaseType()) {
        case Oracle10:
            return "to_date('" + end + " 23:59:59', 'yyyy-mm-dd hh24:mi:ss')";

        default:
            return "'" + end + " 23:59:59'";
        }
    }

    public static String GetAndSearch(String search) {
        if (!StringIsEmpty(search))
            return " and ";
        else
            return "";
    }
}
