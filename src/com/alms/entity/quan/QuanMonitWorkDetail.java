package com.alms.entity.quan;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class QuanMonitWorkDetail implements BaseBean {

    // 业务编号;
    private String tranid;

    // 大项编号;
    private String bigitemid;

    // 大项名称;
    private String bigitemname;

    // 小项编号;
    private String samitemid;

    // 小项名称;
    private String samitemname;

    // 选项编号;
    private String optionid;

    // 选择项名称;
    private String optionname;

    // 不符合表现;
    private String badbehave;

    // 备注;
    private String detailremark;

    // 序号
    private int serial;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<QuanMonitWorkDetail> item;

    public QuanMonitWorkDetail() {
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
        return ToolUtils.CompareProperty((QuanMonitWorkDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "业务编号:tranid", "大项编号:bigitemid", "大项名称:bigitemname", "小项编号:samitemid", "小项名称:samitemname",
                "选项编号:optionid", "选择项名称:optionname", "不符合表现:badbehave", "备注:detailremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.tranid = "";
        this.bigitemid = "";
        this.bigitemname = "";
        this.samitemid = "";
        this.samitemname = "";
        this.optionid = "";
        this.optionname = "";
        this.badbehave = "";
        this.detailremark = "";
        this.serial = 0;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getBigitemid() {
        return bigitemid;
    }

    public void setBigitemid(String bigitemid) {
        this.bigitemid = bigitemid;
    }

    public String getBigitemname() {
        return bigitemname;
    }

    public void setBigitemname(String bigitemname) {
        this.bigitemname = bigitemname;
    }

    public String getSamitemid() {
        return samitemid;
    }

    public void setSamitemid(String samitemid) {
        this.samitemid = samitemid;
    }

    public String getSamitemname() {
        return samitemname;
    }

    public void setSamitemname(String samitemname) {
        this.samitemname = samitemname;
    }

    public String getOptionid() {
        return optionid;
    }

    public void setOptionid(String optionid) {
        this.optionid = optionid;
    }

    public String getOptionname() {
        return optionname;
    }

    public void setOptionname(String optionname) {
        this.optionname = optionname;
    }

    public String getBadbehave() {
        return badbehave;
    }

    public void setBadbehave(String badbehave) {
        this.badbehave = badbehave;
    }

    public String getDetailremark() {
        return detailremark;
    }

    public void setDetailremark(String detailremark) {
        this.detailremark = detailremark;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
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

    public SelectBean<QuanMonitWorkDetail> getItem() {
        if (item == null)
            item = new SelectBean<QuanMonitWorkDetail>();

        return item;
    }

    public void setItem(SelectBean<QuanMonitWorkDetail> item) {
        this.item = item;
    }

}
