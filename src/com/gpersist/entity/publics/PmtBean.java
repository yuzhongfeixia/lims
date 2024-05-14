package com.gpersist.entity.publics;

import com.gpersist.entity.*;
import com.gpersist.utils.ToolUtils;

public class PmtBean implements BaseBean {

    private String id;

    private String name;

    private SelectBean<PmtBean> item;

    public PmtBean() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((PmtBean) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.id = "";
        this.name = "";
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return new String[] { "item" };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }

    public SelectBean<PmtBean> getItem() {
        if (item == null)
            item = new SelectBean<PmtBean>();

        return item;
    }

    public void setItem(SelectBean<PmtBean> item) {
        this.item = item;
    }
}
