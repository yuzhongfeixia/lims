package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class BusFlow implements BaseBean {

    // 业务代码;
    private String busflow;

    // 业务名称;
    private String busflowname;

    // 内容数据接口;
    private String datafunction;

    // 内容显示接口;
    private String htmldatafunction;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusFlow> item;

    public BusFlow() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.getBusflow())) {
            msg.setErrmsg("业务流程编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getBusflowname())) {
            msg.setErrmsg("业务流程名称不能为空！" + ToolUtils.GetNewLines());
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
        return ToolUtils.CompareProperty((BusFlow) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务代码:busflow", "业务名称:busflowname", "内容数据接口:datafunction", "内容显示接口:htmldatafunction" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.busflow = "";
        this.busflowname = "";
        this.datafunction = "";
        this.htmldatafunction = "";
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

    public String getDatafunction() {
        return datafunction;
    }

    public void setDatafunction(String datafunction) {
        this.datafunction = datafunction;
    }

    public String getHtmldatafunction() {
        return htmldatafunction;
    }

    public void setHtmldatafunction(String htmldatafunction) {
        this.htmldatafunction = htmldatafunction;
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

    public SelectBean<BusFlow> getItem() {
        if (item == null)
            item = new SelectBean<BusFlow>();

        return item;
    }

    public void setItem(SelectBean<BusFlow> item) {
        this.item = item;
    }

}
