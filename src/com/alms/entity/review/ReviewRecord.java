package com.alms.entity.review;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class ReviewRecord implements BaseBean {

    // 记录编号;
    private String tranid;

    // 会议编号;
    private String meetid;

    // 评审日期;
    private java.util.Date reviewdate;

    // 评审地点;
    private String reviewaddr;

    // 评审内容;
    private String reviewcontent;

    // 记录人;
    private String tranuser;

    // 记录人姓名;
    private String tranusername;

    // 记录人时间;
    private java.util.Date trandate;

    // 负责人;
    private String reviewlead;

    // 负责人姓名;
    private String reviewleadname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<ReviewRecord> item;

    public ReviewRecord() {
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
        return ToolUtils.CompareProperty((ReviewRecord) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "记录编号:tranid", "会议编号:meetid", "评审日期:reviewdate", "评审地点:reviewaddr", "评审内容:reviewcontent",
                "记录人:tranuser", "记录人姓名:tranusername", "记录人时间:trandate", "负责人:reviewlead", "负责人姓名:reviewleadname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.meetid = "";
        this.reviewdate = ToolUtils.GetMinDate();
        this.reviewaddr = "";
        this.reviewcontent = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.reviewlead = "";
        this.reviewleadname = "";
    }

    public String getMeetid() {
        return meetid;
    }

    public void setMeetid(String meetid) {
        this.meetid = meetid;
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

    public String getReviewcontent() {
        return reviewcontent;
    }

    public void setReviewcontent(String reviewcontent) {
        this.reviewcontent = reviewcontent;
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

    public String getReviewlead() {
        return reviewlead;
    }

    public void setReviewlead(String reviewlead) {
        this.reviewlead = reviewlead;
    }

    public String getReviewleadname() {
        return reviewleadname;
    }

    public void setReviewleadname(String reviewleadname) {
        this.reviewleadname = reviewleadname;
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

    public SelectBean<ReviewRecord> getItem() {
        if (item == null)
            item = new SelectBean<ReviewRecord>();

        return item;
    }

    public void setItem(SelectBean<ReviewRecord> item) {
        this.item = item;
    }

}
