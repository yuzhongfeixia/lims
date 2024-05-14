package com.alms.entity.form;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class FrmReport implements BaseBean {

    // 表单编号;
    private String formid;

    // 表单名称;
    private String formname;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 检测类型;
    private String gettype;

    // 检测依据数据类型名称;
    private String gettypename;

    // 长度隔断;
    private int formlength;

    // 宽度隔断;
    private int formwidth;

    // 表单方向;
    private String formdirect;

    // 表单方向名称;
    private String formdirectname;

    // 分页组数;
    private int pagegroup;

    // 分页试件数;
    private int pagespec;

    // 分页隔断;
    private int pagewidth;

    // 建立日期;
    private java.util.Date createdate;

    // 建立人;
    private String createuser;

    // 建立人姓名;
    private String createusername;

    // 修改日期;
    private java.util.Date modifydate;

    // 修改人;
    private String modifyuser;

    // 修改人名称;
    private String modifyusername;

    // 当前序号;
    private int modiyserial;

    // 方法值;
    private String methodcode;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FrmReport> item;

    public FrmReport() {
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
        return ToolUtils.CompareProperty((FrmReport) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "表单编号:formid", "表单名称:formname", "样品编号:sampleid", "样品名称:samplename", "检测类型:testtype",
                "检测依据数据类型名称:testtypename", "长度隔断:formlength", "宽度隔断:formwidth", "表单方向:formdirect",
                "表单方向名称:formdirectname", "分页组数:pagegroup", "分页试件数:pagespec", "分页隔断:pagewidth", "建立日期:createdate",
                "建立人:createuser", "建立人姓名:createusername", "修改日期:modifydate", "修改人:modifyuser", "修改人名称:modifyusername",
                "当前序号:modiyserial", "方法值:methodcode" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.formid = "";
        this.formname = "";
        this.sampleid = "";
        this.samplename = "";
        this.gettype = "";
        this.gettypename = "";
        this.formlength = 0;
        this.formwidth = 0;
        this.formdirect = "";
        this.formdirectname = "";
        this.pagegroup = 0;
        this.pagespec = 0;
        this.pagewidth = 0;
        this.createdate = ToolUtils.GetMinDate();
        this.createuser = "";
        this.createusername = "";
        this.modifydate = ToolUtils.GetMinDate();
        this.modifyuser = "";
        this.modifyusername = "";
        this.modiyserial = 0;
        this.methodcode = "";
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
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

    public String getGettype() {
        return gettype;
    }

    public void setGettype(String gettype) {
        this.gettype = gettype;
    }

    public String getGettypename() {
        return gettypename;
    }

    public void setGettypename(String gettypename) {
        this.gettypename = gettypename;
    }

    public int getFormlength() {
        return formlength;
    }

    public void setFormlength(int formlength) {
        this.formlength = formlength;
    }

    public int getFormwidth() {
        return formwidth;
    }

    public void setFormwidth(int formwidth) {
        this.formwidth = formwidth;
    }

    public String getFormdirect() {
        return formdirect;
    }

    public void setFormdirect(String formdirect) {
        this.formdirect = formdirect;
    }

    public String getFormdirectname() {
        return formdirectname;
    }

    public void setFormdirectname(String formdirectname) {
        this.formdirectname = formdirectname;
    }

    public int getPagegroup() {
        return pagegroup;
    }

    public void setPagegroup(int pagegroup) {
        this.pagegroup = pagegroup;
    }

    public int getPagespec() {
        return pagespec;
    }

    public void setPagespec(int pagespec) {
        this.pagespec = pagespec;
    }

    public int getPagewidth() {
        return pagewidth;
    }

    public void setPagewidth(int pagewidth) {
        this.pagewidth = pagewidth;
    }

    public java.util.Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(java.util.Date createdate) {
        this.createdate = createdate;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }

    public java.util.Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(java.util.Date modifydate) {
        this.modifydate = modifydate;
    }

    public String getModifyuser() {
        return modifyuser;
    }

    public void setModifyuser(String modifyuser) {
        this.modifyuser = modifyuser;
    }

    public String getModifyusername() {
        return modifyusername;
    }

    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername;
    }

    public int getModiyserial() {
        return modiyserial;
    }

    public void setModiyserial(int modiyserial) {
        this.modiyserial = modiyserial;
    }

    public String getMethodcode() {
        return methodcode;
    }

    public void setMethodcode(String methodcode) {
        this.methodcode = methodcode;
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

    public SelectBean<FrmReport> getItem() {
        if (item == null)
            item = new SelectBean<FrmReport>();

        return item;
    }

    public void setItem(SelectBean<FrmReport> item) {
        this.item = item;
    }

}
