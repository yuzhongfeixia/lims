package com.alms.entity.dev;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class AcceptPartsDetail implements BaseBean {

    // 验收编号;
    private String acceptid;

    // 配件编号;
    private String partsno;

    // 配件序号;
    private int partsserial;

    // 配件名称;
    private String partsname;

    // 规格型号;
    private String partsstandard;

    // 数量;
    private double partscount;

    // 备注;
    private String partsremark;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<AcceptPartsDetail> item;

    public AcceptPartsDetail() {
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
        return ToolUtils.CompareProperty((AcceptPartsDetail) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "验收编号:acceptid", "配件编号:partsno", "配件序号:partsserial", "配件名称:partsname",
                "规格型号:partsstandard", "数量:partscount", "备注:partsremark" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.acceptid = "";
        this.partsno = "";
        this.partsserial = 0;
        this.partsname = "";
        this.partsstandard = "";
        this.partscount = 0;
        this.partsremark = "";
    }

    public String getAcceptid() {
        return acceptid;
    }

    public void setAcceptid(String acceptid) {
        this.acceptid = acceptid;
    }

    public String getPartsno() {
        return partsno;
    }

    public void setPartsno(String partsno) {
        this.partsno = partsno;
    }

    public int getPartsserial() {
        return partsserial;
    }

    public void setPartsserial(int partsserial) {
        this.partsserial = partsserial;
    }

    public String getPartsname() {
        return partsname;
    }

    public void setPartsname(String partsname) {
        this.partsname = partsname;
    }

    public String getPartsstandard() {
        return partsstandard;
    }

    public void setPartsstandard(String partsstandard) {
        this.partsstandard = partsstandard;
    }

    public double getPartscount() {
        return partscount;
    }

    public void setPartscount(double partscount) {
        this.partscount = partscount;
    }

    public String getPartsremark() {
        return partsremark;
    }

    public void setPartsremark(String partsremark) {
        this.partsremark = partsremark;
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

    public SelectBean<AcceptPartsDetail> getItem() {
        if (item == null)
            item = new SelectBean<AcceptPartsDetail>();

        return item;
    }

    public void setItem(SelectBean<AcceptPartsDetail> item) {
        this.item = item;
    }

}
