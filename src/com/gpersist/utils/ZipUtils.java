package com.gpersist.utils;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.zip.*;

import com.gpersist.utils.Consts;
import com.gpersist.utils.ToolUtils;

public class ZipUtils {

    public static String GetZipFile(String fileurls) {
        String rtn = "";

        try {
            String path = FileUtils.GetFileBasePath();

            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.DAY_OF_MONTH, -1);

            FileUtils.DeleteFolder(
                    path + FileUtils.FILE_TYPE_ZIP + PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_SEPARATOR)
                            + ToolUtils.GetFmtDate(cal.getTime(), Consts.STR_DATE_FMT)
                            + PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_SEPARATOR));

            String newpath = path + FileUtils.FILE_TYPE_ZIP
                    + PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_SEPARATOR)
                    + ToolUtils.GetFmtDate(ToolUtils.GetNowDate(), Consts.STR_DATE_FMT)
                    + PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_SEPARATOR);

            FileUtils.CreateFolder(newpath);

            rtn = newpath + FileUtils.GetUUIDFileName(FileUtils.FILE_TYPE_ZIP);

            GetZipFile(path, rtn, fileurls);

        } catch (Exception e) {
            rtn = "";
        }

        return rtn;
    }

    public static void GetZipFile(String basepath, String zipname, String fileurls) throws Exception {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        try {
            fos = new FileOutputStream(zipname);
            zos = new ZipOutputStream(fos);

            String[] urls = fileurls.split(",");
            String[] actfile;

            for (String url : urls) {
                actfile = url.split(":");

                File zipfile = new File(basepath + actfile[1]);

                if (!zipfile.exists() && !zipfile.isFile())
                    continue;

                FileInputStream fis = null;

                try {
                    fis = new FileInputStream(zipfile);

                    ZipEntry ze = new ZipEntry(actfile[0]);
                    zos.putNextEntry(ze);

                    byte[] content = new byte[1024];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }
                } catch (Exception e) {
                    throw e;
                } finally {
                    if (fis != null) {
                        fis.close();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }
}
