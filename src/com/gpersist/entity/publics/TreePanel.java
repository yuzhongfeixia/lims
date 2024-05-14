package com.gpersist.entity.publics;

import com.gpersist.entity.*;
import com.gpersist.utils.ToolUtils;

import java.util.*;

public class TreePanel implements BaseBean {

    private String title;

    private String id;

    private List<TreeMenu> root;

    public TreePanel() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((TreePanel) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.title = "";
        this.id = "";
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TreeMenu> getRoot() {
        if (root == null)
            root = new ArrayList<TreeMenu>();

        return root;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }
}
