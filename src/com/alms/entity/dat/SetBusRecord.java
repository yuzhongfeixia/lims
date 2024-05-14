package com.alms.entity.dat;

import java.util.ArrayList;
import java.util.List;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;
import com.alms.entity.form.*;

public class SetBusRecord implements BaseBean {

    private BusRecord record;

    private FrmRecord form;

    private List<SetBusRecordDetail> details;

    private DataDeal deal;

    private SelectBean<SetBusRecord> item;

    public SetBusRecord() {
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
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {

    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SetBusRecord) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<SetBusRecord> getItem() {
        if (item == null)
            item = new SelectBean<SetBusRecord>();

        return item;
    }

    public void setItem(SelectBean<SetBusRecord> item) {
        this.item = item;
    }

    public BusRecord getRecord() {
        if (record == null)
            record = new BusRecord();

        return record;
    }

    public void setRecord(BusRecord record) {
        this.record = record;
    }

    public List<SetBusRecordDetail> getDetails() {
        if (details == null)
            details = new ArrayList<SetBusRecordDetail>();

        return details;
    }

    public void setDetails(List<SetBusRecordDetail> details) {
        this.details = details;
    }

    public FrmRecord getForm() {
        if (form == null)
            form = new FrmRecord();

        return form;
    }

    public void setForm(FrmRecord form) {
        this.form = form;
    }

}
