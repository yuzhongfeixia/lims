package com.gpersist.entity.publics;

import com.gpersist.entity.*;
import com.gpersist.utils.ToolUtils;

import java.util.*;

public class TreeMenu implements BaseBean {
    private String text;

    private String mid;

    private boolean leaf;

    private boolean expanded;

    private String url;

    private String tooltip;

    private String mcode;

    private boolean istab;

    private String mnormalicon;

    private String iconCls;

    private boolean ismutil;

    private List<TreeMenu> children;

    public TreeMenu() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((TreeMenu) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.text = "";
        this.mid = "";
        this.leaf = true;
        this.expanded = false;
        this.url = "";
        this.tooltip = "";
        this.istab = true;
        this.mnormalicon = "";
        this.iconCls = "";
        this.ismutil = false;
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public List<TreeMenu> getChildren() {
        if (children == null)
            children = new ArrayList<TreeMenu>();

        return children;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public boolean isIstab() {
        return istab;
    }

    public void setIstab(boolean istab) {
        this.istab = istab;
    }

    public String getMnormalicon() {
        return mnormalicon;
    }

    public void setMnormalicon(String mnormalicon) {
        this.mnormalicon = mnormalicon;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public boolean isIsmutil() {
        return ismutil;
    }

    public void setIsmutil(boolean ismutil) {
        this.ismutil = ismutil;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }
}
