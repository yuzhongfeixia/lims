package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DevCommon implements BaseBean {

    // 业务编号;
    private String tranid;

    // 设备编号;
    private String devid;

    // 设备名称;
    private String devname;

    // 生产厂家;
    private String factoryname;

    // 检定日期;
    private java.util.Date commondate;

    // 检定单位(法定机构);
    private String commondept;

    // 检定人;
    private String commonuser;

    // 检查内容;
    private String commoncontent;

    // 检查结果;
    private String commonresult;

    // 备注;
    private String remark;

    // 设备管理员;
    private String devmanager;

    // 设备管理员姓名;
    private String devmanagername;

    // 设备管理员时间;
    private java.util.Date managerdate;

    // 核查单位;
    private String calibunitname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DevCommon> item;

    public DevCommon() {
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
        return ToolUtils.CompareProperty((DevCommon) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "核查单位:calibunitname", "设备编号:devid", "设备名称:devname", "检定日期:commondate",
                "检定单位(法定机构):commondept", "检定人:commonuser", "检查内容:commoncontent", "检查结果:commonresult", "备注:remark",
                "设备管理员:devmanager", "设备管理员姓名:devmanagername", "设备管理员时间:managerdate" };
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
        this.commondept = "";
        this.commonuser = "";
        this.commoncontent = "";
        this.commonresult = "";
        this.remark = "";
        this.devmanager = "";
        this.devmanagername = "";
        this.managerdate = ToolUtils.GetMinDate();
        this.calibunitname = "";
    }

    public String getCalibunitname() {
        return calibunitname;
    }

    public void setCalibunitname(String calibunitname) {
        this.calibunitname = calibunitname;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
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

    public java.util.Date getCommondate() {
        return commondate;
    }

    public void setCommondate(java.util.Date commondate) {
        this.commondate = commondate;
    }

    public String getCommondept() {
        return commondept;
    }

    public void setCommondept(String commondept) {
        this.commondept = commondept;
    }

    public String getCommonuser() {
        return commonuser;
    }

    public void setCommonuser(String commonuser) {
        this.commonuser = commonuser;
    }

    public String getCommoncontent() {
        return commoncontent;
    }

    public void setCommoncontent(String commoncontent) {
        this.commoncontent = commoncontent;
    }

    public String getCommonresult() {
        return commonresult;
    }

    public void setCommonresult(String commonresult) {
        this.commonresult = commonresult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<DevCommon> getItem() {
        if (item == null)
            item = new SelectBean<DevCommon>();

        return item;
    }

    public void setItem(SelectBean<DevCommon> item) {
        this.item = item;
    }

}
