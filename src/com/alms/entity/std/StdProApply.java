package com.alms.entity.std;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StdProApply implements BaseBean {

    // 业务编号;
    private String tranid;

    // 新增项目名称;
    private String projname;

    // 提议人;
    private String propuser;

    // 提议人姓名;
    private String propusername;

    // 提议日期;
    private java.util.Date propdate;

    // 目的意义;
    private String meaning;

    // 市场需求和前景;
    private String marketreq;

    // 新增项目的技术和资源要求(含方法、标准等);
    private String techreq;

    // 本中心资源状况及保证程度;
    private String guaran;

    // 经济分析;
    private String ecoanalyze;

    // 相关部门意见;
    private String reledepart;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 中心负责人意见;
    private String centadvice;

    // 批准人;
    private String approuser;

    // 批准人姓名;
    private String approusername;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 创建时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StdProApply> item;

    public StdProApply() {
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
        return ToolUtils.CompareProperty((StdProApply) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "新增项目名称:projname", "提议人:propuser", "提议人姓名:propusername", "提议日期:propdate",
                "目的意义:meaning", "市场需求和前景:marketreq", "新增项目的技术和资源要求(含方法、标准等):techreq", "本中心资源状况及保证程度:guaran",
                "经济分析:ecoanalyze", "相关部门意见:reledepart", "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus",
                "业务状态名称:flowstatusname", "中心负责人意见:centadvice", "批准人:approuser", "批准人姓名:approusername", "业务员:tranuser",
                "业务员姓名:tranusername", "创建时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.projname = "";
        this.propuser = "";
        this.propusername = "";
        this.propdate = ToolUtils.GetMinDate();
        this.meaning = "";
        this.marketreq = "";
        this.techreq = "";
        this.guaran = "";
        this.ecoanalyze = "";
        this.reledepart = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.centadvice = "";
        this.approuser = "";
        this.approusername = "";
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

    public String getProjname() {
        return projname;
    }

    public void setProjname(String projname) {
        this.projname = projname;
    }

    public String getPropuser() {
        return propuser;
    }

    public void setPropuser(String propuser) {
        this.propuser = propuser;
    }

    public String getPropusername() {
        return propusername;
    }

    public void setPropusername(String propusername) {
        this.propusername = propusername;
    }

    public java.util.Date getPropdate() {
        return propdate;
    }

    public void setPropdate(java.util.Date propdate) {
        this.propdate = propdate;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getMarketreq() {
        return marketreq;
    }

    public void setMarketreq(String marketreq) {
        this.marketreq = marketreq;
    }

    public String getTechreq() {
        return techreq;
    }

    public void setTechreq(String techreq) {
        this.techreq = techreq;
    }

    public String getGuaran() {
        return guaran;
    }

    public void setGuaran(String guaran) {
        this.guaran = guaran;
    }

    public String getEcoanalyze() {
        return ecoanalyze;
    }

    public void setEcoanalyze(String ecoanalyze) {
        this.ecoanalyze = ecoanalyze;
    }

    public String getReledepart() {
        return reledepart;
    }

    public void setReledepart(String reledepart) {
        this.reledepart = reledepart;
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

    public String getCentadvice() {
        return centadvice;
    }

    public void setCentadvice(String centadvice) {
        this.centadvice = centadvice;
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

    public SelectBean<StdProApply> getItem() {
        if (item == null)
            item = new SelectBean<StdProApply>();

        return item;
    }

    public void setItem(SelectBean<StdProApply> item) {
        this.item = item;
    }

}
