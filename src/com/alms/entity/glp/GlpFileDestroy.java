package com.alms.entity.glp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class GlpFileDestroy implements BaseBean {

    // 销毁编号;
    private String tranid;

    // 资料编号;
    private String fileid;

    // 文件名称;
    private String filename;

    // 销毁原因;
    private String destroyreason;

    // 销毁日期;
    private java.util.Date destroydate;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 批准人;
    private String allowuser;

    // 批准人姓名;
    private String allowusername;

    // 批准人时间;
    private java.util.Date allowdate;

    // 批准人意见;
    private String allowdesc;

    // 监销人;
    private String moniuser;

    // 监销人姓名;
    private String monitusername;

    // 监销人时间;
    private java.util.Date monitdate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<GlpFileDestroy> item;

    public GlpFileDestroy() {
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
        return ToolUtils.CompareProperty((GlpFileDestroy) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "销毁编号:tranid", "资料编号:fileid", "文件名称:filename", "销毁原因:destroyreason", "销毁日期:destroydate",
                "业务状态:flowstatus", "业务状态名称:flowstatusname", "业务动作:flowaction", "业务动作名称:flowactionname", "批准人:allowuser",
                "批准人姓名:allowusername", "批准人时间:allowdate", "批准人意见:allowdesc", "监销人:moniuser", "监销人姓名:monitusername",
                "监销人时间:monitdate", "业务员:tranuser", "业务员姓名:tranusername", "业务员时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.fileid = "";
        this.filename = "";
        this.destroyreason = "";
        // this.destroydate = ToolUtils.GetMinDate();
        this.flowstatus = "";
        this.flowstatusname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.allowuser = "";
        this.allowusername = "";
        // this.allowdate = ToolUtils.GetMinDate();
        this.allowdesc = "";
        this.moniuser = "";
        this.monitusername = "";
        // this.monitdate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.tranusername = "";
        // this.trandate = ToolUtils.GetMinDate();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getDestroyreason() {
        return destroyreason;
    }

    public void setDestroyreason(String destroyreason) {
        this.destroyreason = destroyreason;
    }

    public java.util.Date getDestroydate() {
        return destroydate;
    }

    public void setDestroydate(java.util.Date destroydate) {
        this.destroydate = destroydate;
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

    public String getAllowuser() {
        return allowuser;
    }

    public void setAllowuser(String allowuser) {
        this.allowuser = allowuser;
    }

    public String getAllowusername() {
        return allowusername;
    }

    public void setAllowusername(String allowusername) {
        this.allowusername = allowusername;
    }

    public java.util.Date getAllowdate() {
        return allowdate;
    }

    public void setAllowdate(java.util.Date allowdate) {
        this.allowdate = allowdate;
    }

    public String getAllowdesc() {
        return allowdesc;
    }

    public void setAllowdesc(String allowdesc) {
        this.allowdesc = allowdesc;
    }

    public String getMoniuser() {
        return moniuser;
    }

    public void setMoniuser(String moniuser) {
        this.moniuser = moniuser;
    }

    public String getMonitusername() {
        return monitusername;
    }

    public void setMonitusername(String monitusername) {
        this.monitusername = monitusername;
    }

    public java.util.Date getMonitdate() {
        return monitdate;
    }

    public void setMonitdate(java.util.Date monitdate) {
        this.monitdate = monitdate;
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

    public java.util.Date getTrandate() {
        return trandate;
    }

    public void setTrandate(java.util.Date trandate) {
        this.trandate = trandate;
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

    public SelectBean<GlpFileDestroy> getItem() {
        if (item == null)
            item = new SelectBean<GlpFileDestroy>();

        return item;
    }

    public void setItem(SelectBean<GlpFileDestroy> item) {
        this.item = item;
    }

}
