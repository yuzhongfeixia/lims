package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class UserExamRecord implements BaseBean {

    // 考核编号;
    private String examid;

    // 被考核人编号;
    private String userid;

    // 操作员姓名;
    private String username;

    // 所属部门;
    private String deptid;

    // 机构名称;
    private String deptname;

    // 人员岗位;
    private String userpost;

    // 操作员岗位名称;
    private String userpostname;

    // 考核日期;
    private java.util.Date examdate;

    // 考核内容;
    private String examcontent;

    // 综合考核意见;
    private String examadvice;

    // 考核意见名称;
    private String examadvicename;

    // 需要整改的问题;
    private String examdesc;

    // 批准人;
    private String approveuser;

    // 批准人姓名;
    private String approveusername;

    // 批准人签字日期;
    private java.util.Date approvedate;

    // 内审组长;
    private String teamuser;

    // 内审组长姓名;
    private String teamusername;

    // 内审组长签字日期;
    private java.util.Date teamdate;

    // 监考人;
    private String monitoruser;

    // 监考人姓名;
    private String monitorusername;

    // 监考人签字日期;
    private java.util.Date monitordate;

    private String flowstatus;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<UserExamRecord> item;

    public UserExamRecord() {
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
        return ToolUtils.CompareProperty((UserExamRecord) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "考核编号:examid", "被考核人编号:userid", "操作员姓名:username", "所属部门:deptid", "机构名称:deptname",
                "人员岗位:userpost", "操作员岗位名称:userpostname", "考核日期:examdate", "考核内容:examcontent", "综合考核意见:examadvice",
                "考核意见名称:examadvicename", "需要整改的问题:examdesc", "批准人:approveuser", "批准人姓名:approveusername",
                "批准人签字日期:approvedate", "内审组长:teamuser", "内审组长姓名:teamusername", "内审组长签字日期:teamdate", "监考人:monitoruser",
                "监考人姓名:monitorusername", "监考人签字日期:monitordate" };
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
        this.userpost = "";
        this.userpostname = "";
        this.examdate = ToolUtils.GetMinDate();
        this.examcontent = "";
        this.examadvice = "";
        this.examadvicename = "";
        this.examdesc = "";
        this.approveuser = "";
        this.approveusername = "";
        // this.approvedate = ToolUtils.GetMinDate();
        this.teamuser = "";
        this.teamusername = "";
        // this.teamdate = ToolUtils.GetMinDate();
        this.monitoruser = "";
        this.monitorusername = "";
        this.flowstatus = "";
        // this.monitordate = ToolUtils.GetMinDate();
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

    public String getUserpost() {
        return userpost;
    }

    public void setUserpost(String userpost) {
        this.userpost = userpost;
    }

    public String getUserpostname() {
        return userpostname;
    }

    public void setUserpostname(String userpostname) {
        this.userpostname = userpostname;
    }

    public java.util.Date getExamdate() {
        return examdate;
    }

    public void setExamdate(java.util.Date examdate) {
        this.examdate = examdate;
    }

    public String getExamcontent() {
        return examcontent;
    }

    public void setExamcontent(String examcontent) {
        this.examcontent = examcontent;
    }

    public String getExamadvice() {
        return examadvice;
    }

    public void setExamadvice(String examadvice) {
        this.examadvice = examadvice;
    }

    public String getExamadvicename() {
        return examadvicename;
    }

    public void setExamadvicename(String examadvicename) {
        this.examadvicename = examadvicename;
    }

    public String getExamdesc() {
        return examdesc;
    }

    public void setExamdesc(String examdesc) {
        this.examdesc = examdesc;
    }

    public String getApproveuser() {
        return approveuser;
    }

    public void setApproveuser(String approveuser) {
        this.approveuser = approveuser;
    }

    public String getApproveusername() {
        return approveusername;
    }

    public void setApproveusername(String approveusername) {
        this.approveusername = approveusername;
    }

    public java.util.Date getApprovedate() {
        return approvedate;
    }

    public void setApprovedate(java.util.Date approvedate) {
        this.approvedate = approvedate;
    }

    public String getTeamuser() {
        return teamuser;
    }

    public void setTeamuser(String teamuser) {
        this.teamuser = teamuser;
    }

    public String getTeamusername() {
        return teamusername;
    }

    public void setTeamusername(String teamusername) {
        this.teamusername = teamusername;
    }

    public java.util.Date getTeamdate() {
        return teamdate;
    }

    public void setTeamdate(java.util.Date teamdate) {
        this.teamdate = teamdate;
    }

    public String getMonitoruser() {
        return monitoruser;
    }

    public void setMonitoruser(String monitoruser) {
        this.monitoruser = monitoruser;
    }

    public String getMonitorusername() {
        return monitorusername;
    }

    public void setMonitorusername(String monitorusername) {
        this.monitorusername = monitorusername;
    }

    public java.util.Date getMonitordate() {
        return monitordate;
    }

    public void setMonitordate(java.util.Date monitordate) {
        this.monitordate = monitordate;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
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

    public SelectBean<UserExamRecord> getItem() {
        if (item == null)
            item = new SelectBean<UserExamRecord>();

        return item;
    }

    public void setItem(SelectBean<UserExamRecord> item) {
        this.item = item;
    }

}
