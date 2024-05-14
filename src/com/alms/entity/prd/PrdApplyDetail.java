package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class PrdApplyDetail implements BaseBean {

    // 申请编号;
    private String tranid;

    // 明细序号;
    private int prdserial;

    // 耗材编号;
    private String prdid;

    // 耗材名称
    private String prdname;

    // 耗材数量;
    private String prdcount;

    // 备注;
    private String remark;

    // 已验证数量;
    private String buycount;

    private String prdunit;

    private String prdunitname;

    private String prdtype;

    private String prdtypename;

    // 最后验证日期;
    private java.util.Date buydate;

    // 规格型号
    private String prdstandard;

    // 级别
    private String level;
    // 级别名称
    private String levelname;

    // 是否需要验收
    private String isverify;

    // 是否已经验收
    private String isselect;// 0指还未验收，1指已验收，2指已入库

    // 是否需要验收
    private String isverifyname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<PrdApplyDetail> item;

    public PrdApplyDetail() {
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
        return ToolUtils.CompareProperty((PrdApplyDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "申请编号:tranid", "是否已经验收:isselect", "是否验收:isverify", "是否验收:isverifyname", "明细序号:prdserial",
                "耗材编号:prdid", "计量单位:prdunit", "计量单位:prdunitname", "耗材数量:prdcount", "备注:remark", "已验证数量:buycount",
                "最后验证日期:buydate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.prdserial = 0;
        this.prdid = "";
        this.prdcount = "";
        this.remark = "";
        this.buycount = "";
        // this.buydate = ToolUtils.GetMinDate();
        this.prdname = "";
        this.prdunit = "";
        this.prdunitname = "";
        this.prdtype = "";
        this.prdtypename = "";
        this.level = "";
        this.prdstandard = "";
        this.isverify = "";
        this.isverifyname = "";
        this.isselect = "";
    }

    public String getIsselect() {
        return isselect;
    }

    public void setIsselect(String isselect) {
        this.isselect = isselect;
    }

    public String getIsverifyname() {
        return isverifyname;
    }

    public void setIsverifyname(String isverifyname) {
        this.isverifyname = isverifyname;
    }

    public String getIsverify() {
        return isverify;
    }

    public void setIsverify(String isverify) {
        this.isverify = isverify;
    }

    public String getPrdtype() {
        return prdtype;
    }

    public void setPrdtype(String prdtype) {
        this.prdtype = prdtype;
    }

    public String getPrdtypename() {
        return prdtypename;
    }

    public void setPrdtypename(String prdtypename) {
        this.prdtypename = prdtypename;
    }

    public String getPrdunit() {
        return prdunit;
    }

    public void setPrdunit(String prdunit) {
        this.prdunit = prdunit;
    }

    public String getPrdunitname() {
        return prdunitname;
    }

    public void setPrdunitname(String prdunitname) {
        this.prdunitname = prdunitname;
    }

    public String getPrdstandard() {
        return prdstandard;
    }

    public void setPrdstandard(String prdstandard) {
        this.prdstandard = prdstandard;
    }

    public String getPrdname() {
        return prdname;
    }

    public void setPrdname(String prdname) {
        this.prdname = prdname;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public int getPrdserial() {
        return prdserial;
    }

    public void setPrdserial(int prdserial) {
        this.prdserial = prdserial;
    }

    public String getPrdid() {
        return prdid;
    }

    public void setPrdid(String prdid) {
        this.prdid = prdid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrdcount() {
        return prdcount;
    }

    public void setPrdcount(String prdcount) {
        this.prdcount = prdcount;
    }

    public String getBuycount() {
        return buycount;
    }

    public void setBuycount(String buycount) {
        this.buycount = buycount;
    }

    public java.util.Date getBuydate() {
        return buydate;
    }

    public void setBuydate(java.util.Date buydate) {
        this.buydate = buydate;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
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

    public SelectBean<PrdApplyDetail> getItem() {
        if (item == null)
            item = new SelectBean<PrdApplyDetail>();

        return item;
    }

    public void setItem(SelectBean<PrdApplyDetail> item) {
        this.item = item;
    }

}
