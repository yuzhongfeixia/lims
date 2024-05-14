package com.alms.entity.form;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class IntInterface implements BaseBean {

    // 界面编号;
    private String intid;

    // 界面名称;
    private String intname;

    // 界面代码;
    private String intcode;

    // 试件数量;
    private int specserial;

    // 系统类型;
    private String inttype;

    // 系统类型名称;
    private String inttypename;

    // 方法值;
    private String methodcode;

    // 排序序号;
    private int intorder;

    private String sampleid;

    private String parameterid;

    private String samplename;

    private String parametername;

    // 检测方法
    private String teststandardname;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<IntInterface> item;

    public IntInterface() {
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
        return ToolUtils.CompareProperty((IntInterface) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品:sampleid", "样品名称:samplename", "检测参数:parameterid", "检测参数:parametername", "界面编号:intid",
                "界面名称:intname", "界面代码:intcode", "试件数量:specserial", "系统类型:inttype", "系统类型名称:inttypename",
                "方法值:methodcode", "多点检测:ismutil", "排序序号:intorder", "检测方法:teststandardname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.intid = "";
        this.intname = "";
        this.intcode = "";
        this.specserial = 0;
        this.inttype = "";
        this.inttypename = "";
        this.methodcode = "";
        this.intorder = 0;
        this.sampleid = "";
        this.samplename = "";
        this.parameterid = "";
        this.parametername = "";
        this.teststandardname = "";
    }

    public String getTeststandardname() {
        return teststandardname;
    }

    public void setTeststandardname(String teststandardname) {
        this.teststandardname = teststandardname;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getParameterid() {
        return parameterid;
    }

    public void setParameterid(String parameterid) {
        this.parameterid = parameterid;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getParametername() {
        return parametername;
    }

    public void setParametername(String parametername) {
        this.parametername = parametername;
    }

    public String getIntid() {
        return intid;
    }

    public void setIntid(String intid) {
        this.intid = intid;
    }

    public String getIntname() {
        return intname;
    }

    public void setIntname(String intname) {
        this.intname = intname;
    }

    public String getIntcode() {
        return intcode;
    }

    public void setIntcode(String intcode) {
        this.intcode = intcode;
    }

    public int getSpecserial() {
        return specserial;
    }

    public void setSpecserial(int specserial) {
        this.specserial = specserial;
    }

    public String getInttype() {
        return inttype;
    }

    public void setInttype(String inttype) {
        this.inttype = inttype;
    }

    public String getInttypename() {
        return inttypename;
    }

    public void setInttypename(String inttypename) {
        this.inttypename = inttypename;
    }

    public String getMethodcode() {
        return methodcode;
    }

    public void setMethodcode(String methodcode) {
        this.methodcode = methodcode;
    }

    public int getIntorder() {
        return intorder;
    }

    public void setIntorder(int intorder) {
        this.intorder = intorder;
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

    public SelectBean<IntInterface> getItem() {
        if (item == null)
            item = new SelectBean<IntInterface>();

        return item;
    }

    public void setItem(SelectBean<IntInterface> item) {
        this.item = item;
    }

}
