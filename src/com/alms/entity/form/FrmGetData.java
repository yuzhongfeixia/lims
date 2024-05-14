package com.alms.entity.form;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.*;
import com.gpersist.utils.*;

public class FrmGetData implements BaseBean {

    // 表单编号;
    private String formid;

    private String samplecode;

    private String tranid;

    private String fieldcode;

    private String celltext;

    private SearchParams search;

    private DataDeal deal;

    private SelectBean<FrmGetData> item;

    public FrmGetData() {
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
        return ToolUtils.CompareProperty((FrmGetData) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] { "表单编号:formid", "样品编号:samplecode", "业务编号:tranid", "字段名称:fieldcode", "显示值:celltext" };
    }

    @Override
    public String[] OnExclusions() {
        return new String[] { "deal", "item", "search" };
    }

    @Override
    public void OnInit() {
        this.formid = "";
        this.samplecode = "";
        this.tranid = "";
        this.fieldcode = "";
        this.celltext = "";
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getSamplecode() {
        return samplecode;
    }

    public void setSamplecode(String samplecode) {
        this.samplecode = samplecode;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getFieldcode() {
        return fieldcode;
    }

    public void setFieldcode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public String getCelltext() {
        return celltext;
    }

    public void setCelltext(String celltext) {
        this.celltext = celltext;
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

    public SelectBean<FrmGetData> getItem() {
        if (item == null)
            item = new SelectBean<FrmGetData>();

        return item;
    }

    public void setItem(SelectBean<FrmGetData> item) {
        this.item = item;
    }

}
