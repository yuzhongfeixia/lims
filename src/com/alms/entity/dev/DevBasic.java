package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DevBasic implements BaseBean {

    // 设备编号;
    private String devid;

    // 设备名称;
    private String devname;

    // 设备类型;
    private String devtype;

    // 设备类别编号;
    private String devtypename;

    // 生产厂家;
    private String factoryname;

    // 出厂编号;
    private String factorycode;

    // 设备型号;
    private String devstandard;

    // 购买(接收)日期;
    private java.util.Date buydate;

    // 价格;
    private double devprice;

    // 设备用途;
    private String devusage;

    // 设备管理员;
    private String devmanager;

    // 设备管理员姓名;
    private String devmanagername;

    // 放置地点;
    private String devplace;

    // 接收(启用)状态;
    private String devstatus;

    // 检定(校准)情况;
    private String devcheck;

    // 备注;
    private String devremark;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员日期;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DevBasic> item;

    public DevBasic() {
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
        return ToolUtils.CompareProperty((DevBasic) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "设备编号:devid", "设备名称:devname", "设备类型:devtype", "设备类别编号:devtypename", "生产厂家:factoryname",
                "出厂编号:factorycode", "设备型号:devstandard", "购买(接收)日期:buydate", "价格:devprice", "设备用途:devusage",
                "设备管理员:devmanager", "设备管理员姓名:devmanagername", "放置地点:devplace", "接收(启用)状态:devstatus",
                "检定(校准)情况:devcheck", "备注:devremark", "业务员:tranuser", "业务员姓名:tranusername", "业务员日期:trandate" };
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
        this.factoryname = "";
        this.factorycode = "";
        this.devstandard = "";
        // this.buydate = ToolUtils.GetMinDate();
        this.devprice = 0;
        this.devusage = "";
        this.devmanager = "";
        this.devmanagername = "";
        this.devplace = "";
        this.devstatus = "";
        this.devcheck = "";
        this.devremark = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
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

    public String getDevstandard() {
        return devstandard;
    }

    public void setDevstandard(String devstandard) {
        this.devstandard = devstandard;
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

    public String getDevplace() {
        return devplace;
    }

    public void setDevplace(String devplace) {
        this.devplace = devplace;
    }

    public String getDevstatus() {
        return devstatus;
    }

    public void setDevstatus(String devstatus) {
        this.devstatus = devstatus;
    }

    public String getDevcheck() {
        return devcheck;
    }

    public void setDevcheck(String devcheck) {
        this.devcheck = devcheck;
    }

    public String getDevremark() {
        return devremark;
    }

    public void setDevremark(String devremark) {
        this.devremark = devremark;
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

    public SelectBean<DevBasic> getItem() {
        if (item == null)
            item = new SelectBean<DevBasic>();

        return item;
    }

    public void setItem(SelectBean<DevBasic> item) {
        this.item = item;
    }

}
