package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class DevUse implements BaseBean {

    // 业务编号;
    private String tranid;

    // 仪器编号;
    private String devid;

    // 设备名称;
    private String devname;

    // 记录日期;
    private java.util.Date trandate;

    // 开机时间;
    private java.util.Date usebegin;

    // 关机时间;
    private java.util.Date useend;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 测试项目;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 使用功能;
    private String usefunction;

    // 使用前状况;
    private String beforestatus;

    // 使用状态名称;
    private String beforestatusname;

    // 使用后状况;
    private String afterstatus;

    // 使用后状况;
    private String afterstatusname;

    // 业务动作;
    private String flowaction;

    // 业务状态;
    private String flowstatus;

    // 业务动作;
    private String flowactionname;

    // 业务状态;
    private String flowstatusname;

    // 使用人;
    private String tranuser;

    // 使用人姓名;
    private String tranusername;

    // 管理人;
    private String devmanager;

    // 管理人姓名;
    private String devmanagername;

    // 备注;
    private String remark;

    private String sampletran;

    private String samplecode;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DevUse> item;

    public DevUse() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.CheckComboValue(this.getBeforestatus())) {
            msg.setErrmsg("请选择使用前状态！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.CheckComboValue(this.getAfterstatus())) {
            msg.setErrmsg("请选择使用后状态！" + ToolUtils.GetNewLines());
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
        return ToolUtils.CompareProperty((DevUse) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "仪器编号:devid", "设备名称:devname", "记录日期:trandate", "开机时间:usebegin",
                "关机时间:useend", "样品编号:sampleid", "样品名称:samplename", "测试项目:parameterid", "检测参数名称:parametername",
                "使用功能:usefunction", "使用前状况:beforestatus", "使用前状态名称:beforestatusname", "使用后状况:afterstatus",
                "使用后状况名称:afterstatusname", "业务动作:flowaction", "业务状态:flowstatus", "使用人:tranuser", "使用人姓名:tranusername",
                "管理人:devmanager", "管理人姓名:devmanagername", "备注:remark", "试验编号:sampletran", "样品编号:samplecode" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.sampletran = "";
        this.samplecode = "";
        this.devid = "";
        this.devname = "";
        this.trandate = ToolUtils.GetMinDate();
        this.usebegin = ToolUtils.GetMinDate();
        this.useend = ToolUtils.GetMinDate();
        this.sampleid = "";
        this.samplename = "";
        this.parameterid = "";
        this.parametername = "";
        this.usefunction = "";
        this.beforestatus = "";
        this.beforestatusname = "";
        this.afterstatus = "";
        this.afterstatusname = "";
        this.flowaction = "";
        this.flowstatus = "";
        this.flowactionname = "";
        this.flowstatusname = "";
        this.tranuser = "";
        this.tranusername = "";
        this.devmanager = "";
        this.devmanagername = "";
        this.remark = "";
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public String getFlowactionname() {
        return flowactionname;
    }

    public void setFlowactionname(String flowactionname) {
        this.flowactionname = flowactionname;
    }

    public String getFlowstatusname() {
        return flowstatusname;
    }

    public void setFlowstatusname(String flowstatusname) {
        this.flowstatusname = flowstatusname;
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

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public java.util.Date getUsebegin() {
        return usebegin;
    }

    public void setUsebegin(java.util.Date usebegin) {
        this.usebegin = usebegin;
    }

    public java.util.Date getUseend() {
        return useend;
    }

    public void setUseend(java.util.Date useend) {
        this.useend = useend;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getParameterid() {
        return parameterid;
    }

    public void setParameterid(String parameterid) {
        this.parameterid = parameterid;
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
    }

    public String getUsefunction() {
        return usefunction;
    }

    public void setUsefunction(String usefunction) {
        this.usefunction = usefunction;
    }

    public String getBeforestatus() {
        return beforestatus;
    }

    public void setBeforestatus(String beforestatus) {
        this.beforestatus = beforestatus;
    }

    public String getBeforestatusname() {
        return beforestatusname;
    }

    public void setBeforestatusname(String beforestatusname) {
        this.beforestatusname = beforestatusname;
    }

    public String getAfterstatusname() {
        return afterstatusname;
    }

    public void setAfterstatusname(String afterstatusname) {
        this.afterstatusname = afterstatusname;
    }

    public String getAfterstatus() {
        return afterstatus;
    }

    public void setAfterstatus(String afterstatus) {
        this.afterstatus = afterstatus;
    }

    public String getFlowaction() {
        return flowaction;
    }

    public void setFlowaction(String flowaction) {
        this.flowaction = flowaction;
    }

    public String getFlowstatus() {
        return flowstatus;
    }

    public void setFlowstatus(String flowstatus) {
        this.flowstatus = flowstatus;
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

    public SelectBean<DevUse> getItem() {
        if (item == null)
            item = new SelectBean<DevUse>();

        return item;
    }

    public void setItem(SelectBean<DevUse> item) {
        this.item = item;
    }

}
