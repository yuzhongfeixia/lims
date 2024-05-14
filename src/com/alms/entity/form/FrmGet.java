package com.alms.entity.form;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.enums.DataAction;
import com.gpersist.utils.*;

public class FrmGet implements BaseBean {

    // 表单编号;
    private String formid;

    private String tranid;

    // 表单名称;
    private String formname;

    // 样品编号;
    private String sampleid;

    // 样品编号;
    private String samplecode;

    // 样品名称;
    private String samplename;

    // 检测类型;
    private String gettype;

    // 检测类型名称;
    private String gettypename;

    // 长度隔断;
    private int formlength;

    // 宽度隔断;
    private int formwidth;

    // 表单方向;
    private String formdirect;

    // 表单方向名称;
    private String formdirectname;

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

    // 分页组数;
    private int pagegroup;

    // 当前页数;
    private int pagecount;

    private int pagespec;

    private int pagewidth;

    private String methodcode;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FrmGet> item;

    public FrmGet() {
        this.OnInit();
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        msg.setErrmsg("");
        boolean rtn = false;

        if (ToolUtils.StringIsEmpty(this.getFormid())) {
            msg.setErrmsg("表单编号不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getDeal().getAction() == DataAction.Deal.getAction())
                || (this.getDeal().getAction() == DataAction.Delete.getAction()))
            return rtn;

        if (ToolUtils.StringIsEmpty(this.getFormname())) {
            msg.setErrmsg("表单名称不能为空！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        // if (ToolUtils.CheckComboValue(this.getGettype()))
        // {
        // msg.setErrmsg("请选择检测类型！" + ToolUtils.GetNewLines());
        // rtn = true;
        // }

        if (ToolUtils.CheckComboValue(this.getFormdirect())) {
            msg.setErrmsg("请选择表单方向！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        // if (ToolUtils.CheckComboValue(this.getSampleid()))
        // {
        // msg.setErrmsg("请选择检测样品！" + ToolUtils.GetNewLines());
        // rtn = true;
        // }

        if (this.getFormwidth() <= 0) {
            msg.setErrmsg("宽度隔断必须大于0！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (this.getFormlength() <= 0) {
            msg.setErrmsg("长度隔断必须大于0！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (this.getPagegroup() < 0) {
            msg.setErrmsg("分页组数必须大于等于0！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if (this.getPagespec() < 0) {
            msg.setErrmsg("分页试件数必须大于等于0！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getPagegroup() > 0) && (this.getPagespec() > 0)) {
            msg.setErrmsg("当分页组数大于0时分页试件数必须为0！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        if ((this.getPagegroup() == 0) && (this.getPagespec() <= 0)) {
            msg.setErrmsg("当分页组数等于0时分页试件数必须大于0！" + ToolUtils.GetNewLines());
            rtn = true;
        }

        return rtn;
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((FrmGet) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "表单编号:formid", "当前页数:pagecount", "表单名称:formname", "样品编号:sampleid", "检测类型:testtype",
                "长度隔断:formlength", "宽度隔断:formwidth", "表单方向:formdirect", "方法值:methodcode", "分页试件数:pagespec",
                "分页组数:pagegroup", "分页隔断:pagewidth", "业务编号:tranid", "样品编号:samplecode" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.formid = "";
        this.tranid = "";
        this.formname = "";
        this.sampleid = "";
        this.samplename = "";
        this.gettype = "";
        this.gettypename = "";
        this.formlength = 0;
        this.formwidth = 0;
        this.pagecount = 0;
        this.formdirect = "";
        this.formdirectname = "";
        this.createdate = ToolUtils.GetMinDate();
        this.createuser = "";
        this.createusername = "";
        this.modifydate = ToolUtils.GetMinDate();
        this.modifyuser = "";
        this.modifyusername = "";
        this.modiyserial = 0;
        this.pagegroup = 0;
        this.pagewidth = 0;
        this.methodcode = "";
        this.pagespec = 0;
        this.samplecode = "";
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public int getPagespec() {
        return pagespec;
    }

    public void setPagespec(int pagespec) {
        this.pagespec = pagespec;
    }

    public String getMethodcode() {
        return methodcode;
    }

    public void setMethodcode(String methodcode) {
        this.methodcode = methodcode;
    }

    public int getPagewidth() {
        return pagewidth;
    }

    public void setPagewidth(int pagewidth) {
        this.pagewidth = pagewidth;
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

    public int getPagegroup() {
        return pagegroup;
    }

    public void setPagegroup(int pagegroup) {
        this.pagegroup = pagegroup;
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

    public SelectBean<FrmGet> getItem() {
        if (item == null)
            item = new SelectBean<FrmGet>();

        return item;
    }

    public void setItem(SelectBean<FrmGet> item) {
        this.item = item;
    }

}
