package com.alms.entity.file;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SecretApply implements BaseBean {

    // 查阅编号;
    private String tranid;

    // 查阅内容;
    private String readcontent;

    // 查阅人;
    private String readuser;

    // 查阅人姓名;
    private String readusername;

    // 查阅时间;
    private java.util.Date readdate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 批准人;
    private String allowuser;

    // 批准人姓名;
    private String allowusername;

    // 批准人时间;
    private java.util.Date allowdate;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SecretApply> item;

    public SecretApply() {
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
        return ToolUtils.CompareProperty((SecretApply) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "查阅编号:tranid", "查阅内容:readcontent", "查阅人:readuser", "查阅人姓名:readusername", "查阅时间:readdate",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "批准人:allowuser",
                "批准人姓名:allowusername", "批准人时间:allowdate", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.readcontent = "";
        this.readuser = "";
        this.readusername = "";
        this.readdate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.allowuser = "";
        this.allowusername = "";
        this.allowdate = ToolUtils.GetMinDate();
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getReadcontent() {
        return readcontent;
    }

    public void setReadcontent(String readcontent) {
        this.readcontent = readcontent;
    }

    public String getReaduser() {
        return readuser;
    }

    public void setReaduser(String readuser) {
        this.readuser = readuser;
    }

    public String getReadusername() {
        return readusername;
    }

    public void setReadusername(String readusername) {
        this.readusername = readusername;
    }

    public java.util.Date getReaddate() {
        return readdate;
    }

    public void setReaddate(java.util.Date readdate) {
        this.readdate = readdate;
    }

    public String getFlowaction() {
        return flowaction;
    }

    public void setFlowaction(String flowaction) {
        this.flowaction = flowaction;
    }

    public String getFlowactionname() {
        return flowactionname;
    }

    public void setFlowactionname(String flowactionname) {
        this.flowactionname = flowactionname;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
    }

    public String getFlowstatusname() {
        return flowstatusname;
    }

    public void setFlowstatusname(String flowstatusname) {
        this.flowstatusname = flowstatusname;
    }

    public String getAllowuser() {
        return allowuser;
    }

    public void setAllowuser(String allowuser) {
        this.allowuser = allowuser;
    }

    public String getAllowusername() {
        return allowusername;
    }

    public void setAllowusername(String allowusername) {
        this.allowusername = allowusername;
    }

    public java.util.Date getAllowdate() {
        return allowdate;
    }

    public void setAllowdate(java.util.Date allowdate) {
        this.allowdate = allowdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<SecretApply> getItem() {
        if (item == null)
            item = new SelectBean<SecretApply>();

        return item;
    }

    public void setItem(SelectBean<SecretApply> item) {
        this.item = item;
    }

}
