package com.gpersist.entity.publics;

import com.gpersist.entity.*;
import com.gpersist.utils.ToolUtils;

public class FlowSubmit implements BaseBean {

    private String tranid;

    private String busflow;

    private String flownode;

    private String flowdesc;

    private String submittranid;

    private DataDeal deal;

    private SelectBean<FlowSubmit> item;

    public FlowSubmit() {
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
        return ToolUtils.CompareProperty((FlowSubmit) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.busflow = "";
        this.flownode = "";
        this.flowdesc = "";
        this.submittranid = "";
    }

    public String getSubmittranid() {
        return submittranid;
    }

    public void setSubmittranid(String submittranid) {
        this.submittranid = submittranid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String[] getTranids() {
        return this.getTranid().split(",");
    }

    public String getBusflow() {
        return busflow;
    }

    public void setBusflow(String busflow) {
        this.busflow = busflow;
    }

    public String getFlownode() {
        return flownode;
    }

    public void setFlownode(String flownode) {
        this.flownode = flownode;
    }

    public String getFlowdesc() {
        return flowdesc;
    }

    public void setFlowdesc(String flowdesc) {
        this.flowdesc = flowdesc;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<FlowSubmit> getItem() {
        if (item == null)
            item = new SelectBean<FlowSubmit>();

        return item;
    }

    public void setItem(SelectBean<FlowSubmit> item) {
        this.item = item;
    }

}
