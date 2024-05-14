package com.gpersist.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import sun.misc.BASE64Decoder;

/**
 * Created by Lazyeraser on 2017/1/12.
 */
public class JsonUtils {

    public static <T> T getBeanFromJson(String json, Type type) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        T item = gson.fromJson(json, type);
        return item;
    }

    public static <T> T getBeanFromJson(String json, TypeToken<T> classtype) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        T item = gson.fromJson(json, classtype.getType());
        return item;
    }

    public static <T> List<T> getArrayFromJson(String json, TypeToken<List<T>> classtype) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        List<T> lists = gson.fromJson(json, classtype.getType());
        return lists;
    }

    public static String getJsonFromBean(Object bean) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(bean);
    }

    public static String getJsonFromArray(List<Object> lists) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(lists);
    }

    public static String getPhotoByBuffer(String photoBuffer) throws Exception {
        // 图片存放路径
        String path = FileUtils.GetFileBasePath();

        // 按日期归档文件，以便整理
        String rpath;
        rpath = "file/" + ToolUtils.GetFmtDate(ToolUtils.GetNowDate(), "yyyyMMdd") + "/";

        String newFilePath = path + rpath;
        // String newFilePath = "E:/Source/Java/";
        String newFileName = UUID.randomUUID().toString().toLowerCase().replaceAll("-", "") + ".jpg";
        FileOutputStream fos = null;
        byte[] buffer;
        // 图片字符串进行解码
        buffer = new BASE64Decoder().decodeBuffer(photoBuffer);
        File destDir = new File(newFilePath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        fos = new FileOutputStream(new File(destDir, newFileName)); // 保存图片
        fos.write(buffer);
        fos.flush();
        fos.close();
        // Utils.mPrint("上传图片成功!" + newFileName);
        System.out.print("上传图片成功" + newFileName);
        return rpath + newFileName;

    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate;
        formatDate = sdf.format(date);
        return formatDate;
    }

}
