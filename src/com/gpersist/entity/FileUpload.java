package com.gpersist.entity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUpload {

    private static final int BUFFER_SIZE = 1024;
    // 封装上传文件域的属性
    private File upload;
    // 封装上传文件类型的属性
    private String contentType;
    // 封装上传文件名的属性
    private String fileName;

    private String storageFileName;

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadFileName() {
        return fileName;
    }

    public void setUploadFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStorageFileName() {
        return storageFileName;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }

    public String getUploadContentType() {
        return contentType;
    }

    public void setUploadContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = null;
        OutputStream out = null;

        in = new BufferedInputStream(new FileInputStream(src));
        out = new BufferedOutputStream(new FileOutputStream(dst));

        byte[] buffer = new byte[BUFFER_SIZE];
        int numBytesRead = 0;

        while ((numBytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, numBytesRead);
        }

        if (null != in) {
            in.close();
        }
        if (null != out) {
            out.close();
        }
    }

    public static String getExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }
}
