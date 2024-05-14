package com.alms.entity.crm;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class CrmSurveyDetail implements BaseBean {

    // 调查编号;
    private String tranid;

    // 调查内容编号;
    private String surveyitem;

    // 调查内容名称;
    private String surveyitemname;

    // 调查评分;
    private String surveyscore;

    // 评分名称
    private String surveyscorename;

    // 调查评分说明;
    private String surveydesc;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<CrmSurveyDetail> item;

    public CrmSurveyDetail() {
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
        return ToolUtils.CompareProperty((CrmSurveyDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "调查编号:tranid", "调查内容编号:surveyitem", "调查内容名称:surveyitemname", "调查评分:surveyscore",
                "调查评分说明:surveydesc" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.surveyitem = "";
        this.surveyitemname = "";
        this.surveyscore = "";
        this.surveydesc = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getSurveyitem() {
        return surveyitem;
    }

    public void setSurveyitem(String surveyitem) {
        this.surveyitem = surveyitem;
    }

    public String getSurveyitemname() {
        return surveyitemname;
    }

    public void setSurveyitemname(String surveyitemname) {
        this.surveyitemname = surveyitemname;
    }

    public String getSurveyscore() {
        return surveyscore;
    }

    public void setSurveyscore(String surveyscore) {
        this.surveyscore = surveyscore;
    }

    public String getSurveyscorename() {
        return surveyscorename;
    }

    public void setSurveyscorename(String surveyscorename) {
        this.surveyscorename = surveyscorename;
    }

    public String getSurveydesc() {
        return surveydesc;
    }

    public void setSurveydesc(String surveydesc) {
        this.surveydesc = surveydesc;
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

    public SelectBean<CrmSurveyDetail> getItem() {
        if (item == null)
            item = new SelectBean<CrmSurveyDetail>();

        return item;
    }

    public void setItem(SelectBean<CrmSurveyDetail> item) {
        this.item = item;
    }

}
