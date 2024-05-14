package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class PrdWasteDetail implements BaseBean {

    // 申请编号;
    private String tranid;

    // 废物编号;
    private String prdid;

    // 试剂耗材名称;
    private String prdname;

    // 废物数量;
    private String prdcount;

    // 废弃物规格
    private String prdstd;

    // 备注;
    private String tranremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<PrdWasteDetail> item;

    public PrdWasteDetail() {
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
        return ToolUtils.CompareProperty((PrdWasteDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "申请编号:tranid", "废物编号:prdid", "试剂耗材名称:prdname", "废物数量:prdcount", "备注:tranremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.prdid = "";
        this.prdname = "";
        this.prdcount = "";
        this.tranremark = "";
        this.prdstd = "";
    }

    public String getPrdstd() {
        return prdstd;
    }

    public void setPrdstd(String prdstd) {
        this.prdstd = prdstd;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getPrdid() {
        return prdid;
    }

    public void setPrdid(String prdid) {
        this.prdid = prdid;
    }

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public String getPrdcount() {
        return prdcount;
    }

    public void setPrdcount(String prdcount) {
        this.prdcount = prdcount;
    }

    public String getTranremark() {
        return tranremark;
    }

    public void setTranremark(String tranremark) {
        this.tranremark = tranremark;
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

    public SelectBean<PrdWasteDetail> getItem() {
        if (item == null)
            item = new SelectBean<PrdWasteDetail>();

        return item;
    }

    public void setItem(SelectBean<PrdWasteDetail> item) {
        this.item = item;
    }

}
