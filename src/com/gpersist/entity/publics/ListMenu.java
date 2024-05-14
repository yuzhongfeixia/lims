package com.gpersist.entity.publics;

import com.gpersist.entity.BaseBean;
import com.gpersist.entity.ErrorMsg;
import com.gpersist.utils.ToolUtils;

public class ListMenu implements BaseBean {

    private int mid;

    private String mcode;

    private int mauth;

    public ListMenu() {
        this.OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((ListMenu) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.mid = 0;
        this.mcode = "";
        this.mauth = 0;
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

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public int getMauth() {
        return mauth;
    }

    public void setMauth(int mauth) {
        this.mauth = mauth;
    }

}
