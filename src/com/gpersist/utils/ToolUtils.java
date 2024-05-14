package com.gpersist.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.struts2.ServletActionContext;

import com.gpersist.dao.UserDao;
import com.gpersist.entity.BaseBean;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.entity.system.*;
import com.gpersist.entity.user.*;
import com.gpersist.entity.log.*;
import com.gpersist.enums.DataBaseType;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.opensymphony.xwork2.ActionContext;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.*;
import net.sf.json.util.JSONUtils;

public class ToolUtils {

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
        return new java.util.Date(0);
    }

    public static Date GetNowDate() {
        return new java.util.Date();
    }

    public static boolean StringIsEmpty(String val) {
        return (val == null || val.isEmpty() || val.equals("null"));
    }

    public static String GetNewLines() {
        return System.getProperty("line.separator");
    }

    public static String GetFmtDate(Date in) {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

        return sp.format(in);
    }

    public static String GetFmtDate(Date in, String fmt) {
        SimpleDateFormat sp = new SimpleDateFormat(fmt);

        return sp.format(in);
    }

    public static java.util.Date GetDateByFmt(String in, String fmt) {
        SimpleDateFormat sp = new SimpleDateFormat(fmt);

        try {
            return sp.parse(in);
        } catch (Exception e) {
            return new java.util.Date();
        }
    }

    public static String GetHMFmtDate(Date in) {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return sp.format(in);
    }

    public static String GetDebugDate(Date in) {
        if (in == null)
            return "";

        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return sp.format(in);
    }

    public static String GetLongFmtDate(Date in) {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return sp.format(in);
    }

    public static java.util.Date String2Date(String in) throws ParseException {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return sp.parse(in);
    }

    public static String GetJsonFromBean(Object bean) {
        JsonConfig config = new JsonConfig();
        config.registerJsonPropertyNameProcessor(bean.getClass(), new FirstPropertyNameProcessor());

        JSONObject JsonObject = JSONObject.fromObject(bean, config);

        return JsonObject.toString();
    }

    public static String GetJsonFromBean(Object bean, JsonConfig config) {
        JSONObject JsonObject = JSONObject.fromObject(bean, config);

        String rtn = JsonObject.toString();

        return rtn;
    }

    @SuppressWarnings("rawtypes")
    public static String GetJsonFromArray(Object bean, Class classtype) {
        JsonConfig config = new JsonConfig();
        config.registerJsonPropertyNameProcessor(classtype, new FirstPropertyNameProcessor());

        JSONArray JsonObject = JSONArray.fromObject(bean, config);
        return JsonObject.toString();
    }

    public static String GetJsonFromArray(Object bean, JsonConfig config) {
        JSONArray JsonObject = JSONArray.fromObject(bean, config);
        return JsonObject.toString();
    }

    public static String GetJsonFromArray(Object bean) {
        return JSONArray.fromObject(bean).toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> GetArrayFromJson(String json, Class<T> classtype) {
        JSONArray JsonObject = JSONArray.fromObject(json);

        String[] dateFormats = new String[] { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" };

        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

        Collection<T> collections = JSONArray.toCollection(JsonObject, classtype);
        List<T> lists = new ArrayList<T>();
        for (T t : collections) {
            if (classtype.equals(t.getClass()))
                lists.add(t);
        }
        return lists;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> GetArrayFromJson(JSONArray JsonObject, Class<T> classtype) {
        String[] dateFormats = new String[] { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" };

        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

        Collection<T> collections = JSONArray.toCollection(JsonObject, classtype);
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

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString().toUpperCase();
    }

    public static void OutString(String str) {
        try {
            ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
            PrintWriter out = ServletActionContext.getResponse().getWriter();
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void JsonOutString(String str) {
        try {
            ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
            PrintWriter out = ServletActionContext.getResponse().getWriter();
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TranLog InitTranLog(String mcode) {
        TranLog log = new TranLog();

        OnlineUser ou = (OnlineUser) ActionContext.getContext().getSession().get(Consts.DEFAULT_USER);

        log.setTranuser(ou.getUser().getUserid());
        log.setTrandept(ou.getUser().getDeptid());
        log.setUsername(ou.getUser().getUsername());
        log.setTrancode(mcode);

        return log;
    }

    public static String GetBeginDate(String begin) {
        switch (GetDataBaseType()) {
        case Oracle10:
            return "to_date('" + begin + " 00:00:00', 'yyyy-mm-dd hh24:mi:ss')";

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

    public static String LeftFill(String Value, int Len) {
        return LeftFill(Value, Len, '0');
    }

    /// <summary>
    /// 按指定字符串左填充字符串
    /// </summary>
    /// <param name="Value">需要进行填充的字符串</param>
    /// <param name="Len">填充后的长度</param>
    /// <param name="Fill">指定填充的字符</param>
    /// <returns>填充后的字符串</returns>
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

    /// <summary>
    /// 右填充字符串（默认填充0）
    /// </summary>
    /// <param name="Value">需要进行填充的字符串</param>
    /// <param name="Len">填充后的长度</param>
    /// <returns>填充后的字符串</returns>
    public static String RightFill(String Value, int Len) {
        return RightFill(Value, Len, '0');
    }

    /// <summary>
    /// 按指定字符串右填充字符串
    /// </summary>
    /// <param name="Value">需要进行填充的字符串</param>
    /// <param name="Len">填充后的长度</param>
    /// <param name="Fill">指定填充的字符</param>
    /// <returns>填充后的字符串</returns>
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

    public static int GetDaysBetween(java.util.Date beginDate, java.util.Date endDate) throws ParseException {
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
                d1.add(Calendar.YEAR, 1);
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
            case 2627:
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

    public static boolean NullComboValue(String value) {
        if (StringIsEmpty(value))
            return true;

        if (value.equals(Consts.SELECT_NULL_VALUE))
            return true;

        return false;
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

    public static String ConvertObjectValue(Object in) {
        String rtn = "";

        if (in instanceof java.util.Date) {
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            rtn = sp.format(in);
        } else
            rtn = in.toString();

        return rtn;
    }

    public static <T> String CompareProperty(T source, T target, String[] propertynames) throws Exception {
        StringBuilder sb = new StringBuilder();

        for (String property : propertynames) {
            String[] propertys = property.split(":");

            if (propertys.length != 2)
                throw new Exception("对象比较时配置参数出错:" + property);

            Field fld = source.getClass().getDeclaredField(propertys[1]);
            fld.setAccessible(true);

            Object svalue = fld.get(source);
            Object tvalue = fld.get(target);

            if ((svalue != null) && (tvalue != null)) {
                if (!ConvertObjectValue(svalue).equals(ConvertObjectValue(tvalue)))
                    sb.append(propertys[0] + "-" + propertys[1] + ":[" + ConvertObjectValue(svalue) + "]-["
                            + ConvertObjectValue(tvalue) + "];" + WriteEnter());
            } else if ((svalue != null) && (tvalue == null)) {
                // source不为空 target为空
                sb.append(propertys[0] + "-" + propertys[1] + ":[" + ConvertObjectValue(svalue) + "]-[空];"
                        + WriteEnter());
            } else if ((svalue == null) && (tvalue != null)) {
                // source不为空 target为空
                sb.append(propertys[0] + "-" + propertys[1] + ":[空]-[" + ConvertObjectValue(tvalue) + "];"
                        + WriteEnter());
            }
        }

        return sb.toString();
    }

    public static <T> String DebugProperty(T source, String[] propertynames) {
        StringBuilder sb = new StringBuilder();

        for (String property : propertynames) {
            String[] propertys = property.split(":");

            if (propertys.length == 2) {
                try {
                    Field fld = source.getClass().getDeclaredField(propertys[1]);
                    fld.setAccessible(true);

                    Object svalue = fld.get(source);

                    if ((svalue != null))
                        sb.append(propertys[0] + "-" + propertys[1] + ":[" + ConvertObjectValue(svalue) + "];"
                                + WriteEnter());
                    else {
                        sb.append(propertys[0] + "-" + propertys[1] + ":[];" + WriteEnter());
                    }

                } catch (Exception e) {

                }
            }
        }

        return sb.toString();

    }

    public static String CryptSHA256(String in) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(in.getBytes("UTF-8"));

            StringBuffer out = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    out.append('0');
                out.append(hex);
            }

            return out.toString();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return "";
    }

    public static <T> String OutBean(T item, boolean hasdate) {
        JsonConfig config = new JsonConfig();
        if (hasdate)
            config.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());

        config.registerPropertyExclusions(item.getClass(), ((BaseBean) item).OnExclusions());

        return ToolUtils.GetJsonFromBean(item, config);
    }

    public static <T> boolean ValidWebUser(String userid, SelectBean<T> item) {
        SysUser user = UserDao.GetUser(userid);

        if ((user == null) || ToolUtils.StringIsEmpty(user.getUserid())) {
            return false;
        }

        item.setUserid(user.getUserid());
        item.setIsadmin(user.getIsadmin());

        return true;
    }
}
