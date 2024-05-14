package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class UserExamGroup implements BaseBean {

    // 考核编号;
    private String examid;

    // 考核人编号;
    private String userid;

    // 操作员姓名;
    private String username;

    // 所属部门;
    private String deptid;

    // 机构名称;
    private String deptname;

    // 技术职务;
    private String userduty;

    // 技术职务名称;
    private String userdutyname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<UserExamGroup> item;

    public UserExamGroup() {
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
        return ToolUtils.CompareProperty((UserExamGroup) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "考核编号:examid", "考核人编号:userid", "操作员姓名:username", "所属部门:deptid", "机构名称:deptname",
                "技术职务:userduty", "技术职务名称:userdutyname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.examid = "";
        this.userid = "";
        this.username = "";
        this.deptid = "";
        this.deptname = "";
        this.userduty = "";
        this.userdutyname = "";
    }

    public String getExamid() {
        return examid;
    }

    public void setExamid(String examid) {
        this.examid = examid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getUserduty() {
        return userduty;
    }

    public void setUserduty(String userduty) {
        this.userduty = userduty;
    }

    public String getUserdutyname() {
        return userdutyname;
    }

    public void setUserdutyname(String userdutyname) {
        this.userdutyname = userdutyname;
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

    public SelectBean<UserExamGroup> getItem() {
        if (item == null)
            item = new SelectBean<UserExamGroup>();

        return item;
    }

    public void setItem(SelectBean<UserExamGroup> item) {
        this.item = item;
    }

}
