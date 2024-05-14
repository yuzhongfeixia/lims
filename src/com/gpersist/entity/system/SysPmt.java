package com.gpersist.entity.system;

import com.gpersist.entity.*;
import com.gpersist.entity.publics.DataDeal;
import com.gpersist.entity.publics.SelectBean;
import com.gpersist.utils.ToolUtils;

public class SysPmt implements BaseBean {

    // 参数编码;
    private short pmtid;

    // 参数代码;
    private String pmtcode;

    // 参数名称;
    private String pmtname;

    // 参数类型;
    private String pmttype;

    // 参数表;
    private String pmttable;

    // 参数保存;
    private String pmtsave;

    // 选择sql;
    private String selectsql;

    private DataDeal deal;

    private SelectBean<SysPmt> item;

    public SysPmt() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SysPmt) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.pmtid = 0;
        this.pmtname = "";
        this.pmtcode = "";
        this.pmttype = "";
        this.pmttable = "";
        this.pmtsave = "";
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "deal" };
    }

    public short getPmtid() {
        return pmtid;
    }

    public void setPmtid(short pmtid) {
        this.pmtid = pmtid;
    }

    public String getPmtcode() {
        return pmtcode;
    }

    public void setPmtcode(String pmtcode) {
        this.pmtcode = pmtcode;
    }

    public String getPmtname() {
        return pmtname;
    }

    public void setPmtname(String pmtname) {
        this.pmtname = pmtname;
    }

    public String getPmttype() {
        return pmttype;
    }

    public void setPmttype(String pmttype) {
        this.pmttype = pmttype;
    }

    public String getPmttable() {
        return pmttable;
    }

    public void setPmttable(String pmttable) {
        this.pmttable = pmttable;
    }

    public String getPmtsave() {
        return pmtsave;
    }

    public void setPmtsave(String pmtsave) {
        this.pmtsave = pmtsave;
    }

    public DataDeal getDeal() {
        if (deal == null)
            this.deal = new DataDeal();

        return deal;
    }

    public void setDeal(DataDeal deal) {
        this.deal = deal;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }

    public SelectBean<SysPmt> getItem() {
        if (item == null)
            item = new SelectBean<SysPmt>();

        return item;
    }

    public void setItem(SelectBean<SysPmt> item) {
        this.item = item;
    }

    public String getSelectsql() {
        return selectsql;
    }

    public void setSelectsql(String selectsql) {
        this.selectsql = selectsql;
    }

}
