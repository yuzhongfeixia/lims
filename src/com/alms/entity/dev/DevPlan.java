package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class DevPlan implements BaseBean {

    // 计划编号;
    private String planid;

    // 设备编号;
    private String devid;

    // 设备名称;
    private String devname;

    // 设备用途;
    private String devusage;

    // 溯源方式;
    private String devsource;

    // 溯源方式名称;
    private String devsourcename;

    // 溯源开始时间;
    private java.util.Date sourcestart;

    // 溯源结束时间;
    private java.util.Date sourceend;

    // 溯源机构;
    private String deptid;

    // 溯源机构名称;
    private String deptname;

    // 计划确认;
    private String devconfirm;

    // 计划确认名称;
    private String devconfirmname;

    // 期间核查方法;
    private String devmethod;

    // 期间核查方法名称;
    private String devmethodname;

    // 期间核查开始时间;
    private java.util.Date checkstart;

    // 期间核查结束时间;
    private java.util.Date checkend;

    // 使用温度;
    private String usagetemp;

    // 使用湿度;
    private String usagehumid;

    // 维护项目;
    private String maintenproject;

    // 维护开始时间;
    private java.util.Date maintenstart;

    // 维护结束时间;
    private java.util.Date maintenend;

    // 检定校准日期;
    private java.util.Date devcheckdate;

    // 设备管理员;
    private String devmanager;

    // 设备管理员姓名;
    private String devmanagername;

    // 设备管理员日期;
    private java.util.Date devmanagerdate;
    // 核查周期
    private String checkperiod;
    // 维护周期
    private String maintenperiod;
    // 检校周期
    private String calibrationcycleid;
    private String calibrationcyclename;
    // 技术指标
    private String devrange;
    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DevPlan> item;

    public DevPlan() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.CheckComboValue(this.getDevsource())) {
            msg.setErrmsg("请选择溯源方式！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.CheckComboValue(this.getDeptid())) {
            msg.setErrmsg("请选择溯源机构！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.CheckComboValue(this.getDevconfirm())) {
            msg.setErrmsg("请选择计划确认！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        // if (ToolUtils.CheckComboValue(this.getDevmethod())) {
        // msg.setErrmsg("请选择期间核查方法！" + ToolUtils.GetNewLines());
        // rtn = true;
        // }

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((DevPlan) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "计划编号:planid", "设备编号:devid", "设备名称:devname", "设备用途:devusage", "溯源方式:devsource",
                "溯源方式名称:devsourcename", "溯源开始时间:sourcestart", "溯源结束时间:sourceend", "溯源机构:deptid", "溯源机构名称:deptname",
                "计划确认:devconfirm", "计划确认名称:devconfirmname", "期间核查方法:devmethod", "期间核查方法名称:devmethodname",
                "期间核查开始时间:checkstart", "期间核查结束时间:checkend", "使用温度:usagetemp", "使用湿度:usagehumid", "维护项目:maintenproject",
                "维护开始时间:maintenstart", "维护结束时间:maintenend", "检定校准日期:devcheckdate", "设备管理员:devmanager",
                "设备管理员姓名:devmanagername", "设备管理员日期:devmanagerdate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.planid = "";
        this.devid = "";
        this.devname = "";
        this.devusage = "";
        this.devsource = "";
        this.devsourcename = "";
        this.sourcestart = ToolUtils.GetMinDate();
        this.sourceend = ToolUtils.GetMinDate();
        this.deptid = "";
        this.deptname = "";
        this.devconfirm = "";
        this.devconfirmname = "";
        this.devmethod = "";
        this.devmethodname = "";
        this.checkstart = ToolUtils.GetMinDate();
        this.checkend = ToolUtils.GetMinDate();
        this.usagetemp = "";
        this.usagehumid = "";
        this.maintenproject = "";
        this.maintenstart = ToolUtils.GetMinDate();
        this.maintenend = ToolUtils.GetMinDate();
        this.devcheckdate = ToolUtils.GetMinDate();
        this.devmanager = "";
        this.devmanagername = "";
        this.devmanagerdate = ToolUtils.GetMinDate();
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
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

    public String getDevusage() {
        return devusage;
    }

    public void setDevusage(String devusage) {
        this.devusage = devusage;
    }

    public String getDevsource() {
        return devsource;
    }

    public void setDevsource(String devsource) {
        this.devsource = devsource;
    }

    public String getDevsourcename() {
        return devsourcename;
    }

    public void setDevsourcename(String devsourcename) {
        this.devsourcename = devsourcename;
    }

    public java.util.Date getSourcestart() {
        return sourcestart;
    }

    public void setSourcestart(java.util.Date sourcestart) {
        this.sourcestart = sourcestart;
    }

    public java.util.Date getSourceend() {
        return sourceend;
    }

    public void setSourceend(java.util.Date sourceend) {
        this.sourceend = sourceend;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDevconfirm() {
        return devconfirm;
    }

    public void setDevconfirm(String devconfirm) {
        this.devconfirm = devconfirm;
    }

    public String getDevconfirmname() {
        return devconfirmname;
    }

    public void setDevconfirmname(String devconfirmname) {
        this.devconfirmname = devconfirmname;
    }

    public String getDevmethod() {
        return devmethod;
    }

    public void setDevmethod(String devmethod) {
        this.devmethod = devmethod;
    }

    public String getDevmethodname() {
        return devmethodname;
    }

    public void setDevmethodname(String devmethodname) {
        this.devmethodname = devmethodname;
    }

    public java.util.Date getCheckstart() {
        return checkstart;
    }

    public void setCheckstart(java.util.Date checkstart) {
        this.checkstart = checkstart;
    }

    public java.util.Date getCheckend() {
        return checkend;
    }

    public void setCheckend(java.util.Date checkend) {
        this.checkend = checkend;
    }

    public String getUsagetemp() {
        return usagetemp;
    }

    public void setUsagetemp(String usagetemp) {
        this.usagetemp = usagetemp;
    }

    public String getUsagehumid() {
        return usagehumid;
    }

    public void setUsagehumid(String usagehumid) {
        this.usagehumid = usagehumid;
    }

    public String getMaintenproject() {
        return maintenproject;
    }

    public void setMaintenproject(String maintenproject) {
        this.maintenproject = maintenproject;
    }

    public java.util.Date getMaintenstart() {
        return maintenstart;
    }

    public void setMaintenstart(java.util.Date maintenstart) {
        this.maintenstart = maintenstart;
    }

    public java.util.Date getMaintenend() {
        return maintenend;
    }

    public void setMaintenend(java.util.Date maintenend) {
        this.maintenend = maintenend;
    }

    public java.util.Date getDevcheckdate() {
        return devcheckdate;
    }

    public void setDevcheckdate(java.util.Date devcheckdate) {
        this.devcheckdate = devcheckdate;
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

    public java.util.Date getDevmanagerdate() {
        return devmanagerdate;
    }

    public void setDevmanagerdate(java.util.Date devmanagerdate) {
        this.devmanagerdate = devmanagerdate;
    }

    public String getCheckperiod() {
        return checkperiod;
    }

    public void setCheckperiod(String checkperiod) {
        this.checkperiod = checkperiod;
    }

    public String getMaintenperiod() {
        return maintenperiod;
    }

    public void setMaintenperiod(String maintenperiod) {
        this.maintenperiod = maintenperiod;
    }

    public String getCalibrationcycleid() {
        return calibrationcycleid;
    }

    public void setCalibrationcycleid(String calibrationcycleid) {
        this.calibrationcycleid = calibrationcycleid;
    }

    public String getCalibrationcyclename() {
        return calibrationcyclename;
    }

    public void setCalibrationcyclename(String calibrationcyclename) {
        this.calibrationcyclename = calibrationcyclename;
    }

    public String getDevrange() {
        return devrange;
    }

    public void setDevrange(String devrange) {
        this.devrange = devrange;
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

    public SelectBean<DevPlan> getItem() {
        if (item == null)
            item = new SelectBean<DevPlan>();

        return item;
    }

    public void setItem(SelectBean<DevPlan> item) {
        this.item = item;
    }

}
