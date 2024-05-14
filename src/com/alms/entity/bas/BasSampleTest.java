package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasSampleTest implements BaseBean {

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 检测参数编号;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 检测依据编号;
    private String teststandard;

    // 检测依据名称;
    private String teststandardname;

    // 检测依据代码
    private String teststandardcode;

    private String id;

    private String name;

    private DataDeal deal;

    private SelectBean<BasSampleTest> item;

    public BasSampleTest() {
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
        return ToolUtils.CompareProperty((BasSampleTest) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品编号:sampleid", "样品名称:samplename", "检测参数编号:parameterid", "检测参数名称:parametername",
                "检测依据编号:teststandard", "检测依据名称:teststandardname" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item" };
    }

    @Override
    public void OnInit() {
        this.sampleid = "";
        this.samplename = "";
        this.parameterid = "";
        this.parametername = "";
        this.teststandard = "";
        this.teststandardname = "";
        this.teststandardcode = "";
        this.id = "";
        this.name = "";
    }

    public String getTeststandardcode() {
        return teststandardcode;
    }

    public void setTeststandardcode(String teststandardcode) {
        this.teststandardcode = teststandardcode;
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

    public String getTeststandard() {
        return teststandard;
    }

    public void setTeststandard(String teststandard) {
        this.teststandard = teststandard;
    }

    public String getTeststandardname() {
        return teststandardname;
    }

    public void setTeststandardname(String teststandardname) {
        this.teststandardname = teststandardname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<BasSampleTest> getItem() {
        if (item == null)
            item = new SelectBean<BasSampleTest>();

        return item;
    }

    public void setItem(SelectBean<BasSampleTest> item) {
        this.item = item;
    }

}
