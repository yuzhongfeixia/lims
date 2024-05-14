package com.alms.entity.inner;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class InnerFoodReview implements BaseBean {

    // 业务编号;
    private String tranid;

    // 序号;
    private String serial;

    // 检查内容;
    private String recontent;

    // 检查方法及关键点;
    private String checkmethod;

    // 检查结论;
    private String checkresult;

    // 检查结论名称
    private String checkresultname;

    // 记录人;
    private String tranuser;

    // 记录人姓名;
    private String tranusername;

    // 创建日期;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<InnerFoodReview> item;

    public InnerFoodReview() {
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
        return ToolUtils.CompareProperty((InnerFoodReview) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "序号:serial", "检查内容:recontent", "检查方法及关键点:checkmethod", "检查结论:checkresult",
                "记录人:tranuser", "记录人姓名:tranusername", "创建日期:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.serial = "";
        this.recontent = "";
        this.checkmethod = "";
        this.checkresult = "";
        this.checkresultname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getRecontent() {
        return recontent;
    }

    public void setRecontent(String recontent) {
        this.recontent = recontent;
    }

    public String getCheckmethod() {
        return checkmethod;
    }

    public void setCheckmethod(String checkmethod) {
        this.checkmethod = checkmethod;
    }

    public String getCheckresult() {
        return checkresult;
    }

    public void setCheckresult(String checkresult) {
        this.checkresult = checkresult;
    }

    public String getCheckresultname() {
        return checkresultname;
    }

    public void setCheckresultname(String checkresultname) {
        this.checkresultname = checkresultname;
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

    public SelectBean<InnerFoodReview> getItem() {
        if (item == null)
            item = new SelectBean<InnerFoodReview>();

        return item;
    }

    public void setItem(SelectBean<InnerFoodReview> item) {
        this.item = item;
    }

}
