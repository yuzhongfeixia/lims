package com.alms.entity.staff;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class UserExamResult implements BaseBean {

    // 业务编号;
    private String tranid;

    // 考核编号;
    private String examid;

    // 被考核人;
    private String userid;

    // 操作员姓名;
    private String username;

    // 岗位;
    private String userpost;

    // 操作员岗位名称;
    private String userpostname;

    // 所学专业;
    private String userpro;

    // 业务范围;
    private String busscope;

    // 职务(职称);
    private String userduty;

    // 技术职务名称;
    private String userdutyname;

    // 所在科室;
    private String deptid;

    // 机构名称;
    private String deptname;

    // 准许操作的仪器;
    private String allowdev;

    // 准许操作仪器名称;
    private String allowdevname;

    // 可从事的检测项目;
    private String allowparam;

    // 可从事的检测项目名称;
    private String allowparamname;

    // 可从事的检测样品类型;
    private String allowsample;

    // 可从事的检测样品类型;
    private String allowsamplename;

    // 批准人;
    private String examapprove;

    // 批准人姓名;
    private String examapprovename;

    // 批准日期;
    private java.util.Date examapprovedate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 记录日期;
    private java.util.Date crtdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<UserExamResult> item;

    public UserExamResult() {
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
        return ToolUtils.CompareProperty((UserExamResult) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "考核编号:examid", "可从事的检测样品类型:allowsample", "可从事的检测样品类型:allowsamplename",
                "被考核人:userid", "操作员姓名:username", "岗位:userpost", "操作员岗位名称:userpostname", "所学专业:userpro", "业务范围:busscope",
                "职务(职称):userduty", "技术职务名称:userdutyname", "所在科室:deptid", "机构名称:deptname", "准许操作的仪器:allowdev",
                "准许操作仪器名称:allowdevname", "可从事的检测项目:allowparam", "可从事的检测项目名称:allowparamname", "批准人:examapprove",
                "批准人姓名:examapprovename", "批准日期:examapprovedate", "业务员:tranuser", "业务员姓名:tranusername", "记录日期:crtdate",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.examid = "";
        this.userid = "";
        this.username = "";
        this.userpost = "";
        this.userpostname = "";
        this.userpro = "";
        this.busscope = "";
        this.userduty = "";
        this.userdutyname = "";
        this.deptid = "";
        this.deptname = "";
        this.allowdev = "";
        this.allowdevname = "";
        this.allowparam = "";
        this.allowparamname = "";
        this.examapprove = "";
        this.examapprovename = "";
        this.examapprovedate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.tranusername = "";
        this.allowsample = "";
        this.allowsamplename = "";
        this.crtdate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
    }

    public String getAllowsample() {
        return allowsample;
    }

    public void setAllowsample(String allowsample) {
        this.allowsample = allowsample;
    }

    public String getAllowsamplename() {
        return allowsamplename;
    }

    public void setAllowsamplename(String allowsamplename) {
        this.allowsamplename = allowsamplename;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
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

    public String getUserpro() {
        return userpro;
    }

    public void setUserpro(String userpro) {
        this.userpro = userpro;
    }

    public String getBusscope() {
        return busscope;
    }

    public void setBusscope(String busscope) {
        this.busscope = busscope;
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

    public String getAllowdev() {
        return allowdev;
    }

    public void setAllowdev(String allowdev) {
        this.allowdev = allowdev;
    }

    public String getAllowdevname() {
        return allowdevname;
    }

    public void setAllowdevname(String allowdevname) {
        this.allowdevname = allowdevname;
    }

    public String getAllowparam() {
        return allowparam;
    }

    public void setAllowparam(String allowparam) {
        this.allowparam = allowparam;
    }

    public String getAllowparamname() {
        return allowparamname;
    }

    public void setAllowparamname(String allowparamname) {
        this.allowparamname = allowparamname;
    }

    public String getExamapprove() {
        return examapprove;
    }

    public void setExamapprove(String examapprove) {
        this.examapprove = examapprove;
    }

    public String getExamapprovename() {
        return examapprovename;
    }

    public void setExamapprovename(String examapprovename) {
        this.examapprovename = examapprovename;
    }

    public java.util.Date getExamapprovedate() {
        return examapprovedate;
    }

    public void setExamapprovedate(java.util.Date examapprovedate) {
        this.examapprovedate = examapprovedate;
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

    public java.util.Date getCrtdate() {
        return crtdate;
    }

    public void setCrtdate(java.util.Date crtdate) {
        this.crtdate = crtdate;
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

    public SelectBean<UserExamResult> getItem() {
        if (item == null)
            item = new SelectBean<UserExamResult>();

        return item;
    }

    public void setItem(SelectBean<UserExamResult> item) {
        this.item = item;
    }

}
