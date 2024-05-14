package com.alms.entity.bas;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class BasSampleJudge implements BaseBean {

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 检测参数编号;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    // 判定依据编号;
    private String judgestandard;

    // 判定依据名称;
    private String judgestandardname;

    // 判定依据代码
    private String judgestandardcode;

    private DataDeal deal;

    private SelectBean<BasSampleJudge> item;

    public BasSampleJudge() {
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
        return ToolUtils.CompareProperty((BasSampleJudge) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "样品编号:sampleid", "样品名称:samplename", "检测参数编号:parameterid", "检测参数名称:parametername",
                "判定依据编号:judgestandard", "判定依据名称:judgestandardname" };
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
        this.judgestandard = "";
        this.judgestandardname = "";
        this.judgestandardcode = "";
    }

    public String getJudgestandardcode() {
        return judgestandardcode;
    }

    public void setJudgestandardcode(String judgestandardcode) {
        this.judgestandardcode = judgestandardcode;
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

    public String getJudgestandard() {
        return judgestandard;
    }

    public void setJudgestandard(String judgestandard) {
        this.judgestandard = judgestandard;
    }

    public String getJudgestandardname() {
        return judgestandardname;
    }

    public void setJudgestandardname(String judgestandardname) {
        this.judgestandardname = judgestandardname;
    }

    public DataDeal getDeal() {
        if (deal == null)
            deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    public SelectBean<BasSampleJudge> getItem() {
        if (item == null)
            item = new SelectBean<BasSampleJudge>();

        return item;
    }

    public void setItem(SelectBean<BasSampleJudge> item) {
        this.item = item;
    }

}
