package com.alms.entity.dat;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DatClassSource implements BaseBean {

    // 类来源编号;
    private String classsource;

    // 类来源名称;
    private String classsourcename;

    // 是否检测相关;
    private boolean istask;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DatClassSource> item;

    public DatClassSource() {
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
        return ToolUtils.CompareProperty((DatClassSource) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "类来源编号:classsource", "类来源名称:classsourcename", "是否检测相关:istask" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.classsource = "";
        this.classsourcename = "";
        this.istask = false;
    }

    public String getClasssource() {
        return classsource;
    }

    public void setClasssource(String classsource) {
        this.classsource = classsource;
    }

    public String getClasssourcename() {
        return classsourcename;
    }

    public void setClasssourcename(String classsourcename) {
        this.classsourcename = classsourcename;
    }

    public boolean getIstask() {
        return istask;
    }

    public void setIstask(boolean istask) {
        this.istask = istask;
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

    public SelectBean<DatClassSource> getItem() {
        if (item == null)
            item = new SelectBean<DatClassSource>();

        return item;
    }

    public void setItem(SelectBean<DatClassSource> item) {
        this.item = item;
    }

}
