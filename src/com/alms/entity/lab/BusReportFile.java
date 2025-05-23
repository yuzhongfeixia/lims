package com.alms.entity.lab;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class BusReportFile implements BaseBean {

    private String tranid;

    private String attachname;

    private String attachtype;

    private String attachtypename;

    private String attachurl;

    private String samplecode;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusReportFile> item;

    public BusReportFile() {
        this.OnInit();
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getAttachname() {
        return attachname;
    }

    public void setAttachname(String attachname) {
        this.attachname = attachname;
    }

    public String getAttachtype() {
        return attachtype;
    }

    public void setAttachtype(String attachtype) {
        this.attachtype = attachtype;
    }

    public String getAttachurl() {
        return attachurl;
    }

    public void setAttachurl(String attachurl) {
        this.attachurl = attachurl;
    }

    public String getAttachtypename() {
        return attachtypename;
    }

    public void setAttachtypename(String attachtypename) {
        this.attachtypename = attachtypename;
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
        return ToolUtils.CompareProperty((BusReportFile) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.attachname = "";
        this.attachtype = "";
        this.attachtypename = "";
        this.attachurl = "";
        this.samplecode = "";
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

    public SelectBean<BusReportFile> getItem() {
        if (item == null)
            item = new SelectBean<BusReportFile>();

        return item;
    }

    public void setItem(SelectBean<BusReportFile> item) {
        this.item = item;
    }

}
