package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusRecordData implements BaseBean {

    private String tranid;

    private String samplecode;

    // 任务单号;
    private String taskid;

    // 试验样品编号;
    private String sampletran;

    private String sampleid;

    private int groupserial;

    private int specserial;

    private String classsource;

    private String fieldcode;

    private String fieldtype;

    private String displayvalue;

    private String submitvalue;

    private String validcode;

    private DataDeal deal;

    private SelectBean<BusRecordData> item;

    public BusRecordData() {
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
        return ToolUtils.CompareProperty((BusRecordData) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "任务单号:taskid", "试验样品编号:sampletran" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.samplecode = "";
        this.taskid = "";
        this.sampletran = "";
        this.groupserial = 0;
        this.specserial = 0;
        this.classsource = "";
        this.fieldcode = "";
        this.fieldtype = "";
        this.displayvalue = "";
        this.submitvalue = "";
        this.validcode = "";
        this.sampleid = "";
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public int getGroupserial() {
        return groupserial;
    }

    public void setGroupserial(int groupserial) {
        this.groupserial = groupserial;
    }

    public String getClasssource() {
        return classsource;
    }

    public void setClasssource(String classsource) {
        this.classsource = classsource;
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

    public String getValidcode() {
        return validcode;
    }

    public void setValidcode(String validcode) {
        this.validcode = validcode;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<BusRecordData> getItem() {
        if (item == null)
            item = new SelectBean<BusRecordData>();

        return item;
    }

    public void setItem(SelectBean<BusRecordData> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "BusRecordData [tranid=" + tranid + ", samplecode=" + samplecode + ", taskid=" + taskid + ", sampletran="
                + sampletran + ", sampleid=" + sampleid + ", groupserial=" + groupserial + ", specserial=" + specserial
                + ", classsource=" + classsource + ", fieldcode=" + fieldcode + ", fieldtype=" + fieldtype
                + ", displayvalue=" + displayvalue + ", submitvalue=" + submitvalue + ", validcode=" + validcode
                + ", deal=" + deal + ", item=" + item + "]";
    }

}
