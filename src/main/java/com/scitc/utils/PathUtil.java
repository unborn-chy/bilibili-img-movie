package com.scitc.utils;

import java.io.File;

public class PathUtil {
    //创建图片路径
    public static String createImagePath(String cover) {
        System.out.println("开始创建图片路径");
        //图片名称
        String imgName = cover.substring(cover.lastIndexOf("/") + 1);
        //创建路径
        String path = "G://BiliBili//images";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = dir + File.separator + imgName;
        System.out.println("图片路径：" + fileName);
        return fileName;
    }

    // 创建视频路径
    public static String createMoviePath(String title) {
        System.out.println("开始创建图片路径");
        //图片名称
        String movieName = title + ".mp4";
        //创建路径
        String path = "G://BiliBili//movie";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = dir + File.separator + movieName;
        System.out.println("视频路径：" + fileName);
        return fileName;
    }
}
