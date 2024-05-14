package com.gpersist.entity.publics;

import com.gpersist.entity.*;
import com.gpersist.utils.ToolUtils;

public class ExportSetting implements BaseBean {

    private String sheetname;

    private String title;

    private String exporter;

    private String daterange;

    private boolean hasexporter;

    private boolean hasexportdate;

    private String filename;

    private String bottom;

    public ExportSetting() {
        this.OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((ExportSetting) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.sheetname = "";
        this.title = "";
        this.exporter = "";
        this.daterange = "";
        this.hasexportdate = true;
        this.hasexporter = true;
        this.filename = "";
        this.bottom = "";
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

    public String getSheetname() {
        return sheetname;
    }

    public void setSheetname(String sheetname) {
        this.sheetname = sheetname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExporter() {
        return exporter;
    }

    public void setExporter(String exporter) {
        this.exporter = exporter;
    }

    public String getDaterange() {
        return daterange;
    }

    public void setDaterange(String daterange) {
        this.daterange = daterange;
    }

    public boolean isHasexporter() {
        return hasexporter;
    }

    public void setHasexporter(boolean hasexporter) {
        this.hasexporter = hasexporter;
    }

    public boolean isHasexportdate() {
        return hasexportdate;
    }

    public void setHasexportdate(boolean hasexportdate) {
        this.hasexportdate = hasexportdate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

}
