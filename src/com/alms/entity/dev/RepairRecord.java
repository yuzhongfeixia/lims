package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class RepairRecord implements BaseBean {

    // 业务编号;
    private String tranid;

    // 报修单编号;
    private String repairid;

    // 设备编号;
    private String devid;

    // 设备名称;
    private String devname;

    // 主机号码;
    private String hostnumber;

    // 设备管理员;
    private String manageruser;

    // 设备管理员姓名;
    private String managerusername;

    // 所属室;
    private String deptid;

    // 所属实验室;
    private String deptname;

    // 仪器故障;
    private String devdesc;

    // 故障原因;
    private String devreason;

    // 采取措施;
    private String devrepair;

    // 维修结果;
    private String repairresult;

    // 维修人;
    private String repairman;

    // 检测室主任验收;
    private String testcheck;

    // 检测室主任验收姓名;
    private String testcheckname;

    // 检测室主任验收时间;
    private java.util.Date testcheckdate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<RepairRecord> item;

    public RepairRecord() {
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
        return ToolUtils.CompareProperty((RepairRecord) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "报修单编号:repairid", "设备编号:devid", "设备名称:devname", "主机号码:hostnumber",
                "设备管理员:manageruser", "设备管理员姓名:managerusername", "所属实验室:deptname", "所属室:deptid", "仪器故障:devdesc",
                "故障原因:devreason", "采取措施:devrepair", "维修结果:repairresult", "维修人:repairman", "检测室主任验收:testcheck",
                "检测室主任验收姓名:testcheckname", "检测室主任验收时间:testcheckdate", "业务员:tranuser", "业务员姓名:tranusername",
                "业务员时间:trandate", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.repairid = "";
        this.devid = "";
        this.devname = "";
        this.hostnumber = "";
        this.manageruser = "";
        this.managerusername = "";
        this.deptid = "";
        this.deptname = "";
        this.devdesc = "";
        this.devreason = "";
        this.devrepair = "";
        this.repairresult = "";
        this.repairman = "";
        this.testcheck = "";
        this.testcheckname = "";
        this.testcheckdate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getRepairid() {
        return repairid;
    }

    public void setRepairid(String repairid) {
        this.repairid = repairid;
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

    public String getHostnumber() {
        return hostnumber;
    }

    public void setHostnumber(String hostnumber) {
        this.hostnumber = hostnumber;
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

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
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

    public String getRepairman() {
        return repairman;
    }

    public void setRepairman(String repairman) {
        this.repairman = repairman;
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

    public SelectBean<RepairRecord> getItem() {
        if (item == null)
            item = new SelectBean<RepairRecord>();

        return item;
    }

    public void setItem(SelectBean<RepairRecord> item) {
        this.item = item;
    }

}
