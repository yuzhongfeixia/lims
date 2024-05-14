package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasSampleCatalog implements BaseBean {

    // 样品大类编号;
    private String samplecatalog;

    // 样品大类名称;
    private String samplecatalogname;

    // 样品大类类别;
    private String samplecate;

    private String samplecatename;

    private String sampleid;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasSampleCatalog> item;

    public BasSampleCatalog() {
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
        return ToolUtils.CompareProperty((BasSampleCatalog) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品大类编号:samplecatalog", "样品编号:sampleid", "样品大类名称:samplecatalogname",
                "样品大类英文名:samplecatalogen" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.samplecatalog = "";
        this.sampleid = "";
        this.samplecatalogname = "";
        this.samplecate = "";
        this.samplecatename = "";
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

    public String getSamplecate() {
        return samplecate;
    }

    public void setSamplecate(String samplecate) {
        this.samplecate = samplecate;
    }

    public String getSamplecatename() {
        return samplecatename;
    }

    public void setSamplecatename(String samplecatename) {
        this.samplecatename = samplecatename;
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

    public SelectBean<BasSampleCatalog> getItem() {
        if (item == null)
            item = new SelectBean<BasSampleCatalog>();

        return item;
    }

    public void setItem(SelectBean<BasSampleCatalog> item) {
        this.item = item;
    }

}
