package com.alms.entity.inc;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class IncTestEnv implements BaseBean {

    // 业务编号;
    private String tranid;

    // 检测参数;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 检测室编号
    private String labid;

    // 检测室;
    private String labname;

    // 房号;
    private String labnum;

    // 检测环境指标;
    private String envindica;

    // 设施与措施;
    private String facmeasure;

    // 验收记录编号;
    private String acceptnum;

    // 记录人;
    private String tranuser;

    // 记录人姓名;
    private String tranusername;

    // 创建日期;
    private java.util.Date trandate;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<IncTestEnv> item;

    public IncTestEnv() {
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
        return ToolUtils.CompareProperty((IncTestEnv) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "检测参数:parameterid", "检测参数名称:parametername", "检测室:labname", "房号:labnum",
                "检测环境指标:envindica", "设施与措施:facmeasure", "验收记录编号:acceptnum", "记录人:tranuser", "记录人姓名:tranusername",
                "创建日期:trandate" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.parameterid = "";
        this.parametername = "";
        this.labname = "";
        this.labnum = "";
        this.envindica = "";
        this.facmeasure = "";
        this.acceptnum = "";
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

    public String getLabid() {
        return labid;
    }

    public void setLabid(String labid) {
        this.labid = labid;
    }

    public String getLabname() {
        return labname;
    }

    public void setLabname(String labname) {
        this.labname = labname;
    }

    public String getLabnum() {
        return labnum;
    }

    public void setLabnum(String labnum) {
        this.labnum = labnum;
    }

    public String getEnvindica() {
        return envindica;
    }

    public void setEnvindica(String envindica) {
        this.envindica = envindica;
    }

    public String getFacmeasure() {
        return facmeasure;
    }

    public void setFacmeasure(String facmeasure) {
        this.facmeasure = facmeasure;
    }

    public String getAcceptnum() {
        return acceptnum;
    }

    public void setAcceptnum(String acceptnum) {
        this.acceptnum = acceptnum;
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

    public SelectBean<IncTestEnv> getItem() {
        if (item == null)
            item = new SelectBean<IncTestEnv>();

        return item;
    }

    public void setItem(SelectBean<IncTestEnv> item) {
        this.item = item;
    }

}
