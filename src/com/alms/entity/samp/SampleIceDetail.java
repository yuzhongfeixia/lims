package com.alms.entity.samp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SampleIceDetail implements BaseBean {

    // 冰柜编号;
    private String iceid;

    // 记录日期;
    private java.util.Date icedate;

    // 冰柜最低温度;
    private String lowtemp;

    // 冰柜最高温度;
    private String hightemp;

    // 记录人;
    private String iceuser;

    // 记录人姓名;
    private String iceusername;

    // 记录人时间;
    private java.util.Date iceuserdate;

    // 备注;
    private String iceremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SampleIceDetail> item;

    public SampleIceDetail() {
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
        return ToolUtils.CompareProperty((SampleIceDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "冰柜编号:iceid", "记录日期:icedate", "冰柜最低温度:lowtemp", "冰柜最高温度:hightemp", "记录人:iceuser",
                "记录人姓名:iceusername", "记录人时间:iceuserdate", "备注:iceremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.iceid = "";
        this.icedate = ToolUtils.GetMinDate();
        this.lowtemp = "";
        this.hightemp = "";
        this.iceuser = "";
        this.iceusername = "";
        this.iceuserdate = ToolUtils.GetMinDate();
        this.iceremark = "";
    }

    public String getIceid() {
        return iceid;
    }

    public void setIceid(String iceid) {
        this.iceid = iceid;
    }

    public java.util.Date getIcedate() {
        return icedate;
    }

    public void setIcedate(java.util.Date icedate) {
        this.icedate = icedate;
    }

    public String getLowtemp() {
        return lowtemp;
    }

    public void setLowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }

    public String getHightemp() {
        return hightemp;
    }

    public void setHightemp(String hightemp) {
        this.hightemp = hightemp;
    }

    public String getIceuser() {
        return iceuser;
    }

    public void setIceuser(String iceuser) {
        this.iceuser = iceuser;
    }

    public String getIceusername() {
        return iceusername;
    }

    public void setIceusername(String iceusername) {
        this.iceusername = iceusername;
    }

    public java.util.Date getIceuserdate() {
        return iceuserdate;
    }

    public void setIceuserdate(java.util.Date iceuserdate) {
        this.iceuserdate = iceuserdate;
    }

    public String getIceremark() {
        return iceremark;
    }

    public void setIceremark(String iceremark) {
        this.iceremark = iceremark;
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

    public SelectBean<SampleIceDetail> getItem() {
        if (item == null)
            item = new SelectBean<SampleIceDetail>();

        return item;
    }

    public void setItem(SelectBean<SampleIceDetail> item) {
        this.item = item;
    }

}
