package com.alms.entity.std;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StdTestSure implements BaseBean {

    // 业务编号;
    private String tranid;

    // 新增项目名称;
    private String projname;

    // 检测人;
    private String testuser;

    // 检测人姓名;
    private String testusername;

    // 负责人;
    private String respuser;

    // 负责人姓名;
    private String respusername;

    // 质量文件运行情况检查;
    private String qualfile;

    // 环境设施条件检查;
    private String envfacility;

    // 仪器设备满足情况检查;
    private String devsatisfy;

    // 业务动作;
    private String flowaction;

    // 业务动作名称;
    private String flowactionname;

    // 业务状态;
    private String flowstatus;

    // 业务状态名称;
    private String flowstatusname;

    // 过程监督意见;
    private String procmonitor;

    // 质量监督员;
    private String qualuser;

    // 质量监督员姓名;
    private String qualusername;

    // 质量监督员日期;
    private java.util.Date qualdate;

    // 结论;
    private String result;

    // 技术负责人;
    private String techuser;

    // 技术负责人姓名;
    private String techusername;

    // 技术负责人时间;
    private java.util.Date techdate;

    // 中心负责人意见;
    private String centuseradv;

    // 中心负责人;
    private String centuser;

    // 中心负责人姓名;
    private String centusername;

    // 中心负责人时间;
    private java.util.Date centdate;

    // 业务员;
    private String tranuser;

    // 业务员姓名;
    private String tranusername;

    // 创建时间;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StdTestSure> item;

    public StdTestSure() {
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
        return ToolUtils.CompareProperty((StdTestSure) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "新增项目名称:projname", "检测人:testuser", "检测人姓名:testusername", "负责人:respuser",
                "负责人姓名:respusername", "质量文件运行情况检查:qualfile", "环境设施条件检查:envfacility", "仪器设备满足情况检查:devsatisfy",
                "业务动作:flowaction", "业务动作名称:flowactionname", "业务状态:flowstatus", "业务状态名称:flowstatusname",
                "过程监督意见:procmonitor", "质量监督员:qualuser", "质量监督员姓名:qualusername", "质量监督员日期:qualdate", "结论:result",
                "技术负责人:techuser", "技术负责人姓名:techusername", "技术负责人时间:techdate", "中心负责人意见:centuseradv", "中心负责人:centuser",
                "中心负责人姓名:centusername", "中心负责人时间:centdate", "业务员:tranuser", "业务员姓名:tranusername", "创建时间:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.projname = "";
        this.testuser = "";
        this.testusername = "";
        this.respuser = "";
        this.respusername = "";
        this.qualfile = "";
        this.envfacility = "";
        this.devsatisfy = "";
        this.flowaction = "";
        this.flowactionname = "";
        this.flowstatus = "";
        this.flowstatusname = "";
        this.procmonitor = "";
        this.qualuser = "";
        this.qualusername = "";
        this.qualdate = ToolUtils.GetMinDate();
        this.result = "";
        this.techuser = "";
        this.techusername = "";
        this.techdate = ToolUtils.GetMinDate();
        this.centuseradv = "";
        this.centuser = "";
        this.centusername = "";
        this.centdate = ToolUtils.GetMinDate();
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

    public String getTestuser() {
        return testuser;
    }

    public void setTestuser(String testuser) {
        this.testuser = testuser;
    }

    public String getTestusername() {
        return testusername;
    }

    public void setTestusername(String testusername) {
        this.testusername = testusername;
    }

    public String getRespuser() {
        return respuser;
    }

    public void setRespuser(String respuser) {
        this.respuser = respuser;
    }

    public String getRespusername() {
        return respusername;
    }

    public void setRespusername(String respusername) {
        this.respusername = respusername;
    }

    public String getQualfile() {
        return qualfile;
    }

    public void setQualfile(String qualfile) {
        this.qualfile = qualfile;
    }

    public String getEnvfacility() {
        return envfacility;
    }

    public void setEnvfacility(String envfacility) {
        this.envfacility = envfacility;
    }

    public String getDevsatisfy() {
        return devsatisfy;
    }

    public void setDevsatisfy(String devsatisfy) {
        this.devsatisfy = devsatisfy;
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

    public String getProcmonitor() {
        return procmonitor;
    }

    public void setProcmonitor(String procmonitor) {
        this.procmonitor = procmonitor;
    }

    public String getQualuser() {
        return qualuser;
    }

    public void setQualuser(String qualuser) {
        this.qualuser = qualuser;
    }

    public String getQualusername() {
        return qualusername;
    }

    public void setQualusername(String qualusername) {
        this.qualusername = qualusername;
    }

    public java.util.Date getQualdate() {
        return qualdate;
    }

    public void setQualdate(java.util.Date qualdate) {
        this.qualdate = qualdate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTechuser() {
        return techuser;
    }

    public void setTechuser(String techuser) {
        this.techuser = techuser;
    }

    public String getTechusername() {
        return techusername;
    }

    public void setTechusername(String techusername) {
        this.techusername = techusername;
    }

    public java.util.Date getTechdate() {
        return techdate;
    }

    public void setTechdate(java.util.Date techdate) {
        this.techdate = techdate;
    }

    public String getCentuseradv() {
        return centuseradv;
    }

    public void setCentuseradv(String centuseradv) {
        this.centuseradv = centuseradv;
    }

    public String getCentuser() {
        return centuser;
    }

    public void setCentuser(String centuser) {
        this.centuser = centuser;
    }

    public String getCentusername() {
        return centusername;
    }

    public void setCentusername(String centusername) {
        this.centusername = centusername;
    }

    public java.util.Date getCentdate() {
        return centdate;
    }

    public void setCentdate(java.util.Date centdate) {
        this.centdate = centdate;
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

    public SelectBean<StdTestSure> getItem() {
        if (item == null)
            item = new SelectBean<StdTestSure>();

        return item;
    }

    public void setItem(SelectBean<StdTestSure> item) {
        this.item = item;
    }

}
