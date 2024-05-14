package com.alms.entity.file;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class IncFileDestroyRecord implements BaseBean {

    // 登记编号;
    private String tranid;

    // 申请编号
    private String applyid;

    // 资料编号;
    private String fileid;

    // glp文件名称;
    private String filename;

    // 销毁原因;
    private String destroyreason;

    // 销毁日期;
    private java.util.Date destroydate;

    // 批准人;
    private String allowuser;

    // 批准人姓名;
    private String allowusername;

    // 批准人时间;
    private java.util.Date allowdate;

    // 监销人;
    private String monituser;

    // 监销人姓名;
    private String monitusername;

    // 监销人时间;
    private java.util.Date monitdate;

    // 备注;
    private String remark;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 业务员时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<IncFileDestroyRecord> item;

    public IncFileDestroyRecord() {
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
        return ToolUtils.CompareProperty((IncFileDestroyRecord) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "登记编号:tranid", "资料编号:fileid", "GLP文件名称:filename", "销毁原因:destroyreason",
                "销毁日期:destroydate", "批准人:allowuser", "批准人姓名:allowusername", "批准人时间:allowdate", "监销人:moniuser",
                "监销人姓名:monitusername", "监销人时间:monitdate", "备注:remark", "业务员:tranuser", "业务员姓名:tranusername",
                "业务员时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.applyid = "";
        this.fileid = "";
        this.filename = "";
        this.destroyreason = "";
        this.destroydate = ToolUtils.GetMinDate();
        this.allowuser = "";
        this.allowusername = "";
        this.allowdate = ToolUtils.GetMinDate();
        this.monituser = "";
        this.monitusername = "";
        this.monitdate = ToolUtils.GetMinDate();
        this.remark = "";
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public String getMonituser() {
        return monituser;
    }

    public void setMonituser(String monituser) {
        this.monituser = monituser;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public SelectBean<IncFileDestroyRecord> getItem() {
        if (item == null)
            item = new SelectBean<IncFileDestroyRecord>();

        return item;
    }

    public void setItem(SelectBean<IncFileDestroyRecord> item) {
        this.item = item;
    }

}
