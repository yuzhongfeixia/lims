package com.gpersist.utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.UUID;

import javax.imageio.stream.FileImageInputStream;

import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.MimeTypes;
import org.apache.avalon.framework.configuration.DefaultConfiguration;

public class FileUtils {

    public static final String FILE_TYPE_ZIP = "zip";
    public static final String FILE_TYPE_PDF = "pdf";
    public static final String FILE_TYPE_SWF = "swf";

    public static boolean IsLinux() {
        boolean rtn = false;

        try {
            URL ut = Thread.currentThread().getContextClassLoader().getResource("");
            String path = ut.toString().replace("file:/", "");

            System.out.print(path);

            if (path.indexOf("var") >= 0) {
                rtn = true;
            }
        } catch (Exception e) {
            rtn = false;
        }

        return rtn;
    }

    public static String GetBasePath() {
        String rtn = "";

        try {
            URL ut = Thread.currentThread().getContextClassLoader().getResource("");
            String path = ut.toString().replace("file:/", "");
            if (path.indexOf("var") >= 0) {
                rtn = PropertiesUtils.GetString(Consts.CONFIG_FILE_LINUX_ROOT);
            } else {
                rtn = PropertiesUtils.GetString(Consts.CONFIG_FILE_WINDOWS_ROOT);
            }

            rtn += PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_SEPARATOR);
        } catch (Exception e) {
            rtn = "";
        }

        return rtn;
    }

    public static String GetFileBasePath() {
        String rtn = "";

        try {
            URL ut = Thread.currentThread().getContextClassLoader().getResource("");
            String path = ut.toString().replace("file:/", "");
            if (path.indexOf("var") >= 0) {
                rtn = PropertiesUtils.GetString(Consts.CONFIG_FILE_LINUX_ROOT);
            } else {
                rtn = PropertiesUtils.GetString(Consts.CONFIG_FILE_WINDOWS_ROOT);
            }

            rtn += PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_SEPARATOR)
                    + PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_BASE)
                    + PropertiesUtils.GetString(Consts.CONFIG_FILE_PATH_SEPARATOR);
        } catch (Exception e) {
            rtn = "";
        }

        return rtn;
    }

    public static String GetUUIDFileName(String suffix) {
        return UUID.randomUUID().toString().toLowerCase().replaceAll("-", "") + "." + suffix;
    }

    public static String ChangeFileSuffix(String filename, String suffix) {
        return filename.substring(0, filename.lastIndexOf(".") + 1) + suffix;
    }

    public static String GetFileSuffix(String filename) {
        return filename.substring(0, filename.lastIndexOf("."));
    }

    public static void CreateFolder(String path) {
        File newdir = new File(path);
        if (!newdir.exists() && !newdir.isDirectory())
            newdir.mkdirs();
    }

    public static void DeleteFolder(String path) {
        File file = new File(path);

        if (file.exists()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory()) {
                if (file.listFiles().length > 0) {
                    File delFile[] = file.listFiles();
                    int i = file.listFiles().length;

                    for (int j = 0; j < i; j++) {
                        if (delFile[j].isDirectory()) {
                            DeleteFolder(delFile[j].getAbsolutePath());
                        }

                        delFile[j].delete();
                    }
                }

                file.delete();
            }
        }
    }

    public static boolean isFile(File file) {
        return file.exists() && file.isFile();
    }

    public static boolean isDirectory(File file) {
        return file.exists() && file.isDirectory();
    }

    public static void FileChannelCopy(File source, File target) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;

        try {
            fi = new FileInputStream(source);
            fo = new FileOutputStream(target);
            in = fi.getChannel();
            out = fo.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void CopyFile(String sourcefile, String destfile) throws Exception {
        CopyFile(sourcefile, destfile, true);
    }

    public static void CopyFile(String sourcefile, String destfile, boolean overlay) throws Exception {
        File srcFile = new File(sourcefile);

        if (!srcFile.exists()) {
            throw new Exception("1源文件：" + sourcefile + "不存在！");
        } else if (!srcFile.isFile()) {
            throw new Exception("2复制文件失败，源文件：" + sourcefile + "不是一个文件！");
        }

        File destFile = new File(destfile);
        if (destFile.exists()) {
            if (overlay) {
                new File(destfile).delete();
            }
        } else {
            if (!destFile.getParentFile().exists()) {
                if (!destFile.getParentFile().mkdirs()) {
                    return;
                }
            }
        }

        FileChannelCopy(srcFile, destFile);
    }

    public static byte[] Image2Byte(String path) {
        byte[] data = null;
        FileImageInputStream input = null;

        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }

            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException exfile) {
            exfile.printStackTrace();
        } catch (IOException exio) {
            exio.printStackTrace();
        }

        return data;
    }

    public static byte[] BarCode(String msg) {
        byte[] data = null;

        try {
            DefaultConfiguration cfg = new DefaultConfiguration("barcode");

            DefaultConfiguration child = new DefaultConfiguration("code128");
            cfg.addChild(child);

            DefaultConfiguration attr = new DefaultConfiguration("height");
            attr.setValue(12);
            child.addChild(attr);

            attr = new DefaultConfiguration("human-readable");

            DefaultConfiguration subAttr = new DefaultConfiguration("font-size");
            subAttr.setValue("3mm");
            attr.addChild(subAttr);

            child.addChild(attr);

            BarcodeUtil util = BarcodeUtil.getInstance();
            BarcodeGenerator gen = util.createBarcodeGenerator(cfg);

            int resolution = 300;

            ByteArrayOutputStream bout = new ByteArrayOutputStream(4096);
            try {
                BitmapCanvasProvider bitmap = new BitmapCanvasProvider(bout, MimeTypes.MIME_JPEG, resolution,
                        BufferedImage.TYPE_BYTE_BINARY, false, 0);

                gen.generateBarcode(bitmap, msg);
                bitmap.finish();
            } finally {
                bout.close();
            }

            data = bout.toByteArray();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return data;
    }
}
