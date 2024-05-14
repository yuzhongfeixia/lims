package com.alms.service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.alms.dao.*;
import com.alms.entity.cont.*;
import com.alms.entity.crm.*;
import com.alms.entity.dev.*;
import com.alms.entity.file.*;
import com.alms.entity.glp.*;
import com.alms.entity.inc.*;
import com.alms.entity.inner.*;
import com.alms.entity.lab.BusTask;
import com.alms.entity.office.*;
import com.alms.entity.prd.*;
import com.alms.entity.quan.*;
import com.alms.entity.samp.*;
import com.alms.entity.std.*;
import com.alms.entity.sub.*;
import com.alms.entity.review.*;
import com.alms.entity.staff.*;
import com.gpersist.entity.publics.FmtBeanConfig;
import com.gpersist.entity.publics.FmtListConfig;
import com.gpersist.utils.ToolUtils;

public class FlowFmtService {

    public static String GetInfoHtmlHeader() {
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">");
        sb.append("<title></title>");
        sb.append("<style>");
        sb.append("body { cursor: auto; margin: 0px; border: none; } ");
        sb.append(".infotrheader { height: 30px; } ");
        sb.append(
                ".infohead { font-weight: bold; border: solid 1px #E1E1E1; height: 24px; background-color: #EFF6FF; } ");
        sb.append(".infotr { height: 30px; } ");
        sb.append(".infoc { border: solid 1px #E1E1E1; height: 24px; } ");
        sb.append(
                ".infoat { border: solid 1px #E1E1E1; height: 24px; font-weight: bold; background-color: #EFF6FF; } ");
        sb.append(".infoleft { padding-left:5px; } ");
        sb.append(".inforight { padding-right:5px; } ");
        sb.append(".pointer { cursor:pointer; } ");
        sb.append("</style>");
        sb.append("<script>function viewFile(){alert(1)}</script>");
        sb.append("</head>");
        sb.append("<body>");

