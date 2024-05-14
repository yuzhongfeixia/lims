package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasPrd implements BaseBean {

    // 试剂耗材编号;
    private String prdid;

    // 试剂耗材名称;
    private String prdname;

    // 耗材分类;
    private String prdtype;

    // 耗材分类名称;
    private String prdtypename;

    // 规格型号;
    private String prdstandard;

    // 计量单位;
    private String prdunit;

    // 计量单位名称;
    private String prdunitname;

    // 参考价格（单价）;
    private double prdprice;

    // 存放条件;
    private String prdenv;

    // 默认有效期（月）;
    private int validmonth;

    // 试剂和耗材状态;
    private String prdstatus;

    // 耗材状态名称;
    private String prdstatusname;

    // 是否废弃处理;
    private boolean isscrap;

    // 备注;
    private String remark;

    // 最大库存量;
    private double prdmax;

    // 最小库存量;
    private double prdmin;

    // 耗材作用;
    private String prdeffect;

    // 仓库编号;
    private String storeid;

    // 仓库名称;
    private String storename;

    // 耗材管理员;
    private String prduser;

    // 耗材管理员;
    private String prdusername;

    // 供应商名称
    private String tradename;

    // 生产厂商;
    private String factoryname;

    // 生产日期;
    private java.util.Date factorydate;

    // 购买人;
    private String buyuser;

    // 购买人姓名;
    private String buyusername;

    // 购买日期;
    private java.util.Date buydate;

    // 是否需要验证;
    private String isverify;

    // 是否需要验证;
    private String isverifyname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasPrd> item;

    public BasPrd() {
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
        return ToolUtils.CompareProperty((BasPrd) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "是否需要验证:isverify", "是否需要验证:isverifyname", "试剂耗材编号:prdid", "试剂耗材名称:prdname",
                "耗材分类:prdtype", "耗材分类名称:prdtypename", "规格型号:prdstandard", "计量单位:prdunit", "计量单位名称:prdunitname",
                "参考价格（单价）:prdprice", "存放条件:prdenv", "默认有效期（月）:validmonth", "试剂和耗材状态:prdstatus", "耗材状态名称:prdstatusname",
                "是否废弃处理:isscrap", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.prdid = "";
        this.prdname = "";
        this.prdtype = "";
        this.prdtypename = "";
        this.prdstandard = "";
        this.prdunit = "";
        this.prdunitname = "";
        this.prdprice = 0;
        this.prdenv = "";
        this.validmonth = 0;
        this.prdstatus = "";
        this.prdstatusname = "";
        this.isscrap = false;
        this.remark = "";
        this.prdmax = 0;
        this.prdmin = 0;
        this.prdeffect = "";
        this.storeid = "";
        this.storename = "";
        this.prduser = "";
        this.prdusername = "";
        this.tradename = "";
        this.factoryname = "";
        this.buyuser = "";
        this.buyusername = "";
        this.isverify = "";
        this.isverifyname = "";
    }

    public String getIsverify() {
        return isverify;
    }

    public void setIsverify(String isverify) {
        this.isverify = isverify;
    }

    public String getIsverifyname() {
        return isverifyname;
    }

    public void setIsverifyname(String isverifyname) {
        this.isverifyname = isverifyname;
    }

    public String getPrduser() {
        return prduser;
    }

    public String getTradename() {
        return tradename;
    }

    public void setTradename(String tradename) {
        this.tradename = tradename;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public java.util.Date getFactorydate() {
        return factorydate;
    }

    public void setFactorydate(java.util.Date factorydate) {
        this.factorydate = factorydate;
    }

    public String getBuyuser() {
        return buyuser;
    }

    public void setBuyuser(String buyuser) {
        this.buyuser = buyuser;
    }

    public String getBuyusername() {
        return buyusername;
    }

    public void setBuyusername(String buyusername) {
        this.buyusername = buyusername;
    }

    public java.util.Date getBuydate() {
        return buydate;
    }

    public void setBuydate(java.util.Date buydate) {
        this.buydate = buydate;
    }

    public void setPrduser(String prduser) {
        this.prduser = prduser;
    }

    public String getPrdusername() {
        return prdusername;
    }

    public void setPrdusername(String prdusername) {
        this.prdusername = prdusername;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public double getPrdmax() {
        return prdmax;
    }

    public void setPrdmax(double prdmax) {
        this.prdmax = prdmax;
    }

    public double getPrdmin() {
        return prdmin;
    }

    public void setPrdmin(double prdmin) {
        this.prdmin = prdmin;
    }

    public String getPrdeffect() {
        return prdeffect;
    }

    public void setPrdeffect(String prdeffect) {
        this.prdeffect = prdeffect;
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

    public String getPrdstandard() {
        return prdstandard;
    }

    public void setPrdstandard(String prdstandard) {
        this.prdstandard = prdstandard;
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

    public double getPrdprice() {
        return prdprice;
    }

    public void setPrdprice(double prdprice) {
        this.prdprice = prdprice;
    }

    public String getPrdenv() {
        return prdenv;
    }

    public void setPrdenv(String prdenv) {
        this.prdenv = prdenv;
    }

    public int getValidmonth() {
        return validmonth;
    }

    public void setValidmonth(int validmonth) {
        this.validmonth = validmonth;
    }

    public String getPrdstatus() {
        return prdstatus;
    }

    public void setPrdstatus(String prdstatus) {
        this.prdstatus = prdstatus;
    }

    public String getPrdstatusname() {
        return prdstatusname;
    }

    public void setPrdstatusname(String prdstatusname) {
        this.prdstatusname = prdstatusname;
    }

    public boolean getIsscrap() {
        return isscrap;
    }

    public void setIsscrap(boolean isscrap) {
        this.isscrap = isscrap;
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

    public SelectBean<BasPrd> getItem() {
        if (item == null)
            item = new SelectBean<BasPrd>();

        return item;
    }

    public void setItem(SelectBean<BasPrd> item) {
        this.item = item;
    }

}
