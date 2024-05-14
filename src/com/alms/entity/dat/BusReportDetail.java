package com.alms.entity.dat;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusReportDetail implements BaseBean {

    private String reportid;

    private String actreportid;

    private String taskid;

    private String samplecode;

    private int formserial;

    // 表单编号;
    private String formid;

    // 序号;
    private int cellserial;

    // 开始行数;
    private int beginrow;

    // 开始列数;
    private int endrow;

    // 结束行数;
    private int begincolumn;

    // 结束列表;
    private int endcolumn;

    // 项目名称;
    private String cellname;

    // 数据来源;
    private String valuesource;

    // 数据来源名称;
    private String valuesourcename;

    // 数据类型;
    private String valuetype;

    // 检测值类型名称;
    private String valuetypename;

    private String classsource;

    private String fieldcode;

    private int groupserial;

    private int specserial;

    // 显示数据;
    private String celltext;

    // 显示格式;
    private String cellformat;

    // 线框;
    private boolean isborder;

    // 下划线;
    private boolean isline;

    // 是否加粗;
    private boolean isbold;

    // 字体大小;
    private int fontsize;

    // 对齐方式;
    private String aligntype;

    // 对齐方式名称;
    private String aligntypename;

    // 字体类型;
    private String fonttype;
    // 字体类型名称;
    private String fonttypename;
    // 字体类型标识;
    private String fonttypesign;

    // 前缀;
    private String prefixtext;

    // 后缀;
    private String postfixtext;

    private int nowgroup;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusReportDetail> item;

    public BusReportDetail() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((BusReportDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "序号:cellserial", "开始行数:beginrow", "开始列数:endrow", "结束行数:begincolumn", "结束列表:endcolumn",
                "项目名称:cellname", "数据来源:valuesource", "数据类型:valuetype", "显示数据:celltext", "显示格式:cellformat",
                "线框:isborder", "下划线:isline", "是否加粗:isbold", "字体大小:fontsize", "字体类型:fonttype", "字体类型名称:fonttypename",
                "字体类型标识:fonttypesign", "对齐方式:aligntype", "前缀:prefixtext", "后缀:postfixtext", "组号:groupserial",
                "试件号:specserial", "所属类编号:classsource", "类字段编号:fieldcode" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.reportid = "";
        this.formid = "";
        this.samplecode = "";
        this.formserial = 0;
        this.cellserial = 0;
        this.beginrow = 0;
        this.endrow = 0;
        this.begincolumn = 0;
        this.endcolumn = 0;
        this.cellname = "";
        this.valuesource = "";
        this.valuesourcename = "";
        this.valuetype = "";
        this.valuetypename = "";
        this.celltext = "";
        this.cellformat = "";
        this.classsource = "";
        this.fieldcode = "";
        this.isborder = false;
        this.isline = false;
        this.isbold = false;
        this.fontsize = 0;
        this.aligntype = "";
        this.aligntypename = "";
        this.prefixtext = "";
        this.postfixtext = "";
        this.groupserial = 0;
        this.specserial = 0;
        this.nowgroup = 0;
        this.taskid = "";
        this.actreportid = "";
        this.fonttype = "";
        this.fonttypename = "";
        this.fonttypesign = "";
    }

    public String getActreportid() {
        return actreportid;
    }

    public String getFonttype() {
        return fonttype;
    }

    public void setFonttype(String fonttype) {
        this.fonttype = fonttype;
    }

    public String getFonttypename() {
        return fonttypename;
    }

    public void setFonttypename(String fonttypename) {
        this.fonttypename = fonttypename;
    }

    public String getFonttypesign() {
        return fonttypesign;
    }

    public void setFonttypesign(String fonttypesign) {
        this.fonttypesign = fonttypesign;
    }

    public void setActreportid(String actreportid) {
        this.actreportid = actreportid;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public int getFormserial() {
        return formserial;
    }

    public void setFormserial(int formserial) {
        this.formserial = formserial;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public int getNowgroup() {
        return nowgroup;
    }

    public void setNowgroup(int nowgroup) {
        this.nowgroup = nowgroup;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public int getCellserial() {
        return cellserial;
    }

    public void setCellserial(int cellserial) {
        this.cellserial = cellserial;
    }

    public int getBeginrow() {
        return beginrow;
    }

    public void setBeginrow(int beginrow) {
        this.beginrow = beginrow;
    }

    public int getEndrow() {
        return endrow;
    }

    public void setEndrow(int endrow) {
        this.endrow = endrow;
    }

    public int getBegincolumn() {
        return begincolumn;
    }

    public void setBegincolumn(int begincolumn) {
        this.begincolumn = begincolumn;
    }

    public int getEndcolumn() {
        return endcolumn;
    }

    public void setEndcolumn(int endcolumn) {
        this.endcolumn = endcolumn;
    }

    public String getCellname() {
        return cellname;
    }

    public void setCellname(String cellname) {
        this.cellname = cellname;
    }

    public String getValuesource() {
        return valuesource;
    }

    public void setValuesource(String valuesource) {
        this.valuesource = valuesource;
    }

    public String getValuesourcename() {
        return valuesourcename;
    }

    public void setValuesourcename(String valuesourcename) {
        this.valuesourcename = valuesourcename;
    }

    public String getValuetype() {
        return valuetype;
    }

    public void setValuetype(String valuetype) {
        this.valuetype = valuetype;
    }

    public String getValuetypename() {
        return valuetypename;
    }

    public void setValuetypename(String valuetypename) {
        this.valuetypename = valuetypename;
    }

    public String getCelltext() {
        return celltext;
    }

    public void setCelltext(String celltext) {
        this.celltext = celltext;
    }

    public String getCellformat() {
        return cellformat;
    }

    public void setCellformat(String cellformat) {
        this.cellformat = cellformat;
    }

    public boolean getIsline() {
        return isline;
    }

    public void setIsline(boolean isline) {
        this.isline = isline;
    }

    public boolean getIsborder() {
        return isborder;
    }

    public void setIsborder(boolean isborder) {
        this.isborder = isborder;
    }

    public boolean getIsbold() {
        return isbold;
    }

    public void setIsbold(boolean isbold) {
        this.isbold = isbold;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public String getAligntype() {
        return aligntype;
    }

    public void setAligntype(String aligntype) {
        this.aligntype = aligntype;
    }

    public String getAligntypename() {
        return aligntypename;
    }

    public void setAligntypename(String aligntypename) {
        this.aligntypename = aligntypename;
    }

    public String getPrefixtext() {
        return prefixtext;
    }

    public void setPrefixtext(String prefixtext) {
        this.prefixtext = prefixtext;
    }

    public String getPostfixtext() {
        return postfixtext;
    }

    public void setPostfixtext(String postfixtext) {
        this.postfixtext = postfixtext;
    }

    public String getClasssource() {
        return classsource;
    }

    public void setClasssource(String classsource) {
        this.classsource = classsource;
    }

    public String getFieldcode() {
        return fieldcode;
    }

    public void setFieldcode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public int getGroupserial() {
        return groupserial;
    }

    public void setGroupserial(int groupserial) {
        this.groupserial = groupserial;
    }

    public int getSpecserial() {
        return specserial;
    }

    public void setSpecserial(int specserial) {
        this.specserial = specserial;
    }

    public SearchParams getSearch() {
        if (search == null)
            search = new SearchParams();

        return search;
    }

    public void setSearch(SearchParams search) {
        this.search = search;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<BusReportDetail> getItem() {
        if (item == null)
            item = new SelectBean<BusReportDetail>();

        return item;
    }

    public void setItem(SelectBean<BusReportDetail> item) {
        this.item = item;
    }

}
