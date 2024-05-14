package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DevCheck implements BaseBean {

    // 核查编号;
    private String checkid;

    // 仪器编号;
    private String devid;

    // 仪器名称;
    private String devname;

    // 生产厂家;
    private String factoryname;

    // 上次检定日期;
    private java.util.Date lastcheckdate;

    // 测量范围;
    private String devrange;

    // 核查精度;
    private String devprecision;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 检查日期;
    private java.util.Date checkdate;

    // 检查情况;
    private String checkdesc;

    // 检查结论;
    private String checkresult;

    // 检查审批;
    private String checkapprove;

    // 设备管理员;
    private String devmanager;

    // 设备管理员姓名;
    private String devmanagername;

    // 设备管理员时间;
    private java.util.Date managerdate;

    // 技术负责人;
    private String techuser;

    // 技术负责人姓名;
    private String techusername;

    // 期间核查计划;
    private String planid;

    // 技术负责人时间;
    private java.util.Date techdate;

    // 备注;
    private String remark;

    // 开始时间;
    private String startdate;

    // 结束时间;
    private String enddate;

    // 下次核查日期;
    private java.util.Date nextcheckdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DevCheck> item;

    public DevCheck() {
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
        return ToolUtils.CompareProperty((DevCheck) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "核查编号:checkid", "核查计划编号:planid", "仪器编号:devid", "仪器名称:devname", "生产厂家:factoryname",
                "上次检定日期:lastcheckdate", "测量范围:devrange", "核查精度:devprecision", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "检查日期:checkdate", "检查情况:checkdesc",
                "检查结论:checkresult", "检查审批:checkapprove", "设备管理员:devmanager", "设备管理员姓名:devmanagername",
                "设备管理员时间:managerdate", "技术负责人:techuser", "技术负责人姓名:techusername", "技术负责人时间:techdate", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.checkid = "";
        this.devid = "";
        this.devname = "";
        this.factoryname = "";
        this.lastcheckdate = ToolUtils.GetMinDate();
        this.devrange = "";
        this.devprecision = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.checkdate = ToolUtils.GetMinDate();
        this.checkdesc = "";
        this.checkresult = "";
        this.checkapprove = "";
        this.devmanager = "";
        this.devmanagername = "";
        this.managerdate = ToolUtils.GetMinDate();
        this.techuser = "";
        this.techusername = "";
        this.techdate = ToolUtils.GetMinDate();
        this.remark = "";
        this.planid = "";
        this.startdate = "";
        this.enddate = "";
        this.nextcheckdate = ToolUtils.GetMinDate();
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public java.util.Date getNextcheckdate() {
        return nextcheckdate;
    }

    public void setNextcheckdate(java.util.Date nextcheckdate) {
        this.nextcheckdate = nextcheckdate;
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getCheckid() {
        return checkid;
    }

    public void setCheckid(String checkid) {
        this.checkid = checkid;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public java.util.Date getLastcheckdate() {
        return lastcheckdate;
    }

    public void setLastcheckdate(java.util.Date lastcheckdate) {
        this.lastcheckdate = lastcheckdate;
    }

    public String getDevrange() {
        return devrange;
    }

    public void setDevrange(String devrange) {
        this.devrange = devrange;
    }

    public String getDevprecision() {
        return devprecision;
    }

    public void setDevprecision(String devprecision) {
        this.devprecision = devprecision;
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

    public java.util.Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(java.util.Date checkdate) {
        this.checkdate = checkdate;
    }

    public String getCheckdesc() {
        return checkdesc;
    }

    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
    }

    public String getCheckresult() {
        return checkresult;
    }

    public void setCheckresult(String checkresult) {
        this.checkresult = checkresult;
    }

    public String getCheckapprove() {
        return checkapprove;
    }

    public void setCheckapprove(String checkapprove) {
        this.checkapprove = checkapprove;
    }

    public String getDevmanager() {
        return devmanager;
    }

    public void setDevmanager(String devmanager) {
        this.devmanager = devmanager;
    }

    public String getDevmanagername() {
        return devmanagername;
    }

    public void setDevmanagername(String devmanagername) {
        this.devmanagername = devmanagername;
    }

    public java.util.Date getManagerdate() {
        return managerdate;
    }

    public void setManagerdate(java.util.Date managerdate) {
        this.managerdate = managerdate;
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

    public java.util.Date getTechdate() {
        return techdate;
    }

    public void setTechdate(java.util.Date techdate) {
        this.techdate = techdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<DevCheck> getItem() {
        if (item == null)
            item = new SelectBean<DevCheck>();

        return item;
    }

    public void setItem(SelectBean<DevCheck> item) {
        this.item = item;
    }

}
