package com.alms.entity.inner;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class InnerScene implements BaseBean {

    // 计划编号;
    private String tranid;

    // 小组编号;
    private String groupid;

    // 小组名称;
    private String groupname;

    // 审核目的;
    private String auditgoal;

    // 审核依据;
    private String auditby;

    // 审核范围;
    private String auditscope;

    // 审核方法;
    private String auditmethod;

    // 审核组长;
    private String auditlead;

    // 审核组长姓名;
    private String auditleadname;

    // 审核日期;
    private java.util.Date auditdate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    // 审批人;
    private String allowuser;

    // 审批人姓名;
    private String allowusername;

    // 审批人时间;
    private java.util.Date allowdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<InnerScene> item;

    public InnerScene() {
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
        return ToolUtils.CompareProperty((InnerScene) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "计划编号:tranid", "小组编号:groupid", "小组名称:groupname", "审核目的:auditgoal", "审核依据:auditby",
                "审核范围:auditscope", "审核方法:auditmethod", "审核组长:auditlead", "审核组长姓名:auditleadname", "审核日期:auditdate",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "业务员:tranuser",
                "业务员姓名:tranusername", "业务员时间:trandate", "审批人:allowuser", "审批人姓名:allowusername", "审批人时间:allowdate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.groupid = "";
        this.groupname = "";
        this.auditgoal = "";
        this.auditby = "";
        this.auditscope = "";
        this.auditmethod = "";
        this.auditlead = "";
        this.auditleadname = "";
        // this.auditdate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.allowuser = "";
        this.allowusername = "";
        // this.allowdate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getAuditgoal() {
        return auditgoal;
    }

    public void setAuditgoal(String auditgoal) {
        this.auditgoal = auditgoal;
    }

    public String getAuditby() {
        return auditby;
    }

    public void setAuditby(String auditby) {
        this.auditby = auditby;
    }

    public String getAuditscope() {
        return auditscope;
    }

    public void setAuditscope(String auditscope) {
        this.auditscope = auditscope;
    }

    public String getAuditmethod() {
        return auditmethod;
    }

    public void setAuditmethod(String auditmethod) {
        this.auditmethod = auditmethod;
    }

    public String getAuditlead() {
        return auditlead;
    }

    public void setAuditlead(String auditlead) {
        this.auditlead = auditlead;
    }

    public String getAuditleadname() {
        return auditleadname;
    }

    public void setAuditleadname(String auditleadname) {
        this.auditleadname = auditleadname;
    }

    public java.util.Date getAuditdate() {
        return auditdate;
    }

    public void setAuditdate(java.util.Date auditdate) {
        this.auditdate = auditdate;
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

    public SelectBean<InnerScene> getItem() {
        if (item == null)
            item = new SelectBean<InnerScene>();

        return item;
    }

    public void setItem(SelectBean<InnerScene> item) {
        this.item = item;
    }

}
