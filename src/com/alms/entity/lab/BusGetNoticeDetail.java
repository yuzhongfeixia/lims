package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusGetNoticeDetail implements BaseBean {

    // 通知单号;
    private String tranid;

    // 抽样内容;
    private String getcontent;

    // 抽样地点;
    private String getaddress;

    // 是否完成;
    private boolean isfinish;

    // 取样时间;
    private java.util.Date getdate;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusGetNoticeDetail> item;

    public BusGetNoticeDetail() {
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
        return ToolUtils.CompareProperty((BusGetNoticeDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "通知单号:tranid", "抽样内容:getcontent", "抽样地点:getaddress", "是否完成:isfinish", "取样时间:getdate",
                "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.getcontent = "";
        this.getaddress = "";
        this.isfinish = false;
        this.getdate = null;
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getGetcontent() {
        return getcontent;
    }

    public void setGetcontent(String getcontent) {
        this.getcontent = getcontent;
    }

    public String getGetaddress() {
        return getaddress;
    }

    public void setGetaddress(String getaddress) {
        this.getaddress = getaddress;
    }

    public boolean getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(boolean isfinish) {
        this.isfinish = isfinish;
    }

    public java.util.Date getGetdate() {
        return getdate;
    }

    public void setGetdate(java.util.Date getdate) {
        this.getdate = getdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<BusGetNoticeDetail> getItem() {
        if (item == null)
            item = new SelectBean<BusGetNoticeDetail>();

        return item;
    }

    public void setItem(SelectBean<BusGetNoticeDetail> item) {
        this.item = item;
    }

}
