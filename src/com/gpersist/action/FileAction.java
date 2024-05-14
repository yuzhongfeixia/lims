package com.gpersist.action;

import java.io.*;
import java.net.URL;

import org.apache.struts2.ServletActionContext;

import com.gpersist.action.BaseAction;
import com.gpersist.utils.*;

public class FileAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String fileurl;

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String DownFile() throws Exception {
        this.filename = ToolUtils.Encode(this.filename);
        File f = new File(FileUtils.GetFileBasePath() + this.fileurl);

        is = new FileInputStream(f);

        return "file";
    }

    public String Convert2Swf() throws Exception {
        // String file = new
        // String(request.getParameter("file").getBytes("iso-8859-1"), "utf-8");
        // this.fileurl = ToolUtils.Encode(this.fileurl);
        String msg = "";
        String swffile = "";

        try {
            swffile = OfficeUtils.Convert(this.fileurl);
        } catch (Exception e) {
            msg = e.getMessage();
        }

        ToolUtils.OutString("{\"err_msg\": \"" + msg + "\", \"file\": \"" + swffile + "\"}");

        return null;
    }

    public String DownZipFile() throws Exception {

        this.filename = ToolUtils.Encode(this.filename) + ".zip";

        String zipurl = ZipUtils.GetZipFile(this.fileurl);

        File f = new File(zipurl);

        is = new FileInputStream(f);

        return "file";
    }

    public String DownImage() throws Exception {
        URL ut = Thread.currentThread().getContextClassLoader().getResource("");
        String path = ut.toString().replace("file:/", "").replace("%100", " ");

        boolean isWindows = isWindows();
        if (isWindows) {
            path = PropertiesUtils.GetString(Consts.CONFIG_FILE_WINDOWS_ROOT);
        } else {
            path = PropertiesUtils.GetString(Consts.CONFIG_FILE_LINUX_ROOT);
        }

        this.filename = ServletActionContext.getRequest().getParameter("file");
        path = path + PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_SEPARATOR)
                + PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_BASE)
                + PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_SEPARATOR) + this.filename;

        this.is = new FileInputStream(path);

        return "file";
    }

    public static boolean isWindows() {
        boolean isWindows = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindows = true;
        }
        return isWindows;
    }

}
