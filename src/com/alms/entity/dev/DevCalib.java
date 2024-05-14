package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class DevCalib implements BaseBean {

    // 业务编号;
    private String tranid;

    // 设备编号;
    private String devid;

    // 设备名称;
    private String devname;

    // 检定日期;
    private java.util.Date calibdate;

    // 检定单位(法定机构);
    private String calibunit;
    private String calibunitname;
    // 检定人;
    private String calibuser;

    // 检定人姓名;
    private String calibusername;

    // 检定人时间;
    private java.util.Date calibuserdate;

    // 准确等级/最大允许误差/不确定度
    private String problemdesc;

    // 检查内容;
    private String calibcontent;

    // 校准结果
    private String calibresult;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 设备管理员;
    private String devmanager;

    // 设备管理员姓名;
    private String devmanagername;

    // 检定校准要求;
    private String calibrequire;

    // 最近检定日期;
    private java.util.Date lastdate;

    // 下次检定日期;
    private java.util.Date nextdate;

    // 检定周期;
    private String devperiod;

    // 设备校验周期名称;
    private String devperiodname;

    // 备注;
    private String remark;

    // 业务人;
    private String tranuser;

    // 业务人姓名;
    private String tranusername;

    // 业务日期;
    private java.util.Date trandate;

    // 设备数量
    private String devcount;

    // 测量范围
    private String measurerange;

    // 规格型号
    private String specimodel;

    // 出厂编号
    private String factorycode;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DevCalib> item;

    public DevCalib() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.CheckComboValue(this.getDevperiod())) {
            msg.setErrmsg("请选择检定周期！" + ToolUtils.GetNewLines());
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
        return ToolUtils.CompareProperty((DevCalib) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "设备编号:devid", "设备名称:devname", "检定人:calibuser", "检定日期:calibdate",
                "检定单位(法定机构):calibunit", "检定人姓名:calibusername", "检定人时间:calibuserdate", "问题分析:problemdesc",
                "检查内容:calibcontent", "检查结果:calibresult", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname", "设备管理员:devmanager", "设备管理员姓名:devmanagername", "检定校准要求:calibrequire",
                "最近检定日期:lastdate", "下次检定日期:nextdate", "检定周期:devperiod", "设备校验周期名称:devperiodname", "备注:remark",
                "业务人:tranuser", "业务人姓名:tranusername", "业务日期:trandate" };
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
        this.calibdate = ToolUtils.GetMinDate();
        this.calibunit = "";
        this.calibusername = "";
        this.calibuser = "";
        this.calibuserdate = ToolUtils.GetMinDate();
        this.problemdesc = "";
        this.calibcontent = "";
        this.calibresult = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.devmanager = "";
        this.devmanagername = "";
        this.calibrequire = "";
        this.lastdate = ToolUtils.GetMinDate();
        this.nextdate = ToolUtils.GetMinDate();
        this.devperiod = "";
        this.devperiodname = "";
        this.remark = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
    }

    public String getCalibuser() {
        return calibuser;
    }

    public void setCalibuser(String calibuser) {
        this.calibuser = calibuser;
    }

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname;
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

    public java.util.Date getCalibdate() {
        return calibdate;
    }

    public void setCalibdate(java.util.Date calibdate) {
        this.calibdate = calibdate;
    }

    public String getCalibunit() {
        return calibunit;
    }

    public void setCalibunit(String calibunit) {
        this.calibunit = calibunit;
    }

    public String getCalibunitname() {
        return calibunitname;
    }

    public void setCalibunitname(String calibunitname) {
        this.calibunitname = calibunitname;
    }

    public String getCalibusername() {
        return calibusername;
    }

    public void setCalibusername(String calibusername) {
        this.calibusername = calibusername;
    }

    public java.util.Date getCalibuserdate() {
        return calibuserdate;
    }

    public void setCalibuserdate(java.util.Date calibuserdate) {
        this.calibuserdate = calibuserdate;
    }

    public String getProblemdesc() {
        return problemdesc;
    }

    public void setProblemdesc(String problemdesc) {
        this.problemdesc = problemdesc;
    }

    public String getCalibcontent() {
        return calibcontent;
    }

    public void setCalibcontent(String calibcontent) {
        this.calibcontent = calibcontent;
    }

    public String getCalibresult() {
        return calibresult;
    }

    public void setCalibresult(String calibresult) {
        this.calibresult = calibresult;
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

    public String getCalibrequire() {
        return calibrequire;
    }

    public void setCalibrequire(String calibrequire) {
        this.calibrequire = calibrequire;
    }

    public java.util.Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(java.util.Date lastdate) {
        this.lastdate = lastdate;
    }

    public java.util.Date getNextdate() {
        return nextdate;
    }

    public void setNextdate(java.util.Date nextdate) {
        this.nextdate = nextdate;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getDevcount() {
        return devcount;
    }

    public void setDevcount(String devcount) {
        this.devcount = devcount;
    }

    public String getMeasurerange() {
        return measurerange;
    }

    public void setMeasurerange(String measurerange) {
        this.measurerange = measurerange;
    }

    public String getSpecimodel() {
        return specimodel;
    }

    public void setSpecimodel(String specimodel) {
        this.specimodel = specimodel;
    }

    public String getFactorycode() {
        return factorycode;
    }

    public void setFactorycode(String factorycode) {
        this.factorycode = factorycode;
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

    public SelectBean<DevCalib> getItem() {
        if (item == null)
            item = new SelectBean<DevCalib>();

        return item;
    }

    public void setItem(SelectBean<DevCalib> item) {
        this.item = item;
    }

}
