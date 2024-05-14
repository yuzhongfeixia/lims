package com.gpersist.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import com.gpersist.utils.ToolUtils;

public class DownloadAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String fname;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public InputStream getDownloadfile() throws FileNotFoundException {

        try {
            this.fname = new String(this.fname.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.filename = ToolUtils.Encode(this.fname);

        String basepath = ServletActionContext.getServletContext().getRealPath("upload");

        File f = new File(basepath + "\\" + this.fname);

        return new FileInputStream(f);
        // return
        // ServletActionContext.getServletContext().getResourceAsStream(basepath +
        // "\\" + this.fname);
    }

    @Override
    public String execute() throws Exception {
        return "file";
    }
}
