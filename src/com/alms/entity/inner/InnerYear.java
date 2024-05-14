package com.alms.entity.inner;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class InnerYear implements BaseBean {

    // 计划编号;
    private String tranid;

    // 审核目的;
    private String auditgoal;

    // 审核范围;
    private String auditscope;

    // 审核依据;
    private String auditby;

    // 内审组长;
    private String auditlead;

    // 内审组长姓名;
    private String auditleadname;

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

    // 批准人;
    private String allowuser;

    // 批准人姓名;
    private String allowusername;

    // 批准人时间;
    private java.util.Date allowdate;

    // 批准人意见;
    private String allowdesc;

    // 内审小组编号
    private String groupid;

    // 内审小组名称
    private String groupname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<InnerYear> item;

    public InnerYear() {
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
        return ToolUtils.CompareProperty((InnerYear) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "计划编号:tranid", "审核目的:auditgoal", "审核范围:auditscope", "审核依据:auditby", "内审组长:auditlead",
                "内审组长姓名:auditleadname", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname", "业务员:tranuser", "业务员姓名:tranusername", "业务员时间:trandate", "批准人:allowuser",
                "批准人姓名:allowusername", "批准人时间:allowdate", "批准人意见:allowdesc" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.auditgoal = "";
        this.auditscope = "";
        this.auditby = "";
        this.auditlead = "";
        this.auditleadname = "";
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
        this.allowdesc = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getAuditgoal() {
        return auditgoal;
    }

    public void setAuditgoal(String auditgoal) {
        this.auditgoal = auditgoal;
    }

    public String getAuditscope() {
        return auditscope;
    }

    public void setAuditscope(String auditscope) {
        this.auditscope = auditscope;
    }

    public String getAuditby() {
        return auditby;
    }

    public void setAuditby(String auditby) {
        this.auditby = auditby;
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

    public String getAllowdesc() {
        return allowdesc;
    }

    public void setAllowdesc(String allowdesc) {
        this.allowdesc = allowdesc;
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

    public SelectBean<InnerYear> getItem() {
        if (item == null)
            item = new SelectBean<InnerYear>();

        return item;
    }

    public void setItem(SelectBean<InnerYear> item) {
        this.item = item;
    }

}
