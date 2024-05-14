package com.alms.entity.review;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class ReviewPlan implements BaseBean {

    // 计划编号;
    private String tranid;

    // 评审日期;
    private java.util.Date reviewdate;

    // 评审地点;
    private String reviewaddr;

    // 主持人;
    private String reviewuser;

    // 主持人姓名;
    private String reviewusername;

    // 评审目的;
    private String reviewgoal;

    // 评审依据;
    private String reviewtype;

    // 评审依据名称;
    private String reviewtypename;

    // 参加人员;
    private String joinuser;

    // 评审内容;
    private String reviewcontent;

    // 评审内容名称;
    private String reviewcontentname;

    // 其他;
    private String othercontent;

    // 收集信息要求;
    private String inforequire;

    // 业务员;
    private String tranuser;

    // 业务员编号;
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

    private SelectBean<ReviewPlan> item;

    public ReviewPlan() {
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
        return ToolUtils.CompareProperty((ReviewPlan) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "计划编号:tranid", "评审日期:reviewdate", "评审地点:reviewaddr", "主持人:reviewuser",
                "主持人姓名:reviewusername", "评审目的:reviewgoal", "评审依据:reviewtype", "评审依据名称:reviewtypename", "参加人员:joinuser",
                "评审内容:reviewcontent", "评审内容名称:reviewcontentname", "其他:othercontent", "收集信息要求:inforequire",
                "业务员:tranuser", "业务员编号:tranusername", "业务员时间:trandate", "批准人:allowuser", "批准人姓名:allowusername",
                "批准人时间:allowdate", "批准人意见:allowdesc", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.reviewdate = ToolUtils.GetMinDate();
        this.reviewaddr = "";
        this.reviewuser = "";
        this.reviewusername = "";
        this.reviewgoal = "";
        this.reviewtype = "";
        this.reviewtypename = "";
        this.joinuser = "";
        this.reviewcontent = "";
        this.reviewcontentname = "";
        this.othercontent = "";
        this.inforequire = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
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

    public java.util.Date getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(java.util.Date reviewdate) {
        this.reviewdate = reviewdate;
    }

    public String getReviewaddr() {
        return reviewaddr;
    }

    public void setReviewaddr(String reviewaddr) {
        this.reviewaddr = reviewaddr;
    }

    public String getReviewuser() {
        return reviewuser;
    }

    public void setReviewuser(String reviewuser) {
        this.reviewuser = reviewuser;
    }

    public String getReviewusername() {
        return reviewusername;
    }

    public void setReviewusername(String reviewusername) {
        this.reviewusername = reviewusername;
    }

    public String getReviewgoal() {
        return reviewgoal;
    }

    public void setReviewgoal(String reviewgoal) {
        this.reviewgoal = reviewgoal;
    }

    public String getReviewtype() {
        return reviewtype;
    }

    public void setReviewtype(String reviewtype) {
        this.reviewtype = reviewtype;
    }

    public String getReviewtypename() {
        return reviewtypename;
    }

    public void setReviewtypename(String reviewtypename) {
        this.reviewtypename = reviewtypename;
    }

    public String getJoinuser() {
        return joinuser;
    }

    public void setJoinuser(String joinuser) {
        this.joinuser = joinuser;
    }

    public String getReviewcontent() {
        return reviewcontent;
    }

    public void setReviewcontent(String reviewcontent) {
        this.reviewcontent = reviewcontent;
    }

    public String getReviewcontentname() {
        return reviewcontentname;
    }

    public void setReviewcontentname(String reviewcontentname) {
        this.reviewcontentname = reviewcontentname;
    }

    public String getOthercontent() {
        return othercontent;
    }

    public void setOthercontent(String othercontent) {
        this.othercontent = othercontent;
    }

    public String getInforequire() {
        return inforequire;
    }

    public void setInforequire(String inforequire) {
        this.inforequire = inforequire;
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

    public SelectBean<ReviewPlan> getItem() {
        if (item == null)
            item = new SelectBean<ReviewPlan>();

        return item;
    }

    public void setItem(SelectBean<ReviewPlan> item) {
        this.item = item;
    }

}
