package com.gpersist.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alms.dao.FormDao;
import com.alms.entity.bas.BusTestedUnit;
import com.alms.entity.form.IntField;
import com.alms.entity.lab.BusGet;
import com.alms.entity.lab.BusGetDetail;
import com.alms.entity.lab.BusGetNotice;
import com.alms.entity.lab.BusTaskData;

public class LogDetailsUtils {
    // 比较两个对象的不同
    public static String ContrastObj(Object oldBean, Object newBean) {
        StringBuilder str = new StringBuilder();
        try {
            // 通过反射获取类的类类型及字段属性
            Class clazz = oldBean.getClass();
            Field[] fields = clazz.getDeclaredFields();
            // int i = 1;
            for (Field field : fields) {
                // 排除序列化属性和非修改属性
                if ("serialVersionUID".equals(field.getName()) || "search".equals(field.getName())
                        || "deal".equals(field.getName()) || "item".equals(field.getName())) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                // 获取对应属性值
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(oldBean);
                Object o2 = getMethod.invoke(newBean);
                if (o1 == null || o2 == null) {
                    continue;
                }
                if (!o1.toString().equals(o2.toString())) {
                    str.append("字段名称:" + field.getName() + ",  旧值:" + o1 + ",  新值:" + o2 + ";");
                    // str.append(i + "、字段名称:" + field.getName() + ", 旧值:" + o1 + ", 新值:"
                    // + o2 + ";");
                    // i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    // 比较busget的不同
    public static String BusGetDiff(BusGet oldBean, BusGet newBean) {
        String str = "";

        if (!oldBean.getTesteddate().equals(newBean.getTesteddate())) {
            str = str + "  字段名称:委托时间,  旧值:" + oldBean.getTesteddate() + ",  新值:" + newBean.getTesteddate() + ";";
        }
        if (!oldBean.getTimereqname().equals(newBean.getTimereqname())) {
            str = str + "  字段名称:时间要求,  旧值:" + oldBean.getTimereqname() + ",  新值:" + newBean.getTimereqname() + ";";
        }
        if (!oldBean.getReportgetname().equals(newBean.getReportgetname())) {
            str = str + "  字段名称:报告提取方式,  旧值:" + oldBean.getReportgetname() + ",  新值:" + newBean.getReportgetname()
                    + ";";
        }
        if (!oldBean.getRestdealname().equals(newBean.getRestdealname())) {
            str = str + "  字段名称:余样处置,  旧值:" + oldBean.getRestdealname() + ",  新值:" + newBean.getRestdealname() + ";";
        }
        if (!oldBean.getProductcatename().equals(newBean.getProductcatename())) {
            str = str + "  字段名称:样品类别,  旧值:" + oldBean.getProductcatename() + ",  新值:" + newBean.getProductcatename()
                    + ";";
        }
        if (!oldBean.getEnteraddr().equals(newBean.getEnteraddr())) {
            str = str + "  字段名称:通讯地址,  旧值:" + oldBean.getEnteraddr() + ",  新值:" + newBean.getEnteraddr() + ";";
        }

        return str.toString();
    }

    // 比较委托单位的不同
    public static String BusTestedUnitDiff(BusTestedUnit bUnit, BusGet item) {
        String str = "";

        if (!bUnit.getTestedname().equals(item.getTestedname())) {
            str = str + "  字段名称:受检单位,  旧值:" + bUnit.getTestedname() + ",  新值:" + item.getTestedname() + ";";
        }
        if (!bUnit.getEnterlegal().equals(item.getTesteduser())) {
            str = str + "  字段名称:委托人,  旧值:" + bUnit.getEnterlegal() + ",  新值:" + item.getTesteduser() + ";";
        }
        if (!bUnit.getEntertele().equals(item.getEntertele())) {
            str = str + "  字段名称:联系电话,  旧值:" + bUnit.getEntertele() + ",  新值:" + item.getEntertele() + ";";
        }
        if (!bUnit.getEnterpost().equals(item.getEnterpost())) {
            str = str + "  字段名称:邮政编码,  旧值:" + bUnit.getEnterpost() + ",  新值:" + item.getEnterpost() + ";";
        }

        return str.toString();
    }

    // 比较BusGetNotice委托单位的不同
    public static String BusGetNoticeTestedUnitDiff(BusTestedUnit bUnit, BusGetNotice item) {
        String str = "";

        if (!bUnit.getTestedname().equals(item.getTestedname())) {
            str = str + "  字段名称:受检单位,  旧值:" + bUnit.getTestedname() + ",  新值:" + item.getTestedname() + ";";
        }

        return str.toString();
    }

    // 比较委托书详细页面的不同
    public static String compareBusGetDetail(List<BusGetDetail> olddetail, List<BusGetDetail> newdetail) {
        String str = "";
        if (null != olddetail && !olddetail.isEmpty() && null != newdetail && !newdetail.isEmpty()) {
            Map<String, Object> oldmap = objectToMap(olddetail.get(0));
            Map<String, Object> newmap = objectToMap(newdetail.get(0));

            if (!olddetail.get(0).getSamplename().equals(newdetail.get(0).getSamplename())) {
                str = str + "  字段名称:样品名称,  旧值:" + olddetail.get(0).getSamplename() + ",  新值:"
                        + newdetail.get(0).getSamplename() + ";";
            }
            if (!olddetail.get(0).getTrademark().equals(newdetail.get(0).getTrademark())) {
                str = str + "  字段名称:商标,  旧值:" + olddetail.get(0).getTrademark() + ",  新值:"
                        + newdetail.get(0).getTrademark() + ";";
            }
            if (!olddetail.get(0).getSamplebase().equals(newdetail.get(0).getSamplebase())) {
                str = str + "  字段名称:样品基数,  旧值:" + olddetail.get(0).getSamplebase() + ",  新值:"
                        + newdetail.get(0).getSamplebase() + ";";
            }
            if (!olddetail.get(0).getSamplelevel().equals(newdetail.get(0).getSamplelevel())) {
                str = str + "  字段名称:样品等级,  旧值:" + olddetail.get(0).getSamplelevel() + ",  新值:"
                        + newdetail.get(0).getSamplelevel() + ";";
            }
            if (!olddetail.get(0).getFactname().equals(newdetail.get(0).getFactname())) {
                str = str + "  字段名称:生产单位,  旧值:" + olddetail.get(0).getFactname() + ",  新值:"
                        + newdetail.get(0).getFactname() + ";";
            }
            if (!olddetail.get(0).getPrdcode().equals(newdetail.get(0).getPrdcode())) {
                str = str + "  字段名称:生产批号,  旧值:" + olddetail.get(0).getPrdcode() + ",  新值:"
                        + newdetail.get(0).getPrdcode() + ";";
            }
            if (!olddetail.get(0).getSamplecount().equals(newdetail.get(0).getSamplecount())) {
                str = str + "  字段名称:样品数量,  旧值:" + olddetail.get(0).getSamplecount() + ",  新值:"
                        + newdetail.get(0).getSamplecount() + ";";
            }
            if (!olddetail.get(0).getSamplestatus().equals(newdetail.get(0).getSamplestatus())) {
                str = str + "  字段名称:样品状态,  旧值:" + olddetail.get(0).getSamplestatus() + ",  新值:"
                        + newdetail.get(0).getSamplestatus() + ";";
            }
            if (!olddetail.get(0).getGetaddr().equals(newdetail.get(0).getGetaddr())) {
                str = str + "  字段名称:抽样地点,  旧值:" + olddetail.get(0).getGetaddr() + ",  新值:"
                        + newdetail.get(0).getGetaddr() + ";";
            }
            if (!olddetail.get(0).getSamplestand().equals(newdetail.get(0).getSamplestand())) {
                str = str + "  字段名称:规格型号,  旧值:" + olddetail.get(0).getSamplestand() + ",  新值:"
                        + newdetail.get(0).getSamplestand() + ";";
            }
            if (!olddetail.get(0).getTestitems().equals(newdetail.get(0).getTestitems())) {
                str = str + "  字段名称:检测参数,  旧值:" + olddetail.get(0).getTestitems().replace(";", ",") + "<br>新值:"
                        + newdetail.get(0).getTestitems().replace(";", ",") + ";";
            }
        }

        return str.toString();
    }

    // 比较两个list String中不同的元素
    private static List<String> getDiffrent(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<String>();
        long start = System.currentTimeMillis();
        Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size()) {
            maxList = list2;
            minList = list1;
        }
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            Integer count = map.get(string);
            if (count != null) {
                map.put(string, ++count);
                continue;
            }
            map.put(string, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        System.out.println("方法 耗时：" + (System.currentTimeMillis() - start) + " 毫秒");
        return diff;

    }

    // 比较两个list taskdata中不同的元素
    public static String getDiffrentdata(List<BusTaskData> list1, List<BusTaskData> list2) {
        String diff = "";
        long start = System.currentTimeMillis();
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (list1.get(i).getFieldcode().equals(list2.get(j).getFieldcode())
                        && list1.get(i).getSpecserial() == list2.get(j).getSpecserial()) {
                    if (!list1.get(i).getSubmitvalue().equals(list2.get(j).getSubmitvalue())) {
                        IntField labelname = new IntField();
                        labelname.setFieldcode(list1.get(i).getFieldcode());
                        labelname.setFieldserial(list1.get(i).getSpecserial());
                        labelname = FormDao.GetLogIntFieldName(labelname);
                        diff = diff + "  参数名称:" + labelname.getDefaultvalue() + "  字段名称：" + labelname.getFieldlabel()
                                + ",  旧值:" + list1.get(i).getSubmitvalue() + ",  新值:" + list2.get(j).getSubmitvalue()
                                + ";";
                    }
                }
            }

        }
        System.out.println("方法 耗时：" + (System.currentTimeMillis() - start) + " 毫秒");
        return diff;

    }
    // 比较两个list 对象中不同的元素

    public static List<String> comparebusGetDetail(List<BusGetDetail> olddetail, List<BusGetDetail> newdetail) {

        List<String> oldstring = new ArrayList<String>();
        List<String> newstring = new ArrayList<String>();

        for (BusGetDetail busGetDetail : olddetail) {
            oldstring.add(busGetDetail.toString());
        }

        for (BusGetDetail newGetDetail : newdetail) {
            newstring.add(newGetDetail.toString());
        }

        return getDiffrent(oldstring, newstring);
    }
    // 比较两个单个 中不同的元素

    public static String compareMapBusGetDetail(List<BusGetDetail> olddetail, List<BusGetDetail> newdetail) {
        String str = ";";
        if (null != olddetail && !olddetail.isEmpty() && null != newdetail && !newdetail.isEmpty()) {
            Map<String, Object> oldmap = objectToMap(olddetail.get(0));
            Map<String, Object> newmap = objectToMap(newdetail.get(0));
            str = getDiffrentmap(oldmap, newmap);
        }
        return str;
    }

    /**
     * 将Object对象里面的属性和值转化成Map对象
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                map.put(fieldName, field.get(obj));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return map;
    }

    // 比较两个list String中不同的元素
    private static String getDiffrentmap(Map<String, Object> map1, Map<String, Object> map2) {
        StringBuilder str = new StringBuilder();
        Iterator<Entry<String, Object>> it1 = map1.entrySet().iterator();

        while (it1.hasNext()) {
            Entry<String, Object> entry1 = it1.next();
            // System.out.println(entry1.getKey());
            if (entry1.getValue() == null || map2.get(entry1.getKey()) == null) {
                continue;
            } else if (!entry1.getValue().equals(map2.get(entry1.getKey()))) {
                str.append("字段名称:" + entry1.getKey() + ",旧值:" + entry1.getValue() + ",新值:" + map2.get(entry1.getKey())
                        + ";");
            }
        }
        return str.toString();
    }
}
