package com.gpersist.utils;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import com.gpersist.entity.log.TranLog;
import com.gpersist.entity.system.SysSet;
import com.gpersist.entity.user.OnlineUser;
import com.gpersist.enums.DataBaseType;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.star.document.FilterOptionsRequest;
import com.sun.star.io.IOException;
import com.sun.star.lib.uno.environments.java.java_environment;

public class ToolUtils2 {
    public static DataBaseType GetDataBaseType() {
        return DataBaseType.parse(PropertiesUtils.pps.getProperty(Consts.CONFIG_DATABASE_TYPE));
    }

    public static String GetSetValue(OnlineUser ou, String key) {
        for (SysSet set : ou.getSets()) {
            if (set.getSetcode().equals(key))
                return set.getSetvalue();
        }

        return "";
    }

    public static OnlineUser GetOnlineUser() {
        return (OnlineUser) ActionContext.getContext().getSession().get(Consts.DEFAULT_USER);
    }

    public static Date GetMinDate() {
        return new Date(0);
    }

    public static Date GetNowDate() {
        return new Date();
    }

    public static boolean StringIsEmpty(String val) {
        return (val == null || val.isEmpty());
    }

    public static String GetNewLines() {
        return System.getProperty("line.separator");
    }

    public static String GetFmtDate(Date in) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(in);
    }

    public static String GetFmtDate(Date in, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(in);
    }

    public static Date GetDateByFmt(String in, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        try {
            return sdf.parse(in);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String GetHMFmtDate(Date in) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(in);
    }

    public static String GetHMSFmtDate(Date in) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(in);
    }

    /**
     * 为啥SimpleDateFormat用parse抛异常而用format就不用呢,为何要如此设计，有那么点意思<-_->
     * 难道是设计者默认你的格式都是正确的时间格式你不会捣乱输入随意的字符串作为格式吗？
     * 
     * @param in
     * @return
     */
    public static String GetDebugDate(Date in) {
        if (in == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // sdf.parse("");
        return sdf.format(in);
    }

    public static String GetLongFmtDate(Date in) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(in);
    }

    public static Date String2Date(String in) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(in);
    }

    // 对于这个简单的转换操作能否把它升级一下：按层级递归的生成复杂结构的json字符串？？
    public static String GetJsonFormBean(Object bean) {
        JsonConfig config = new JsonConfig();
        config.registerJsonPropertyNameProcessor(bean.getClass(), new FirstPropertyNameProcessor());
        JSONObject jsonObject = JSONObject.fromObject(bean, config);
        return jsonObject.toString();
    }

    public static String GetJsonFromBean(Object bean, JsonConfig config) {
        JSONObject jsonObject = JSONObject.fromObject(bean, config);
        String rtn = jsonObject.toString();
        return rtn;
    }

    @SuppressWarnings("rawtypes")
    public static String GetJsonFromArray(Object bean, Class classtype) {
        JsonConfig config = new JsonConfig();
        config.registerJsonPropertyNameProcessor(classtype, new FirstPropertyNameProcessor());
        JSONArray jsonArray = JSONArray.fromObject(bean, config);
        return jsonArray.toString();
    }

    public static String GetJsonFromArray(Object bean, JsonConfig config) {
        JSONArray jsonArray = JSONArray.fromObject(bean, config);
        return jsonArray.toString();
    }

    public static String GetJsonFromArray(Object bean) {
        return JSONArray.fromObject(bean).toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> GetArrayFromJson(String json, Class<T> classtype) {
        JSONArray jsonArray = JSONArray.fromObject(json);
        String[] dateFormats = new String[] { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" };
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(dateFormats));
        Collection<T> collections = JSONArray.toCollection(jsonArray, classtype);
        List<T> lists = new ArrayList<T>();
        for (T t : collections) {
            if (classtype.equals(t.getClass()))
                lists.add(t);
        }
        return lists;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> GetArrayFromJson(JSONArray jsonarray, Class<T> classtype) {
        String[] dateFormats = new String[] { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" };
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(dateFormats));
        Collection<T> collections = JSONArray.toCollection(jsonarray, classtype);
        List<T> lists = new ArrayList<T>();
        for (T t : collections) {
            if (classtype.equals(t.getClass()))
                lists.add(t);
        }
        return lists;
    }

    public static String GetMD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StringBuffer.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StringBuffer.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StringBuffer.toString().toUpperCase();

    }

    public static void OutString(String str) {
        try {
            ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
            PrintWriter out = ServletActionContext.getResponse().getWriter();
            out.write(str);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void JsonOutString(String str) {
        try {
            ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
            PrintWriter out = ServletActionContext.getResponse().getWriter();
            out.write(str);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static TranLog InitTranLog(String mcode) {
        TranLog log = new TranLog();
        OnlineUser ou = (OnlineUser) ActionContext.getContext().getSession().get(Consts.DEFAULT_USER);
        log.setTranuser(ou.getUser().getUserid());
        log.setUsername(ou.getUser().getDeptid());
        log.setUsername(ou.getUser().getUsername());
        log.setTrancode(mcode);
        return log;
    }

    public static String GetBeginDate(String begin) {
        switch (GetDataBaseType()) {
        case Oracle10:
            return "to_date('" + begin + " 00:00:00','yyyy-mm-dd hh24:mi:ss')";
        default:
            return "'" + begin + " 00:00:00'";
        }
    }

    public static String GetEndDate(String end) {
        switch (GetDataBaseType()) {
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

    public static String Decode(String decode) {
        try {
            decode = URLDecoder.decode(decode, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decode;
    }

    public static String Encode(String encode) {
        try {
            encode = URLEncoder.encode(encode, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encode;
    }

    public static String LeftFill(String value, int len) {
        return LeftFill(value, len, '0');
    }

    // / <summary>
    // / 按指定字符串左填充字符串
    // / </summary>
    // / <param name="Value">需要进行填充的字符串</param>
    // / <param name="Len">填充后的长度</param>
    // / <param name="Fill">指定填充的字符</param>
    // / <returns>填充后的字符串</returns>
    public static String LeftFill(String Value, int Len, char Fill) {
        int j = 0;
        if (StringIsEmpty(Value))
            j = 0;
        else
            j = Value.length();

        for (int i = 0; i < Len - j; i++) {
            Value = String.valueOf(Fill) + Value;
        }

        return Value;
    }

    public static void main(String[] args) {

        // System.out.print("Hello" + GetNewLines() + "world" + GetNewLines());
        // System.out.print(GetHMFmtDate(new Date()) + GetNewLines());
        // System.out.print(GetHMSFmtDate(new Date())+GetNewLines());
        // try {
        // System.out.println(String2Date("2017-10-31 23:45:56") instanceof Date);
        // } catch (ParseException e) {
        // e.printStackTrace();
        // }
        MPOJO pojo = new MPOJO(1, "MARLOWE");
        System.out.println(GetJsonFormBean(pojo));
        System.out.println(GetJsonFromArray(pojo, MPOJO.class));
    }

    // / <summary>
    // / 按指定字符串右填充字符串
    // / </summary>
    // / <param name="Value">需要进行填充的字符串</param>
    // / <param name="Len">填充后的长度</param>
    // / <param name="Fill">指定填充的字符</param>
    // / <returns>填充后的字符串</returns>
    public static String RightFill(String Value, int Len, char Fill) {
        int j = 0;
        if (StringIsEmpty(Value))
            j = 0;
        else
            j = Value.length();

        for (int i = 0; i < Len - j; i++) {
            Value += String.valueOf(Fill);
        }

        return Value;
    }

    public static boolean IsValidUser(OnlineUser ou) {
        return (ou != null);
    }

    public static int GetDaysBetween(Date beginDate, Date endDate) throws ParseException {
        Calendar d1 = new GregorianCalendar();
        d1.setTime(beginDate);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(endDate);
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
            } while (d1.get(Calendar.YEAR) != y2);
        }

        return days;

    }

    public static String WriteEnter() {
        return WriteEnter(1);
    }

    public static String WriteEnter(int count) {
        String rtn = "";
        for (int i = 0; i < count; i++) {
            rtn += "\r\n";
        }
        return rtn;
    }

    public static String GetErrorMessage(Exception e) {
        return GetErrorMessage(e, "");
    }

    public static String GetErrorMessage(Exception e, String prefix) {
        String rtn = "";

        if (e.getCause() instanceof SQLServerException) {

            SQLServerException sex = (SQLServerException) e.getCause();

            int sqlcode = sex.getErrorCode();

            switch (sqlcode) {
            case 2601:
                rtn = prefix + "存在重复数据！";
                break;

            case 18456:
                rtn = prefix + "数据库登录失败！";
                break;

            default:
                rtn = prefix + sex.getMessage();
                break;
            }
        } else if (e.getCause() instanceof java.sql.SQLException) {
            java.sql.SQLException oex = (java.sql.SQLException) e.getCause();

            switch (oex.getErrorCode()) {
            case 17002:
                rtn = prefix + "数据库登录失败！";
                break;

            default:
                rtn = prefix + oex.getMessage();
                break;
            }
        } else
            rtn = prefix + e.getMessage();

        return rtn.replace('"', '\'');
    }

    public static boolean MustComboValue(String value) {
        if (StringIsEmpty(value))
            return true;
        if (value.equals(Consts.SELECT_MUST_VALUE))
            return true;
        return false;
    }

    public static boolean CheckComboValue(String value) {
        if (StringIsEmpty(value))
            return true;
        if (value.equals(Consts.SELECT_MUST_VALUE))
            return true;
        if (value.equals(Consts.SELECT_ALL_VALUE))
            return true;
        return false;
    }

    public static boolean AllComboValue(String value) {
        if (StringIsEmpty(value))
            return true;
        if (value.equals(Consts.SELECT_ALL_VALUE))
            return true;
        return false;
    }
}
