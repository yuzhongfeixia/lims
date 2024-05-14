package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusGetNotice implements BaseBean {

    // 业务编号（通知单号）;
    private String tranid;

    // 抽样单类型;
    private String gettype;

    // 抽样单类型名称;
    private String gettypename;

    // 检测类型;
    private String testtype;

    // 检测依据数据类型名称;
    private String testtypename;

    // 下达日期;
    private java.util.Date trandate;

    // 下达日期cn;
    private String trandatecn;

    // 业务人员;
    private String tranuser;

    // 业务人员姓名;
    private String tranusername;

    // 抽样人;
    private String getuser;

    // 抽样人姓名;
    private String getusername;

    // 退单说明;
    private String backdesc;

    // 受检单位编号;
    private String testedid;

    // 受检单位名称;
    private String testedname;

    // 完成日期;
    private java.util.Date finishdate;

    // 完成日期cn;
    private String finishdatecn;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务说明;
    private String tranremark;

    // 修改时间;
    private java.util.Date modifydate;

    // 当前修改序号;
    private int modifyserial;

    private String getdept;

    private String procid;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusGetNotice> item;

    public BusGetNotice() {
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
        return ToolUtils.CompareProperty((BusGetNotice) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号（通知单号）:tranid", "抽样单类型:gettype", "抽样单类型名称:gettypename", "检测类型:testtype",
                "检测依据数据类型名称:testtypename", "下达日期:trandate", "下达日期CN:trandatecn", "业务人员:tranuser", "业务人员姓名:tranusername",
                "抽样人:getuser", "抽样人姓名:getusername", "退单说明:backdesc", "受检单位编号:testedid", "受检单位名称:testedname",
                "完成日期:finishdate", "完成日期CN:finishdatecn", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname", "业务说明:tranremark", "修改时间:modifydate", "当前修改序号:modifyserial" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.gettype = "";
        this.gettypename = "";
        this.testtype = "";
        this.testtypename = "";
        this.trandate = ToolUtils.GetMinDate();
        this.trandatecn = "";
        this.tranuser = "";
        this.tranusername = "";
        this.getuser = "";
        this.getusername = "";
        this.backdesc = "";
        this.testedid = "";
        this.testedname = "";
        this.finishdate = ToolUtils.GetMinDate();
        this.finishdatecn = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.tranremark = "";
        this.modifydate = ToolUtils.GetMinDate();
        this.modifyserial = 0;
        this.procid = "";
        this.getdept = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getGettype() {
        return gettype;
    }

    public void setGettype(String gettype) {
        this.gettype = gettype;
    }

    public String getGettypename() {
        return gettypename;
    }

    public void setGettypename(String gettypename) {
        this.gettypename = gettypename;
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

    public String getTesttypename() {
        return testtypename;
    }

    public void setTesttypename(String testtypename) {
        this.testtypename = testtypename;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public String getTrandatecn() {
        return trandatecn;
    }

    public void setTrandatecn(String trandatecn) {
        this.trandatecn = trandatecn;
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

    public String getGetuser() {
        return getuser;
    }

    public void setGetuser(String getuser) {
        this.getuser = getuser;
    }

    public String getGetusername() {
        return getusername;
    }

    public void setGetusername(String getusername) {
        this.getusername = getusername;
    }

    public String getBackdesc() {
        return backdesc;
    }

    public void setBackdesc(String backdesc) {
        this.backdesc = backdesc;
    }

    public String getTestedid() {
        return testedid;
    }

    public void setTestedid(String testedid) {
        this.testedid = testedid;
    }

    public String getTestedname() {
        return testedname;
    }

    public void setTestedname(String testedname) {
        this.testedname = testedname;
    }

    public java.util.Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(java.util.Date finishdate) {
        this.finishdate = finishdate;
    }

    public String getFinishdatecn() {
        return finishdatecn;
    }

    public void setFinishdatecn(String finishdatecn) {
        this.finishdatecn = finishdatecn;
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

    public String getTranremark() {
        return tranremark;
    }

    public void setTranremark(String tranremark) {
        this.tranremark = tranremark;
    }

    public java.util.Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(java.util.Date modifydate) {
        this.modifydate = modifydate;
    }

    public int getModifyserial() {
        return modifyserial;
    }

    public void setModifyserial(int modifyserial) {
        this.modifyserial = modifyserial;
    }

    public String getProcid() {
        return procid;
    }

    public void setProcid(String procid) {
        this.procid = procid;
    }

    public String getGetdept() {
        return getdept;
    }

    public void setGetdept(String getdept) {
        this.getdept = getdept;
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

    public SelectBean<BusGetNotice> getItem() {
        if (item == null)
            item = new SelectBean<BusGetNotice>();

        return item;
    }

    public void setItem(SelectBean<BusGetNotice> item) {
        this.item = item;
    }

}
