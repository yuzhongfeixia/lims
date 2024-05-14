package com.alms.entity.dev;

import java.util.Date;

import com.alms.entity.crm.CrmAccidentDeal;
import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SearchParams;
import com.gpersist.entity.publics.SelectBean;

public class DevUseBack implements BaseBean {
    private String devid;
    private String devname;
    private String acceptuser;
    private String acceptusername;
    private String backuser;

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

    public String getAcceptuser() {
        return acceptuser;
    }

    public void setAcceptuser(String acceptuser) {
        this.acceptuser = acceptuser;
    }

    public String getAcceptusername() {
        return acceptusername;
    }

    public void setAcceptusername(String acceptusername) {
        this.acceptusername = acceptusername;
    }

    public String getBackuser() {
        return backuser;
    }

    public void setBackuser(String backuser) {
        this.backuser = backuser;
    }

    public String getBackusername() {
        return backusername;
    }

    public void setBackusername(String backusername) {
        this.backusername = backusername;
    }

    public Date getBackdate() {
        return backdate;
    }

    public void setBackdate(Date backdate) {
        this.backdate = backdate;
    }

    public Date getUsedate() {
        return usedate;
    }

    public void setUsedate(Date usedate) {
        this.usedate = usedate;
    }

    public String getDevstatus() {
        return devstatus;
    }

    public void setDevstatus(String devstatus) {
        this.devstatus = devstatus;
    }

    public String getDevstatusname() {
        return devstatusname;
    }

    public void setDevstatusname(String devstatusname) {
        this.devstatusname = devstatusname;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    private String backusername;
    private Date backdate;
    private Date usedate;
    private String devstatus;
    private String devstatusname;
    private String tranid;
    private SearchParams search;

    private DataDeal deal;

    private SelectBean<CrmAccidentDeal> item;

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

    public SelectBean<CrmAccidentDeal> getItem() {
        if (item == null)
            item = new SelectBean<CrmAccidentDeal>();

        return item;
    }

    public void setItem(SelectBean<CrmAccidentDeal> item) {
        this.item = item;
    }

}
