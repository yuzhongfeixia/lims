package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class AcceptManage implements BaseBean {

    // 业务编号;
    private String tranid;

    // 关联申请编号;
    private String applyid;

    // 申请设备编号;
    private String devid;

    // 设备名称;
    private String devname;

    // 出厂编号;
    private String factorycode;

    // 设备型号;
    private String devstandard;

    // 设备价格;
    private double devprice;

    // 生产厂家;
    private String factoryname;

    // 进场日期;
    private java.util.Date enterdate;

    // 技术参数;
    private String devrange;

    // 安装单位;
    private String instalunit;

    // 安装时间;
    private java.util.Date instaldate;

    // 安装情况;
    private String instaldesc;

    // 安装调试人(供应商);
    private String instaluser;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 验收结论;
    private String acceptremark;

    // 设备管理员;
    private String devmanager;

    // 设备管理员姓名;
    private String devmanagername;

    // 设备管理员时间;
    private java.util.Date managerdate;

    // 办公室主任;
    private String officeuser;

    // 办公室主任姓名;
    private String officeusername;

    // 办公室主任时间;
    private java.util.Date officedate;

    // 备注;
    private String remark;

    // 使用部门(检测室负责人);
    private String deptuser;

    // 使用部门人姓名;
    private String deptusername;

    // 使用部门签字日期;
    private java.util.Date deptdate;

    // 技术负责人;
    private String techuser;

    // 技术负责人姓名;
    private String techusername;

    // 负责人签字日期;
    private java.util.Date techdate;

    // 验收人员
    private String devoperators;

    private String devoperatorsname;

    private String labname;

    private String installsign;

    private String installpath;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<AcceptManage> item;

    public AcceptManage() {
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
        return ToolUtils.CompareProperty((AcceptManage) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "关联申请编号:applyid", "申请设备编号:devid", "设备名称:devname", "出厂编号:factorycode",
                "设备型号:devstandard", "设备价格:devprice", "生产厂家:factoryname", "进场日期:enterdate", "技术参数:devrange",
                "安装单位:instalunit", "安装时间:instaldate", "安装情况:instaldesc", "安装调试人(供应商):instaluser", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "验收结论:acceptremark",
                "设备管理员:devmanager", "设备管理员姓名:devmanagername", "设备管理员时间:managerdate", "办公室主任:officeuser",
                "办公室主任姓名:officeusername", "办公室主任时间:officedate", "备注:remark", "使用部门(检测室负责人):deptuser",
                "使用部门人姓名:deptusername", "使用部门签字日期:deptdate", "技术负责人:techuser", "技术负责人姓名:techusername",
                "负责人签字日期:techdate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.applyid = "";
        this.devid = "";
        this.devname = "";
        this.factorycode = "";
        this.devstandard = "";
        this.devprice = 0;
        this.factoryname = "";
        this.enterdate = ToolUtils.GetMinDate();
        this.devrange = "";
        this.instalunit = "";
        this.instaldate = ToolUtils.GetMinDate();
        this.instaldesc = "";
        this.instaluser = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.acceptremark = "";
        this.devmanager = "";
        this.devmanagername = "";
        this.managerdate = ToolUtils.GetMinDate();
        this.officeuser = "";
        this.officeusername = "";
        this.officedate = ToolUtils.GetMinDate();
        this.remark = "";
        this.deptuser = "";
        this.deptusername = "";
        this.deptdate = ToolUtils.GetMinDate();
        this.techuser = "";
        this.techusername = "";
        this.techdate = ToolUtils.GetMinDate();
        this.installpath = "";
        this.installsign = "";
    }

    public String getInstallsign() {
        return installsign;
    }

    public void setInstallsign(String installsign) {
        this.installsign = installsign;
    }

    public String getInstallpath() {
        return installpath;
    }

    public void setInstallpath(String installpath) {
        this.installpath = installpath;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid;
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

    public String getFactorycode() {
        return factorycode;
    }

    public void setFactorycode(String factorycode) {
        this.factorycode = factorycode;
    }

    public String getDevstandard() {
        return devstandard;
    }

    public void setDevstandard(String devstandard) {
        this.devstandard = devstandard;
    }

    public double getDevprice() {
        return devprice;
    }

    public void setDevprice(double devprice) {
        this.devprice = devprice;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public java.util.Date getEnterdate() {
        return enterdate;
    }

    public void setEnterdate(java.util.Date enterdate) {
        this.enterdate = enterdate;
    }

    public String getDevrange() {
        return devrange;
    }

    public void setDevrange(String devrange) {
        this.devrange = devrange;
    }

    public String getInstalunit() {
        return instalunit;
    }

    public void setInstalunit(String instalunit) {
        this.instalunit = instalunit;
    }

    public java.util.Date getInstaldate() {
        return instaldate;
    }

    public void setInstaldate(java.util.Date instaldate) {
        this.instaldate = instaldate;
    }

    public String getInstaldesc() {
        return instaldesc;
    }

    public void setInstaldesc(String instaldesc) {
        this.instaldesc = instaldesc;
    }

    public String getInstaluser() {
        return instaluser;
    }

    public void setInstaluser(String instaluser) {
        this.instaluser = instaluser;
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

    public String getAcceptremark() {
        return acceptremark;
    }

    public void setAcceptremark(String acceptremark) {
        this.acceptremark = acceptremark;
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

    public java.util.Date getOfficedate() {
        return officedate;
    }

    public void setOfficedate(java.util.Date officedate) {
        this.officedate = officedate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeptuser() {
        return deptuser;
    }

    public void setDeptuser(String deptuser) {
        this.deptuser = deptuser;
    }

    public String getDeptusername() {
        return deptusername;
    }

    public void setDeptusername(String deptusername) {
        this.deptusername = deptusername;
    }

    public java.util.Date getDeptdate() {
        return deptdate;
    }

    public void setDeptdate(java.util.Date deptdate) {
        this.deptdate = deptdate;
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

    public String getDevoperators() {
        return devoperators;
    }

    public void setDevoperators(String devoperators) {
        this.devoperators = devoperators;
    }

    public String getDevoperatorsname() {
        return devoperatorsname;
    }

    public void setDevoperatorsname(String devoperatorsname) {
        this.devoperatorsname = devoperatorsname;
    }

    public String getLabname() {
        return labname;
    }

    public void setLabname(String labname) {
        this.labname = labname;
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

    public SelectBean<AcceptManage> getItem() {
        if (item == null)
            item = new SelectBean<AcceptManage>();

        return item;
    }

    public void setItem(SelectBean<AcceptManage> item) {
        this.item = item;
    }

}
