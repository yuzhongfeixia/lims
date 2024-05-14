package com.alms.entity.lab;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BusGetDetail implements BaseBean {

    // 业务编号（抽样单号）;
    private String tranid;

    private String samplecode;

    // 样品编号;
    private String sampleid;

    // 抽样类型;
    private String gettype;

    // 样品主类编号;
    private String samplemain;

    // 样品名称;
    private String samplename;

    // 规格型号;
    private String samplestand;

    private String sampletype;

    // 生产日期或批号;
    private String prdcode;

    // 抽样地点;
    private String getaddr;

    // 样本大小;
    private String samplesize;

    // 样品基数;
    private String samplebase;

    // 商标;
    private String trademark;

    // 批量;
    private String samplecount;

    // 生产日期;
    private java.util.Date prddate;

    // 样品来源;
    private String samplesource;

    // 封样状态;
    private String sealedstatus;

    // 采样部位;
    private String getbody;

    // 保存条件;
    private String storetype;

    // 保存条件名称;
    private String storetypename;

    // 抽样方式;
    private String getmethod;

    // 标准号;
    private String standcode;

    // 登记证号;
    private String regcode;

    // 准产证号;
    private String allowcode;

    // 生产厂家;
    private String factname;

    // 通讯地址;
    private String factaddr;

    // 邮编;
    private String factpost;

    // 电话;
    private String facttele;

    // 传真;
    private String factfax;

    // 证书编号;
    private String certcode;

    // 换证日期;
    private java.util.Date certdate;

    // 抽检产品生产规模;
    private String prdscale;

    // 抽检产品年总产量;
    private String prdtotal;

    // 样品状态;
    private String samplestatus;

    // 产品执行标准;
    private String samplerule;

    // 保存要求;
    private String keepwarm;

    // 保存要求名称;
    private String keepwarmname;

    // 生产单位法人代表;
    private String factlegal;

    // 可追溯标识;
    private String samplesign;

    // 单位性质;
    private String unitcharacte;

    // 单位性质名称;
    private String unitcharactename;

    // E-mail;
    private String factemail;

    // 保质期;
    private String keeptime;

    // 包装方式;
    private String packmethod;

    // 抽样方法;
    private String samplemethod;

    // 采样部位;
    private String getPosition;

    // 同类多种产品;
    private String similarvariety;

    // 同类多种产品名称;
    private String similarvarietyname;

    // 包装;
    private String packstatus;

    // 包装名称;
    private String packstatusname;

    // 标识;
    private String markstatus;

    // 标识名称;
    private String markstatusname;

    // 等级;
    private String samplelevel;

    // 产品认证情况;
    private String certstatus;

    // 认证名称;
    private String certstatusname;

    // 抽样场所;
    private String getsource;

    // 抽样场所名称;
    private String getsourcename;

    // 厂家联系人;
    private String factlink;

    // 检测项目;
    private String testitems;

    private String remark;

    private String parameterids;

    private String parameternames;

    private String mainstandname;

    private String testprop;

    private String senduser;

    private java.util.Date senddate;

    private String standtype1;

    private String standtype2;

    private String standtype3;

    private String standtype4;

    private String standtype5;

    private String taskid;

    private String testtype;

    private String tranidcn;

    private String gpsnum;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<BusGetDetail> item;

    public BusGetDetail() {
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
        return ToolUtils.CompareProperty((BusGetDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号（抽样单号）:tranid", "编号:tranidcn", "GPS:gpsnum", "检测类型:testtype", "样品编号:sampleid",
                "检测项目:testitems", "抽样类型:gettype", "样品主类编号:samplemain", "样品名称:samplename", "规格型号:samplestand",
                "生产批号:prdcode", "抽样地点:getaddr", "样本大小:samplesize", "样本基数:samplebase", "商标:trademark", "批量:samplecount",
                "生产日期:prddate", "样品来源:samplesource", "封样状态:sealedstatus", "采样部位:getbody", "保存条件:storetype",
                "保存条件名称:storetypename", "抽样方式:getmethod", "标准号:standcode", "登记证号:regcode", "准产证号:allowcode",
                "生产厂家:factname", "通讯地址:factaddr", "邮编:factpost", "电话:facttele", "传真:factfax", "证书编号:certcode",
                "换证日期:certdate", "抽检产品生产规模:prdscale", "抽检产品年总产量:prdtotal", "样品状态:samplestatus", "包装:packstatus",
                "包装名称:packstatusname", "标识:markstatus", "标识名称:markstatusname", "等级:samplelevel", "产品认证情况:certstatus",
                "认证名称:certstatusname", "抽样场所:getsource", "抽样场所名称:getsourcename", "产品执行标准:samplerule", "保质期:keeptime",
                "E-mail:factemail", "单位性质:unitcharacte", "同类多种产品名称:similarvarietyname", "保存要求名称:keepwarmname",
                "单位性质名称:unitcharactename", "保存要求:keepwarm", "生产单位法人代表:factlegal", "可追溯标识:samplesign",
                "同类多种产品:similarvariety", "采样部位:getPosition", "抽样方法:samplemethod", "厂家联系人:factlink" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.tranidcn = "";
        this.samplecode = "";
        this.gettype = "";
        this.samplemain = "";
        this.sampleid = "";
        this.samplename = "";
        this.samplestand = "";
        this.prdcode = "";
        this.getaddr = "";
        this.samplesize = "";
        this.samplebase = "";
        this.trademark = "";
        this.similarvarietyname = "";
        this.keepwarmname = "";
        this.unitcharactename = "";
        this.samplecount = "";
        this.prddate = ToolUtils.GetMinDate();
        this.samplesource = "";
        this.sealedstatus = "";
        this.getbody = "";
        this.storetype = "";
        this.storetypename = "";
        this.getmethod = "";
        this.standcode = "";
        this.regcode = "";
        this.allowcode = "";
        this.factname = "";
        this.factaddr = "";
        this.factpost = "";
        this.facttele = "";
        this.factfax = "";
        this.certcode = "";
        this.certdate = ToolUtils.GetMinDate();
        this.prdscale = "";
        this.prdtotal = "";
        this.testitems = "";
        this.samplestatus = "";
        this.packstatus = "";
        this.packstatusname = "";
        this.markstatus = "";
        this.markstatusname = "";
        this.samplelevel = "";
        this.certstatus = "";
        this.certstatusname = "";
        this.getsource = "";
        this.getsourcename = "";
        this.factlink = "";
        this.remark = "";
        this.parameterids = "";
        this.parameternames = "";
        this.mainstandname = "";
        this.testprop = "";
        this.senduser = "";
        this.senddate = null;
        this.standtype1 = "";
        this.standtype2 = "";
        this.standtype3 = "";
        this.standtype4 = "";
        this.standtype5 = "";
        this.testtype = "";
        this.gpsnum = "";
        this.samplerule = "";
        this.keepwarm = "";
        this.factlegal = "";
        this.samplesign = "";
        this.factemail = "";
        this.keeptime = "";
        this.packmethod = "";
        this.samplemethod = "";
        this.getPosition = "";
        this.similarvariety = "";
        this.unitcharacte = "";

    }

    public String getKeepwarmname() {
        return keepwarmname;
    }

    public void setKeepwarmname(String keepwarmname) {
        this.keepwarmname = keepwarmname;
    }

    public String getUnitcharactename() {
        return unitcharactename;
    }

    public void setUnitcharactename(String unitcharactename) {
        this.unitcharactename = unitcharactename;
    }

    public String getSimilarvarietyname() {
        return similarvarietyname;
    }

    public void setSimilarvarietyname(String similarvarietyname) {
        this.similarvarietyname = similarvarietyname;
    }

    public String getUnitcharacte() {
        return unitcharacte;
    }

    public void setUnitcharacte(String unitcharacte) {
        this.unitcharacte = unitcharacte;
    }

    public String getSamplerule() {
        return samplerule;
    }

    public void setSamplerule(String samplerule) {
        this.samplerule = samplerule;
    }

    public String getKeepwarm() {
        return keepwarm;
    }

    public void setKeepwarm(String keepwarm) {
        this.keepwarm = keepwarm;
    }

    public String getFactlegal() {
        return factlegal;
    }

    public void setFactlegal(String factlegal) {
        this.factlegal = factlegal;
    }

    public String getSamplesign() {
        return samplesign;
    }

    public void setSamplesign(String samplesign) {
        this.samplesign = samplesign;
    }

    public String getFactemail() {
        return factemail;
    }

    public void setFactemail(String factemail) {
        this.factemail = factemail;
    }

    public String getKeeptime() {
        return keeptime;
    }

    public void setKeeptime(String keeptime) {
        this.keeptime = keeptime;
    }

    public String getPackmethod() {
        return packmethod;
    }

    public void setPackmethod(String packmethod) {
        this.packmethod = packmethod;
    }

    public String getSamplemethod() {
        return samplemethod;
    }

    public void setSamplemethod(String samplemethod) {
        this.samplemethod = samplemethod;
    }

    public String getGetPosition() {
        return getPosition;
    }

    public void setGetPosition(String getPosition) {
        this.getPosition = getPosition;
    }

    public String getSimilarvariety() {
        return similarvariety;
    }

    public void setSimilarvariety(String similarvariety) {
        this.similarvariety = similarvariety;
    }

    public String getGpsnum() {
        return gpsnum;
    }

    public void setGpsnum(String gpsnum) {
        this.gpsnum = gpsnum;
    }

    public String getTranidcn() {
        return tranidcn;
    }

    public void setTranidcn(String tranidcn) {
        this.tranidcn = tranidcn;
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

    public String getTestitems() {
        return testitems;
    }

    public void setTestitems(String testitems) {
        this.testitems = testitems;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getSampletype() {
        return sampletype;
    }

    public void setSampletype(String sampletype) {
        this.sampletype = sampletype;
    }

    public String getGettype() {
        return gettype;
    }

    public void setGettype(String gettype) {
        this.gettype = gettype;
    }

    public String getSamplemain() {
        return samplemain;
    }

    public void setSamplemain(String samplemain) {
        this.samplemain = samplemain;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
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

    public String getSamplestand() {
        return samplestand;
    }

    public void setSamplestand(String samplestand) {
        this.samplestand = samplestand;
    }

    public String getPrdcode() {
        return prdcode;
    }

    public void setPrdcode(String prdcode) {
        this.prdcode = prdcode;
    }

    public String getGetaddr() {
        return getaddr;
    }

    public void setGetaddr(String getaddr) {
        this.getaddr = getaddr;
    }

    public String getSamplesize() {
        return samplesize;
    }

    public void setSamplesize(String samplesize) {
        this.samplesize = samplesize;
    }

    public String getSamplebase() {
        return samplebase;
    }

    public void setSamplebase(String samplebase) {
        this.samplebase = samplebase;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getSamplecount() {
        return samplecount;
    }

    public void setSamplecount(String samplecount) {
        this.samplecount = samplecount;
    }

    public java.util.Date getPrddate() {
        return prddate;
    }

    public void setPrddate(java.util.Date prddate) {
        this.prddate = prddate;
    }

    public String getSamplesource() {
        return samplesource;
    }

    public void setSamplesource(String samplesource) {
        this.samplesource = samplesource;
    }

    public String getSealedstatus() {
        return sealedstatus;
    }

    public void setSealedstatus(String sealedstatus) {
        this.sealedstatus = sealedstatus;
    }

    public String getGetbody() {
        return getbody;
    }

    public void setGetbody(String getbody) {
        this.getbody = getbody;
    }

    public String getStoretype() {
        return storetype;
    }

    public void setStoretype(String storetype) {
        this.storetype = storetype;
    }

    public String getStoretypename() {
        return storetypename;
    }

    public void setStoretypename(String storetypename) {
        this.storetypename = storetypename;
    }

    public String getGetmethod() {
        return getmethod;
    }

    public void setGetmethod(String getmethod) {
        this.getmethod = getmethod;
    }

    public String getStandcode() {
        return standcode;
    }

    public void setStandcode(String standcode) {
        this.standcode = standcode;
    }

    public String getRegcode() {
        return regcode;
    }

    public void setRegcode(String regcode) {
        this.regcode = regcode;
    }

    public String getAllowcode() {
        return allowcode;
    }

    public void setAllowcode(String allowcode) {
        this.allowcode = allowcode;
    }

    public String getFactname() {
        return factname;
    }

    public void setFactname(String factname) {
        this.factname = factname;
    }

    public String getFactaddr() {
        return factaddr;
    }

    public void setFactaddr(String factaddr) {
        this.factaddr = factaddr;
    }

    public String getFactpost() {
        return factpost;
    }

    public void setFactpost(String factpost) {
        this.factpost = factpost;
    }

    public String getFacttele() {
        return facttele;
    }

    public void setFacttele(String facttele) {
        this.facttele = facttele;
    }

    public String getFactfax() {
        return factfax;
    }

    public void setFactfax(String factfax) {
        this.factfax = factfax;
    }

    public String getCertcode() {
        return certcode;
    }

    public void setCertcode(String certcode) {
        this.certcode = certcode;
    }

    public java.util.Date getCertdate() {
        return certdate;
    }

    public void setCertdate(java.util.Date certdate) {
        this.certdate = certdate;
    }

    public String getPrdscale() {
        return prdscale;
    }

    public void setPrdscale(String prdscale) {
        this.prdscale = prdscale;
    }

    public String getPrdtotal() {
        return prdtotal;
    }

    public void setPrdtotal(String prdtotal) {
        this.prdtotal = prdtotal;
    }

    public String getSamplestatus() {
        return samplestatus;
    }

    public void setSamplestatus(String samplestatus) {
        this.samplestatus = samplestatus;
    }

    public String getPackstatus() {
        return packstatus;
    }

    public void setPackstatus(String packstatus) {
        this.packstatus = packstatus;
    }

    public String getPackstatusname() {
        return packstatusname;
    }

    public void setPackstatusname(String packstatusname) {
        this.packstatusname = packstatusname;
    }

    public String getMarkstatus() {
        return markstatus;
    }

    public void setMarkstatus(String markstatus) {
        this.markstatus = markstatus;
    }

    public String getMarkstatusname() {
        return markstatusname;
    }

    public void setMarkstatusname(String markstatusname) {
        this.markstatusname = markstatusname;
    }

    public String getSamplelevel() {
        return samplelevel;
    }

    public void setSamplelevel(String samplelevel) {
        this.samplelevel = samplelevel;
    }

    public String getCertstatus() {
        return certstatus;
    }

    public void setCertstatus(String certstatus) {
        this.certstatus = certstatus;
    }

    public String getCertstatusname() {
        return certstatusname;
    }

    public void setCertstatusname(String certstatusname) {
        this.certstatusname = certstatusname;
    }

    public String getGetsource() {
        return getsource;
    }

    public void setGetsource(String getsource) {
        this.getsource = getsource;
    }

    public String getGetsourcename() {
        return getsourcename;
    }

    public void setGetsourcename(String getsourcename) {
        this.getsourcename = getsourcename;
    }

    public String getFactlink() {
        return factlink;
    }

    public void setFactlink(String factlink) {
        this.factlink = factlink;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParameterids() {
        return parameterids;
    }

    public void setParameterids(String parameterids) {
        this.parameterids = parameterids;
    }

    public String getParameternames() {
        return parameternames;
    }

    public void setParameternames(String parameternames) {
        this.parameternames = parameternames;
    }

    public String getMainstandname() {
        return mainstandname;
    }

    public void setMainstandname(String mainstandname) {
        this.mainstandname = mainstandname;
    }

    public String getTestprop() {
        return testprop;
    }

    public void setTestprop(String testprop) {
        this.testprop = testprop;
    }

    public String getSenduser() {
        return senduser;
    }

    public void setSenduser(String senduser) {
        this.senduser = senduser;
    }

    public java.util.Date getSenddate() {
        return senddate;
    }

    public void setSenddate(java.util.Date senddate) {
        this.senddate = senddate;
    }

    public String getStandtype1() {
        return standtype1;
    }

    public void setStandtype1(String standtype1) {
        this.standtype1 = standtype1;
    }

    public String getStandtype2() {
        return standtype2;
    }

    public void setStandtype2(String standtype2) {
        this.standtype2 = standtype2;
    }

    public String getStandtype3() {
        return standtype3;
    }

    public void setStandtype3(String standtype3) {
        this.standtype3 = standtype3;
    }

    public String getStandtype4() {
        return standtype4;
    }

    public void setStandtype4(String standtype4) {
        this.standtype4 = standtype4;
    }

    public String getStandtype5() {
        return standtype5;
    }

    public void setStandtype5(String standtype5) {
        this.standtype5 = standtype5;
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

    public SelectBean<BusGetDetail> getItem() {
        if (item == null)
            item = new SelectBean<BusGetDetail>();

        return item;
    }

    public void setItem(SelectBean<BusGetDetail> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "[tranid=" + tranid + ", samplecode=" + samplecode + ", sampleid=" + sampleid + ", gettype=" + gettype
                + ", samplemain=" + samplemain + ", samplename=" + samplename + ", samplestand=" + samplestand
                + ", sampletype=" + sampletype + ", prdcode=" + prdcode + ", getaddr=" + getaddr + ", samplesize="
                + samplesize + ", samplebase=" + samplebase + ", trademark=" + trademark + ", samplecount="
                + samplecount + ", prddate=" + prddate + ", samplesource=" + samplesource + ", sealedstatus="
                + sealedstatus + ", getbody=" + getbody + ", storetype=" + storetype + ", storetypename="
                + storetypename + ", getmethod=" + getmethod + ", standcode=" + standcode + ", regcode=" + regcode
                + ", allowcode=" + allowcode + ", factname=" + factname + ", factaddr=" + factaddr + ", factpost="
                + factpost + ", facttele=" + facttele + ", factfax=" + factfax + ", certcode=" + certcode
                + ", certdate=" + certdate + ", prdscale=" + prdscale + ", prdtotal=" + prdtotal + ", samplestatus="
                + samplestatus + ", samplerule=" + samplerule + ", keepwarm=" + keepwarm + ", keepwarmname="
                + keepwarmname + ", factlegal=" + factlegal + ", samplesign=" + samplesign + ", unitcharacte="
                + unitcharacte + ", unitcharactename=" + unitcharactename + ", factemail=" + factemail + ", keeptime="
                + keeptime + ", packmethod=" + packmethod + ", samplemethod=" + samplemethod + ", getPosition="
                + getPosition + ", similarvariety=" + similarvariety + ", similarvarietyname=" + similarvarietyname
                + ", packstatus=" + packstatus + ", packstatusname=" + packstatusname + ", markstatus=" + markstatus
                + ", markstatusname=" + markstatusname + ", samplelevel=" + samplelevel + ", certstatus=" + certstatus
                + ", certstatusname=" + certstatusname + ", getsource=" + getsource + ", getsourcename=" + getsourcename
                + ", factlink=" + factlink + ", testitems=" + testitems + ", remark=" + remark + ", parameterids="
                + parameterids + ", parameternames=" + parameternames + ", mainstandname=" + mainstandname
                + ", testprop=" + testprop + ", senduser=" + senduser + ", senddate=" + senddate + ", standtype1="
                + standtype1 + ", standtype2=" + standtype2 + ", standtype3=" + standtype3 + ", standtype4="
                + standtype4 + ", standtype5=" + standtype5 + ", taskid=" + taskid + ", testtype=" + testtype
                + ", tranidcn=" + tranidcn + ", gpsnum=" + gpsnum + "]";
    }

}
