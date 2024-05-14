package com.alms.entity.inner;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class InnerSceneWork implements BaseBean {

    // 计划编号;
    private String tranid;

    // 工作编号;
    private String workid;

    // 工作内容;
    private String workcontent;

    // 工作内容
    private String workcontentname;

    // 参加人员;
    private String joinuser;

    // 实施人员;
    private String operuser;

    // 实施人员姓名;
    private String operuserame;

    // 工作时间;
    private java.util.Date workdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<InnerSceneWork> item;

    public InnerSceneWork() {
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
        return ToolUtils.CompareProperty((InnerSceneWork) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "计划编号:tranid", "工作编号:workid", "工作内容:workcontent", "参加人员:joinuser", "实施人员:operuser",
                "实施人员姓名:operuserame", "工作时间:workdate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.workid = "";
        this.workcontent = "";
        this.joinuser = "";
        this.operuser = "";
        this.operuserame = "";
        this.workdate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getWorkcontent() {
        return workcontent;
    }

    public void setWorkcontent(String workcontent) {
        this.workcontent = workcontent;
    }

    public String getWorkcontentname() {
        return workcontentname;
    }

    public void setWorkcontentname(String workcontentname) {
        this.workcontentname = workcontentname;
    }

    public String getJoinuser() {
        return joinuser;
    }

    public void setJoinuser(String joinuser) {
        this.joinuser = joinuser;
    }

    public String getOperuser() {
        return operuser;
    }

    public void setOperuser(String operuser) {
        this.operuser = operuser;
    }

    public String getOperuserame() {
        return operuserame;
    }

    public void setOperuserame(String operuserame) {
        this.operuserame = operuserame;
    }

    public java.util.Date getWorkdate() {
        return workdate;
    }

    public void setWorkdate(java.util.Date workdate) {
        this.workdate = workdate;
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

    public SelectBean<InnerSceneWork> getItem() {
        if (item == null)
            item = new SelectBean<InnerSceneWork>();

        return item;
    }

    public void setItem(SelectBean<InnerSceneWork> item) {
        this.item = item;
    }

}
