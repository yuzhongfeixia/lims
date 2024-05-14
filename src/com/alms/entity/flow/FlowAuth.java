package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class FlowAuth implements BaseBean {

    // 业务编号;
    private String tranid;

    // 授权角色;
    private String noderole;

    // 执行角色名称;
    private String benoderolename;

    // 授权人;
    private String authuser;

    // 授权人姓名;
    private String authusername;

    // 被授权角色;
    private String benoderole;

    // 执行角色名称;
    private String noderolename;

    // 被授权人;
    private String beauth;

    // 被授权人姓名;
    private String beauthname;

    // 授权时间;
    private java.util.Date authdate;

    // 授权业务;
    private String busflow;

    // 业务名称;
    private String busflowname;

    // 业务节点;
    private String flownode;

    // 节点名称;
    private String flownodename;

    // 授权取消时间;
    private java.util.Date canceldate;

    // 是否取消;
    private boolean iscancel;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FlowAuth> item;

    public FlowAuth() {
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
        return ToolUtils.CompareProperty((FlowAuth) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "授权角色:noderole", "执行角色名称:noderolename", "授权人:authuser",
                "授权人姓名:authusername", "被授权角色:benoderole", "执行角色名称:noderolename", "被授权人:beauth", "被授权人姓名:beauthname",
                "授权时间:authdate", "授权业务:busflow", "业务名称:busflowname", "业务节点:flownode", "节点名称:flownodename",
                "授权取消时间:canceldate", "是否取消:iscancel" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.noderole = "";
        this.noderolename = "";
        this.authuser = "";
        this.authusername = "";
        this.benoderole = "";
        this.benoderolename = "";
        this.beauth = "";
        this.beauthname = "";
        this.authdate = ToolUtils.GetMinDate();
        this.busflow = "";
        this.busflowname = "";
        this.flownode = "";
        this.flownodename = "";
        // this.canceldate = ToolUtils.GetMinDate();
        // this.iscancel = false;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getNoderole() {
        return noderole;
    }

    public void setNoderole(String noderole) {
        this.noderole = noderole;
    }

    public String getAuthuser() {
        return authuser;
    }

    public void setAuthuser(String authuser) {
        this.authuser = authuser;
    }

    public String getAuthusername() {
        return authusername;
    }

    public void setAuthusername(String authusername) {
        this.authusername = authusername;
    }

    public String getBenoderole() {
        return benoderole;
    }

    public void setBenoderole(String benoderole) {
        this.benoderole = benoderole;
    }

    public String getNoderolename() {
        return noderolename;
    }

    public void setNoderolename(String noderolename) {
        this.noderolename = noderolename;
    }

    public String getBeauth() {
        return beauth;
    }

    public void setBeauth(String beauth) {
        this.beauth = beauth;
    }

    public String getBenoderolename() {
        return benoderolename;
    }

    public void setBenoderolename(String benoderolename) {
        this.benoderolename = benoderolename;
    }

    public String getBeauthname() {
        return beauthname;
    }

    public void setBeauthname(String beauthname) {
        this.beauthname = beauthname;
    }

    public java.util.Date getAuthdate() {
        return authdate;
    }

    public void setAuthdate(java.util.Date authdate) {
        this.authdate = authdate;
    }

    public String getBusflow() {
        return busflow;
    }

    public void setBusflow(String busflow) {
        this.busflow = busflow;
    }

    public String getBusflowname() {
        return busflowname;
    }

    public void setBusflowname(String busflowname) {
        this.busflowname = busflowname;
    }

    public String getFlownode() {
        return flownode;
    }

    public void setFlownode(String flownode) {
        this.flownode = flownode;
    }

    public String getFlownodename() {
        return flownodename;
    }

    public void setFlownodename(String flownodename) {
        this.flownodename = flownodename;
    }

    public java.util.Date getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(java.util.Date canceldate) {
        this.canceldate = canceldate;
    }

    public boolean isIscancel() {
        return iscancel;
    }

    public void setIscancel(boolean iscancel) {
        this.iscancel = iscancel;
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

    public SelectBean<FlowAuth> getItem() {
        if (item == null)
            item = new SelectBean<FlowAuth>();

        return item;
    }

    public void setItem(SelectBean<FlowAuth> item) {
        this.item = item;
    }

}
