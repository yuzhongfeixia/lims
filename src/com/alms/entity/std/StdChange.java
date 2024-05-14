package com.alms.entity.std;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StdChange implements BaseBean {

    // 业务编号;
    private String tranid;

    // 关联标准变更编号
    private String changeid;

    // 序号
    private int serial;

    // 检测产品;
    private String sampleid;

    // 产品名称;
    private String samplename;

    // 检测参数;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 已批准的标准代号;
    private String stdid;

    // 已批准的标准名称;
    private String stdname;

    // 判定依据限制范围;
    private String judgestandardrange;
    // 判定依据说明;
    private String judgestandardexplain;

    // 变更后的标准代号;
    private String replstdid;

    // 变更后的标准名称;
    private String replstdname;

    // 变更内容;
    private String changecontent;

    // 能力确认方式;
    private String sureability;

    // 能力确认方式名称;
    private String sureabilityname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 标准跟踪人;
    private String stduser;

    // 标准跟踪人姓名;
    private String stdusername;

    // 跟踪人时间;
    private java.util.Date stddate;

    // 批准人;
    private String approuser;

    // 批准人姓名;
    private String approusername;

    // 批准日期;
    private java.util.Date approdate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 创建时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StdChange> item;

    public StdChange() {
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
        return ToolUtils.CompareProperty((StdChange) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "检测产品:sampleid", "产品名称:samplename", "检测参数:parameterid",
                "检测参数名称:parametername", "判定依据说明:judgestandardexplain", "判定依据范围:judgestandardrange", "已批准的标准代号:stdid",
                "已批准的标准名称:stdname", "变更后的标准代号:replstdid", "变更后的标准名称:replstdname", "变更内容:changecontent",
                "能力确认方式:sureability", "能力确认方式名称:sureabilityname", "业务状态:flowstatus", "业务状态名称:flowstatusname",
                "业务动作:flowaction", "业务动作名称:flowactionname", "标准跟踪人:stduser", "标准跟踪人姓名:stdusername", "跟踪人时间:stddate",
                "批准人:approuser", "批准人姓名:approusername", "批准日期:approdate", "业务员:tranuser", "业务员姓名:tranusername",
                "创建时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.changeid = "";
        this.sampleid = "";
        this.samplename = "";
        this.parameterid = "";
        this.parametername = "";
        this.stdid = "";
        this.stdname = "";
        this.replstdid = "";
        this.replstdname = "";
        this.judgestandardexplain = "";
        this.judgestandardrange = "";
        this.changecontent = "";
        this.sureability = "";
        this.sureabilityname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.stduser = "";
        this.stdusername = "";
        this.stddate = ToolUtils.GetMinDate();
        this.approuser = "";
        this.approusername = "";
        this.approdate = ToolUtils.GetMinDate();
        this.tranuser = "";
        this.tranusername = "";
        this.trandate = ToolUtils.GetMinDate();
    }

    public String getSureabilityname() {
        return sureabilityname;
    }

    public void setSureabilityname(String sureabilityname) {
        this.sureabilityname = sureabilityname;
    }

    public String getJudgestandardrange() {
        return judgestandardrange;
    }

    public void setJudgestandardrange(String judgestandardrange) {
        this.judgestandardrange = judgestandardrange;
    }

    public String getJudgestandardexplain() {
        return judgestandardexplain;
    }

    public void setJudgestandardexplain(String judgestandardexplain) {
        this.judgestandardexplain = judgestandardexplain;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getChangeid() {
        return changeid;
    }

    public void setChangeid(String changeid) {
        this.changeid = changeid;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getParameterid() {
        return parameterid;
    }

    public void setParameterid(String parameterid) {
        this.parameterid = parameterid;
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
    }

    public String getStdid() {
        return stdid;
    }

    public void setStdid(String stdid) {
        this.stdid = stdid;
    }

    public String getStdname() {
        return stdname;
    }

    public void setStdname(String stdname) {
        this.stdname = stdname;
    }

    public String getReplstdid() {
        return replstdid;
    }

    public void setReplstdid(String replstdid) {
        this.replstdid = replstdid;
    }

    public String getReplstdname() {
        return replstdname;
    }

    public void setReplstdname(String replstdname) {
        this.replstdname = replstdname;
    }

    public String getChangecontent() {
        return changecontent;
    }

    public void setChangecontent(String changecontent) {
        this.changecontent = changecontent;
    }

    public String getSureability() {
        return sureability;
    }

    public void setSureability(String sureability) {
        this.sureability = sureability;
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

    public String getStduser() {
        return stduser;
    }

    public void setStduser(String stduser) {
        this.stduser = stduser;
    }

    public String getStdusername() {
        return stdusername;
    }

    public void setStdusername(String stdusername) {
        this.stdusername = stdusername;
    }

    public java.util.Date getStddate() {
        return stddate;
    }

    public void setStddate(java.util.Date stddate) {
        this.stddate = stddate;
    }

    public String getApprouser() {
        return approuser;
    }

    public void setApprouser(String approuser) {
        this.approuser = approuser;
    }

    public String getApprousername() {
        return approusername;
    }

    public void setApprousername(String approusername) {
        this.approusername = approusername;
    }

    public java.util.Date getApprodate() {
        return approdate;
    }

    public void setApprodate(java.util.Date approdate) {
        this.approdate = approdate;
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

    public SelectBean<StdChange> getItem() {
        if (item == null)
            item = new SelectBean<StdChange>();

        return item;
    }

    public void setItem(SelectBean<StdChange> item) {
        this.item = item;
    }

}
