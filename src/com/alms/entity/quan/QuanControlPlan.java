package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanControlPlan implements BaseBean {

    // 业务编号;
    private String tranid;

    // 年度;
    private int contyear;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 创建时间;
    private java.util.Date trandate;

    // 编制人;
    private String edituser;

    // 编制人姓名;
    private String editusername;

    // 编制人时间;
    private java.util.Date editdate;

    // 审核人;
    private String audituser;

    // 审核人姓名;
    private String auditusername;

    // 审核人时间;
    private java.util.Date auditdate;

    // 批准人;
    private String approuser;

    // 批准人姓名;
    private String approusername;

    // 批准人时间;
    private java.util.Date approdate;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanControlPlan> item;

    public QuanControlPlan() {
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
        return ToolUtils.CompareProperty((QuanControlPlan) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "年度:contyear", "业务状态:flowstatus", "业务状态名称:flowstatusname",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务员:tranuser", "业务员姓名:tranusername", "创建时间:trandate",
                "编制人:edituser", "编制人姓名:editusername", "编制人时间:editdate", "审核人:audituser", "审核人姓名:auditusername",
                "审核人时间:auditdate", "批准人:approuser", "批准人姓名:approusername", "批准人时间:approdate", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.contyear = 0;
        this.flowstatus = "";
        this.flowstatusname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.edituser = "";
        this.editusername = "";
        // this.editdate = ToolUtils.GetMinDate();
        this.audituser = "";
        this.auditusername = "";
        // this.auditdate = ToolUtils.GetMinDate();
        this.approuser = "";
        this.approusername = "";
        // this.approdate = ToolUtils.GetMinDate();
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public int getContyear() {
        return contyear;
    }

    public void setContyear(int contyear) {
        this.contyear = contyear;
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

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public String getTranusername() {
        return tranusername;
    }

    public void setTranusername(String tranusername) {
        this.tranusername = tranusername;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public String getEdituser() {
        return edituser;
    }

    public void setEdituser(String edituser) {
        this.edituser = edituser;
    }

    public String getEditusername() {
        return editusername;
    }

    public void setEditusername(String editusername) {
        this.editusername = editusername;
    }

    public java.util.Date getEditdate() {
        return editdate;
    }

    public void setEditdate(java.util.Date editdate) {
        this.editdate = editdate;
    }

    public String getAudituser() {
        return audituser;
    }

    public void setAudituser(String audituser) {
        this.audituser = audituser;
    }

    public String getAuditusername() {
        return auditusername;
    }

    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername;
    }

    public java.util.Date getAuditdate() {
        return auditdate;
    }

    public void setAuditdate(java.util.Date auditdate) {
        this.auditdate = auditdate;
    }

    public String getApprouser() {
        return approuser;
    }

    public void setApprouser(String approuser) {
        this.approuser = approuser;
    }

    public String getApprousername() {
        return approusername;
    }

    public void setApprousername(String approusername) {
        this.approusername = approusername;
    }

    public java.util.Date getApprodate() {
        return approdate;
    }

    public void setApprodate(java.util.Date approdate) {
        this.approdate = approdate;
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

    public SelectBean<QuanControlPlan> getItem() {
        if (item == null)
            item = new SelectBean<QuanControlPlan>();

        return item;
    }

    public void setItem(SelectBean<QuanControlPlan> item) {
        this.item = item;
    }

}
