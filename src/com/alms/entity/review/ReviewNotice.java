package com.alms.entity.review;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class ReviewNotice implements BaseBean {

    // 业务编号;
    private String tranid;

    // 评审计划;
    private String planid;

    // 称呼;
    private String noticeobj;

    // 评审时间;
    private java.util.Date reivewdate;

    // 评审地点;
    private String reviewplace;

    // 年度;
    private int reviewyear;

    // 通知正文;
    private String noticetext;

    // 通知时间;
    private java.util.Date noticedate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<ReviewNotice> item;

    public ReviewNotice() {
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
        return ToolUtils.CompareProperty((ReviewNotice) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "评审计划:planid", "称呼:noticeobj", "评审时间:reivewdate", "评审地点:reviewplace",
                "年度:reviewyear", "通知正文:noticetext", "通知时间:noticedate", "业务员:tranuser", "业务员姓名:tranusername",
                "业务员时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.planid = "";
        this.noticeobj = "";
        this.reivewdate = ToolUtils.GetMinDate();
        this.reviewplace = "";
        this.reviewyear = 0;
        this.noticetext = "";
        this.noticedate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getNoticeobj() {
        return noticeobj;
    }

    public void setNoticeobj(String noticeobj) {
        this.noticeobj = noticeobj;
    }

    public java.util.Date getReivewdate() {
        return reivewdate;
    }

    public void setReivewdate(java.util.Date reivewdate) {
        this.reivewdate = reivewdate;
    }

    public String getReviewplace() {
        return reviewplace;
    }

    public void setReviewplace(String reviewplace) {
        this.reviewplace = reviewplace;
    }

    public int getReviewyear() {
        return reviewyear;
    }

    public void setReviewyear(int reviewyear) {
        this.reviewyear = reviewyear;
    }

    public String getNoticetext() {
        return noticetext;
    }

    public void setNoticetext(String noticetext) {
        this.noticetext = noticetext;
    }

    public java.util.Date getNoticedate() {
        return noticedate;
    }

    public void setNoticedate(java.util.Date noticedate) {
        this.noticedate = noticedate;
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

    public SelectBean<ReviewNotice> getItem() {
        if (item == null)
            item = new SelectBean<ReviewNotice>();

        return item;
    }

    public void setItem(SelectBean<ReviewNotice> item) {
        this.item = item;
    }

}
