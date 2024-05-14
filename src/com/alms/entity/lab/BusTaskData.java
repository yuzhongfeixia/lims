package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTaskData implements BaseBean {

    // 任务单号;
    private String taskid;

    // 样品明细编号
    private String samplecode;

    // 样品编号;
    private String sampletran;

    // 样品名称;
    private String samplename;

    // 显示值;
    private String displayvalue;

    // 提交值;
    private String submitvalue;

    // 试件序号;
    private int specserial;

    // 字段代码;
    private String fieldcode;

    // 字段类型;
    private String fieldtype;

    // 字段类型名称;
    private String fieldtypename;

    private String intid;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTaskData> item;

    public BusTaskData() {
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
        return ToolUtils.CompareProperty((BusTaskData) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "任务单号:taskid", "界面编号:intid", "样品编号:sampleid", "样品明细编号:samplecode", "样品名称:samplename",
                "显示值:displayvalue", "提交值:submitvalue", "试件序号:specserial", "字段代码:fieldcode", "字段类型:fieldtype",
                "字段类型名称:fieldtypename" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.taskid = "";
        this.samplecode = "";
        this.sampletran = "";
        this.samplename = "";
        this.displayvalue = "";
        this.submitvalue = "";
        this.specserial = 0;
        this.fieldcode = "";
        this.fieldtype = "";
        this.fieldtypename = "";
        this.intid = "";
    }

    public String getIntid() {
        return intid;
    }

    public void setIntid(String intid) {
        this.intid = intid;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getDisplayvalue() {
        return displayvalue;
    }

    public void setDisplayvalue(String displayvalue) {
        this.displayvalue = displayvalue;
    }

    public String getSubmitvalue() {
        return submitvalue;
    }

    public void setSubmitvalue(String submitvalue) {
        this.submitvalue = submitvalue;
    }

    public int getSpecserial() {
        return specserial;
    }

    public void setSpecserial(int specserial) {
        this.specserial = specserial;
    }

    public String getFieldcode() {
        return fieldcode;
    }

    public void setFieldcode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public String getFieldtypename() {
        return fieldtypename;
    }

    public void setFieldtypename(String fieldtypename) {
        this.fieldtypename = fieldtypename;
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

    public SelectBean<BusTaskData> getItem() {
        if (item == null)
            item = new SelectBean<BusTaskData>();

        return item;
    }

    public void setItem(SelectBean<BusTaskData> item) {
        this.item = item;
    }

}
