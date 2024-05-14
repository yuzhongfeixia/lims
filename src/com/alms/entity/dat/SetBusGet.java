package com.alms.entity.dat;

import java.util.ArrayList;
import java.util.List;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.ToolUtils;
import com.alms.entity.form.*;

public class SetBusGet implements BaseBean {

    private BusRecord record;

    private FrmGet form;

    private String tranid;

    private List<SetBusGetDetail> details;

    private DataDeal deal;

    private SelectBean<SetBusGet> item;

    public SetBusGet() {
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
        return ToolUtils.CompareProperty((SetBusGet) item, this, this.OnProperties());
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

    public SelectBean<SetBusGet> getItem() {
        if (item == null)
            item = new SelectBean<SetBusGet>();

        return item;
    }

    public void setItem(SelectBean<SetBusGet> item) {
        this.item = item;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public BusRecord getRecord() {
        if (record == null)
            record = new BusRecord();

        return record;
    }

    public void setRecord(BusRecord record) {
        this.record = record;
    }

    public List<SetBusGetDetail> getDetails() {
        if (details == null)
            details = new ArrayList<SetBusGetDetail>();

        return details;
    }

    public void setDetails(List<SetBusGetDetail> details) {
        this.details = details;
    }

    public FrmGet getForm() {
        if (form == null)
            form = new FrmGet();

        return form;
    }

    public void setForm(FrmGet form) {
        this.form = form;
    }

}
