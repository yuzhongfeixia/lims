package com.alms.entity.samp;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class SampleEnvDetail implements BaseBean {

    // 业务编号;
    private String tranid;

    // 环境编号;
    private String envid;

    // 采样地点;
    private String sampleaddress;

    // 采样量;
    private String samplequantity;

    // gps定位;
    private String sampleposi;

    // 样点示意图;
    private String samplepic;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<SampleEnvDetail> item;

    public SampleEnvDetail() {
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
        return ToolUtils.CompareProperty((SampleEnvDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "环境编号:envid", "采样地点:sampleaddress", "采样量:samplequantity",
                "GPS定位:sampleposi", "样点示意图:samplepic" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.envid = "";
        this.sampleaddress = "";
        this.samplequantity = "";
        this.sampleposi = "";
        this.samplepic = "";
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getEnvid() {
        return envid;
    }

    public void setEnvid(String envid) {
        this.envid = envid;
    }

    public String getSampleaddress() {
        return sampleaddress;
    }

    public void setSampleaddress(String sampleaddress) {
        this.sampleaddress = sampleaddress;
    }

    public String getSamplequantity() {
        return samplequantity;
    }

    public void setSamplequantity(String samplequantity) {
        this.samplequantity = samplequantity;
    }

    public String getSampleposi() {
        return sampleposi;
    }

    public void setSampleposi(String sampleposi) {
        this.sampleposi = sampleposi;
    }

    public String getSamplepic() {
        return samplepic;
    }

    public void setSamplepic(String samplepic) {
        this.samplepic = samplepic;
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

    public SelectBean<SampleEnvDetail> getItem() {
        if (item == null)
            item = new SelectBean<SampleEnvDetail>();

        return item;
    }

    public void setItem(SelectBean<SampleEnvDetail> item) {
        this.item = item;
    }

}
