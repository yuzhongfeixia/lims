package com.alms.entity.inner;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class InnerReport implements BaseBean {

    // 报告编号;
    private String reportid;

    // 小组编号;
    private String groupid;

    // 小组名称;
    private String groupname;

    // 审核要素;
    private String auditelem;

    // 受审核部门;
    private String auditeddept;

    // 收审核部门名称;
    private String auditeddeptname;

    // 审核依据;
    private String auditby;

    // 审核时间;
    private java.util.Date auditdate;

    // 审核目的;
    private String auditgoal;

    // 审核组长;
    private String auditlead;

    // 审核组长姓名;
    private String auditleadname;

    // 审核组长时间;
    private java.util.Date leaddate;

    // 内审情况;
    private String auditdesc;

    // 不合格项;
    private String belowstandard;

    // 前次执行情况;
    private String beforedesc;

    // 质量体系运行与评价;
    private String inneradv;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<InnerReport> item;

    public InnerReport() {
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
        return ToolUtils.CompareProperty((InnerReport) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "报告编号:reportid", "小组编号:groupid", "小组名称:groupname", "审核要素:auditelem", "受审核部门:auditeddept",
                "收审核部门名称:auditeddeptname", "审核依据:auditby", "审核时间:auditdate", "审核目的:auditgoal", "审核组长:auditlead",
                "审核组长姓名:auditleadname", "审核组长时间:leaddate", "内审情况:auditdesc", "不合格项:belowstandard", "前次执行情况:beforedesc",
                "质量体系运行与评价:inneradv", "业务员:tranuser", "业务员姓名:tranusername", "业务员时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.reportid = "";
        this.groupid = "";
        this.groupname = "";
        this.auditelem = "";
        this.auditeddept = "";
        this.auditeddeptname = "";
        this.auditby = "";
        this.auditdate = ToolUtils.GetMinDate();
        this.auditgoal = "";
        this.auditlead = "";
        this.auditleadname = "";
        this.leaddate = ToolUtils.GetMinDate();
        this.auditdesc = "";
        this.belowstandard = "";
        this.beforedesc = "";
        this.inneradv = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
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

    public String getAuditelem() {
        return auditelem;
    }

    public void setAuditelem(String auditelem) {
        this.auditelem = auditelem;
    }

    public String getAuditeddept() {
        return auditeddept;
    }

    public void setAuditeddept(String auditeddept) {
        this.auditeddept = auditeddept;
    }

    public String getAuditeddeptname() {
        return auditeddeptname;
    }

    public void setAuditeddeptname(String auditeddeptname) {
        this.auditeddeptname = auditeddeptname;
    }

    public String getAuditby() {
        return auditby;
    }

    public void setAuditby(String auditby) {
        this.auditby = auditby;
    }

    public java.util.Date getAuditdate() {
        return auditdate;
    }

    public void setAuditdate(java.util.Date auditdate) {
        this.auditdate = auditdate;
    }

    public String getAuditgoal() {
        return auditgoal;
    }

    public void setAuditgoal(String auditgoal) {
        this.auditgoal = auditgoal;
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

    public java.util.Date getLeaddate() {
        return leaddate;
    }

    public void setLeaddate(java.util.Date leaddate) {
        this.leaddate = leaddate;
    }

    public String getAuditdesc() {
        return auditdesc;
    }

    public void setAuditdesc(String auditdesc) {
        this.auditdesc = auditdesc;
    }

    public String getBelowstandard() {
        return belowstandard;
    }

    public void setBelowstandard(String belowstandard) {
        this.belowstandard = belowstandard;
    }

    public String getBeforedesc() {
        return beforedesc;
    }

    public void setBeforedesc(String beforedesc) {
        this.beforedesc = beforedesc;
    }

    public String getInneradv() {
        return inneradv;
    }

    public void setInneradv(String inneradv) {
        this.inneradv = inneradv;
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

    public SelectBean<InnerReport> getItem() {
        if (item == null)
            item = new SelectBean<InnerReport>();

        return item;
    }

    public void setItem(SelectBean<InnerReport> item) {
        this.item = item;
    }

}
