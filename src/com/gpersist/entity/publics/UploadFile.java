package com.gpersist.entity.publics;

import java.io.File;

import com.gpersist.entity.*;
import com.gpersist.utils.ToolUtils;

public class UploadFile implements BaseBean {

    private File upload;

    private String uploadFileName;

    private String uploadContentType;

    public UploadFile() {
        OnInit();
    }

    @Override
    public String OnDebug() {
        return ToolUtils.DebugProperty(this, this.OnProperties());
    }

    @Override
    public String OnCompare(BaseBean item) throws Exception {
        return ToolUtils.CompareProperty((UploadFile) item, this, this.OnProperties());
    }

    @Override
    public String[] OnProperties() {
        return new String[] {};
    }

    @Override
    public void OnInit() {
        // TODO Auto-generated method stub
        this.uploadFileName = "";
        this.uploadContentType = "";
    }

    @Override
    public String[] OnExclusions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean OnBeforeSave(ErrorMsg msg) {

        return false;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }
}
