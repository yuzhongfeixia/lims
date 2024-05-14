package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class RepairApply implements BaseBean {

    // 业务编号;
    private String tranid;

    // 设备编号;
    private String devid;

    // 设备名称;
    private String devname;

    // 设备型号;
    private String devstandard;

    // 设备类别;
    private String devtype;

    // 设备类别编号;
    private String devtypename;

    // 故障情况;
    private String faultdesc;

    // 维修地点;
    private String repairaddress;

    // 维修费用;
    private double repaircost;

    // 故障发生时间;
    private java.util.Date faultstart;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 设备管理员;
    private String manageruser;

    // 设备管理员姓名;
    private String managerusername;

    // 设备管理员时间;
    private java.util.Date devmanagerdate;

    // 检测室主任;
    private String testuser;

    // 检测室主任姓名;
    private String testusername;

    // 检测室主任时间;
    private java.util.Date testdate;

    // 技术负责人;
    private String techleader;

    // 技术负责人姓名;
    private String techleadername;

    // 技术负责人意见;
    private String techdesc;

    // 技术负责人时间;
    private java.util.Date techdate;

    // 办公室主任;
    private String officeuser;

    // 办公室主任姓名;
    private String officeusername;

    // 办公室主任意见;
    private String officedesc;

    // 办公室主任时间;
    private java.util.Date officedate;

    // 维修情况;
    private String repairdesc;

    // 维修时间;
    private java.util.Date repairdate;

    // 维修单位;
    private String repairunit;

    // 维修人;
    private String repairman;

    // 联系电话;
    private String repairtelephone;

    // 仪器故障;
    private String devdesc;

    // 故障原因;
    private String devreason;

    // 采取措施;
    private String devrepair;

    // 维修结果;
    private String repairresult;

    // 检测室主任验收;
    private String testcheck;

    // 检测室主任验收姓名;
    private String testcheckname;

    // 检测室主任验收时间;
    private java.util.Date testcheckdate;

    // 设备管理员验收;
    private String managercheck;

    // 设备管理员验收姓名;
    private String managercheckname;

    // 设备管理员验收时间;
    private java.util.Date managercheckdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<RepairApply> item;

    public RepairApply() {
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
        return ToolUtils.CompareProperty((RepairApply) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "设备编号:devid", "设备名称:devname", "设备型号:devstandard", "设备类别:devtype",
                "设备类别编号:devtypename", "故障情况:faultdesc", "维修地点:repairaddress", "维修费用:repaircost", "故障发生时间:faultstart",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname",
                "设备管理员:manageruser", "设备管理员姓名:managerusername", "设备管理员时间:devmanagerdate", "检测室主任:testuser",
                "检测室主任姓名:testusername", "检测室主任时间:testdate", "技术负责人:techleader", "技术负责人姓名:techleadername",
                "技术负责人意见:techdesc", "技术负责人时间:techdate", "办公室主任:officeuser", "办公室主任姓名:officeusername",
                "办公室主任意见:officedesc", "办公室主任时间:officedate", "维修情况:repairdesc", "维修时间:repairdate", "维修单位:repairunit",
                "维修人:repairman", "联系电话:repairtelephone", "仪器故障:devdesc", "故障原因:devreason", "采取措施:devrepair",
                "维修结果:repairresult", "检测室主任验收:testcheck", "检测室主任验收姓名:testcheckname", "检测室主任验收时间:testcheckdate",
                "设备管理员验收:managercheck", "设备管理员验收姓名:managercheckname", "设备管理员验收时间:managercheckdate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.devid = "";
        this.devname = "";
        this.devstandard = "";
        this.devtype = "";
        this.devtypename = "";
        this.faultdesc = "";
        this.repairaddress = "";
        this.repaircost = 0;
        this.faultstart = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.manageruser = "";
        this.managerusername = "";
        this.devmanagerdate = ToolUtils.GetMinDate();
        this.testuser = "";
        this.testusername = "";
        this.testdate = ToolUtils.GetMinDate();
        this.techleader = "";
        this.techleadername = "";
        this.techdesc = "";
        this.techdate = ToolUtils.GetMinDate();
        this.officeuser = "";
        this.officeusername = "";
        this.officedesc = "";
        this.officedate = ToolUtils.GetMinDate();
        this.repairdesc = "";
        this.repairdate = ToolUtils.GetMinDate();
        this.repairunit = "";
        this.repairman = "";
        this.repairtelephone = "";
        this.devdesc = "";
        this.devreason = "";
        this.devrepair = "";
        this.repairresult = "";
        this.testcheck = "";
        this.testcheckname = "";
        this.testcheckdate = ToolUtils.GetMinDate();
        this.managercheck = "";
        this.managercheckname = "";
        this.managercheckdate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
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

    public String getDevstandard() {
        return devstandard;
    }

    public void setDevstandard(String devstandard) {
        this.devstandard = devstandard;
    }

    public String getDevtype() {
        return devtype;
    }

    public void setDevtype(String devtype) {
        this.devtype = devtype;
    }

    public String getDevtypename() {
        return devtypename;
    }

    public void setDevtypename(String devtypename) {
        this.devtypename = devtypename;
    }

    public String getFaultdesc() {
        return faultdesc;
    }

    public void setFaultdesc(String faultdesc) {
        this.faultdesc = faultdesc;
    }

    public String getRepairaddress() {
        return repairaddress;
    }

    public void setRepairaddress(String repairaddress) {
        this.repairaddress = repairaddress;
    }

    public double getRepaircost() {
        return repaircost;
    }

    public void setRepaircost(double repaircost) {
        this.repaircost = repaircost;
    }

    public java.util.Date getFaultstart() {
        return faultstart;
    }

    public void setFaultstart(java.util.Date faultstart) {
        this.faultstart = faultstart;
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

    public String getManageruser() {
        return manageruser;
    }

    public void setManageruser(String manageruser) {
        this.manageruser = manageruser;
    }

    public String getManagerusername() {
        return managerusername;
    }

    public void setManagerusername(String managerusername) {
        this.managerusername = managerusername;
    }

    public java.util.Date getDevmanagerdate() {
        return devmanagerdate;
    }

    public void setDevmanagerdate(java.util.Date devmanagerdate) {
        this.devmanagerdate = devmanagerdate;
    }

    public String getTestuser() {
        return testuser;
    }

    public void setTestuser(String testuser) {
        this.testuser = testuser;
    }

    public String getTestusername() {
        return testusername;
    }

    public void setTestusername(String testusername) {
        this.testusername = testusername;
    }

    public java.util.Date getTestdate() {
        return testdate;
    }

    public void setTestdate(java.util.Date testdate) {
        this.testdate = testdate;
    }

    public String getTechleader() {
        return techleader;
    }

    public void setTechleader(String techleader) {
        this.techleader = techleader;
    }

    public String getTechleadername() {
        return techleadername;
    }

    public void setTechleadername(String techleadername) {
        this.techleadername = techleadername;
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

    public String getOfficeuser() {
        return officeuser;
    }

    public void setOfficeuser(String officeuser) {
        this.officeuser = officeuser;
    }

    public String getOfficeusername() {
        return officeusername;
    }

    public void setOfficeusername(String officeusername) {
        this.officeusername = officeusername;
    }

    public String getOfficedesc() {
        return officedesc;
    }

    public void setOfficedesc(String officedesc) {
        this.officedesc = officedesc;
    }

    public java.util.Date getOfficedate() {
        return officedate;
    }

    public void setOfficedate(java.util.Date officedate) {
        this.officedate = officedate;
    }

    public String getRepairdesc() {
        return repairdesc;
    }

    public void setRepairdesc(String repairdesc) {
        this.repairdesc = repairdesc;
    }

    public java.util.Date getRepairdate() {
        return repairdate;
    }

    public void setRepairdate(java.util.Date repairdate) {
        this.repairdate = repairdate;
    }

    public String getRepairunit() {
        return repairunit;
    }

    public void setRepairunit(String repairunit) {
        this.repairunit = repairunit;
    }

    public String getRepairman() {
        return repairman;
    }

    public void setRepairman(String repairman) {
        this.repairman = repairman;
    }

    public String getRepairtelephone() {
        return repairtelephone;
    }

    public void setRepairtelephone(String repairtelephone) {
        this.repairtelephone = repairtelephone;
    }

    public String getDevdesc() {
        return devdesc;
    }

    public void setDevdesc(String devdesc) {
        this.devdesc = devdesc;
    }

    public String getDevreason() {
        return devreason;
    }

    public void setDevreason(String devreason) {
        this.devreason = devreason;
    }

    public String getDevrepair() {
        return devrepair;
    }

    public void setDevrepair(String devrepair) {
        this.devrepair = devrepair;
    }

    public String getRepairresult() {
        return repairresult;
    }

    public void setRepairresult(String repairresult) {
        this.repairresult = repairresult;
    }

    public String getTestcheck() {
        return testcheck;
    }

    public void setTestcheck(String testcheck) {
        this.testcheck = testcheck;
    }

    public String getTestcheckname() {
        return testcheckname;
    }

    public void setTestcheckname(String testcheckname) {
        this.testcheckname = testcheckname;
    }

    public java.util.Date getTestcheckdate() {
        return testcheckdate;
    }

    public void setTestcheckdate(java.util.Date testcheckdate) {
        this.testcheckdate = testcheckdate;
    }

    public String getManagercheck() {
        return managercheck;
    }

    public void setManagercheck(String managercheck) {
        this.managercheck = managercheck;
    }

    public String getManagercheckname() {
        return managercheckname;
    }

    public void setManagercheckname(String managercheckname) {
        this.managercheckname = managercheckname;
    }

    public java.util.Date getManagercheckdate() {
        return managercheckdate;
    }

    public void setManagercheckdate(java.util.Date managercheckdate) {
        this.managercheckdate = managercheckdate;
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

    public SelectBean<RepairApply> getItem() {
        if (item == null)
            item = new SelectBean<RepairApply>();

        return item;
    }

    public void setItem(SelectBean<RepairApply> item) {
        this.item = item;
    }

}
