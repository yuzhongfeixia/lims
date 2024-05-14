package com.gpersist.utils;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.*;
import java.util.Date;

public class OfficeUtils {

    public static final String OFFICE_CONVERT_EXE = "OFFICE_CONVERT_EXE";
    public static final String SWF_CONVERT_EXE = "SWF_CONVERT_EXE";
    public static final String LINUX_OFFICE_CONVERT_EXE = "LINUX_OFFICE_CONVERT_EXE";
    public static final String LINUX_SWF_CONVERT_EXE = "LINUX_SWF_CONVERT_EXE";
    public static final String SWF_LANGUAGE_DIR = "SWF_LANGUAGE_DIR";
    public static final String fileUrlPre = "flexpaper/fs.jsp?path=<file_path>";

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static OfficeManager om;

    public static String Convert(String filename) throws Exception {

        File2Pdf2Swf(FileUtils.GetFileBasePath() + filename);

        return FileUtils.ChangeFileSuffix(filename, FileUtils.FILE_TYPE_SWF);
    }

    public static FileInputStream GetFile(String fileName) throws FileNotFoundException {
        return new FileInputStream(FileUtils.GetFileBasePath() + fileName);
    }

    public static void File2Pdf2Swf(String filepath) {
        try {
            String pdfpath = FileUtils.ChangeFileSuffix(filepath, FileUtils.FILE_TYPE_PDF);
            String swfpath = FileUtils.ChangeFileSuffix(filepath, FileUtils.FILE_TYPE_SWF);

            java.util.Date nowdate = new Date();

            File swf = new File(swfpath);
            if (swf.exists())
                return;

            File pdf = new File(pdfpath);

            if (!pdf.exists()) {
                if (!pdf.getParentFile().exists()) {
                    FileUtils.CreateFolder(pdf.getParent());
                }

                File file = new File(filepath);

                System.out.println(
                        "before convert to pdf: " + String.valueOf((new Date()).getTime() - nowdate.getTime()));
                OfficeDocumentConverter converter = new OfficeDocumentConverter(om);
                converter.convert(file, pdf);
                System.out.println("end convert to pdf: " + String.valueOf((new Date()).getTime() - nowdate.getTime()));
            }

            if (!swf.getParentFile().exists()) {
                FileUtils.CreateFolder(pdf.getParent());
            }

            System.out.println("before convert to swf: " + String.valueOf((new Date()).getTime() - nowdate.getTime()));

            // String cmd = PropertiesUtils.GetString(FileUtils.IsLinux() ?
            // OfficeUtils.LINUX_SWF_CONVERT_EXE : OfficeUtils.SWF_CONVERT_EXE)
            // + " -z -s flashversion=9 " + "\"" + pdf.getAbsolutePath() + "\"" + " -o
            // " + "\"" + swf.getAbsolutePath() + "\"";

            String cmd = PropertiesUtils
                    .GetString(FileUtils.IsLinux() ? OfficeUtils.LINUX_SWF_CONVERT_EXE : OfficeUtils.SWF_CONVERT_EXE)
                    + " -z -s flashversion=9 -s languagedir=" + PropertiesUtils.GetString(OfficeUtils.SWF_LANGUAGE_DIR)
                    + " " + pdf.getAbsolutePath() + " -o " + swf.getAbsolutePath();

            System.out.println("swf command: " + cmd);

            Process pro = Runtime.getRuntime().exec(cmd);

            System.out.println(pro);

            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String text = "";
            while ((text = br.readLine()) != null) {
                System.out.println(text);
            }

            pro.waitFor();
            System.out.println("end convert to swf: " + String.valueOf((new Date()).getTime() - nowdate.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void File2Pdf(String filepath, String pdfpath) {
        File pdf = new File(pdfpath);

        if (pdf.exists())
            return;

        try {
            File file = new File(filepath);

            if (!FileUtils.isFile(file)) {
                throw new Exception("文件不存在: " + file.getAbsolutePath());
            }

            if (!pdf.getParentFile().exists()) {
                FileUtils.CreateFolder(pdf.getParent());
            }

            java.util.Date nowdate = new Date();

            System.out.println("before convert to pdf: " + String.valueOf((new Date()).getTime() - nowdate.getTime()));
            OfficeDocumentConverter converter = new OfficeDocumentConverter(om);
            converter.convert(file, pdf);
            System.out.println("end convert to pdf: " + String.valueOf((new Date()).getTime() - nowdate.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Pdf2Swf(String pdfpath, String swfpath) {
        File swf = new File(swfpath);

        if (swf.exists())
            return;

        try {
            File pdf = new File(pdfpath);

            if (!FileUtils.isFile(pdf)) {
                throw new Exception("文件不存在: " + pdf.getAbsolutePath());
            }

            if (!swf.getParentFile().exists()) {
                FileUtils.CreateFolder(pdf.getParent());
            }

            java.util.Date nowdate = new Date();
            System.out.println("before convert to swf: " + String.valueOf((new Date()).getTime() - nowdate.getTime()));

            Process pro = Runtime.getRuntime()
                    .exec(PropertiesUtils
                            .GetString(FileUtils.IsLinux() ? OfficeUtils.LINUX_SWF_CONVERT_EXE
                                    : OfficeUtils.SWF_CONVERT_EXE)
                            + " -z -s  flashversion=9 " + "\"" + pdf.getAbsolutePath() + "\"" + " -o " + "\""
                            + swf.getAbsolutePath() + "\"");

            // BufferedReader br = new BufferedReader(new
            // InputStreamReader(pro.getInputStream()));
            // String text = "";
            // while ((text = br.readLine()) != null) {
            // System.out.println(text);
            // }

            pro.waitFor();
            System.out.println("end convert to swf: " + String.valueOf((new Date()).getTime() - nowdate.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }

    public static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }

    public static String GetFontName() {
        if (isWindows())
            return "宋体";
        else {
            return "simsun";
        }
    }

}
