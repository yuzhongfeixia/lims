package com.gpersist.action;

import java.io.File;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.gpersist.utils.FileUpload;
import com.gpersist.utils.FileUtils;
import com.gpersist.utils.ToolUtils;

public class UploadAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // region Upload methods

    private File file;

    private String fileFileName;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String FileUpLoad() throws Exception {

        try {
            if (!ToolUtils.StringIsEmpty(fileFileName)) {
                String path = FileUtils.GetFileBasePath();

                // 按日期归档文件，以便整理
                String rpath;
                rpath = "file/" + ToolUtils.GetFmtDate(ToolUtils.GetNowDate(), "yyyyMMdd") + "/";

                FileUtils.CreateFolder(path + rpath);

                // 为了上传文件的唯一性以及保密要求，转换文件名称
                String fileext = FileUpload.getExtention(fileFileName);
                String uuid = UUID.randomUUID().toString().toLowerCase().replaceAll("-", "");
                String filename = path + rpath + uuid + fileext;

                File uploadFile = new File(filename);
                FileUtils.FileChannelCopy(file, uploadFile);

                ToolUtils.JsonOutString(
                        "{success:true,url:\"" + rpath + uuid + fileext + "\",ufile:\"" + uuid + fileext + "\"}");
            } else {
                ToolUtils.JsonOutString("{seccess:false}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToolUtils.JsonOutString("{seccess:false}");
        }

        return null;
    }

    // endregion Upload methods

    // region FileDelete methods

    public String FileDeleteByFileUrl() throws Exception {
        String fileurl = ToolUtils.Decode(ServletActionContext.getRequest().getParameter("fileurl"));
        String[] fileurls = fileurl.split(",");
        for (String url : fileurls) {
            File file = new File(url);
            file.delete();
        }
        return null;
    }

    // endregion FileDelete methods

}
