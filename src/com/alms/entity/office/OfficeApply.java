package com.alms.entity.office;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class OfficeApply implements BaseBean {

    // 业务编号;
    private String tranid;

    // 使用项目;
    private String projid;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 申请人;
    private String tranuser;

    // 申请人姓名;
    private String tranusername;

    // 申请日期;
    private java.util.Date trandate;

    // 室主任;
    private String confirmuser;

    // 室主任姓名;
    private String confirmusername;

    // 室主任确认日期;
    private java.util.Date confirmdate;

    // 室主任确认意见;
    private String confirmdesc;

    // 经费渠道;
    private String fundsource;

    // 办公室审核人;
    private String audituser;

    // 办公室审核人姓名;
    private String auditusername;

    // 办公室审核日期;
    private java.util.Date auditdate;

    // 办公室审核意见;
    private String auditdesc;

    // 分管主任审批人;
    private String checkuser;

    // 分管主任审批人姓名;
    private String checkusername;

    // 分管主任审核日期;
    private java.util.Date checkdate;

    // 分管主任审批意见;
    private String checkdesc;

    // 主任;
    private String agreeuser;

    // 主任姓名;
    private String agreeusername;

    // 签字时间;
    private java.util.Date agreedate;

    // 特殊要求说明;
    private String tranremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<OfficeApply> item;

    public OfficeApply() {
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
        return ToolUtils.CompareProperty((OfficeApply) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "使用项目:projid", "业务动作:flowaction", "业务动作名称:flowactionname",
                "业务状态:flowstatus", "业务状态名称:flowstatusname", "申请人:tranuser", "申请人姓名:tranusername", "申请日期:trandate",
                "室主任:confirmuser", "室主任姓名:confirmusername", "室主任确认日期:confirmdate", "室主任确认意见:confirmdesc",
                "经费渠道:fundsource", "办公室审核人:audituser", "办公室审核人姓名:auditusername", "办公室审核日期:auditdate",
                "办公室审核意见:auditdesc", "分管主任审批人:checkuser", "分管主任审批人姓名:checkusername", "分管主任审核日期:checkdate",
                "分管主任审批意见:checkdesc", "主任:agreeuser", "主任姓名:agreeusername", "签字时间:agreedate", "特殊要求说明:tranremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.projid = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.confirmuser = "";
        this.confirmusername = "";
        this.confirmdate = ToolUtils.GetMinDate();
        this.confirmdesc = "";
        this.fundsource = "";
        this.audituser = "";
        this.auditusername = "";
        // this.auditdate = ToolUtils.GetMinDate();
        this.auditdesc = "";
        this.checkuser = "";
        this.checkusername = "";
        // this.checkdate = ToolUtils.GetMinDate();
        this.checkdesc = "";
        this.agreeuser = "";
        this.agreeusername = "";
        // this.agreedate = ToolUtils.GetMinDate();
        this.tranremark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getProjid() {
        return projid;
    }

    public void setProjid(String projid) {
        this.projid = projid;
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

    public String getConfirmuser() {
        return confirmuser;
    }

    public void setConfirmuser(String confirmuser) {
        this.confirmuser = confirmuser;
    }

    public String getConfirmusername() {
        return confirmusername;
    }

    public void setConfirmusername(String confirmusername) {
        this.confirmusername = confirmusername;
    }

    public java.util.Date getConfirmdate() {
        return confirmdate;
    }

    public void setConfirmdate(java.util.Date confirmdate) {
        this.confirmdate = confirmdate;
    }

    public String getConfirmdesc() {
        return confirmdesc;
    }

    public void setConfirmdesc(String confirmdesc) {
        this.confirmdesc = confirmdesc;
    }

    public String getFundsource() {
        return fundsource;
    }

    public void setFundsource(String fundsource) {
        this.fundsource = fundsource;
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

    public String getAuditdesc() {
        return auditdesc;
    }

    public void setAuditdesc(String auditdesc) {
        this.auditdesc = auditdesc;
    }

    public String getCheckuser() {
        return checkuser;
    }

    public void setCheckuser(String checkuser) {
        this.checkuser = checkuser;
    }

    public String getCheckusername() {
        return checkusername;
    }

    public void setCheckusername(String checkusername) {
        this.checkusername = checkusername;
    }

    public java.util.Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(java.util.Date checkdate) {
        this.checkdate = checkdate;
    }

    public String getCheckdesc() {
        return checkdesc;
    }

    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
    }

    public String getAgreeuser() {
        return agreeuser;
    }

    public void setAgreeuser(String agreeuser) {
        this.agreeuser = agreeuser;
    }

    public String getAgreeusername() {
        return agreeusername;
    }

    public void setAgreeusername(String agreeusername) {
        this.agreeusername = agreeusername;
    }

    public java.util.Date getAgreedate() {
        return agreedate;
    }

    public void setAgreedate(java.util.Date agreedate) {
        this.agreedate = agreedate;
    }

    public String getTranremark() {
        return tranremark;
    }

    public void setTranremark(String tranremark) {
        this.tranremark = tranremark;
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

    public SelectBean<OfficeApply> getItem() {
        if (item == null)
            item = new SelectBean<OfficeApply>();

        return item;
    }

    public void setItem(SelectBean<OfficeApply> item) {
        this.item = item;
    }

}
