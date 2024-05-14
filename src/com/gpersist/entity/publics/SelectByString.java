package com.gpersist.entity.publics;

import com.gpersist.entity.*;
import com.gpersist.enums.*;
import com.gpersist.utils.ToolUtils;

public class SelectByString implements BaseBean {

    private String selectid;

    private String getaction;

    private String procedurename;

    public SelectByString() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SelectByString) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.selectid = "";
        this.getaction = ActionGetType.full.toString();
        this.procedurename = "";
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getSelectid() {
        return selectid;
    }

    public void setSelectid(String selectid) {
        this.selectid = selectid;
    }

    public String getGetaction() {
        return getaction;
    }

    public void setGetaction(String getaction) {
        this.getaction = getaction;
    }

    public String getProcedurename() {
        return procedurename;
    }

    public void setProcedurename(String procedurename) {
        this.procedurename = procedurename;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }
}
