package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class DevTest implements BaseBean {

    // 设备编号;
    private String devid;

    // 样品编号;
    private String sampleid;

    // 样品名称;
    private String samplename;

    // 检测参数编号;
    private String parameterid;

    // 检测参数名称;
    private String parametername;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<DevTest> item;

    public DevTest() {
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
        return ToolUtils.CompareProperty((DevTest) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "设备编号:devid", "样品编号:sampleid", "样品名称:samplename", "检测参数编号:parameterid",
                "检测参数名称:parametername" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.devid = "";
        this.sampleid = "";
        this.samplename = "";
        this.parameterid = "";
        this.parametername = "";
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
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

    public SelectBean<DevTest> getItem() {
        if (item == null)
            item = new SelectBean<DevTest>();

        return item;
    }

    public void setItem(SelectBean<DevTest> item) {
        this.item = item;
    }

}
