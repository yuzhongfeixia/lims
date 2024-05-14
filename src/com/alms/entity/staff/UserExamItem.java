package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class UserExamItem implements BaseBean {

    // 考核情况编号;
    private String examitem;

    // 考核情况名称;
    private String examitemname;

    // 是否文字表述;
    private boolean isword;

    // 排序序号;
    private int examitemorder;

    // 是否启用;
    private boolean isactive;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<UserExamItem> item;

    public UserExamItem() {
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
        return ToolUtils.CompareProperty((UserExamItem) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "考核情况编号:examitem", "考核情况名称:examitemname", "是否文字表述:isword", "排序序号:examitemorder",
                "是否启用:isactive" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.examitem = "";
        this.examitemname = "";
        this.isword = false;
        this.examitemorder = 0;
        this.isactive = false;
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

    public boolean getIsword() {
        return isword;
    }

    public void setIsword(boolean isword) {
        this.isword = isword;
    }

    public int getExamitemorder() {
        return examitemorder;
    }

    public void setExamitemorder(int examitemorder) {
        this.examitemorder = examitemorder;
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

    public SelectBean<UserExamItem> getItem() {
        if (item == null)
            item = new SelectBean<UserExamItem>();

        return item;
    }

    public void setItem(SelectBean<UserExamItem> item) {
        this.item = item;
    }

}
