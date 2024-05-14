package com.alms.entity.crm;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class CrmSurveyItem implements BaseBean {

    // 调查内容编号;
    private String surveyitem;

    // 调查内容名称;
    private String surveyitemname;

    // 是否文字表述;
    private boolean isword;

    // 排序序号;
    private int surveyitemorder;

    // 是否启用;
    private boolean isactive;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<CrmSurveyItem> item;

    public CrmSurveyItem() {
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
        return ToolUtils.CompareProperty((CrmSurveyItem) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "调查内容编号:surveyitem", "调查内容名称:surveyitemname", "是否文字表述:isword", "排序序号:surveyitemorder",
                "是否启用:isactive" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.surveyitem = "";
        this.surveyitemname = "";
        this.isword = false;
        this.surveyitemorder = 0;
        this.isactive = false;
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

    public boolean getIsword() {
        return isword;
    }

    public void setIsword(boolean isword) {
        this.isword = isword;
    }

    public int getSurveyitemorder() {
        return surveyitemorder;
    }

    public void setSurveyitemorder(int surveyitemorder) {
        this.surveyitemorder = surveyitemorder;
    }

    public boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
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

    public SelectBean<CrmSurveyItem> getItem() {
        if (item == null)
            item = new SelectBean<CrmSurveyItem>();

        return item;
    }

    public void setItem(SelectBean<CrmSurveyItem> item) {
        this.item = item;
    }

}
