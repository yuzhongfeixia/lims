package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DevCheckPlan implements BaseBean {

    // 业务编号;
    private String tranid;

    // 设备编号;
    private String devid;

    // 仪器名称;
    private String devname;

    // 设备型号;
    private String devstandard;

    // 出厂编号;
    private String factorycode;

    // 生产厂家;
    private String factoryname;

    // 技术指标;
    private String devrange;

    // 检定周期;
    private String devperiod;

    // 设备校验周期名称;
    private String devperiodname;

    // 设备用途;
    private String devusage;

    // 上次检定日期;
    private java.util.Date lastcheckdate;

    // 期间核查计划时间;
    private java.util.Date devcheckdate;

    // 下次检定日期;
    private java.util.Date nextdate;

    // 使用温度;
    private String usetemp;

    // 使用湿度;
    private String usehumid;

    // 检定单位编号;
    private String devmanager;

    // 检定单位名称;
    private String deptname;

    private String checkorgname;

    public String getCheckorgname() {
        return checkorgname;
    }

    public void setCheckorgname(String checkorgname) {
        this.checkorgname = checkorgname;
    }

    // 检定人;
    private String devmanagername;

    // 业务人;
    private String tranuser;

    // 业务人姓名;
    private String tranusername;

    // 业务日期;
    private java.util.Date trandate;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    private String startdate;

    private String enddate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DevCheckPlan> item;

    public DevCheckPlan() {
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
        return ToolUtils.CompareProperty((DevCheckPlan) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "设备编号:devid", "开始时间:startdate", "结束时间:enddate", "仪器名称:devname",
                "检定单位名称：deptname", "设备型号:devstandard", "出厂编号:factorycode", "生产厂家:factoryname", "技术指标:devrange",
                "检定周期:devperiod", "设备校验周期名称:devperiodname", "设备用途:devusage", "上次检定日期:lastcheckdate",
                "期间核查计划时间:devcheckdate", "下次检定日期:nextdate", "使用温度:usetemp", "使用湿度:usehumid", "设备管理员:devmanager",
                "设备管理员姓名:devmanagername", "业务人:tranuser", "业务人姓名:tranusername", "业务日期:trandate", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname" };
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
        this.factorycode = "";
        this.factoryname = "";
        this.devrange = "";
        this.devperiod = "";
        this.devperiodname = "";
        this.devusage = "";
        this.lastcheckdate = ToolUtils.GetMinDate();
        this.devcheckdate = ToolUtils.GetMinDate();
        this.nextdate = ToolUtils.GetMinDate();
        this.usetemp = "";
        this.usehumid = "";
        this.devmanager = "";
        this.devmanagername = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.deptname = "";
        this.startdate = "";
        this.enddate = "";
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

    public String getFactorycode() {
        return factorycode;
    }

    public void setFactorycode(String factorycode) {
        this.factorycode = factorycode;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public String getDevrange() {
        return devrange;
    }

    public void setDevrange(String devrange) {
        this.devrange = devrange;
    }

    public String getDevperiod() {
        return devperiod;
    }

    public void setDevperiod(String devperiod) {
        this.devperiod = devperiod;
    }

    public String getDevperiodname() {
        return devperiodname;
    }

    public void setDevperiodname(String devperiodname) {
        this.devperiodname = devperiodname;
    }

    public String getDevusage() {
        return devusage;
    }

    public void setDevusage(String devusage) {
        this.devusage = devusage;
    }

    public java.util.Date getLastcheckdate() {
        return lastcheckdate;
    }

    public void setLastcheckdate(java.util.Date lastcheckdate) {
        this.lastcheckdate = lastcheckdate;
    }

    public java.util.Date getDevcheckdate() {
        return devcheckdate;
    }

    public void setDevcheckdate(java.util.Date devcheckdate) {
        this.devcheckdate = devcheckdate;
    }

    public java.util.Date getNextdate() {
        return nextdate;
    }

    public void setNextdate(java.util.Date nextdate) {
        this.nextdate = nextdate;
    }

    public String getUsetemp() {
        return usetemp;
    }

    public void setUsetemp(String usetemp) {
        this.usetemp = usetemp;
    }

    public String getUsehumid() {
        return usehumid;
    }

    public void setUsehumid(String usehumid) {
        this.usehumid = usehumid;
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

    public SelectBean<DevCheckPlan> getItem() {
        if (item == null)
            item = new SelectBean<DevCheckPlan>();

        return item;
    }

    public void setItem(SelectBean<DevCheckPlan> item) {
        this.item = item;
    }

}
