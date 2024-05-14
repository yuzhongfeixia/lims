package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StkCheckDetail implements BaseBean {

    // 盘点单号;
    private String tranid;

    // 物品编号;
    private String prdid;

    // 试剂耗材名称;
    private String prdname;

    // 规格型号
    private String prdstandard;

    // 库存数量;
    private double kcnumber;

    // 盘点数量;
    private double prdnumber;

    // 盘赢盘亏数量;
    private double yknumber;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StkCheckDetail> item;

    public StkCheckDetail() {
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
        return ToolUtils.CompareProperty((StkCheckDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "盘点单号:tranid", "物品编号:prdid", "试剂耗材名称:prdname", "库存数量:kcnumber", "盘点数量:prdnumber",
                "盘赢盘亏数量:yknumber", "备注:remark" };
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
        this.kcnumber = 0;
        this.prdnumber = 0;
        this.yknumber = 0;
        this.remark = "";
        this.prdstandard = "";
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

    public String getPrdstandard() {
        return prdstandard;
    }

    public void setPrdstandard(String prdstandard) {
        this.prdstandard = prdstandard;
    }

    public double getKcnumber() {
        return kcnumber;
    }

    public void setKcnumber(double kcnumber) {
        this.kcnumber = kcnumber;
    }

    public double getPrdnumber() {
        return prdnumber;
    }

    public void setPrdnumber(double prdnumber) {
        this.prdnumber = prdnumber;
    }

    public double getYknumber() {
        return yknumber;
    }

    public void setYknumber(double yknumber) {
        this.yknumber = yknumber;
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

    public SelectBean<StkCheckDetail> getItem() {
        if (item == null)
            item = new SelectBean<StkCheckDetail>();

        return item;
    }

    public void setItem(SelectBean<StkCheckDetail> item) {
        this.item = item;
    }

}
