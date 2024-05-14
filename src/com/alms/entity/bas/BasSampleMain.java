package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasSampleMain implements BaseBean {

    // 样品主类编号;
    private String samplemain;

    // 所属样品大类;
    private String samplecatalog;

    // 样品大类名称;
    private String samplecatalogname;

    // 样品主类名称;
    private String samplemainname;

    // 检测标准;
    private String mainstand;

    // 检测标准;
    private String mainstandname;

    private String samplecate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasSampleMain> item;

    public BasSampleMain() {
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
        return ToolUtils.CompareProperty((BasSampleMain) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品主类编号:samplemain", "所属样品大类:samplecatalog", "样品大类名称:samplecatalogname",
                "样品主类名称:samplemainname", "样品主类英文名:samplemainen", "检测标准:mainstand", "检测标准:mainstandname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.samplemain = "";
        this.samplecatalog = "";
        this.samplecatalogname = "";
        this.samplemainname = "";
        this.mainstand = "";
        this.mainstandname = "";
        this.samplecate = "";
    }

    public String getMainstand() {
        return mainstand;
    }

    public void setMainstand(String mainstand) {
        this.mainstand = mainstand;
    }

    public String getMainstandname() {
        return mainstandname;
    }

    public void setMainstandname(String mainstandname) {
        this.mainstandname = mainstandname;
    }

    public String getSamplemain() {
        return samplemain;
    }

    public void setSamplemain(String samplemain) {
        this.samplemain = samplemain;
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

    public String getSamplemainname() {
        return samplemainname;
    }

    public void setSamplemainname(String samplemainname) {
        this.samplemainname = samplemainname;
    }

    public String getSamplecate() {
        return samplecate;
    }

    public void setSamplecate(String samplecate) {
        this.samplecate = samplecate;
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

    public SelectBean<BasSampleMain> getItem() {
        if (item == null)
            item = new SelectBean<BasSampleMain>();

        return item;
    }

    public void setItem(SelectBean<BasSampleMain> item) {
        this.item = item;
    }

}
