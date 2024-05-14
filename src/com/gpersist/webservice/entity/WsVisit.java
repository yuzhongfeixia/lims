package com.gpersist.webservice.entity;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class WsVisit implements BaseBean {

    // 访问序号;
    private int visitid;

    // 服务商编号;
    private String appid;

    private String appname;

    // 访问日期;
    private java.util.Date visitdate;

    // 访问函数;
    private String visitfunc;

    // 访问地址;
    private String visitaddress;

    private String visitdev;

    private String visituser;

    private String username;

    // 备注;
    private String visitcontent;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<WsVisit> item;

    public WsVisit() {
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

        if (ToolUtils.StringIsEmpty(this.getVisitfunc())) {
            msg.setErrmsg("访问函数不能为空！" + ToolUtils.GetNewLines());
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
        return ToolUtils.CompareProperty((WsVisit) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "访问序号:visitid", "服务商编号:appid", "访问日期:visitdate", "访问函数:visitfunc", "访问地址:visitaddress",
                "备注:visitcontent" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.visitid = 0;
        this.appid = "";
        this.visitdate = ToolUtils.GetMinDate();
        this.visitfunc = "";
        this.visitaddress = "";
        this.visitcontent = "";
        this.appname = "";
        this.visitdev = "";
        this.visituser = "";
        this.username = "";
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getVisitdev() {
        return visitdev;
    }

    public void setVisitdev(String visitdev) {
        this.visitdev = visitdev;
    }

    public String getVisituser() {
        return visituser;
    }

    public void setVisituser(String visituser) {
        this.visituser = visituser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVisitid() {
        return visitid;
    }

    public void setVisitid(int visitid) {
        this.visitid = visitid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public java.util.Date getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(java.util.Date visitdate) {
        this.visitdate = visitdate;
    }

    public String getVisitfunc() {
        return visitfunc;
    }

    public void setVisitfunc(String visitfunc) {
        this.visitfunc = visitfunc;
    }

    public String getVisitaddress() {
        return visitaddress;
    }

    public void setVisitaddress(String visitaddress) {
        this.visitaddress = visitaddress;
    }

    public String getVisitcontent() {
        return visitcontent;
    }

    public void setVisitcontent(String visitcontent) {
        this.visitcontent = visitcontent;
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

    public SelectBean<WsVisit> getItem() {
        if (item == null)
            item = new SelectBean<WsVisit>();

        return item;
    }

    public void setItem(SelectBean<WsVisit> item) {
        this.item = item;
    }

}
