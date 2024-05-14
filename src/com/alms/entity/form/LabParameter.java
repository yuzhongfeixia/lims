package com.alms.entity.form;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class LabParameter implements BaseBean {

    private String testusername;

    private String parametercount;

    private String deptid;

    private String begindate;

    private String enddate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<LabParameter> item;

    public LabParameter() {
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
        return ToolUtils.CompareProperty((LabParameter) item, this, this.OnProperties());
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
        this.testusername = "";
        this.parametercount = "";
        this.begindate = "";
        this.enddate = "";
        this.deptid = "";
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getTestusername() {
        return testusername;
    }

    public void setTestusername(String testusername) {
        this.testusername = testusername;
    }

    public String getParametercount() {
        return parametercount;
    }

    public void setParametercount(String parametercount) {
        this.parametercount = parametercount;
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

    public SelectBean<LabParameter> getItem() {
        if (item == null)
            item = new SelectBean<LabParameter>();

        return item;
    }

    public void setItem(SelectBean<LabParameter> item) {
        this.item = item;
    }
}
