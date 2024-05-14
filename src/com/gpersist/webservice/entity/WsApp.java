package com.gpersist.webservice.entity;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class WsApp implements BaseBean {

    // 服务商编号;
    private String appid;

    private String appname;

    // 密钥;
    private String appkey;

    // 建立日期;
    private java.util.Date crtdate;

    // 建立人;
    private String crtuser;

    private String username;

    // 记录日期;
    private java.util.Date historydate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<WsApp> item;

    public WsApp() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.getAppid())) {
            msg.setErrmsg("服务商编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getAppname())) {
            msg.setErrmsg("服务商名称不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (ToolUtils.StringIsEmpty(this.getAppkey())) {
            msg.setErrmsg("密钥不能为空！" + ToolUtils.GetNewLines());
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
        return ToolUtils.CompareProperty((WsApp) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "服务商编号:appid", "服务商名称:appname", "密钥:appkey" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.appid = "";
        this.appkey = "";
        this.crtdate = ToolUtils.GetMinDate();
        this.crtuser = "";
        this.historydate = ToolUtils.GetMinDate();
        this.username = "";
        this.appname = "";
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public java.util.Date getCrtdate() {
        return crtdate;
    }

    public void setCrtdate(java.util.Date crtdate) {
        this.crtdate = crtdate;
    }

    public String getCrtuser() {
        return crtuser;
    }

    public void setCrtuser(String crtuser) {
        this.crtuser = crtuser;
    }

    public java.util.Date getHistorydate() {
        return historydate;
    }

    public void setHistorydate(java.util.Date historydate) {
        this.historydate = historydate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public SelectBean<WsApp> getItem() {
        if (item == null)
            item = new SelectBean<WsApp>();

        return item;
    }

    public void setItem(SelectBean<WsApp> item) {
        this.item = item;
    }

}
