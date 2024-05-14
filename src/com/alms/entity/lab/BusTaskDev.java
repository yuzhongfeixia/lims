package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTaskDev implements BaseBean {

    // 任务单号;
    private String taskid;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 设备编号;
    private String devid;

    private String devname;

    private String sampletran;

    private String samplecode;

    private String parameterids;

    private String parametername;

    // 开始时间;
    private java.util.Date devuse;

    // 确认时间;
    private java.util.Date devconfirm;

    // 设备使用状态;
    private String devresult;

    private String fieldcode;

    private String intid;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTaskDev> item;

    public BusTaskDev() {
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
        return ToolUtils.CompareProperty((BusTaskDev) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "任务单号:taskid", "检测参数:parameterids", "检测参数:parametername", "编码:fieldcode", "样品编号:sampleid",
                "样品名称:samplename", "样品编号:samplecode", "样品编号:sampletran", "设备编号:devid", "开始时间:devuse", "确认时间:devconfirm",
                "设备使用状态:devresult" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.taskid = "";
        this.fieldcode = "";
        this.samplecode = "";
        this.sampletran = "";
        this.sampleid = "";
        this.samplename = "";
        this.devid = "";
        this.devname = "";
        this.devuse = ToolUtils.GetMinDate();
        // this.devconfirm = ToolUtils.GetMinDate();
        this.devresult = "";
        this.parameterids = "";
        this.parametername = "";
        this.intid = "";
    }

    public String getIntid() {
        return intid;
    }

    public void setIntid(String intid) {
        this.intid = intid;
    }

    public String getParameterids() {
        return parameterids;
    }

    public void setParameterids(String parameterids) {
        this.parameterids = parameterids;
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
    }

    public String getSamplename() {
        return samplename;
    }

    public String getFieldcode() {
        return fieldcode;
    }

    public void setFieldcode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname;
    }

    public java.util.Date getDevuse() {
        return devuse;
    }

    public void setDevuse(java.util.Date devuse) {
        this.devuse = devuse;
    }

    public java.util.Date getDevconfirm() {
        return devconfirm;
    }

    public void setDevconfirm(java.util.Date devconfirm) {
        this.devconfirm = devconfirm;
    }

    public String getDevresult() {
        return devresult;
    }

    public void setDevresult(String devresult) {
        this.devresult = devresult;
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

    public SelectBean<BusTaskDev> getItem() {
        if (item == null)
            item = new SelectBean<BusTaskDev>();

        return item;
    }

    public void setItem(SelectBean<BusTaskDev> item) {
        this.item = item;
    }

}
