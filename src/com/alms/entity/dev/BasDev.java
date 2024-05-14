package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class BasDev implements BaseBean {

    // 设备编号;
    private String devid;

    // 设备名称;
    private String devname;

    // 设备类型;
    private String devtype;

    // 设备类别;
    private String devtypename;

    // 设备状态;
    private String devstatus;

    // 设备状态名称;
    private String devstatusname;

    // 生产厂家;
    private String factoryname;

    // 出厂编号;
    private String factorycode;

    // 维护编号;
    private String maintainid;

    // 设备型号;
    private String devstandard;

    // 技术指标;
    private String devrange;

    // 设备用途;
    private String devusage;

    // 设备管理员;
    private String devmanager;

    // 设备管理员姓名;
    private String devmanagername;

    // 使用温度;
    private String usetemp;

    // 使用湿度;
    private String usehumid;

    // 建立时间;
    private java.util.Date crtdate;

    // 修改时间;
    private java.util.Date mondifydate;

    // 建立人;
    private String crtuser;

    // 建立人姓名;
    private String crtusername;

    // 实验室
    private String labid;

    // 实验室名称
    private String deptname;

    // 购买日期;
    private java.util.Date buydate;

    // 价格
    private double devprice;

    // 检校周期
    private String devperiod;

    // 检校周期
    private String devperiodname;

    // 上次检定时间
    private java.util.Date prevtime;

    // 下次检定时间
    private java.util.Date nexttime;

    // 存放地点
    private String storeplace;

    // 过滤参与总体计划的设备指示字段
    private String jointotalplan;
    //
    private String accepttranid;

    // 添加的用于指示当前devid的字段
    private String nowdevid;

    // 操作员
    private String operateuser;

    // 操作员
    private String operateusername;

    // 操作时间
    private java.util.Date operatedate;

    // 操作原因
    private String operatereason;

    // 设备借出状态
    private String borrowStatu;

    // 设备借出状态
    private String borrowStatuName;

    private String startdate;

    private String enddate;

    private String devoperators;
    private String devoperatorsname;
    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BasDev> item;

    public BasDev() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.CheckComboValue(this.getDevtype())) {
            msg.setErrmsg("请选择设备类型！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.CheckComboValue(this.getLabid())) {
            msg.setErrmsg("请选择所属实验室！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((BasDev) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "设备编号:devid", "设备名称:devname", "设备类型:devtype", "设备类型名称:devtypename", "设备状态:devstatus",
                "设备状态名称:devstatusname", "生产厂家:factoryname", "出厂编号:factorycode", "维护编号:maintainid", "设备型号:devstandard",
                "技术指标:devrange", "设备用途:devusage", "设备管理员:devmanager", "设备管理员姓名:devmanagername", "使用温度:usetemp",
                "使用湿度:usehumid", "建立时间:crtdate", "修改时间:mondifydate", "建立人:crtuser", "建立人姓名:crtusername", "实验室:labid",
                "实验室名称:deptname", "检校周期:devperiod", "检校周期名称:devperiodname", "价格:devprice", "购买日期:buydate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.devid = "";
        this.devname = "";
        this.devtype = "";
        this.devtypename = "";
        this.devstatus = "";
        this.devstatusname = "";
        this.factoryname = "";
        this.factorycode = "";
        this.maintainid = "";
        this.devstandard = "";
        this.devrange = "";
        this.devusage = "";
        this.devmanager = "";
        this.devmanagername = "";
        this.usetemp = "";
        this.usehumid = "";
        this.crtdate = ToolUtils.GetMinDate();
        this.mondifydate = ToolUtils.GetMinDate();
        this.crtuser = "";
        this.crtusername = "";
        this.labid = "";
        this.deptname = "";
        this.devperiod = "";
        this.devperiodname = "";
        this.operateuser = "";
        this.operateusername = "";
        // this.operatedate = ToolUtils.GetMinDate();
        this.operatereason = "";
        this.startdate = "";
        this.enddate = "";
        this.storeplace = "";
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

    public String getOperateuser() {
        return operateuser;
    }

    public void setOperateuser(String operateuser) {
        this.operateuser = operateuser;
    }

    public String getOperateusername() {
        return operateusername;
    }

    public void setOperateusername(String operateusername) {
        this.operateusername = operateusername;
    }

    public java.util.Date getOperatedate() {
        return operatedate;
    }

    public void setOperatedate(java.util.Date operatedate) {
        this.operatedate = operatedate;
    }

    public String getOperatereason() {
        return operatereason;
    }

    public void setOperatereason(String operatereason) {
        this.operatereason = operatereason;
    }

    public String getDevperiodname() {
        return devperiodname;
    }

    public void setDevperiodname(String devperiodname) {
        this.devperiodname = devperiodname;
    }

    public java.util.Date getBuydate() {
        return buydate;
    }

    public void setBuydate(java.util.Date buydate) {
        this.buydate = buydate;
    }

    public double getDevprice() {
        return devprice;
    }

    public void setDevprice(double devprice) {
        this.devprice = devprice;
    }

    public String getDevperiod() {
        return devperiod;
    }

    public void setDevperiod(String devperiod) {
        this.devperiod = devperiod;
    }

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getNowdevid() {
        return nowdevid;
    }

    public void setNowdevid(String nowdevid) {
        this.nowdevid = nowdevid;
    }

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname;
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

    public String getDevstatus() {
        return devstatus;
    }

    public void setDevstatus(String devstatus) {
        this.devstatus = devstatus;
    }

    public String getDevstatusname() {
        return devstatusname;
    }

    public void setDevstatusname(String devstatusname) {
        this.devstatusname = devstatusname;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public String getFactorycode() {
        return factorycode;
    }

    public void setFactorycode(String factorycode) {
        this.factorycode = factorycode;
    }

    public String getMaintainid() {
        return maintainid;
    }

    public void setMaintainid(String maintainid) {
        this.maintainid = maintainid;
    }

    public String getDevstandard() {
        return devstandard;
    }

    public void setDevstandard(String devstandard) {
        this.devstandard = devstandard;
    }

    public String getDevrange() {
        return devrange;
    }

    public void setDevrange(String devrange) {
        this.devrange = devrange;
    }

    public String getDevusage() {
        return devusage;
    }

    public void setDevusage(String devusage) {
        this.devusage = devusage;
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

    public java.util.Date getCrtdate() {
        return crtdate;
    }

    public void setCrtdate(java.util.Date crtdate) {
        this.crtdate = crtdate;
    }

    public java.util.Date getMondifydate() {
        return mondifydate;
    }

    public void setMondifydate(java.util.Date mondifydate) {
        this.mondifydate = mondifydate;
    }

    public String getCrtuser() {
        return crtuser;
    }

    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
    }

    public String getCrtusername() {
        return crtusername;
    }

    public void setCrtusername(String crtusername) {
        this.crtusername = crtusername;
    }

    public java.util.Date getPrevtime() {
        return prevtime;
    }

    public void setPrevtime(java.util.Date prevtime) {
        this.prevtime = prevtime;
    }

    public java.util.Date getNexttime() {
        return nexttime;
    }

    public void setNexttime(java.util.Date nexttime) {
        this.nexttime = nexttime;
    }

    public String getStoreplace() {
        return storeplace;
    }

    public void setStoreplace(String storeplace) {
        this.storeplace = storeplace;
    }

    public String getJointotalplan() {
        return jointotalplan;
    }

    public void setJointotalplan(String jointotalplan) {
        this.jointotalplan = jointotalplan;
    }

    public String getAccepttranid() {
        return accepttranid;
    }

    public void setAccepttranid(String accepttranid) {
        this.accepttranid = accepttranid;
    }

    public String getBorrowStatu() {
        return borrowStatu;
    }

    public void setBorrowStatu(String borrowStatu) {
        this.borrowStatu = borrowStatu;
    }

    public String getBorrowStatuName() {
        return borrowStatuName;
    }

    public void setBorrowStatuName(String borrowStatuName) {
        this.borrowStatuName = borrowStatuName;
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

    public SelectBean<BasDev> getItem() {
        if (item == null)
            item = new SelectBean<BasDev>();

        return item;
    }

    public void setItem(SelectBean<BasDev> item) {
        this.item = item;
    }

}
