package com.alms.entity.flow;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusTodoNow implements BaseBean {

    // ҵ����;
    private String tranid;

    // ҵ�����;
    private String busflow;

    // �ύ��;
    private String tranuser;

    // �ύ����;
    private String trandept;

    // �ύ����;
    private java.util.Date trandate;

    // ��ǰ��������;
    private java.util.Date senddate;

    // ������;
    private String actusername;

    // ����ʱ��;
    private java.util.Date actdate;

    // ��ǰ���;
    private String tododesc;

    // ��ǰ״̬����;
    private String todostatusdesc;

    // ����״̬;
    private String todostatus;

    // ����״̬����;
    private String todostatusname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusTodoNow> item;

    public BusTodoNow() {
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
        return ToolUtils.CompareProperty((BusTodoNow) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "ҵ����:tranid", "ҵ�����:busflow", "�ύ��:tranuser", "�ύ����:trandept", "�ύ����:trandate",
                "��ǰ��������:senddate", "������:actusername", "����ʱ��:actdate", "��ǰ���:tododesc",
                "��ǰ״̬����:todostatusdesc", "����״̬:todostatus", "����״̬����:todostatusname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.busflow = "";
        this.tranuser = "";
        this.trandept = "";
        this.trandate = ToolUtils.GetMinDate();
        this.senddate = ToolUtils.GetMinDate();
        this.actusername = "";
        this.actdate = ToolUtils.GetMinDate();
        this.tododesc = "";
        this.todostatusdesc = "";
        this.todostatus = "";
        this.todostatusname = "";
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

    public String getTranuser() {
        return tranuser;
    }

    public void setTranuser(String tranuser) {
        this.tranuser = tranuser;
    }

    public String getTrandept() {
        return trandept;
    }

    public void setTrandept(String trandept) {
        this.trandept = trandept;
    }

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
    }

    public java.util.Date getSenddate() {
        return senddate;
    }

    public void setSenddate(java.util.Date senddate) {
        this.senddate = senddate;
    }

    public String getActusername() {
        return actusername;
    }

    public void setActusername(String actusername) {
        this.actusername = actusername;
    }

    public java.util.Date getActdate() {
        return actdate;
    }

    public void setActdate(java.util.Date actdate) {
        this.actdate = actdate;
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

    public String getTodostatus() {
        return todostatus;
    }

    public void setTodostatus(String todostatus) {
        this.todostatus = todostatus;
    }

    public String getTodostatusname() {
        return todostatusname;
    }

    public void setTodostatusname(String todostatusname) {
        this.todostatusname = todostatusname;
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

    public SelectBean<BusTodoNow> getItem() {
        if (item == null)
            item = new SelectBean<BusTodoNow>();

        return item;
    }

    public void setItem(SelectBean<BusTodoNow> item) {
        this.item = item;
    }

}
