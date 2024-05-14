package com.alms.entity.samp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SampleBackup implements BaseBean {

    // 业务编号;
    private String tranid;

    // 投诉编号
    private String complaintid;

    // 样品编号;
    private String sampletran;

    // 样品名称;
    private String samplename;

    // 备样状态;
    private String backupstatus;

    // 备份样品状态;
    private String backupstatusname;

    // 申领原因;
    private String backupreason;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务员
    private String tranuser;

    // 业务员姓名
    private String tranusername;

    // 业务员时间
    private java.util.Date trandate;

    // 质量负责人;
    private String qualityuser;

    // 质量负责人姓名;
    private String qualityusername;

    // 质量负责人时间;
    private java.util.Date qualitydate;

    // 启封前状态;
    private String backupbefore;

    // 证明人;
    private String certuser;

    // 证明人姓名;
    private String certusername;

    // 证明人时间;
    private java.util.Date certdate;

    // 启封封样记录;
    private String backuprecord;

    // 封样人;
    private String sealuser;

    // 封样人姓名;
    private String sealusername;

    // 封样人时间;
    private java.util.Date sealdate;

    // 重新封样日期;
    private java.util.Date backupdate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SampleBackup> item;

    public SampleBackup() {
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
        return ToolUtils.CompareProperty((SampleBackup) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "样品编号:sampleid", "样品名称:samplename", "备样状态:backupstatus",
                "备份样品状态:backupstatusname", "申领原因:backupreason", "业务动作:flowaction", "业务动作名称:flowactionname",
                "业务状态:flowstatus", "业务状态名称:flowstatusname", "质量负责人:qualityuser", "质量负责人姓名:qualityusername",
                "质量负责人时间:qualitydate", "启封前状态:backupbefore", "证明人:certuser", "证明人姓名:certusername", "证明人时间:certdate",
                "启封封样记录:backuprecord", "封样人:sealuser", "封样人姓名:sealusername", "封样人时间:sealdate", "重新封样日期:backupdate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.sampletran = "";
        this.samplename = "";
        this.backupstatus = "";
        this.backupstatusname = "";
        this.backupreason = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.qualityuser = "";
        this.qualityusername = "";
        // this.qualitydate = ToolUtils.GetMinDate();
        this.backupbefore = "";
        this.certuser = "";
        this.certusername = "";
        // this.certdate = ToolUtils.GetMinDate();
        this.backuprecord = "";
        this.sealuser = "";
        this.sealusername = "";
        // this.sealdate = ToolUtils.GetMinDate();
        // this.backupdate = ToolUtils.GetMinDate();
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getComplaintid() {
        return complaintid;
    }

    public void setComplaintid(String complaintid) {
        this.complaintid = complaintid;
    }

    public String getSampletran() {
        return sampletran;
    }

    public void setSampletran(String sampletran) {
        this.sampletran = sampletran;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getBackupstatus() {
        return backupstatus;
    }

    public void setBackupstatus(String backupstatus) {
        this.backupstatus = backupstatus;
    }

    public String getBackupstatusname() {
        return backupstatusname;
    }

    public void setBackupstatusname(String backupstatusname) {
        this.backupstatusname = backupstatusname;
    }

    public String getBackupreason() {
        return backupreason;
    }

    public void setBackupreason(String backupreason) {
        this.backupreason = backupreason;
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

    public String getQualityuser() {
        return qualityuser;
    }

    public void setQualityuser(String qualityuser) {
        this.qualityuser = qualityuser;
    }

    public String getQualityusername() {
        return qualityusername;
    }

    public void setQualityusername(String qualityusername) {
        this.qualityusername = qualityusername;
    }

    public java.util.Date getQualitydate() {
        return qualitydate;
    }

    public void setQualitydate(java.util.Date qualitydate) {
        this.qualitydate = qualitydate;
    }

    public String getBackupbefore() {
        return backupbefore;
    }

    public void setBackupbefore(String backupbefore) {
        this.backupbefore = backupbefore;
    }

    public String getCertuser() {
        return certuser;
    }

    public void setCertuser(String certuser) {
        this.certuser = certuser;
    }

    public String getCertusername() {
        return certusername;
    }

    public void setCertusername(String certusername) {
        this.certusername = certusername;
    }

    public java.util.Date getCertdate() {
        return certdate;
    }

    public void setCertdate(java.util.Date certdate) {
        this.certdate = certdate;
    }

    public String getBackuprecord() {
        return backuprecord;
    }

    public void setBackuprecord(String backuprecord) {
        this.backuprecord = backuprecord;
    }

    public String getSealuser() {
        return sealuser;
    }

    public void setSealuser(String sealuser) {
        this.sealuser = sealuser;
    }

    public String getSealusername() {
        return sealusername;
    }

    public void setSealusername(String sealusername) {
        this.sealusername = sealusername;
    }

    public java.util.Date getSealdate() {
        return sealdate;
    }

    public void setSealdate(java.util.Date sealdate) {
        this.sealdate = sealdate;
    }

    public java.util.Date getBackupdate() {
        return backupdate;
    }

    public void setBackupdate(java.util.Date backupdate) {
        this.backupdate = backupdate;
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

    public SelectBean<SampleBackup> getItem() {
        if (item == null)
            item = new SelectBean<SampleBackup>();

        return item;
    }

    public void setItem(SelectBean<SampleBackup> item) {
        this.item = item;
    }

}
