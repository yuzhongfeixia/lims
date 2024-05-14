package com.alms.entity.review;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class ReviewYear implements BaseBean {

    // 计划编号;
    private String tranid;

    // 评审目的;
    private String reviewgoal;

    // 评审组成员;
    private String reviewcrew;

    // 评审日期安排;
    private String reviewplan;

    // 评审要点;
    private String reviewgist;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    // 质量负责人;
    private String audituser;

    // 质量负责人姓名;
    private String auditusername;

    // 质量负责人时间;
    private java.util.Date auditdate;

    // 质量负责人意见;
    private String auditdesc;

    // 主任;
    private String allowuser;

    // 主任姓名;
    private String allowusername;

    // 主任时间;
    private java.util.Date allowdate;

    // 主任意见;
    private String allowdesc;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<ReviewYear> item;

    public ReviewYear() {
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
        return ToolUtils.CompareProperty((ReviewYear) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "计划编号:tranid", "评审目的:reviewgoal", "评审组成员:reviewcrew", "评审日期安排:reviewplan",
                "评审要点:reviewgist", "业务员:tranuser", "业务员姓名:tranusername", "业务员时间:trandate", "质量负责人:audituser",
                "质量负责人姓名:auditusername", "质量负责人时间:auditdate", "质量负责人意见:auditdesc", "主任:allowuser", "主任姓名:allowusername",
                "主任时间:allowdate", "主任意见:allowdesc", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.reviewgoal = "";
        this.reviewcrew = "";
        this.reviewplan = "";
        this.reviewgist = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.audituser = "";
        this.auditusername = "";
        this.auditdate = ToolUtils.GetMinDate();
        this.auditdesc = "";
        this.allowuser = "";
        this.allowusername = "";
        this.allowdate = ToolUtils.GetMinDate();
        this.allowdesc = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getReviewgoal() {
        return reviewgoal;
    }

    public void setReviewgoal(String reviewgoal) {
        this.reviewgoal = reviewgoal;
    }

    public String getReviewcrew() {
        return reviewcrew;
    }

    public void setReviewcrew(String reviewcrew) {
        this.reviewcrew = reviewcrew;
    }

    public String getReviewplan() {
        return reviewplan;
    }

    public void setReviewplan(String reviewplan) {
        this.reviewplan = reviewplan;
    }

    public String getReviewgist() {
        return reviewgist;
    }

    public void setReviewgist(String reviewgist) {
        this.reviewgist = reviewgist;
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

    public SelectBean<ReviewYear> getItem() {
        if (item == null)
            item = new SelectBean<ReviewYear>();

        return item;
    }

    public void setItem(SelectBean<ReviewYear> item) {
        this.item = item;
    }

}
