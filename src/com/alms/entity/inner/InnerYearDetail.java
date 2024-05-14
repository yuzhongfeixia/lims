package com.alms.entity.inner;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class InnerYearDetail implements BaseBean {

    // 计划编号;
    private String tranid;

    // 审核组别;
    private String groupinner;

    // 审核时间;
    private java.util.Date auditdate;

    // 审核内容;
    private String auditcontent;

    // 备注;
    private String auditremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<InnerYearDetail> item;

    public InnerYearDetail() {
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
        return ToolUtils.CompareProperty((InnerYearDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "计划编号:tranid", "审核组别:groupid", "审核时间:auditdate", "审核内容:auditcontent", "备注:auditremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.groupinner = "";
        this.auditdate = ToolUtils.GetMinDate();
        this.auditcontent = "";
        this.auditremark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getGroupinner() {
        return groupinner;
    }

    public void setGroupinner(String groupinner) {
        this.groupinner = groupinner;
    }

    public java.util.Date getAuditdate() {
        return auditdate;
    }

    public void setAuditdate(java.util.Date auditdate) {
        this.auditdate = auditdate;
    }

    public String getAuditcontent() {
        return auditcontent;
    }

    public void setAuditcontent(String auditcontent) {
        this.auditcontent = auditcontent;
    }

    public String getAuditremark() {
        return auditremark;
    }

    public void setAuditremark(String auditremark) {
        this.auditremark = auditremark;
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

    public SelectBean<InnerYearDetail> getItem() {
        if (item == null)
            item = new SelectBean<InnerYearDetail>();

        return item;
    }

    public void setItem(SelectBean<InnerYearDetail> item) {
        this.item = item;
    }

}
