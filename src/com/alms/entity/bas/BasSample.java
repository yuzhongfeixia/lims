package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasSample implements BaseBean {

    // 样品编号;
    private String sampleid;

    // 所属样品大类;
    private String samplecatalog;

    // 样品大类名称;
    private String samplecatalogname;

    // 所属样品主类;
    private String samplemain;

    // 样品主类名称;
    private String samplemainname;

    // 样品检测依据;
    private String mainstandname;

    // 样品名称;
    private String samplename;

    // 简称;
    private String code;

    // 简称;
    private String codename;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasSample> item;

    public BasSample() {
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
        return ToolUtils.CompareProperty((BasSample) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品编号:sampleid", "简称:code", "检测依据:mainstandname", "简称:codename", "所属样品大类:samplecatalog",
                "样品大类名称:samplecatalogname", "所属样品主类:samplemain", "样品主类名称:samplemainname", "样品名称:samplename",
                "样品检索:samplecn", "样品英文名:sampleen" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.sampleid = "";
        this.code = "";
        this.codename = "";
        this.mainstandname = "";
        this.samplecatalog = "";
        this.samplecatalogname = "";
        this.samplemain = "";
        this.samplemainname = "";
        this.samplename = "";
    }

    public String getMainstandname() {
        return mainstandname;
    }

    public void setMainstandname(String mainstandname) {
        this.mainstandname = mainstandname;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSamplecatalog() {
        return samplecatalog;
    }

    public void setSamplecatalog(String samplecatalog) {
        this.samplecatalog = samplecatalog;
    }

    public String getSamplecatalogname() {
        return samplecatalogname;
    }

    public void setSamplecatalogname(String samplecatalogname) {
        this.samplecatalogname = samplecatalogname;
    }

    public String getSamplemain() {
        return samplemain;
    }

    public void setSamplemain(String samplemain) {
        this.samplemain = samplemain;
    }

    public String getSamplemainname() {
        return samplemainname;
    }

    public void setSamplemainname(String samplemainname) {
        this.samplemainname = samplemainname;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
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

    public SelectBean<BasSample> getItem() {
        if (item == null)
            item = new SelectBean<BasSample>();

        return item;
    }

    public void setItem(SelectBean<BasSample> item) {
        this.item = item;
    }

}
