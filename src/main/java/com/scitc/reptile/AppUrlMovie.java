package com.scitc.reptile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class AppUrlMovie {
    public static void main(String[] args) {
        System.out.println("开始");
        long start = System.currentTimeMillis();
        /**
         *  从json中获取到的 url
         *  请获取后手动填写
         */
        String lastUrl = "http://upos-hz-mirrorkodou.acgvideo.com/upgcxcode/22/48/111804822/111804822-1-80.flv?e=ig8euxZM2rNcNbu37zUVhoManwuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&uipk=5&nbs=1&deadline=1570200690&gen=playurl&os=kodou&oi=1033761651&trid=c05e1a0ff69a47a08dbb4b39210cbd49u&platform=pc&upsig=03d0df9f30c814d21e70d4e2e1be2e17&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=28569524";
        //自定义文件名称
        String fileName = "a.mp4";
        uploadMovie(lastUrl, fileName);

        long end = System.currentTimeMillis();
        System.out.println("完成 ");
        System.err.println("总共耗时：" + (end - start) / 1000 + "s");
    }

    public static void uploadMovie(String BLUrl, String fileName) {
        InputStream inputStream = null;
        try {
            URL url = new URL(BLUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Referer", "https://www.bilibili.com/video/av69924947"); // 填需要爬取的av号
            urlConnection.setRequestProperty("Sec-Fetch-Mode", "no-cors");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
            urlConnection.setConnectTimeout(10 * 1000);
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //定义路径
        String path = "G:\\app\\" + fileName;
        File file = new File(path);
        int i = 1;
        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bys = new byte[1024];
            int len = 0;
            while ((len = bis.read(bys)) != -1) {
                bos.write(bys, 0, len);
                System.out.println("写入第 " + i++ + "次");
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
