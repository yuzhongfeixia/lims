package com.alms.entity.form;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class FrmRecordParameter implements BaseBean {

    private String formid;

    private String formname;

    private String sampleid;

    private String samplename;

    private String parameterid;

    private String parametername;

    private String teststandard;

    private String teststandardname;

    private String judgestandard;

    private String judgestandardname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<IntField> item;

    public FrmRecordParameter() {
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
        return ToolUtils.CompareProperty((IntField) item, this, this.OnProperties());
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
        this.formid = "";
        this.formname = "";
        this.sampleid = "";
        this.samplename = "";
        this.parameterid = "";
        this.parametername = "";
        this.teststandard = "";
        this.teststandardname = "";
        this.judgestandard = "";
        this.judgestandardname = "";
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
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

    public String getTeststandard() {
        return teststandard;
    }

    public void setTeststandard(String teststandard) {
        this.teststandard = teststandard;
    }

    public String getTeststandardname() {
        return teststandardname;
    }

    public void setTeststandardname(String teststandardname) {
        this.teststandardname = teststandardname;
    }

    public String getJudgestandard() {
        return judgestandard;
    }

    public void setJudgestandard(String judgestandard) {
        this.judgestandard = judgestandard;
    }

    public String getJudgestandardname() {
        return judgestandardname;
    }

    public void setJudgestandardname(String judgestandardname) {
        this.judgestandardname = judgestandardname;
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

    public SelectBean<IntField> getItem() {
        if (item == null)
            item = new SelectBean<IntField>();

        return item;
    }

    public void setItem(SelectBean<IntField> item) {
        this.item = item;
    }

}