        return sb.toString();
    }

    public static String GetInfoHtmlBottom() {
        StringBuilder sb = new StringBuilder();

        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();
    }

    public static String GetTranData(String value) {
        if (ToolUtils.StringIsEmpty(value))
            return "";
        else {
            return value;
        }
    }

    public static String GetTranDate(java.util.Date value, String fmt) {
        if (value == null)
            return "";
        else {
            return ToolUtils.GetFmtDate(value, fmt);
        }
    }

    public static <T> String GetBeanHtml(T item, String title, String[][] fmts, int maxcol) throws Exception {
        return GetBeanHtml(item, title, fmts, maxcol, true);
    }

    public static <T> String GetBeanHtml(T item, String title, String[][] fmts, int maxcol, boolean hastitle)
            throws Exception {
        try {
            StringBuffer sb = new StringBuffer();

            sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"infotable\" width=\"100%\">");

            if (hastitle)
                sb.append("<tr><td colspan=\"" + String.valueOf(maxcol)
                        + "\" align=\"center\" width=\"100%\" class=\"infohead\"><b>" + title + "</b></td></tr>");

            int rowidx = 0;
            FmtBeanConfig config = new FmtBeanConfig();
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String value = "";

            for (String[] rowfmt : fmts) {
                rowidx++;

                sb.append("<tr class=\"flowlisttr\">");

                for (String fmt : rowfmt) {
                    config.SetConfig(fmt);

                    if (config.getColtype() == 1)
                        value = config.getColcode();
                    else {
                        Field f = item.getClass().getDeclaredField(config.getColcode());
                        f.setAccessible(true);

                        if (f.get(item) != null) {
                            if (f.get(item) instanceof java.util.Date)
                                value = sp.format(f.get(item));
                            else
                                value = f.get(item).toString();
                        }
                    }

                    sb.append("<td class=\"" + (config.getColtype() == 1 ? "flowlisttitle" : "flowlisttd flowlistleft")
                            + "\" align=\"" + (config.getColtype() == 1 ? "center" : "left") + "\" "
                            + (config.getColspan() <= 1 ? ""
                                    : "colspan=\"" + String.valueOf(config.getColspan()) + "\" ")
                            + (rowidx == 1 ? "width=\"" + String.valueOf(config.getColwidth()) + "%\"" : "") + " >"
                            + value + "</td>");
                }

                sb.append("</tr>");
            }

            sb.append("</table>");

            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static <T> String GetListHtml(List<T> items, String title, String[] fmts) {
        return GetListHtml(items, title, fmts, false);
    }

    public static <T> String GetListHtml(List<T> lists, String title, String[] fmts, boolean allownull) {
        try {
            if (!allownull && (lists.size() <= 0))
                return "";

            StringBuffer sb = new StringBuffer();

            List<FmtListConfig> configs = GetFmtListConfig(fmts);

            sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"infotable\" width=\"100%\">");

            sb.append("<tr class=\"infotr\">");

            for (FmtListConfig config : configs) {
                sb.append("<td class=\"flowlisttitle\" align=\"center\" style=\"width:" + config.getColwidth() + "%;\">"
                        + config.getColname() + "</td>");
            }
            sb.append("</tr>");

            String value = "";
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
            int i = 0;

            for (T t : lists) {
                sb.append("<tr class=\"flowlisttr\">");

                for (FmtListConfig config : configs) {
                    if (config.getColcode().equals("listserial")) {
                        sb.append("<td class=\"flowlisttd\" align=\"center\">" + String.valueOf(++i) + "</td>");
                        continue;
                    }

                    Field f = t.getClass().getDeclaredField(config.getColcode());
                    f.setAccessible(true);
                    value = "";

                    if (f.get(t) != null) {
                        if (f.get(t) instanceof java.util.Date)
                            value = sp.format(f.get(t));
                        else
                            value = f.get(t).toString();
                    }

                    sb.append("<td class=\"flowlisttd flowlistleft\" align=\"left\">" + value + "</td>");
                }
                sb.append("</tr>");
            }

            sb.append("</table>");

            return sb.toString();

        } catch (Exception e) {
            return "";
        }
    }

    public static List<FmtListConfig> GetFmtListConfig(String[] fmts) {
        List<FmtListConfig> rtn = new ArrayList<FmtListConfig>();

        for (String fmt : fmts) {
            String[] items = fmt.split(":");

            if (items.length < 3)
                continue;

            FmtListConfig config = new FmtListConfig();
            config.setColname(items[0]);
            config.setColcode(items[1]);
            config.setColwidth(items[2]);

            rtn.add(config);
        }

        return rtn;
    }

    // region 设备申请HTML
    public static String GetHtmlDevApply(BuyApply item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">设备名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备类型</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevtypename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备型号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevstandard() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">生产厂家</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getFactoryname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">申请数量</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevcount() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getApplyusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">申请部门</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getApplydeptname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">申请日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getApplydate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">技术指标</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevrange() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">申请理由</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getApplyreason() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 设备停用申请HTML
    public static String GetHtmlBasDev(BasDev item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">设备名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">生产厂家</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getFactoryname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">出厂编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getFactorycode() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">设备型号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevstandard() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备类型</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevtypename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">使用温度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getUsetemp() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">使用湿度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getUsehumid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备负责人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevmanagername()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">所属实验室</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDeptname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">价格(万元)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevprice() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">购买日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getBuydate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">上次检定</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">"
                + GetTranDate(item.getPrevtime(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">下次检定</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">"
                + GetTranDate(item.getNexttime(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">存放位置</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getStoreplace() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">设备用途</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevusage() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">技术指标</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevrange() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 领取样品申请
    public static String GetHtmlReceiveSamples(BusTask item) {

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">任务单编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"15%\">" + item.getTaskid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">样品编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"15%\">" + item.getSamplecode() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"10%\">样品名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"15%\">" + item.getSamplename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"10%\">规格型号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getSamplestand() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">检测性质</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"15%\">" + item.getTestpropname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">样品状态</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"15%\">" + item.getSamplestatus() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"10%\">承检室</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"15%\">" + item.getLabname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"10%\">承检室负责人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getLabusername() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">任务下达人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"15%\">" + item.getSendusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">下达日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"15%\">"
                + GetTranDate(item.getSenddate(), "yyyy-MM-dd HH:mm") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"10%\">要求完成日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"15%\">"
                + GetTranDate(item.getReqdate(), "yyyy-MM-dd HH:mm") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"10%\">检测依据</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getTeststandardname()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 耗材申请HTML
    public static String GetHtmlPrdApply(PrdApply item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">使用项目</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getProjectid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">耗材类别</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getPrdtypename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">申请日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getTrandate(), "yyyy-MM-dd HH:mm") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">招标情况</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getPrdsourcename()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">经费渠道</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getFundsource() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">特殊要求说明</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getTranremark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlPrdApplyDetail(List<PrdApplyDetail> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        if (details.size() > 0) {
            sb.append(
                    "<tr class=\"infotr\"><td class=\"infoat\"  align=\"center\" colspan=\"1\" width=\"10%\">明细序号</td>"
                            + "<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">名称</td>"
                            + "<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">购买数量</td>"
                            + "<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">单位</td>"
                            + "<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">规格型号</td>"
                            + "<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">级别</td>"
                            + "<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">是否验收</td>"
                            + "<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"10%\">备注</td></tr>");

            for (PrdApplyDetail prd : details) {
                sb.append("<tr class=\"infotr\"><td class=\"infoc\" colspan=\"1\" align=\"center\" width=\"10%\">"
                        + prd.getPrdserial() + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"1\" width=\"10%\">"
                        + GetTranData(prd.getPrdname()) + "</td>" +

                        "<td class=\"infoc\" align=\"center\" colspan=\"1\" width=\"10%\">"
                        + GetTranData(prd.getPrdcount()) + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"1\" width=\"10%\">"
                        + GetTranData(prd.getPrdunitname()) + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"1\" width=\"10%\">"
                        + GetTranData(prd.getPrdstandard()) + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"1\" width=\"10%\">"
                        + GetTranData(prd.getLevelname()) + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"1\" width=\"10%\">"
                        + GetTranData(prd.getIsverifyname()) + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"2\" width=\"10%\">"
                        + GetTranData(prd.getRemark()) + "</td></tr>");
            }
        }
        sb.append("</table>");

        return sb.toString();

    }
    // endregion

    // region 耗材验证HTML
    public static String GetHtmlPrdVerify(PrdVerify item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">试剂耗材名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getPrdname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">规格型号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getPrdstandard() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">生产厂家</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getFactoryname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">供应商</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getTradename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">(拟)进货量</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getBuycount() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getPrdunitname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">进货日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getBuydate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    // endregion

    // region 废弃物申请Html

    public static Object GetHtmlWasteApply(List<PrdWasteDetail> items) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        for (PrdWasteDetail item : items) {
            sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">业务编号</td>"
                    + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getTranid() + "</td>"
                    + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">废弃物名称</td>"
                    + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getPrdname() + "</td>"
                    + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">规格型号</td>"
                    + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getPrdstd() + "</td>"
                    + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">数量</td>"
                    + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getPrdcount() + "</td>"
                    + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">备注</td>"
                    + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getTranremark()
                    + "</td></tr>");
        }

        sb.append("</table>");

        return sb.toString();
    }

    // endregion 废弃物申请Html

    // region 安全检查记录Html

    public static Object GetHtmlCheckSafe(IncCheckSafe item) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String checkdate = sdf.format(item.getCheckdate());

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">检查人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getCheckusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">检查时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + checkdate + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">安全隐患</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getDangerdesc()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">拟处理措施</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getDealdesc()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion 安全检查记录Html

    // region 非标方法html

    public static Object GetHtmlNonStd(StdNonstd item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">检测方法名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getTestmethname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">方法来源</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getMethsource() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">理由</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getReason()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">试验记录</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getTrialrecord()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">备注</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getRemark()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion 非标方法html

    // region 检测方法偏离确认 html

    public static Object GetHtmlMethodDevi(StdMethodDevi item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">检测方法名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getMethodname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">方法偏离情况</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getMethodsource() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">理由</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getReason()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">试验记录</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getTrialrecord()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">备注</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getRemark()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion 检测方法偏离确认 html

    // region 新增检验项目 html

    public static Object GetHtmlProApply(StdProApply item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String propdate = sdf.format(item.getPropdate());

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"8%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"8%\">新增项目名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getProjname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"8%\">提议人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getPropusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"8%\">提议时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"17%\">" + propdate + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">目的、意义</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getMeaning()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">市场需求和前景</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getMarketreq()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">技术和资源要求</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getTechreq()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">资源状况及保证程度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getGuaran()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">经济分析</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getEcoanalyze()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion 新增检验项目html

    // region 新增检验项目能力确认html

    public static Object GetHtmlTestSure(StdTestSure item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"8%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"8%\">新增项目名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getProjname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"8%\">检测人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getTechusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"8%\">负责人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"17%\">" + item.getRespusername() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">质量文件运行情况</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getQualfile()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">环境设施条件</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getEnvfacility()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">仪器设备满足情况</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getDevsatisfy()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion 新增检验项目能力确认html

    // region 分包评审html

    public static Object GetHtmlSubReview(SubReview item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"8%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"8%\">分包方名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getSubname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"8%\">地址</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getSubaddr() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"8%\">联系人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"17%\">" + item.getLinktele() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">分包项目</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getSubproject()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">检测人员数量、素质</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getTesterdesc()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">检测设备</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getTestdev()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">检测设备量值溯源情况</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getTestsource()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">环境条件</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getEnvdesc()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">质量体系和运行情况</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getQualsys()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">结论</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"7\">" + item.getAuditresult()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion 分包评审

    // region 管理评审报告html

    public static String GetHtmlReviewReport(ReviewReport item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"12%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">编制人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">编制日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getTrandate(), "yyyy-MM-dd HH:mm") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\"  width=\"12%\">报告内容</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\"  width=\"12%\" >" + item.getReportcontent()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }
    // endregion

    // region 管理改进措施
    public static String GetHtmlReviewImprove(ReviewImprove item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\"  width=\"10%\">责任部门</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\"  width=\"90%\" >" + item.getRespdeptname()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\"  width=\"10%\">质量改进内容</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\"  width=\"90%\" >" + item.getImprovecontent()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\"  width=\"10%\">要求期限</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\"  width=\"90%\" >" + item.getTimerequire()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }
    // endregion

    // region 评审年度工作计划
    public static String GetHtmlReviewYear(ReviewYear item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\"  width=\"10%\">评审目的</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\"  width=\"90%\" >" + item.getReviewgoal()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\"  width=\"10%\">评审组成员</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\"  width=\"90%\" >" + item.getReviewcrew()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\"  width=\"10%\">评审日期安排</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\"  width=\"90%\" >" + item.getReviewplan()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\"  width=\"10%\">评审要点</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\"  width=\"90%\" >" + item.getReviewgist()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }
    // endregion

    // region 评审年度工作计划
    public static String GetHtmlReviewPlan(ReviewPlan item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">评审日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">"
                + GetTranDate(item.getReviewdate(), "yyyy-MM-dd HH:mm") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">评审地点</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getReviewaddr() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">主持人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getReviewusername()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">评审目的</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getReviewgoal() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">评审依据</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getReviewtypename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">参加人员</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getJoinuser() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">评审内容</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getReviewcontentname()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">收集信息要求</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getInforequire() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }
    // endregion

    // region 管理评审记录
    public static String GetHtmlReviewRecord(ReviewRecord item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\"  width=\"10%\">评审日期</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\"  width=\"90%\">"
                + GetTranDate(item.getReviewdate(), "yyyy-MM-dd HH:mm") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">评审地点</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + item.getReviewaddr() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">评审内容</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + item.getReviewcontent() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }
    // endregion

    // region 人员培训计划申请HTML
    public static String GetHtmlUserPlanYear(UserPlanYear item) {

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">申请说明</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"90%\" >" + item.getTranremark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlUserPlanYearDetail(List<UserPlanYearDetail> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");
        sb.append("<tr ><td colspan=\"8\" class=\"infoat\"  align=\"center\" width=\"10%\">"
                + details.get(0).getTranyear() + "年度人员培训计划表</tr>");

        if (details.size() > 0) {
            sb.append("<tr class=\"infotr\"><td class=\"infoat\"  align=\"center\" width=\"10%\">培训内容与方式</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"10%\">培训方式</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"10%\">培训时间</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">拟培训人员</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"10%\">经费预算(元)</td></tr>");

            for (UserPlanYearDetail plan : details) {
                sb.append("<tr class=\"infotr\"><td class=\"infoc\" colspan=\"1\" align=\"center\" width=\"10%\">"
                        + plan.getTraincontent() + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"2\" width=\"10%\">"
                        + GetTranData(plan.getTraintypename()) + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"2\" width=\"10%\">"
                        + GetTranDate(plan.getTraindate(), "yyyy-MM-dd") + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"1\" width=\"10%\">" + plan.getTrainer()
                        + "</td>" + "<td class=\"infoc\" align=\"center\" colspan=\"2\" width=\"10%\">"
                        + plan.getTrainfee() + "</td></tr>");
            }
        }
        sb.append("</table>");

        return sb.toString();

    }
    // endregion

    // region 供应商调查html

    public static Object GetHtmlTradeSurvey(TradeSurvey item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">企业名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getTradename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">联系人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getLinkman() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">产品名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getPrdname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">联系电话</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getLinktele() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">调查人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getTradename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">企业规模</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getEnterscalename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">知名度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getEnterpopularname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">获取资格/证书</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getEntercretname()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">产品质量</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getPrdqualityname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">试用情况</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getPrdtestname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">管理</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getPrdmanagename()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">生产/检测设备</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getPrdcheckname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">服务质量</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getPrdservicename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">价格</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getPrdpricename() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion 供应商调查html

    // region 合同评审html

    public static Object GetHtmlContractReview(ContractReview item) {
        String samplenames = "";
        String params = "";
        String tranid = item.getTranid();
        ContractReviewSample crs = new ContractReviewSample();
        crs.setTranid(tranid);
        List<ContractReviewSample> crss = ContDao.GetListContractReviewSample(crs);
        for (ContractReviewSample contractReviewSample : crss) {
            if ("".equals(samplenames)) {
                samplenames = contractReviewSample.getSamplename();
            } else {
                samplenames = samplenames + "," + contractReviewSample.getSamplename();
            }
        }

        ContractReviewParam crp = new ContractReviewParam();
        crp.setTranid(tranid);
        List<ContractReviewParam> crps = ContDao.GetListContractReviewParam(crp);

        for (ContractReviewParam contractReviewParam : crps) {
            if ("".equals(params)) {
                params = contractReviewParam.getParametername();
            } else {
                params = params + "," + contractReviewParam.getParametername();
            }
        }

        ContractReviewDetail crd = new ContractReviewDetail();
        crd.setTranid(tranid);
        List<ContractReviewDetail> crds = ContDao.GetListContractReviewDetail(crd);

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">合同/委托书编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getContractid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">合同名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getConsignname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">样品名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + samplenames + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">检测项目</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + params + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">联系人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getConsigncontact()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">电话</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getConsigntele()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">特殊要求</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getTestrequest()
                + "</td></tr>");

        for (ContractReviewDetail contractReviewDetail : crds) {
            sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">评审人</td>"
                    + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">"
                    + contractReviewDetail.getReviewusername() + "</td>"
                    + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">评审意见</td>"
                    + "<td class=\"infoc infoleft\" align=\"left\" width=\"74%\" colspan=\"3\">"
                    + contractReviewDetail.getReviewadvice() + "</td></tr>");
        }

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 人员上岗资格HTML
    public static String GetHtmlUserExamReport(UserExamReport item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<table align = \"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:12px;\" width=\"80%\">"
                        + "<tr class=\"infotrheader\"><td colspan=\"8\" align=\"center\" width=\"100%\" style=\"height: 40px;font-size:20px\"><b>人员上岗考核报告</b></td></tr>"
                        + "<tr class=\"infotrheader\" border=0><td colspan=\"5\" align=\"left\" width=\"50%\" >NO：</td><td colspan=\"5\" align=\"right\" width=\"50%\" >QCR 5-1-2</td></tr>"
                        + "</table>");
        sb.append(
                "<br/><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"12%\">被考核人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getUsername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\"  width=\"12%\">岗位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getUserpostname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\" width=\"12%\">考核形式</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getExamtypename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"12%\">部门</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDeptname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\"  width=\"12%\">技术职务</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getUserdutyname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\" width=\"12%\">考核日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getExamdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"10%\">考核内容</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" colspan=5  >" + item.getExamcontent() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"10%\">综合评定</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" colspan=5  >" + item.getExamdesc() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlUserExamGroup(List<UserExamGroup> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        if (details.size() > 0) {
            sb.append("<tr class=\"infotr\"><td rowspan=" + details.size() + 1
                    + " class=\"infoat\"  align=\"center\" width=\"12%\">考核小组成员</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"30%\">姓名</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"30%\">技术职务</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"28%\">部门</td></tr>");

            for (UserExamGroup group : details) {
                sb.append("<tr class=\"infotr\"><td class=\"infoc\" colspan=\"1\" align=\"center\" width=\"30%\">"
                        + group.getUsername() + "</td>" + "<td class=\"infoc\" align=\"center\"  width=\"30%\">"
                        + group.getUserdutyname() + "</td>" + "<td class=\"infoc\" align=\"center\"  width=\"28%\">"
                        + group.getDeptname() + "</td></tr>");
            }
        }
        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlUserExamDev(List<UserExamDev> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");
        String devname = "";
        if (details.size() > 0) {
            for (int i = 0; i < details.size(); i++) {
                devname += details.get(i).getDevname();
                if (i < details.size() - 1) {
                    devname += ",";
                }
            }

            sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"12%\">设备仪器</td>"
                    + "<td class=\"infoc infoleft\" align=\"left\" width=\"88%\" >" + devname + "</td></tr>");
        }
        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlUserExamFile(List<UserExamFile> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");
        if (details.size() > 0) {
            sb.append("<tr class=\"infotr\"> " + "<td class=\"infoat\" align=\"center\"  width=\"12%\">报告序号</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"30%\">报告名称</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"30%\">备注</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"28%\"></td></tr>");

            for (UserExamFile file : details) {
                sb.append("<tr class=\"infotr\"><td class=\"infoc\" colspan=\"1\" align=\"center\" width=\"12%\">"
                        + file.getFileno() + "</td>" + "<td class=\"infoc\" align=\"center\"  width=\"30%\">"
                        + file.getFilename() + "</td>" + "<td class=\"infoc\" align=\"center\"  width=\"30%\">"
                        + file.getFileremark() + "</td>" +
                        // "<td class=\"infoc\" align=\"center\" width=\"28%\"><a
                        // class=\"pointer\"
                        // onclick=\"viewFile('"+file.getFileurl()+"','"+file.getFilename()+"')\"
                        // >预览</a></td></tr>");
                        "<td  class=\"infoc\" align=\"center\"  width=\"28%\"><a class=\"pointer\" onclick=\"alms.PopupFileShow('文件预览', 'FileDownFile.do', '"
                        + file.getFileurl() + "','" + file.getFilename() + "')\" >预览</a></td></tr>");
            }
        }

        sb.append("</table>");

        return sb.toString();

    }
    // endregion

    // region 人员岗位考核HTML

    public static String GetHtmlUserExamRecord(UserExamRecord item) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"8%\">被考核人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getUsername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"8%\">岗位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getUserpostname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"8%\">考核结果</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"17%\">" + item.getExamadvicename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"8%\">考核日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"17%\">"
                + GetTranDate(item.getExamdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">考核内容</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" colspan=\"7\"  >" + item.getExamcontent()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">需要整改的问题</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" colspan=\"7\"  >" + item.getExamdesc() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    public static String GetHtmlUserExamDetail(List<UserExamDetail> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        if (details.size() > 0) {
            sb.append("<tr class=\"infotr\"><td rowspan=" + details.size() + 1
                    + " class=\"infoat\"  align=\"right\" width=\"10%\">考核情况分析</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"30%\">内容</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"20%\">考核成绩</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"30%\">考核成绩说明</td></tr>");

            for (UserExamDetail exam : details) {
                sb.append("<tr class=\"infotr\"><td class=\"infoc\" colspan=\"1\" align=\"center\" width=\"30%\">"
                        + exam.getExamitemname() + "</td>" + "<td class=\"infoc\" align=\"center\"  width=\"20%\">"
                        + exam.getExamscorename() + "</td>" + "<td class=\"infoc\" align=\"center\"  width=\"30%\">"
                        + exam.getExamitemdesc() + "</td></tr>");
            }
        }
        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 设备检查HTML
    public static String GetHtmlDevCheck(DevCheck item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getCheckid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">仪器编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">仪器名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">生产厂家</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getFactoryname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">测量范围</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevrange() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">检查精度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevprecision() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">上次检查日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">"
                + GetTranDate(item.getLastcheckdate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">设备管理员</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevmanagername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">检查日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getCheckdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检查情况</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCheckdesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检查结论</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCheckresult() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 设备检定校准计划HTML
    public static String GetHtmlDevCalibPlan(DevCalibPlan item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">仪器编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">仪器名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备型号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevstandard() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">出厂编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getFactorycode() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">生产厂商</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getFactoryname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">使用温度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getUsetemp() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">使用湿度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getUsehumid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">购买日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getBuydate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">上次检查日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">"
                + GetTranDate(item.getLastdate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">下次检定时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">"
                + GetTranDate(item.getNextdate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">检定周期(月)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevperiod() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">价格(万元)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevprice() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">检定单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getCalibunitname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">检定人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevmanagername()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">设备用途</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevusage() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">技术指标</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevrange() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 仪器设备故障报修单HTML
    public static String GetHtmlRepairApply(RepairApply item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">仪器编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">仪器名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">仪器型号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevstandard() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备类别</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevtypename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">故障发生时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">"
                + GetTranDate(item.getFaultstart(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">维修费用</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getRepaircost() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">故障情况</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getFaultdesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">维修地点</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRepairaddress() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 维修记录HTML
    public static String GetHtmlRepairRecord(RepairRecord item) {

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">设备编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">报修单编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getRepairid() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">设备管理员</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getManageruser() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">管理员姓名</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getManagerusername()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">所属室</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDeptname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">主机号码</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getHostnumber() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">维修人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getRepairman() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">仪器故障</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevdesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">故障原因</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevreason() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">采取措施</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevrepair() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">维修结果</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRepairresult() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 仪器降级HTML
    public static String GetHtmlDevScrap(DevScrap item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">仪器编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">仪器名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">仪器型号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevstandard() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">生产单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getFactoryname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">出厂编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getFactorycode() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">申请部门</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getTrandeptname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">申请事由</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getApplyreason() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 设备验收HTML
    public static String GetHtmlAcceptManage(AcceptManage item) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">设备名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">型号规格</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevstandard() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">生产厂家</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getFactoryname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">出厂编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getFactorycode() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">进场日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getEnterdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">价格(万元)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevprice() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">安装单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getInstalunit() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">安装时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getInstaldate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">技术参数</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" colspan=5  >" + item.getDevrange() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">安装情况</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" colspan=5  >" + item.getInstaldesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">安装调试人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" colspan=5  >" + item.getInstaluser() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlAcceptFileDetail(List<AcceptFileDetail> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        if (details.size() > 0) {

            String files = "";
            for (AcceptFileDetail file : details) {
                files += file.getFileserial() + "." + file.getFilename() + " &nbsp;&nbsp;&nbsp;&nbsp;备注："
                        + file.getFileremark() + "<br/>";
            }
            sb.append(
                    "<tr class=\"infotr\"><td  class=\"infoat\" style=\"height:60px;\" align=\"right\" width=\"12%\">随机资料</td>");
            sb.append("<td class=\"infoc\" colspan=\"1\" align=\"left\" width=\"88%\">" + files + "</td></tr>");
        }
        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlAcceptPartsDetail(List<AcceptPartsDetail> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");
        String parts = "";
        if (details.size() > 0) {
            for (AcceptPartsDetail part : details) {
                parts += part.getPartsserial() + "." + part.getPartsname() + "&nbsp;&nbsp;&nbsp;&nbsp;规格型号:"
                        + part.getPartsstandard() + "&nbsp;&nbsp;&nbsp;&nbsp;数量:" + part.getPartscount()
                        + "&nbsp;&nbsp;&nbsp;&nbsp;备注:" + part.getPartsremark() + "<br/>";
            }
            sb.append(
                    "<tr class=\"infotr\"><td class=\"infoat inforight\"　style=\"height:60px;\" align=\"right\" width=\"12%\">设备仪器</td>"
                            + "<td class=\"infoc infoleft\" align=\"left\" width=\"88%\" >" + parts + "</td></tr>");
        }
        sb.append("</table>");

        return sb.toString();

    }
    // endregion

    // region 设备期间核查计划HTML
    public static String GetHtmlDevCheckPlan(DevCheckPlan item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">仪器编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">仪器名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备型号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevstandard() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">出厂编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getFactorycode() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">生产厂商</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getFactoryname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">使用温度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getUsetemp() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">使用湿度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getUsehumid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">上次检查日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getLastcheckdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">核查计划日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">"
                + GetTranDate(item.getDevcheckdate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">下次检定时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">"
                + GetTranDate(item.getNextdate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">检定周期(月)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevperiod() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">检定单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getCheckorgname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">检定人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevmanagername()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">设备用途</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevusage() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">技术指标</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevrange() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 仪器校准HTML
    public static String GetHtmlDevCalib(DevCalib item) {

        BasDev basDev = new BasDev();
        basDev.setDevid(item.getDevid());
        basDev = DevDao.GetBasDev(basDev);
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">仪器编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getDevid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">仪器名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + basDev.getDevname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">检定日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getCalibuserdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">检查人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getCalibusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">检定周期(月)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getDevperiod() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">最近检定日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getLastdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">问题分析</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getProblemdesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检查内容</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCalibcontent() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检查结果</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCalibresult() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检定校准要求</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCalibrequire() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">下次检定日期</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >"
                + GetTranDate(item.getNextdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion
    // region 常用设备检查HTML
    public static String GetHtmlDevCommon(DevCommon item) {

        BasDev basDev = new BasDev();
        basDev.setDevid(item.getDevid());
        basDev = DevDao.GetBasDev(basDev);
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">仪器编号及名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + basDev.getDevid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">管理员名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + basDev.getDevmanagername()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">检定日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">"
                + GetTranDate(item.getCommondate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">检查单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getCalibunitname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">检查人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getCommonuser() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检查内容</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCommoncontent() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检查结果</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCommonresult() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 不符合纠正措施html

    public static Object GetHtmlInnerInvalid(InnerInvalid item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">发生部门</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getOccurdeptname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">不符合来源</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getCorrectsourcename()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">不符合识别人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getInvalidusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"6%\">不符合评价</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getInvalidadvname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">责任人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"14%\">" + item.getTranusername() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">不符合事实描述</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getInvaliddesc()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">已发生不符合原因分析</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"94%\" colspan=\"5\">" + item.getInvalidreason()
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion 不符合纠正措施html

    // region 内审年度计划html

    public static Object GetHtmlInnerYear(InnerYear item) {
        InnerYearDetail iyd = new InnerYearDetail();
        iyd.setTranid(item.getTranid());
        List<InnerYearDetail> iyds = InnerDao.GetListInnerYearDetail(iyd);

        InnerGroupMember member = new InnerGroupMember();
        member.setGroupid(item.getGroupid());
        List<InnerGroupMember> mems = InnerDao.GetListInnerGroupMember(member);
        StringBuilder ms = new StringBuilder();

        for (int i = 0; i < mems.size(); i++) {
            ms.append(mems.get(i).getMemberusername());
            if (i < mems.size() - 1) {
                ms.append(',');
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"14%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"6%\">评审目的</td>"
                + "<td class=\"infoc infoleft\" colspan = 2 align=\"left\" >" + item.getAuditgoal() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">审核范围</td>"
                + "<td class=\"infoc infoleft\" colspan = 4 align=\"left\"  >" + item.getAuditscope() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">审核依据及有关文件</td>"
                + "<td class=\"infoc infoleft\" colspan = 4 align=\"left\"  >" + item.getAuditby() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat  inforight\" rowspan=" + (iyds.size() + 1)
                + " align=\"right\" width=\"10%\" >审核日程</td>"
                + "<td class=\"infoc infoleft\"  align=\"center\" width=\"10%\">组别</td>"
                + "<td class=\"infoc infoleft\"  align=\"center\" width=\"10%\">时间</td>"
                + "<td class=\"infoc infoleft\"  align=\"center\" width=\"30%\">内容</td>"
                + "<td class=\"infoc infoleft\"  align=\"center\" width=\"30%\">备注</td></tr>");

        for (InnerYearDetail innerYearDetail : iyds) {

            sb.append("<tr class=\"infotr\"><td class=\"infoc infoleft\" align=\"center\" width=\"10%\">"
                    + innerYearDetail.getGroupinner() + "</td>"
                    + "<td class=\"infoc infoleft\"  align=\"center\" width=\"10%\">"
                    + GetTranDate(innerYearDetail.getAuditdate(), "yyyy-MM-dd") + "</td>"
                    + "<td class=\"infoc infoleft\"  align=\"center\" width=\"30%\">"
                    + innerYearDetail.getAuditcontent() + "</td>"
                    + "<td class=\"infoc infoleft\"  align=\"center\" width=\"30%\">"
                    + GetTranData(innerYearDetail.getAuditremark()) + "</td></tr>");

        }

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">内审组长</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  colspan=\"4\">" + item.getAuditleadname()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"6%\">组员</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  colspan=\"4\">" + GetTranData(ms.toString())
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion 内审年度计划html

    // region 检验事故处理登记HTML
    public static String GetHtmlAccidentDeal(CrmAccidentDeal item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">送检单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTestedname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">样品名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getSamplename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">事故来源</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getOldreport() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">事故日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">"
                + GetTranDate(item.getEventdate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">事故责任人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getEventusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\"></td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\"></td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">事故原因</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getEventreason() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">处理措施</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDealdesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 投诉处理HTML
    public static String GetHtmlReceptDeal(CrmReceptDeal item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">申诉单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getComplainobject() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">申诉时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">"
                + GetTranDate(item.getComplaindate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">申诉时效</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + GetTranData(item.getPrescription())
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">受理时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">"
                + GetTranDate(item.getAcceptdate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">联系人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getLinkman() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">联系电话</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getLinktele() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">邮编</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getLinkpost() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">联系人地址</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" colspan=3  >" + item.getLinkaddr() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">投诉内容</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getComplaindesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 机密内容查阅HTML
    public static String GetHtmlFileSecret(SecretApply item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">查阅编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">查阅人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getReadusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">查阅时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getReaddate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">查阅内容</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getReadcontent() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 文件更改查阅HTML
    public static String GetHtmlFileChange(ChangeApply item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">申请部门</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getChangedeptname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">拟更改文件名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getChangefilename()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">会办部门</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getChangedeptname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">拟更改文件编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getChangefileid()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">更改理由</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getChangereason() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">更改内容</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getChangedesc() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 文件销毁HTML
    public static String GetHtmlFileDestory(DestoryApply item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"10%\">销毁资料编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getFileid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\"  width=\"10%\">资料名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getFilename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"10%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\"  width=\"10%\">销毁日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getDestroydate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append(
                "<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" style=\"height:80px;\">销毁原因</td>"
                        + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDestroyreason()
                        + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 文件借阅、复印登记HTML
    public static String GetHtmlFileLoan(LoanApply item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">借阅时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getLoandate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">借阅资料</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getFilename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">归还时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getReturndate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:60px;\">借阅理由</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getLoanreason() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 文件在线查阅
    public static String GetHtmlIncFileOnline(IncFileOnline item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">申请时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getTrandate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" >申请文件</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getFilename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:60px;\">申请理由</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getApplyreason() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 文件泄密处置HTML
    public static String GetHtmlFileLeak(BasLeak item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">泄密人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getLeakusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">泄密时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getLeakdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">泄密原因</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getLeakreason() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">造成后果</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getLeakresult() + "</td></tr>");

        sb.append(
                "<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\"style=\"height:80px;\">泄密报告时间</td>"
                        + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >"
                        + GetTranDate(item.getReportdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">泄密简述</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getLeakdesc() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 能力计划申请HTML
    public static String GetHtmlControlPlan(QuanControlPlan item) {

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">年度</td>"
                + "<td class=\"infoc infoleft\" align=\"center\" width=\"90%\" >" + item.getContyear()
                + "年能力验证（质量控制）计划</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlControlPlanDetail(List<QuanControlPlanDetail> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");
        if (details.size() > 0) {
            sb.append("<tr class=\"infotr\"><td class=\"infoat\"  align=\"center\" width=\"10%\">时间</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"40%\">内容</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"30%\">组织机构(人员)</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"20%\">备注</td></tr>");

            for (QuanControlPlanDetail plan : details) {
                sb.append("<tr class=\"infotr\"><td class=\"infoc\"  align=\"center\" width=\"10%\">"
                        + plan.getPlantime() + "</td>" + "<td class=\"infoc\" align=\"center\"  width=\"40%\">"
                        + GetTranData(plan.getPlancontent()) + "</td>"
                        + "<td class=\"infoc\" align=\"center\"  width=\"30%\">" + plan.getOrgdept() + "</td>"
                        + "<td class=\"infoc\" align=\"center\"  width=\"20%\">" + plan.getPlanremark() + "</td></tr>");
            }
        }
        sb.append("</table>");

        return sb.toString();

    }
    // endregion

    // region 质量保证（QA）检查记录表HTML
    public static String GetHtmlQuanCheckRecord(QuanCheckRecord item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">项目</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getProjname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检查对象</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCheckobject() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检查方法</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCheckmethod() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">检查人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getCheckusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">检查时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getCheckdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检查内容</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCheckcontent() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">检查结论</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCheckdesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">问题及处理措施</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDealmeasure() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getCheckremark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 质量监督计划HTML
    public static String GetHtmlQuanMonitPlan(QuanMonitPlan item) {

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">年度</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getMonityear() + "质量监督计划</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:40px\">目的</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getMonitpurp() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:40px\">范围</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getMonitscope() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:40px\">监督频次</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getMonitfreq() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px\">人员分工</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >监督考核小组:<br/>" + "组长:"
                + item.getMonitleadername() + "<br/>" + "组员:" + item.getMonitmembername() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">编制人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getEditusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">编制日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getEditdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 质量控制计划HTML
    public static String GetHtmlQuanControlEval(QuanControlEval item) {

        QuanControlUser user = new QuanControlUser();
        user.setTranid(item.getTranid());
        List<QuanControlUser> users = QuanDao.GetListQuanControlUser(user);

        QuanControlSamp samp = new QuanControlSamp();
        samp.setTranid(item.getTranid());
        List<QuanControlSamp> samps = QuanDao.GetListQuanControlSamp(samp);

        String uses = "";
        String sampnames = "";
        String sampstats = "";
        String sampsources = "";
        String methods = "";
        String results = "";
        if (users != null) {
            for (QuanControlUser cu : users) {
                uses += cu.getUsername() + "     ";
            }
        }

        if (samps != null) {
            for (QuanControlSamp cs : samps) {
                sampnames += cs.getSamplename() + "     ";
                sampstats += cs.getSamplestatus() + "     ";
                sampsources += cs.getSamplesource() + "     ";
                methods += cs.getUsestdmethod() + "    ";
                results += cs.getTestresult() + "     ";
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">项目名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getProjname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">项目类别</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getProjtype() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">参加人员</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + GetTranData(uses) + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">完成时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getFinishdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">样品名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + GetTranData(sampnames) + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">样品来源</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + GetTranData(sampsources)
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">样品状态</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + GetTranData(sampstats) + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">使用标准或方法</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + GetTranData(methods) + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">测试验证及结果</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + GetTranData(results) + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 备份样品申领HTML
    public static String GetHtmlSampleBackup(SampleBackup item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">样品编号</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"90%\">" + item.getSampletran() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">样品名称</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"90%\">" + item.getSamplename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备样状态</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" >" + item.getBackupstatusname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">申领原因</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" >" + item.getBackupreason() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 样品处置HTML
    public static String GetHtmlSampleDeal(SampleDeal item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">样品编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getSamplecode() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">样品名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getSamplename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">样品检测结束日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getFinishdate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">收样日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getCollectdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">处置方式</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getDealway() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">处置日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getDealdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append(
                "<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\" style=\"height:80px\">处置方法</td>"
                        + "<td class=\"infoc infoleft\" align=\"left\"  colspan=3>" + item.getDealmethod()
                        + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region 办公用品HTML
    public static String GetHtmlOfficeApply(OfficeApply item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">使用项目</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getProjid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">申请类别</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">办公用品 </td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">申请日期</td>"
                + "<td class=\"infoc infoleft\" colspan=3 align=\"left\" width=\"21%\">"
                + GetTranDate(item.getTrandate(), "yyyy-MM-dd HH:mm") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">经费渠道</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getFundsource() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">特殊要求说明</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getTranremark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlOfficeApplyDetail(List<OfficeApplyDetail> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        if (details.size() > 0) {
            sb.append("<tr class=\"infotr\"><td class=\"infoat\"  align=\"center\" width=\"10%\">明细序号</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"10%\">名称</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"10%\">单位</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">数量</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"10%\">备注</td></tr>");

            for (OfficeApplyDetail prd : details) {
                sb.append("<tr class=\"infotr\"><td class=\"infoc\" colspan=\"1\" align=\"center\" width=\"10%\">"
                        + prd.getOfficeserial() + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"2\" width=\"10%\">"
                        + GetTranData(prd.getOfficename()) + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"2\" width=\"10%\">"
                        + GetTranData(prd.getQuanunitname()) + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"1\" width=\"10%\">" + prd.getOfficequan()
                        + "</td>" + "<td class=\"infoc\" align=\"center\" colspan=\"2\" width=\"10%\">"
                        + GetTranData(prd.getRemark()) + "</td></tr>");
            }
        }
        sb.append("</table>");

        return sb.toString();

    }
    // endregion

    // region 标准变更技术确认流程HTML
    public static String GetHtmlStdReview(StdReview item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" width=\"8%\" align=\"right\">业务编号</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"95%\">" + item.getTranid() + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" width=\"5%\" align=\"right\">描述</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"95%\">" + item.getReviewdesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" width=\"5%\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"95%\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    // region 标准变更技术确认流程HTML
    public static String GetHtmlBusCountry(StdReview item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" width=\"8%\" align=\"right\">业务编号</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"95%\">" + item.getTranid() + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" width=\"5%\" align=\"right\">描述</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"95%\">" + item.getReviewdesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" width=\"5%\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"95%\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlStdReviewDetail(List<StdReviewDetail> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        if (details.size() > 0) {
            sb.append("<tr class=\"infotr\"><td class=\"infoat\"  align=\"center\" width=\"5%\">序号</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"15%\">检测产品/参数名称</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"15%\">已批准的标准名称、代号（含年号）</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"15%\">变更后的标准名称、代号（含年号）</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"50%\">变更内容</td></tr>");

            for (StdReviewDetail prd : details) {
                sb.append(
                        "<tr class=\"infotr\" ><td class=\"infoc\"  align=\"center\" width=\"5%\" style=\"height:60px;\">"
                                + prd.getSerial() + "</td>" + "<td class=\"infoc\" align=\"center\"  width=\"15%\">"
                                + GetTranData(prd.getSamplename()) + "<br/>" + prd.getParametername() + "</td>"
                                + "<td class=\"infoc\" align=\"center\"  width=\"15%\">" + GetTranData(prd.getStdid())
                                + "<br/>" + prd.getStdname() + "</td>"
                                + "<td class=\"infoc\" align=\"center\"  width=\"15%\">"
                                + GetTranData(prd.getReplstdid()) + "<br/>" + prd.getReplstdname() + "</td>"
                                + "<td class=\"infoc\" align=\"center\"  width=\"50%\">"
                                + GetTranData(prd.getChangecontent()) + "</td></tr>");
            }
        }
        sb.append("</table>");

        return sb.toString();

    }

    // endregion
    // region 标准查新记录
    public static String GetHtmlStdReplRecord(StdReplRecord item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" width=\"8%\" align=\"right\">业务编号</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"95%\">" + item.getTranid() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" width=\"8%\" align=\"right\">创建日期</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"95%\" >"
                + GetTranDate(item.getTrandate(), "yyyy-MM-dd") + "</td></tr>");
        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">标准名称</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\" >" +
        // item.getStdname() + "</td></tr>");
        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">代替标准号</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\" >" +
        // item.getReplstdid() + "</td></tr>");
        //
        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">作废日期</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\" >" +
        // GetTranDate(item.getCanceldate(),"yyyy-MM-dd") + "</td></tr>");
        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">实施日期</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\" >" +
        // GetTranDate(item.getImpldate(),"yyyy-MM-dd") + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" width=\"8%\" align=\"right\">变更描述</td>"
                + "<td class=\"infoc infoleft\"  align=\"left\" width=\"95%\" >" + item.getRemark() + "</td></tr>");
        sb.append("</table>");

        return sb.toString();

    }

    // end region 标准查新记录
    // region 标准变更内容识别记录
    public static String GetHtmlStdChange(StdChange item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">类别(产品/项目/参数)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getParametername()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">标准编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getStdid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">标准名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getStdname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">代替标准号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getReplstdid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">代替标准名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getReplstdname() + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">确认方式</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getSureability() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">标准跟踪人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getStdusername() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" width=\"10%\" align=\"right\">变更内容</td>"
                + "<td class=\"infoc infoleft\" colspan=3  align=\"left\" width=\"90%\" >" + item.getChangecontent()
                + "</td></tr>");

        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">业务编号</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\">" +
        // item.getTranid() + "</td></tr>");
        //
        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">标准编号</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\" >" +
        // item.getStdid() + "</td></tr>");
        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">标准名称</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\" >" +
        // item.getStdname() + "</td></tr>");
        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">代替标准号</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\" >" +
        // item.getReplstdid() + "</td></tr>");

        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">作废日期</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\" >" +
        // GetTranDate(item.getCanceldate(),"yyyy-MM-dd") + "</td></tr>");
        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">实施日期</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\" >" +
        // GetTranDate(item.getImpldate(),"yyyy-MM-dd") + "</td></tr>");
        // sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\"
        // width=\"8%\" align=\"right\">备注</td>" +
        // "<td class=\"infoc infoleft\" align=\"left\" width=\"95%\" >" +
        // item.getRemark() + "</td></tr>");
        sb.append("</table>");

        return sb.toString();

    }

    // end region 标准变更内容识别记录

    // region 易制毒HTML
    public static String GetHtmlPrdPoison(PrdPoison item) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"12%\">业务编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\"  width=\"12%\">使用项目</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getProjectid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\" width=\"12%\">耗材类别</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getPrdtypename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"12%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\" width=\"12%\">申请日期</td>"
                + "<td class=\"infoc infoleft\" colspan=3 align=\"left\" width=\"21%\">"
                + GetTranDate(item.getTrandate(), "yyyy-MM-dd HH:mm") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlPrdPoisonDetail(List<PrdPoisonDetail> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        if (details.size() > 0) {
            sb.append("<tr class=\"infotr\"><td class=\"infoat\"  align=\"center\" width=\"10%\">明细序号</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"10%\">名称</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">数量</td>"
                    + "<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"10%\">备注</td></tr>");

            for (PrdPoisonDetail prd : details) {
                sb.append("<tr class=\"infotr\"><td class=\"infoc\" colspan=\"1\" align=\"center\" width=\"10%\">"
                        + prd.getPrdserial() + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"2\" width=\"10%\">"
                        + GetTranData(prd.getPrdname()) + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"1\" width=\"10%\">" + prd.getPrdcount()
                        + prd.getPrdunitname() + "</td>"
                        + "<td class=\"infoc\" align=\"center\" colspan=\"2\" width=\"10%\">"
                        + GetTranData(prd.getPoiremark()) + "</td></tr>");
            }
        }
        sb.append("</table>");

        return sb.toString();

    }

    public static String GetHtmlPrdPoisonFile(List<PrdPoisonFile> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");
        if (details.size() > 0) {
            sb.append("<tr class=\"infotr\"> " + "<td class=\"infoat\" align=\"center\"  width=\"12%\">文件序号</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"30%\">文件名称</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"30%\">备注</td>"
                    + "<td class=\"infoat\" align=\"center\"  width=\"28%\"></td></tr>");

            for (PrdPoisonFile file : details) {
                sb.append("<tr class=\"infotr\"><td class=\"infoc\" colspan=\"1\" align=\"center\" width=\"12%\">"
                        + file.getFileno() + "</td>" + "<td class=\"infoc\" align=\"center\"  width=\"30%\">"
                        + file.getFilename() + "</td>" + "<td class=\"infoc\" align=\"center\"  width=\"30%\">"
                        + file.getFileremark() + "</td>"
                        + "<td  class=\"infoc\" align=\"center\"  width=\"28%\"><a class=\"pointer\" onclick=\"viewFile('"
                        + file.getFileurl() + "','" + file.getFilename() + "')\" >预览</a></td></tr>");
            }
        }

        sb.append("</table>");

        return sb.toString();
    }
    // endregion

    // region 上岗考核结果HTML
    public static String GetHtmlUserExamResult(UserExamResult item) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"12%\">考核报告编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getExamid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\"  width=\"12%\">被考核人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getUsername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\" width=\"12%\">所在科室</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDeptname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"12%\">岗位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getUserpostname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\"  width=\"12%\">职务(职称)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getUserdutyname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\" width=\"12%\">所学专业</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getUserpro() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\">准许操作的仪器</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getAllowdevname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\">可从事检测项目</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getAllowparamname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\">可从事检测样品类型</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getAllowsamplename() + "</td></tr>");

        sb.append(
                "<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" style=\"height:80px;\">业务范围</td>"
                        + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getBusscope()
                        + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }

    // endregion

    // region GLP更改申请文件查阅HTML
    public static String GetHtmlGlpFileChange(GlpFileChange item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">申请部门</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getChangedeptname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">拟更改文件名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getChangefilename()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">会办部门</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getChangedeptname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">拟更改文件编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getChangefileid()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">更改理由</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getChangereason() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">更改内容</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getChangedesc() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region Glp文件销毁HTML
    public static String GetHtmlGlpFileDestory(GlpFileDestroy item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"10%\">销毁资料编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getFileid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\"  width=\"10%\">资料名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getFilename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" width=\"10%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"center\"  width=\"10%\">销毁日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getDestroydate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append(
                "<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"center\" style=\"height:80px;\">销毁原因</td>"
                        + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDestroyreason()
                        + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region Glp文件借阅、复印登记HTML
    public static String GetHtmlGlpFileLoan(GlpFileLoan item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">借阅时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getLoandate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">借阅资料</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getFilename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">归还时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getReturndate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:60px;\">申请理由</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getLoanreason() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region Glp文件泄密处置HTML
    public static String GetHtmlGlpFileLeak(GlpFileLeak item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">泄密人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getLeakusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">泄密时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getLeakdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">泄密原因</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getLeakreason() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">造成后果</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getLeakresult() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">泄密报告时间</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >"
                + GetTranDate(item.getReportdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:80px;\">泄密简述</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getLeakdesc() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region Glp文件在线查阅
    public static String GetHtmlGlpFileOnline(GlpFileOnline item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">申请时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getTrandate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" >申请文件</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getFilename() + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:60px;\">申请理由</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getApplyreason() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion

    // region Dev 设备申请
    public static String GetHtmlUseApply(DevUseApply item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">申请人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">申请日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getApplydate(), "yyyy-MM-dd") + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" >设备编号</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevid() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" >设备名称</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevname() + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" >设备状态</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevstatusname() + "</td></tr>");
        sb.append(
                "<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:60px;\">设备接收人</td>"
                        + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getAcceptusername()
                        + "</td></tr>");
        sb.append(
                "<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:60px;\">设备借出状态</td>"
                        + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getBorrowstatuname()
                        + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:60px;\">实验室</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getLabname() + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:60px;\">使用项目</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getUseproject() + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:60px;\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");
        sb.append("</table>");

        return sb.toString();
    }

    // end region Dev 设备申请

    // region RecordSummary 年度计划总结
    public static String GetHtmlRecordSummary(RecordSummary item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"10%\">总结人员</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"10%\">总结日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"40%\">"
                + GetTranDate(item.getTrandate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" >培训目标</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getTraintarget() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" >培训对象</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getTrainobject() + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" >培训项目</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getTraintarget() + "</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" style=\"height:60px;\">培训总结</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getTrainresult() + "</td></tr>");
        sb.append("</table>");

        return sb.toString();
    }

    // end region Dev 设备申请

}
