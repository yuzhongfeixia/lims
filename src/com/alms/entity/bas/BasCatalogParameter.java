package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasCatalogParameter implements BaseBean {

    // 样品大类编号;
    private String samplecatalog;

    // 检测参数编号;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasCatalogParameter> item;

    public BasCatalogParameter() {
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
        return ToolUtils.CompareProperty((BasCatalogParameter) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品大类编号:samplecatalog", "检测参数编号:parameterid", "检测参数名称:parametername" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.samplecatalog = "";
        this.parameterid = "";
        this.parametername = "";
    }

    public String getSamplecatalog() {
        return samplecatalog;
    }

    public void setSamplecatalog(String samplecatalog) {
        this.samplecatalog = samplecatalog;
    }

    public String getParameterid() {
        return parameterid;
    }

    public void setParameterid(String parameterid) {
        this.parameterid = parameterid;
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
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

    public SelectBean<BasCatalogParameter> getItem() {
        if (item == null)
            item = new SelectBean<BasCatalogParameter>();

        return item;
    }

    public void setItem(SelectBean<BasCatalogParameter> item) {
        this.item = item;
    }

}
