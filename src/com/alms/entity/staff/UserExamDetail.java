package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class UserExamDetail implements BaseBean {

    // 考核编号;
    private String examid;

    // 考核情况编号;
    private String examitem;

    // 考核情况名称;
    private String examitemname;

    // 考核成绩;
    private String examscore;

    // 考核分数名称;
    private String examscorename;

    // 考核成绩说明;
    private String examitemdesc;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<UserExamDetail> item;

    public UserExamDetail() {
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
        return ToolUtils.CompareProperty((UserExamDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "考核编号:examid", "考核情况编号:examitem", "考核情况名称:examitemname", "考核成绩:examscore",
                "考核分数名称:examscorename", "考核成绩说明:examitemdesc" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.examid = "";
        this.examitem = "";
        this.examitemname = "";
        this.examscore = "";
        this.examscorename = "";
        this.examitemdesc = "";
    }

    public String getExamid() {
        return examid;
    }

    public void setExamid(String examid) {
        this.examid = examid;
    }

    public String getExamitem() {
        return examitem;
    }

    public void setExamitem(String examitem) {
        this.examitem = examitem;
    }

    public String getExamitemname() {
        return examitemname;
    }

    public void setExamitemname(String examitemname) {
        this.examitemname = examitemname;
    }

    public String getExamscore() {
        return examscore;
    }

    public void setExamscore(String examscore) {
        this.examscore = examscore;
    }

    public String getExamscorename() {
        return examscorename;
    }

    public void setExamscorename(String examscorename) {
        this.examscorename = examscorename;
    }

    public String getExamitemdesc() {
        return examitemdesc;
    }

    public void setExamitemdesc(String examitemdesc) {
        this.examitemdesc = examitemdesc;
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

    public SelectBean<UserExamDetail> getItem() {
        if (item == null)
            item = new SelectBean<UserExamDetail>();

        return item;
    }

    public void setItem(SelectBean<UserExamDetail> item) {
        this.item = item;
    }

}
