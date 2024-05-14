package com.gpersist.entity.publics;

import com.gpersist.entity.*;
import com.gpersist.utils.ToolUtils;

public class FmtBeanConfig implements BaseBean {

    private String colcode;

    private int coltype;

    private int colspan;

    private int colwidth;

    public FmtBeanConfig() {
        this.OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((FmtBeanConfig) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        this.colcode = "";
        this.coltype = 0;
        this.colspan = 1;
        this.colwidth = 0;
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

    public String getColcode() {
        return colcode;
    }

    public void setColcode(String colcode) {
        this.colcode = colcode;
    }

    public int getColtype() {
        return coltype;
    }

    public void setColtype(int coltype) {
        this.coltype = coltype;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public int getColwidth() {
        return colwidth;
    }

    public void setColwidth(int colwidth) {
        this.colwidth = colwidth;
    }

    public void SetConfig(String fmt) {
        String[] fmts = fmt.split(":");

        if (fmts.length == 4) {
            this.setColcode(fmts[0]);
            this.setColtype(Integer.valueOf(fmts[1]));
            this.setColwidth(Integer.valueOf(fmts[2]));
            this.setColspan(Integer.valueOf(fmts[3]));
        }
    }

}
