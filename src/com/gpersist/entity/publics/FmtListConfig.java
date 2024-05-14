package com.gpersist.entity.publics;

import com.gpersist.entity.*;
import com.gpersist.utils.ToolUtils;

public class FmtListConfig implements BaseBean {

    private String colname;

    private String colcode;

    private String colwidth;

    public FmtListConfig() {
        this.OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((FmtListConfig) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.colname = "";
        this.colcode = "";
        this.colwidth = "";
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {
        // TODO Auto-generated method stub
        return false;
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

    public String getColwidth() {
        return colwidth;
    }

    public void setColwidth(String colwidth) {
        this.colwidth = colwidth;
    }

}
