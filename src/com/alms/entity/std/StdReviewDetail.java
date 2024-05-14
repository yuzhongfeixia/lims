package com.alms.entity.std;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class StdReviewDetail implements BaseBean {

    // 业务编号;
    private String tranid;

    // 序号;
    private int serial;

    // 产品编号
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

    // 变更后的标准代号;
    private String replstdid;

    // 变更后的标准名称;
    private String replstdname;

    // 变更内容;
    private String changecontent;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<StdReviewDetail> item;

    public StdReviewDetail() {
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
        return ToolUtils.CompareProperty((StdReviewDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "序号:serial", "产品名称:samplename", "检测参数:parameterid", "检测参数名称:parametername",
                "已批准的标准代号:stdid", "已批准的标准名称:stdname", "变更后的标准代号:replstdid", "变更后的标准名称:replstdname",
                "变更内容:changecontent" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.serial = 0;
        this.samplename = "";
        this.parameterid = "";
        this.parametername = "";
        this.stdid = "";
        this.stdname = "";
        this.replstdid = "";
        this.replstdname = "";
        this.changecontent = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
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

    public SelectBean<StdReviewDetail> getItem() {
        if (item == null)
            item = new SelectBean<StdReviewDetail>();

        return item;
    }

    public void setItem(SelectBean<StdReviewDetail> item) {
        this.item = item;
    }

}
