package com.alms.entity.review;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class MeetSign implements BaseBean {

    // 会议编号;
    private String meetid;

    // 评审通知编号;
    private String noticeid;

    // 会议主题;
    private String meettopic;

    // 会议时间;
    private java.util.Date meetdate;

    // 会议地点;
    private String meetplace;

    // 记录人;
    private String tranuser;

    // 记录人姓名;
    private String tranusername;

    // 记录日期;
    private java.util.Date trandate;

    // 审核组长;
    private String checkuser;

    // 审核组长姓名;
    private String checkusername;

    // 审核日期;
    private java.util.Date checkdate;

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

    private SelectBean<MeetSign> item;

    public MeetSign() {
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
        return ToolUtils.CompareProperty((MeetSign) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "会议编号:meetid", "评审通知:noticeid", "会议主题:meettopic", "会议时间:meetdate", "会议地点:meetplace",
                "记录人:tranuser", "记录人姓名:tranusername", "记录日期:trandate", "审核组长:checkuser", "审核组长姓名:checkusername",
                "审核日期:checkdate", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.meetid = "";
        this.noticeid = "";
        this.meettopic = "";
        this.meetdate = ToolUtils.GetMinDate();
        this.meetplace = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.checkuser = "";
        this.checkusername = "";
        this.checkdate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
    }

    public String getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(String noticeid) {
        this.noticeid = noticeid;
    }

    public String getMeetid() {
        return meetid;
    }

    public void setMeetid(String meetid) {
        this.meetid = meetid;
    }

    public String getMeettopic() {
        return meettopic;
    }

    public void setMeettopic(String meettopic) {
        this.meettopic = meettopic;
    }

    public java.util.Date getMeetdate() {
        return meetdate;
    }

    public void setMeetdate(java.util.Date meetdate) {
        this.meetdate = meetdate;
    }

    public String getMeetplace() {
        return meetplace;
    }

    public void setMeetplace(String meetplace) {
        this.meetplace = meetplace;
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

    public SelectBean<MeetSign> getItem() {
        if (item == null)
            item = new SelectBean<MeetSign>();

        return item;
    }

    public void setItem(SelectBean<MeetSign> item) {
        this.item = item;
    }

}
