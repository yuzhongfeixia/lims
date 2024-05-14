package com.alms.entity.flow;

import java.util.Date;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;

public class HBusTodo implements BaseBean {

    private String tranid;
    private String busflow;
    private String flownode;
    private String tranusername;
    private String tranuser;
    private Date trandate;
    private String tododesc;
    private String todostatusdesc;
    private String trandeptname;
    private SearchParams search;

    private DataDeal deal;

    private SelectBean<HBusTodo> item;

    @Override
    public String OnDebug() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub

    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] OnProperties() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getBusflow() {
        return busflow;
    }

    public void setBusflow(String busflow) {
        this.busflow = busflow;
    }

    public String getFlownode() {
        return flownode;
    }

    public void setFlownode(String flownode) {
        this.flownode = flownode;
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

    public Date getTrandate() {
        return trandate;
    }

    public void setTrandate(Date trandate) {
        this.trandate = trandate;
    }

    public String getTododesc() {
        return tododesc;
    }

    public void setTododesc(String tododesc) {
        this.tododesc = tododesc;
    }

    public String getTodostatusdesc() {
        return todostatusdesc;
    }

    public void setTodostatusdesc(String todostatusdesc) {
        this.todostatusdesc = todostatusdesc;
    }

    public String getTrandeptname() {
        return trandeptname;
    }

    public void setTrandeptname(String trandeptname) {
        this.trandeptname = trandeptname;
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

    public SelectBean<HBusTodo> getItem() {
        if (item == null)
            item = new SelectBean<HBusTodo>();

        return item;
    }

    public void setItem(SelectBean<HBusTodo> item) {
        this.item = item;
    }

}
