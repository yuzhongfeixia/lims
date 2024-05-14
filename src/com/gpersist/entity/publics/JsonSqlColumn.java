package com.gpersist.entity.publics;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.utils.ToolUtils;

public class JsonSqlColumn implements BaseBean {

    private String sqlid;

    private short colid;

    private String colname;

    private String colcode;

    private String colext;

    private short collen;

    private short colpre;

    private short colwidth;

    private boolean isdisplay;

    private String colformat;

    private boolean isprint;

    private boolean islock;

    private boolean isorder;

    private boolean issummary;

    private String colrender;

    private String summarytype;

    private String summaryrender;

    private boolean isedit;

    private boolean isnull;

    private short flexcnt;

    private String pmtsql;

    private String vtype;

    private boolean isparent;

    private boolean ischild;

    private String parentcol;

    private short colorder;

    private SelectBean<JsonSqlColumn> item;

    public JsonSqlColumn() {
        this.OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((DataDeal) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.sqlid = "";
        this.colid = 0;
        this.colname = "";
        this.colcode = "";
        this.colext = "";
        this.collen = 0;
        this.colpre = 0;
        this.colwidth = 0;
        this.isdisplay = true;
        this.colformat = "";
        this.isprint = true;
        this.islock = false;
        this.isorder = true;
        this.issummary = false;
        this.colrender = "";
        this.summarytype = "";
        this.summaryrender = "";
        this.isedit = false;
        this.isnull = true;
        this.flexcnt = 0;
        this.pmtsql = "";
        this.vtype = "";
        this.isparent = false;
        this.ischild = false;
        this.parentcol = "";
        this.colorder = 0;
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "sqlid", "item" };
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isIsparent() {
        return isparent;
    }

    public void setIsparent(boolean isparent) {
        this.isparent = isparent;
    }

    public boolean isIschild() {
        return ischild;
    }

    public void setIschild(boolean ischild) {
        this.ischild = ischild;
    }

    public String getParentcol() {
        return parentcol;
    }

    public void setParentcol(String parentcol) {
        this.parentcol = parentcol;
    }

    public short getColorder() {
        return colorder;
    }

    public void setColorder(short colorder) {
        this.colorder = colorder;
    }

    public short getColid() {
        return colid;
    }

    public void setColid(short colid) {
        this.colid = colid;
    }

    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }

    public String getColcode() {
        return colcode;
    }

    public void setColcode(String colcode) {
        this.colcode = colcode;
    }

    public String getColext() {
        return colext;
    }

    public void setColext(String colext) {
        this.colext = colext;
    }

    public short getCollen() {
        return collen;
    }

    public void setCollen(short collen) {
        this.collen = collen;
    }

    public short getColpre() {
        return colpre;
    }

    public void setColpre(short colpre) {
        this.colpre = colpre;
    }

    public short getColwidth() {
        return colwidth;
    }

    public void setColwidth(short colwidth) {
        this.colwidth = colwidth;
    }

    public boolean isIsdisplay() {
        return isdisplay;
    }

    public void setIsdisplay(boolean isdisplay) {
        this.isdisplay = isdisplay;
    }

    public String getColformat() {
        return colformat;
    }

    public void setColformat(String colformat) {
        this.colformat = colformat;
    }

    public boolean isIsprint() {
        return isprint;
    }

    public void setIsprint(boolean isprint) {
        this.isprint = isprint;
    }

    public boolean isIslock() {
        return islock;
    }

    public void setIslock(boolean islock) {
        this.islock = islock;
    }

    public boolean isIsorder() {
        return isorder;
    }

    public void setIsorder(boolean isorder) {
        this.isorder = isorder;
    }

    public boolean isIssummary() {
        return issummary;
    }

    public void setIssummary(boolean issummary) {
        this.issummary = issummary;
    }

    public String getColrender() {
        return colrender;
    }

    public void setColrender(String colrender) {
        this.colrender = colrender;
    }

    public String getSummarytype() {
        return summarytype;
    }

    public void setSummarytype(String summarytype) {
        this.summarytype = summarytype;
    }

    public String getSummaryrender() {
        return summaryrender;
    }

    public void setSummaryrender(String summaryrender) {
        this.summaryrender = summaryrender;
    }

    public boolean isIsedit() {
        return isedit;
    }

    public void setIsedit(boolean isedit) {
        this.isedit = isedit;
    }

    public boolean isIsnull() {
        return isnull;
    }

    public void setIsnull(boolean isnull) {
        this.isnull = isnull;
    }

    public short getFlexcnt() {
        return flexcnt;
    }

    public void setFlexcnt(short flexcnt) {
        this.flexcnt = flexcnt;
    }

    public String getPmtsql() {
        return pmtsql;
    }

    public void setPmtsql(String pmtsql) {
        this.pmtsql = pmtsql;
    }

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    public SelectBean<JsonSqlColumn> getItem() {
        if (item == null)
            item = new SelectBean<JsonSqlColumn>();

        return item;
    }

    public void setItem(SelectBean<JsonSqlColumn> item) {
        this.item = item;
    }

    public String getSqlid() {
        return sqlid;
    }

    public void setSqlid(String sqlid) {
        this.sqlid = sqlid;
    }

}
