package com.gpersist.entity.publics;

import com.gpersist.entity.*;
import com.gpersist.enums.*;
import com.gpersist.utils.ToolUtils;

public class SelectByInt implements BaseBean {

    private int selectid;
    private String getaction;
    private String procedurename;

    public SelectByInt() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((SelectByInt) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.selectid = 0;
        this.getaction = ActionGetType.full.toString();
        this.procedurename = "";
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getSelectid() {
        return selectid;
    }

    public void setSelectid(int selectid) {
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
