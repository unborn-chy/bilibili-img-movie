package com.scitc;

import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class AppMovie {
    // 改成移动端版本
    @Test
    public void test1(){
        String BLUrl = "http://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/22/48/111804822/111804822-1-6.mp4?e=ig8euxZM2rNcNbuVhwdVtWuVhwdVNEVEuCIv29hEn0l5QK==&uipk=5&nbs=1&deadline=1570116563&gen=playurl&os=ks3u&oi=1033761651&trid=104e853173184c64881fa426700428eah&platform=html5&upsig=30bb82abe414a6264de95bb5e640bbcd&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=0";
        String fileName = "b.mp4";


    }

    // 使用flash模式 ，找到 flv 获取连接进行下载
    @Test
    public void test2() throws IOException {
        String fileName = "c_360-2_json-2.mp4";

        String lastUrl = "http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/22/48/111804822/111804822-1-15.flv?e=ig8euxZM2rNcNbRM7WdVhoM17wUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&uipk=5&nbs=1&deadline=1570188762&gen=playurl&os=cosu&oi=1033761651&trid=9293980488b74138b4aa492ff08ef0b7u&platform=pc&upsig=82c36ff7d6ee33841b2de0dc1a10c86a&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=28569524";

        uploadMovie(lastUrl,fileName);
    }

    public static void uploadMovie(String BLUrl,String fileName) {
        InputStream inputStream = null;
        long start = System.currentTimeMillis();
        try {
            URL url = new URL(BLUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Referer","https://www.bilibili.com/video/av58906853");
            urlConnection.setRequestProperty("Sec-Fetch-Mode","no-cors");
            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
            urlConnection.setConnectTimeout(10 * 1000);

            inputStream= urlConnection.getInputStream();
        }catch (IOException e){
            e.printStackTrace();
        }
        String path = "G:\\app\\" + fileName ;
        File file =  new File(path);

        System.out.println("开始");
        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

            byte[] bys = new byte[10240];
            int len = 0;
            while ((len = bis.read(bys))!=-1){
                bos.write(bys,0,len);
            }
            bis.close();
            bos.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("完成 ");
        System.err.println("总共耗时：" + (end-start)/1000  + "s");
    }

}
