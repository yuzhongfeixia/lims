package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class UserExamReport implements BaseBean {

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

    // 技术职务;
    private String userduty;

    // 技术职务名称;
    private String userdutyname;

    // 考核形式;
    private String examtype;

    // 考核形式名称;
    private String examtypename;

    // 考核日期;
    private java.util.Date examdate;

    // 考核内容;
    private String examcontent;

    // 综合评定(需要调整问题);
    private String examdesc;

    // 考核组长;
    private String examleader;

    // 考核组长姓名;
    private String examleadername;

    // 考核结论;
    private String examresult;

    // 考核组长签字日期;
    private java.util.Date examleaderdate;

    // 技术负责人;
    private String techuser;

    // 技术负责人姓名;
    private String techusername;

    // 技术负责人意见;
    private String techdesc;

    // 技术负责人签字日期;
    private java.util.Date techdate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务提交人;
    private String tranuser;

    // 业务提交人姓名;
    private String tranusername;

    // 业务日期;
    private java.util.Date trandate;

    // 是否已选择;
    private String isselect;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<UserExamReport> item;

    public UserExamReport() {
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
        return ToolUtils.CompareProperty((UserExamReport) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "考核编号:examid", "被考核人编号:userid", "操作员姓名:username", "所属部门:deptid", "机构名称:deptname",
                "人员岗位:userpost", "操作员岗位名称:userpostname", "技术职务:userduty", "技术职务名称:userdutyname", "考核形式:examtype",
                "考核形式名称:examtypename", "考核日期:examdate", "考核内容:examcontent", "综合评定(需要调整问题):examdesc", "考核组长:examleader",
                "考核组长姓名:examleadername", "考核结论:examresult", "考核组长签字日期:examleaderdate", "技术负责人:techuser",
                "技术负责人姓名:techusername", "技术负责人意见:techdesc", "技术负责人签字日期:techdate", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "业务提交人:tranuser",
                "业务提交人姓名:tranusername", "业务日期:trandate" };
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
        this.userduty = "";
        this.userdutyname = "";
        this.examtype = "";
        this.examtypename = "";
        // this.examdate = ToolUtils.GetMinDate();
        this.examcontent = "";
        this.examdesc = "";
        this.examleader = "";
        this.examleadername = "";
        this.examresult = "";
        // this.examleaderdate = ToolUtils.GetMinDate();
        this.techuser = "";
        this.techusername = "";
        this.techdesc = "";
        // this.techdate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.isselect = "";
    }

    public String getIsselect() {
        return isselect;
    }

    public void setIsselect(String isselect) {
        this.isselect = isselect;
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

    public String getExamtype() {
        return examtype;
    }

    public void setExamtype(String examtype) {
        this.examtype = examtype;
    }

    public String getExamtypename() {
        return examtypename;
    }

    public void setExamtypename(String examtypename) {
        this.examtypename = examtypename;
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

    public String getExamdesc() {
        return examdesc;
    }

    public void setExamdesc(String examdesc) {
        this.examdesc = examdesc;
    }

    public String getExamleader() {
        return examleader;
    }

    public void setExamleader(String examleader) {
        this.examleader = examleader;
    }

    public String getExamleadername() {
        return examleadername;
    }

    public void setExamleadername(String examleadername) {
        this.examleadername = examleadername;
    }

    public String getExamresult() {
        return examresult;
    }

    public void setExamresult(String examresult) {
        this.examresult = examresult;
    }

    public java.util.Date getExamleaderdate() {
        return examleaderdate;
    }

    public void setExamleaderdate(java.util.Date examleaderdate) {
        this.examleaderdate = examleaderdate;
    }

    public String getTechuser() {
        return techuser;
    }

    public void setTechuser(String techuser) {
        this.techuser = techuser;
    }

    public String getTechusername() {
        return techusername;
    }

    public void setTechusername(String techusername) {
        this.techusername = techusername;
    }

    public String getTechdesc() {
        return techdesc;
    }

    public void setTechdesc(String techdesc) {
        this.techdesc = techdesc;
    }

    public java.util.Date getTechdate() {
        return techdate;
    }

    public void setTechdate(java.util.Date techdate) {
        this.techdate = techdate;
    }

    public String getFlowaction() {
        return flowaction;
    }

    public void setFlowaction(String flowaction) {
        this.flowaction = flowaction;
    }

    public String getFlowactionname() {
        return flowactionname;
    }

    public void setFlowactionname(String flowactionname) {
        this.flowactionname = flowactionname;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
    }

    public String getFlowstatusname() {
        return flowstatusname;
    }

    public void setFlowstatusname(String flowstatusname) {
        this.flowstatusname = flowstatusname;
    }

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public String getTranusername() {
        return tranusername;
    }

    public void setTranusername(String tranusername) {
        this.tranusername = tranusername;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
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

    public SelectBean<UserExamReport> getItem() {
        if (item == null)
            item = new SelectBean<UserExamReport>();

        return item;
    }

    public void setItem(SelectBean<UserExamReport> item) {
        this.item = item;
    }

}
