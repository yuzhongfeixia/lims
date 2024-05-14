package com.alms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alms.dao.DevDao;
import com.alms.dao.LabDao;
import com.alms.dao.PrdDao;
import com.alms.entity.dat.BusRecordDetail;
import com.alms.entity.dat.SetBusGet;
import com.alms.entity.dat.SetBusGetDetail;
import com.alms.entity.dat.SetBusRecord;
import com.alms.entity.dat.SetBusRecordDetail;
import com.alms.entity.dev.*;
import com.alms.entity.form.FrmGet;
import com.alms.entity.lab.*;
import com.alms.entity.prd.BasPrd;
import com.alms.entity.prd.PrdCode;
import com.gpersist.utils.ToolUtils;

public class LabAndroidService {

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
        sb.append(".infotop { padding-left:5px; } ");
        sb.append(".inforbottom { padding-right:5px; } ");
        sb.append("</style>");
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

    // region Get BusTask Html

    public static String GetBusTaskHtml(BusTask item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table align = \"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:12px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">任务单编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getTaskid() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">样品编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getSamplecode() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">样品名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getSamplename() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">规格型号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getSamplestand() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">检测性质</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getTestpropname() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">样品状态</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getSamplestatus() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">承检室</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getLabname() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">承检室负责人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getLabusername() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">任务下达人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getSendusername() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">下达日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">"
                + GetTranDate(item.getSenddate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">要求完成日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">"
                + GetTranDate(item.getReqdate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">检测依据</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getTeststandardname()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">领样人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getTakeusername() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">领样数量</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getTakenumber() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">领样时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">"
                + GetTranDate(item.getTakedate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">发样人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"15%\">" + item.getSendsampleuser()
                + "</td></tr>");

        if (!item.getTakesign().equals("")) {
            sb.append(
                    "<tr class=\"infotr\"><td colspan=\"2\" class=\"infoat\" align=\"center\" width=\"25%\">领样人签字</td>"
                            + "<td colspan=\"2\" align=\"center\"><img src=\"file://" + item.getTakepath()
                            + "\" width=\"50%\" height=\"10%\" /></td>"
                            + "<td colspan=\"2\" class=\"infoat\" align=\"center\" width=\"25%\">发样人签字</td>"
                            + "<td colspan=\"2\" align=\"center\"><img src=\"file://" + item.getSendpath()
                            + "\" width=\"50%\" height=\"10%\" /></td></tr>");
        }

        sb.append("</table>");

        return sb.toString();
    }
    // region Get BusTask Html

    public static String GetPrintBusGetHtml(FrmGet item) {

        SetBusGet rtn = new SetBusGet();
        rtn = FormService.GetSetFrmGet(item);

        int i = 0, j = 0;
        BusRecordDetail record;
        int rowspan = 0, colspan = 0;

        String celltext = "";
        String style = "";
        String align = "";
        String zhuanhuan = "";
        int height, width = 38, nowheight = 0, nowwidth = 0;
        if (item.getGettype().equals("01")) {
            height = 23;
        } else if (item.getGettype().equals("02")) {
            height = 23;
        } else if (item.getGettype().equals("03")) {
            height = 23;
        } else if (item.getGettype().equals("04")) {
            height = 23;
        } else if (item.getGettype().equals("05")) {
            height = 23;
        } else if (item.getGettype().equals("07")) {
            height = 23;
        } else if (item.getGettype().equals("08")) {
            height = 21;
        } else if (item.getGettype().equals("11")) {
            height = 18;
        } else if (item.getGettype().equals("12")) {
            height = 25;
        } else {
            height = 28;
        }
        StringBuilder sb = new StringBuilder();
        for (int page = 0; page < rtn.getDetails().size(); page++) {
            List<BusRecordDetail> nowdetails = rtn.getDetails().get(page).getDatas();
            if (page == rtn.getDetails().size() - 1) {
                sb.append(
                        "<table align = \"center\" width=\"90%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:12px; border-collapse: collapse;\">");
            } else {
                sb.append(
                        "<table align = \"center\" width=\"90%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:12px;page-break-after: always; border-collapse: collapse;\">");
            }
            sb.append("<tr>");
            for (i = 0; i < rtn.getForm().getFormwidth(); i++) {
                sb.append("<td width=\"" + width + "px\"; height=\"1px\" ></td>");
            }
            sb.append("</tr>");

            for (i = 1; i <= rtn.getForm().getFormlength(); i++) {
                sb.append("<tr><td width=\" " + width + "px\"; height=\"" + height + "px\"></td>");
                for (j = 0; j < nowdetails.size(); j++) {
                    record = nowdetails.get(j);
                    nowheight = 0;
                    nowwidth = 0;
                    if (record.getBeginrow() == i) {
                        rowspan = record.getEndrow() - record.getBeginrow() + 1;
                        nowheight = height * rowspan;

                        colspan = record.getEndcolumn() - record.getBegincolumn() + 1;
                        nowwidth = width * colspan;

                        celltext = "";
                        switch (record.getValuesource()) {
                        case "01":
                            if (record.getFieldcode().equals("unitcharacte")) {
                                zhuanhuan = record.getCelltext() + "br单位名称";
                                celltext = zhuanhuan.replace("br", "<br/>");
                                celltext = celltext.replace("gm", "<br/>");
                            } else {
                                celltext = record.getCellname().replace("br", "<br/>");
                                celltext = celltext.replace("gm", "<br/>");
                            }
                            break;
                        case "05":
                            celltext = record.getCellname().replace("br", "<br/>");
                            celltext = celltext.replace("gm", "<br/>");
                            celltext = celltext.replace("lsgz",
                                    "<image width=\"190px\" height=\"190px\" src=\"file:///android_asset/images/sign_center.png\"style=\"top:50px;left:20px; position:relative; z-index:1;\" />");
                            celltext = celltext.replace("hjgz",
                                    "<image width=\"190px\" height=\"190px\" src=\"file:///android_asset/images/sign_center.png\"style=\"top:-250px;left:350px; position:relative; z-index:1;\" />");
                            celltext = celltext.replace("jdccgz",
                                    "<image width=\"190px\" height=\"190px\" src=\"file:///android_asset/images/sign_center.png\"style=\"top:150px;left:100px; position:relative; z-index:1;\" />");
                            celltext = celltext.replace("slxjcgz",
                                    "<image width=\"190px\" height=\"190px\" src=\"file:///android_asset/images/sign_center.png\"style=\"top:150px;left:100px; position:relative; z-index:1;\" />");
                            celltext = celltext.replace("zlcydgz",
                                    "<image width=\"190px\" height=\"190px\" src=\"file:///android_asset/images/sign_center.png\"style=\"top:0px;left:0px; position:relative; z-index:1;\" />");
                            break;
                        default:
                            celltext = record.getCelltext();
                            break;
                        }
                        celltext = record.getPrefixtext() + celltext + record.getPostfixtext();

                        style = "";
                        align = "";
                        switch (record.getAligntype()) {
                        case "1":
                            align = " align=\"left\" ";
                            style += "padding-left:3px;";
                            break;

                        case "2":
                            align = " align=\"center\" ";
                            break;

                        case "3":
                            align = " align=\"right\" ";
                            style += "padding-right:3px;";
                            break;

                        default:
                            break;
                        }

                        if (record.getIsborder() > 0) {
                            style += "border: solid " + record.getIsborder() + "px Black;";
                        }

                        if (record.getIsline() && (record.getIsborder() <= 0)) {
                            style += "border-bottom: solid 1px Black;";
                        }
                        if (record.getIsbold()) {
                            style += "font-weight:bold;";

                        }
                        style += "font-size:" + record.getFontsize() + "px;";

                        if (record.getValuesource() != "04") {
                            sb.append("<td width=\"" + nowwidth + " px\" height=\" " + nowheight + "px\"  style=\""
                                    + style + " \" " + (rowspan == 1 ? "" : "rowspan =\" " + rowspan + "\" ")
                                    + (colspan == 1 ? "" : "colspan =\"" + colspan + "\" ") + align
                                    + " valign=\"middle\">" + celltext + "</td>");
                        } else {
                            sb.append("<td width=\"" + nowwidth + " px\" height=\" " + nowheight + "px\"  style=\""
                                    + style + " \" " + (rowspan == 1 ? "" : "rowspan =\" " + rowspan + "\" ")
                                    + (colspan == 1 ? "" : "colspan =\"" + colspan + "\" ") + align
                                    + " valign=\"middle\">"
                                    + "<image width=\"250px\" height=\"250px\" src=\"file:///android_asset/images/sign_center.png\" style=\" position:relative; z-index:1;\" /></td>");

                        }
                    }
                }
                sb.append("</tr>");
            }
            sb.append("</table>");
        }
        return sb.toString();
    }

    // endregion Get BusTask Html

    // region BusTaskTest Html

    public static Object GetBusTaskTestHtml(List<BusTaskTest> btts) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");
        sb.append(
                "<tr class=\"infotr\" ><td class=\"infoat\" colspan=10 align=\"center\" width=\"100%\">检测项目</td></tr>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"20%\">样品编号</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"20%\">检测参数</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"20%\">单位</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"20%\">指标</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"20%\">检测结果</td></tr>");

        for (BusTaskTest item : btts) {
            sb.append("<tr class=\"infotr\">");

            sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"20%\">");
            sb.append(item.getSamplecode());
            sb.append("</td>");

            sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"20%\">");
            sb.append(item.getParametername());
            sb.append("</td>");

            sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"20%\">");
            sb.append(item.getParamunit());
            sb.append("</td>");

            sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"20%\">");
            sb.append(item.getTestjudge() + item.getStandvalue());
            sb.append("</td>");

            sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"20%\">");
            sb.append(item.getSubmitvalue());
            sb.append("</td>");
        }

        sb.append("</table>");

        return sb.toString();
    }

    // endregion BusTaskTest Html

    // region BusTaskSample Html

    public static Object GetBusTaskSampleHtml(List<BusTaskSample> btss) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        sb.append(
                "<br/><table align = \"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:12px;\" width=\"100%\">");
        sb.append(
                "<tr class=\"infotr\" ><td class=\"infoat\" colspan=8 align=\"center\" width=\"100%\">检测任务分配</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"25%\">检测员</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"25%\">开始试验</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"25%\">结束试验</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"25%\">是否判定</td></tr>");
        for (BusTaskSample item : btss) {
            Date begindate = item.getBegintestdate();
            Date enddate = item.getEndtestdate();
            boolean judge = item.getIsjudge();
            String begintestdate;
            String endtestdate;
            String isjudge;
            if (begindate == null) {
                begintestdate = "";
            } else {
                begintestdate = sdf.format(begindate);
            }

            if (enddate == null) {
                endtestdate = "";
            } else {
                endtestdate = sdf.format(enddate);
            }
            if (judge) {
                isjudge = "是";
            } else {
                isjudge = "否";
            }

            sb.append(
                    "<tr class=\"infotr\"><td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"25%\">");
            sb.append(item.getTestusername());
            sb.append("</td>");
            sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"25%\">");
            sb.append(begintestdate);
            sb.append("</td>");
            sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"25%\">");
            sb.append(endtestdate);
            sb.append("</td>");
            sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"25%\">");
            sb.append(isjudge);
            sb.append("</td></tr>");
        }

        sb.append("</table>");
        return sb.toString();
    }

    public static Object GetBusTaskResultHtml(List<BusTaskSample> btss) {
        StringBuilder sb = new StringBuilder();

        sb.append(
                "<br/><table align = \"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:12px;\" width=\"100%\">");
        sb.append(
                "<tr class=\"infotr\" ><td class=\"infoat\" colspan=10 align=\"center\" width=\"100%\">检测结果</td></tr>");
        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">检测人员</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"20%\">检测参数</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"20%\">检测项目</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">单位</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">指标</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"1\" width=\"10%\">检测数据</td>");
        sb.append("<td class=\"infoat\" align=\"center\" colspan=\"2\" width=\"20%\">单项评价</td></tr>");
        for (BusTaskSample item : btss) {
            BusTaskResult bResult = new BusTaskResult();
            bResult.setTaskid(item.getTaskid());
            bResult.setSampletran(item.getSampletran());
            List<BusTaskResult> bResults = LabDao.GetListBusTaskResult(bResult);
            for (BusTaskResult btr : bResults) {
                sb.append(
                        "<tr class=\"infotr\"><td class=\"infoc infoleft\" colspan=\"1\" align=\"center\" width=\"10%\">");
                sb.append(item.getTestusername());
                sb.append("</td>");
                sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"20%\">");
                sb.append(btr.getSamplecode());
                sb.append("</td>");
                sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"20%\">");
                sb.append(btr.getParametername());
                sb.append("</td>");
                sb.append("<td class=\"infoc infoleft\" colspan=\"1\" align=\"center\" width=\"10%\">");
                sb.append(btr.getParamunit());
                sb.append("</td>");
                sb.append("<td class=\"infoc infoleft\" colspan=\"1\" align=\"center\" width=\"10%\">");
                sb.append(btr.getStandvalue());
                sb.append("</td>");
                sb.append("<td class=\"infoc infoleft\" colspan=\"1\" align=\"center\" width=\"10%\">");
                sb.append(btr.getSubmitvalue());
                sb.append("</td>");
                sb.append("<td class=\"infoc infoleft\" colspan=\"2\" align=\"center\" width=\"20%\">");
                sb.append(btr.getJudgestatusname());
                sb.append("</td></tr>");
            }

        }

        sb.append("</table>");
        return sb.toString();
    }

    // endregion BusTaskSample Html

    // region Get BasDev Html

    public static String GetBasDevHtml(BasDev item) {
        item = DevDao.GetBasDev(item);
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

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">使用温度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + item.getUsetemp() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" >使用湿度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\">" + item.getUsehumid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">设备负责人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevmanagername()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">所属实验室</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + item.getDeptname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" >价格(万元)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\">" + item.getDevprice() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">购买日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getBuydate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">上次检定</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + GetTranDate(item.getPrevtime(), "yyyy-MM-dd")
                + "</td>" + "<td class=\"infoat inforight\" align=\"right\" >下次检定</td>"
                + "<td class=\"infoc infoleft\" align=\"left\">" + GetTranDate(item.getNexttime(), "yyyy-MM-dd")
                + "</td>" + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">存放位置</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getStoreplace() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">设备状态</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + item.getDevstatusname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" >借出状态</td>"
                + "<td class=\"infoc infoleft\" align=\"left\">" + item.getBorrowStatuName() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">技术指标</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getDevrange() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">开启/停用人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + item.getOperateusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" >开启/停用时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\">" + GetTranDate(item.getOperatedate(), "yyyy-MM-dd")
                + "</td>" + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">开启/停用原因</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getOperatereason()
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">设备用途</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getDevusage() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion Get BasDev Html

    // region Get BusRecord Html
    public static String GetBusRecordHtml(SetBusRecord items) {
        StringBuffer sb = new StringBuffer();
        int i = 0, j = 0;
        int rowspan = 0, colspan = 0;
        int height = 36, width = 40, nowheight = 0, nowwidth = 0;
        String form = "";
        String celltext = "";
        String style = "";
        String align = "";
        BusRecordDetail record = new BusRecordDetail();

        for (int page = 0; page < items.getDetails().size(); page++) {
            SetBusRecordDetail nowdetails = items.getDetails().get(page);

            form += "<table cellspacing=\"0\" cellpadding = \"0\" style=\"font-size:12px;\">";
            form += "<tr>";

            for (i = 0; i < items.getForm().getFormwidth(); i++) {
                form += "<td width=\"" + width + " px\"; height=\"1px\" ></td>";
            }

            form += "</tr>";

            for (i = 1; i < items.getForm().getFormlength(); i++) {
                form += "<tr><td width=\"" + width + "px\"; height=\"" + height + "px\"></td>";

                for (j = 0; j < nowdetails.getDatas().size(); j++) {
                    record = nowdetails.getDatas().get(j);

                    nowheight = 0;
                    nowwidth = 0;
                    if (record.getBeginrow() == i) {
                        rowspan = record.getEndrow() - record.getBeginrow() + 1;
                        nowheight = height * rowspan;

                        colspan = record.getEndcolumn() - record.getBegincolumn() + 1;
                        nowwidth = width * colspan;

                        celltext = "";

                        switch (record.getValuesource()) {
                        case "02":
                            celltext = record.getCelltext();
                            break;

                        case "03":
                            celltext = "";
                            break;

                        case "01":
                            celltext = record.getCellname();
                            break;

                        default:
                            break;
                        }

                        celltext = record.getPrefixtext() + celltext + record.getPostfixtext();

                        style = "";
                        align = "";

                        switch (record.getAligntype()) {
                        case "1":
                            align = " align=\"left\" ";
                            style += "padding-left:3px;";
                            break;

                        case "2":
                            align = " align=\"center\" ";
                            break;

                        case "3":
                            align = " align=\"right\" ";
                            style += "padding-right:3px;";
                            break;

                        default:
                            break;
                        }

                        if (record.getIsborder() > 0)
                            style += "border: solid " + record.getIsborder() + "px Black;";

                        if (record.getIsline() && (record.getIsborder() <= 0))
                            style += "border-bottom: solid 1px Black;";

                        if (record.getIsbold())
                            style += "font-weight:bold;";

                        style += "font-size:" + record.getFontsize() + "px;";

                        if (!record.getValuesource().equals("04")) {

                            form += "<td width=\"" + nowwidth + "px\" height=\"" + nowheight + "px\" " + " style=\""
                                    + style + "\" " + (rowspan == 1 ? "" : "rowspan =\"" + rowspan + "\" ")
                                    + (colspan == 1 ? "" : "colspan =\"" + colspan + "\" ") + align
                                    + "valign=\"middle\">" + celltext + "</td>";
                        }

                        if (record.getValuesource().equals("04")) {

                            if (record.getClasssource().equals("")) {
                                form += "<td width=\"" + nowwidth + "px\" height=\"" + nowheight + "px\" border= 0"
                                        + (rowspan == 1 ? "" : "rowspan =\"" + rowspan + "\" ")
                                        + (colspan == 1 ? "" : "colspan =\"" + colspan + "\" ") + align
                                        + " valign=\"middle\">" + "<image width=\"" + nowwidth + "px\" height=\""
                                        + nowheight + "px\" src=\"images/sign/" + record.getFieldcode()
                                        + ".png\" /></td>";
                            } else if (!celltext.equals("")) {
                                form += "<td width=\"" + nowwidth + "px\" height=\"" + nowheight + "px\" border= 0"
                                        + (rowspan == 1 ? "" : "rowspan =\"" + rowspan + "\" ")
                                        + (colspan == 1 ? "" : "colspan =\"" + colspan + "\" ") + align
                                        + " valign=\"middle\">" + "<image height=\"" + nowheight
                                        + "px\" src=\"images/sign/" + celltext + ".png\" /></td>";
                            }
                            // 公式变成图片
                            else if (record.getClasssource().equals("formula")) {
                                form += "<td width=\"" + nowwidth + "px\" height=\"" + nowheight + "px\""
                                        + " style=\"border: solid 1px Black;\" "
                                        + (rowspan == 1 ? "" : "rowspan =\"" + rowspan + "\" ")
                                        + (colspan == 1 ? "" : "colspan =\"" + colspan + "\" ") + align
                                        + " valign=\"middle\">"
                                        + "w = p×V1× V3×V4×A1/m×V2×V5×A 式中：p-标准溶液质量浓度,V4-标准溶液进样体积,A-标准溶液响应值</td>";
                            } else {
                                form += "<td width=\"" + nowwidth + "px\" height=\"" + nowheight + "px\" " + " style=\""
                                        + style + "\" " + (rowspan == 1 ? "" : "rowspan =\"" + rowspan + "\" ")
                                        + (colspan == 1 ? "" : "colspan =\"" + colspan + "\" ") + align
                                        + "valign=\"middle\">" + celltext + "</td>";

                            }
                        }
                    }
                }
                form += "</tr>";
            }

            form += "</table></br>";
        }

        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"width:98%;\">"
                + "<tr><td align=\"center\">" + form + "</td></tr></table>");
        return sb.toString();
    }
    // endregion Get BusRecord Html

    // region Get BasPrd
    public static String GetBasPrdHtml(BasPrd item) {

        PrdCode pCode = new PrdCode();
        pCode.setPrdid(item.getPrdid());
        pCode.setStoreid(item.getStoreid());
        pCode = PrdDao.GetPrdCodeByPrd(pCode);

        double lastnumber = 0.0;

        if (pCode == null) {
            lastnumber = 0.0;
        } else {
            lastnumber = pCode.getLastnumber();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">试剂耗材编号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getPrdid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">试剂耗材名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getPrdname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">耗材分类</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getPrdtypename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">最大库存量</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getPrdmax() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">最小库存量</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getPrdmin() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">计量单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getPrdunitname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">参考价格(元)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getPrdprice() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">默认有效期(月)</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getValidmonth() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">所在仓库</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getStorename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">生产厂商</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getFactoryname() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">供应商</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getTradename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">生产日期"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">"
                + GetTranDate(item.getFactorydate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">购买人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getBuyusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">购买日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">"
                + GetTranDate(item.getBuydate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">存放条件"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getPrdenv() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">规格型号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getPrdstandard() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">耗材管理员</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getPrdusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">剩余库存量</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + lastnumber + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">试剂耗材作用</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getPrdeffect() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">备注</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getRemark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }
    // endregion Get BasPrd

    // region Get BusNoticeHtml
    public static String GetBusGetNoticeHtml(BusGetNotice item) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:14px;\" width=\"100%\">");

        sb.append(
                "<tr class=\"infotrheader\"><td colspan=\"6\" align=\"center\" width=\"100%\" class=\"infohead\"><b>取样通知单</b></td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">通知单号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">" + item.getTranid() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">抽样单类型</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getGettypename() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">检测类型</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getTesttypename() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\" width=\"12%\">下达日期</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"22%\">"
                + GetTranDate(item.getTrandate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\"  width=\"12%\">下达人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\"  width=\"21%\">" + item.getTranusername() + "</td>"
                + "<td class=\"infoat inforight\" align=\"right\" width=\"12%\">抽样人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"21%\">" + item.getGetusername() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">受检单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + item.getTestedname() + "</td>"
                + "<td class=\"infoat\" align=\"center\">完成日期</td>" + "<td class=\"infoc infoleft\" align=\"left\">"
                + GetTranDate(item.getFinishdate(), "yyyy-MM-dd") + "</td>"
                + "<td class=\"infoat\" align=\"center\">当前状态</td>" + "<td class=\"infoc infoleft\" align=\"left\">"
                + item.getFlowstatusname() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">退单说明</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getBackdesc() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat inforight\" align=\"right\">业务说明</td>"
                + "<td class=\"infoc infoleft\" colspan=5 align=\"left\" >" + item.getTranremark() + "</td></tr>");

        sb.append("</table>");

        return sb.toString();

    }
    // endregion

    // region Get BusGetNoticeDetail Html

    public static String GetBusGetNoticeDetailHtml(BusGetNoticeDetail item) {
        StringBuilder sb = new StringBuilder();

        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:12px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">抽样内容</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + item.getGetcontent() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">抽样地点</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + item.getGetaddress() + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">取样时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"24%\">"
                + GetTranDate(item.getGetdate(), "yyyy-MM-dd") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">备注</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" colspan=\"5\" width=\"90%\">"
                + GetTranData(item.getRemark()) + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion Get BusGetNoticeDetail Html

    // region Get BusGet Html
    public static String GetBusGetHtml(BusGet item) {
        StringBuffer sb = new StringBuffer();
        sb.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:12px;\" width=\"100%\">");

        sb.append(
                "<tr class=\"infotrheader\"><td colspan=\"6\" align=\"center\" width=\"100%\" class=\"infohead\"><b>抽样单</b></td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">抽样单号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getTranid()) + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">抽样人</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getSampleusername())
                + "</td>" + "<td class=\"infoat\" align=\"center\" width=\"10%\">抽样时间</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"24%\">"
                + GetTranDate(item.getGetdate(), "yyyy-MM-dd hh:mm") + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\">抽样单类型</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + GetTranData(item.getGettypename()) + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">检验类别</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getTesttypename())
                + "</td>" + "<td class=\"infoat\" align=\"center\">抽样地点</td>"
                + "<td class=\"infoc infoleft\" align=\"left\">" + GetTranData(item.getSendaddr()) + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\">受检单位</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + GetTranData(item.getTestedname()) + "</td>"
                + "<td class=\"infoat\" align=\"center\">企业地址</td>" + "<td class=\"infoc infoleft\" align=\"left\">"
                + GetTranData(item.getEnteraddr()) + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">邮编</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getEnterpost())
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\">联系电话</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + GetTranData(item.getTestedtele()) + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">受检单位性质</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getEntertypename())
                + "</td>" + "<td class=\"infoat\" align=\"center\">受检单位规模</td>"
                + "<td class=\"infoc infoleft\" align=\"left\">" + GetTranData(item.getEnterscalename())
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\">基地名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" >" + GetTranData(item.getBasename()) + "</td>"
                + "<td class=\"infoat\" align=\"center\" width=\"10%\">基地面积</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getBasearea())
                + "</td>" + "<td class=\"infoat\" align=\"center\">采样深度</td>"
                + "<td class=\"infoc infoleft\" align=\"left\">" + GetTranData(item.getSampledeep()) + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\">业务说明</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" colspan=\"5\">" + GetTranData(item.getTranremark())
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    public static String GetBusGetDetailHtml(BusGetDetail item) {
        StringBuffer sb = new StringBuffer();

        sb.append(
                "<br /><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"font-size:12px;\" width=\"100%\">");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">样品名称</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getSamplename())
                + "</td>" + "<td class=\"infoat\" align=\"center\" width=\"10%\">商标</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getTrademark())
                + "</td>" + "<td class=\"infoat\" align=\"center\" width=\"10%\">生产厂家</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"24%\">" + GetTranData(item.getFactname())
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">抽样地点</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getGetaddr())
                + "</td>" + "<td class=\"infoat\" align=\"center\" width=\"10%\">抽样方式</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getGetmethod())
                + "</td>" + "<td class=\"infoat\" align=\"center\" width=\"10%\">生产批号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"24%\">" + GetTranData(item.getPrdcode())
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">样本</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getSamplesize())
                + "</td>" + "<td class=\"infoat\" align=\"center\" width=\"10%\">样本基数</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getSamplebase())
                + "</td>" + "<td class=\"infoat\" align=\"center\" width=\"10%\">样品数量</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"24%\">" + item.getSamplecount() + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">通讯地址</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getFactaddr())
                + "</td>" + "<td class=\"infoat\" align=\"center\" width=\"10%\">邮编</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getFactpost())
                + "</td>" + "<td class=\"infoat\" align=\"center\" width=\"10%\">电话</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"24%\">" + GetTranData(item.getFacttele())
                + "</td></tr>");

        sb.append("<tr class=\"infotr\"><td class=\"infoat\" align=\"center\" width=\"10%\">登记证号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getRegcode())
                + "</td>" + "<td class=\"infoat\" align=\"center\" width=\"10%\">准产证号</td>"
                + "<td class=\"infoc infoleft\" align=\"left\" width=\"23%\">" + GetTranData(item.getAllowcode())
                + "</td></tr>");

        sb.append("</table>");

        return sb.toString();
    }

    // endregion Get BusGet Html

}
