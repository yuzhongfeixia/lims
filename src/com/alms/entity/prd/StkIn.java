package com.alms.entity.prd;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StkIn implements BaseBean {

    // 入库单号;
    private String tranid;

    // 实际单据编码;
    private String infact;

    // 入库仓库;
    private String storeid;

    // 仓库名称;
    private String storename;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 入库日期;
    private java.util.Date indate;

    // 入库类型;
    private String stockintype;

    // 入库类型名称;
    private String stockintypename;

    // 入库人;
    private String inuser;

    // 入库人姓名;
    private String inusername;

    // 审核人;
    private String checkuser;

    // 审核人姓名;
    private String checkusername;

    // 审核说明;
    private String checkdesc;

    // 记帐员;
    private String recouser;

    // 记账员姓名;
    private String recousername;

    // 记录日期;
    private java.util.Date recodate;

    // 备注;
    private String remark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StkIn> item;

    public StkIn() {
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
        return ToolUtils.CompareProperty((StkIn) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "入库单号:tranid", "实际单据编码:infact", "入库仓库:storeid", "仓库名称:storename", "业务动作:flowaction",
                "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname", "入库日期:indate", "入库类型:stockintype",
                "入库类型名称:stockintypename", "入库人:inuser", "入库人姓名:inusername", "审核人:checkuser", "审核人姓名:checkusername",
                "审核说明:checkdesc", "记帐员:recouser", "记账员姓名:recousername", "记录日期:recodate", "备注:remark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.infact = "";
        this.storeid = "";
        this.storename = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.indate = ToolUtils.GetMinDate();
        this.stockintype = "";
        this.stockintypename = "";
        this.inuser = "";
        this.inusername = "";
        this.checkuser = "";
        this.checkusername = "";
        this.checkdesc = "";
        this.recouser = "";
        this.recousername = "";
        this.recodate = ToolUtils.GetMinDate();
        this.remark = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getInfact() {
        return infact;
    }

    public void setInfact(String infact) {
        this.infact = infact;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
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

    public java.util.Date getIndate() {
        return indate;
    }

    public void setIndate(java.util.Date indate) {
        this.indate = indate;
    }

    public String getStockintype() {
        return stockintype;
    }

    public void setStockintype(String stockintype) {
        this.stockintype = stockintype;
    }

    public String getStockintypename() {
        return stockintypename;
    }

    public void setStockintypename(String stockintypename) {
        this.stockintypename = stockintypename;
    }

    public String getInuser() {
        return inuser;
    }

    public void setInuser(String inuser) {
        this.inuser = inuser;
    }

    public String getInusername() {
        return inusername;
    }

    public void setInusername(String inusername) {
        this.inusername = inusername;
    }

    public String getCheckuser() {
        return checkuser;
    }

    public void setCheckuser(String checkuser) {
        this.checkuser = checkuser;
    }

    public String getCheckusername() {
        return checkusername;
    }

    public void setCheckusername(String checkusername) {
        this.checkusername = checkusername;
    }

    public String getCheckdesc() {
        return checkdesc;
    }

    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
    }

    public String getRecouser() {
        return recouser;
    }

    public void setRecouser(String recouser) {
        this.recouser = recouser;
    }

    public String getRecousername() {
        return recousername;
    }

    public void setRecousername(String recousername) {
        this.recousername = recousername;
    }

    public java.util.Date getRecodate() {
        return recodate;
    }

    public void setRecodate(java.util.Date recodate) {
        this.recodate = recodate;
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

    public SelectBean<StkIn> getItem() {
        if (item == null)
            item = new SelectBean<StkIn>();

        return item;
    }

    public void setItem(SelectBean<StkIn> item) {
        this.item = item;
    }

}
