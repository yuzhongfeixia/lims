package com.alms.entity.dat;

import java.util.ArrayList;
import java.util.List;

import com.alms.entity.form.FrmReport;
import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;

public class SetBusReport implements BaseBean {

    private BusReport record;

    private FrmReport form;

    private List<SetBusReportDetail> details;

    private DataDeal deal;

    private SelectBean<SetBusReport> item;

    public SetBusReport() {
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
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {

    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SetBusReport) item, this, this.OnProperties());
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

    public SelectBean<SetBusReport> getItem() {
        if (item == null)
            item = new SelectBean<SetBusReport>();

        return item;
    }

    public void setItem(SelectBean<SetBusReport> item) {
        this.item = item;
    }

    public BusReport getRecord() {
        if (record == null)
            record = new BusReport();

        return record;
    }

    public void setRecord(BusReport record) {
        this.record = record;
    }

    public List<SetBusReportDetail> getDetails() {
        if (details == null)
            details = new ArrayList<SetBusReportDetail>();

        return details;
    }

    public void setDetails(List<SetBusReportDetail> details) {
        this.details = details;
    }

    public FrmReport getForm() {
        if (form == null)
            form = new FrmReport();

        return form;
    }

    public void setForm(FrmReport form) {
        this.form = form;
    }

}
