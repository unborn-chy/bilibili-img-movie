package com.scitc.reptile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scitc.utils.IOTransUtil;
import com.scitc.utils.PathUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class AppImg {
    public static void main(String[] args) {
        int imageNum = 1;
        int pageSize = 149;//总页数
        for (int page = 1; page <= pageSize; page++) {

            String url = "https://api.bilibili.com/pgc/season/index/result?season_version=-1&area=-1&is_finish=-1&copyright=-1&season_status=-1&season_month=-1&year=-1&style_id=-1&order=3&st=1&sort=0&page="
                    + page + "&season_type=1&pagesize=20&type=1";

            Document document = null;
            try {
                document = Jsoup.connect(url).timeout(3000).ignoreContentType(true).get();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("解析url失败");
            }
            //转换为JSON
            JSONObject jsonObject = JSON.parseObject(document.text());

            List<String> coverList = JSONObjectToCoverList(jsonObject);
            for (String cover : coverList) {
                //创建路径
                String imagePath = PathUtil.createImagePath(cover);
                InputStream inputStream =createUrlConnection(cover);
                System.err.println("第 " + imageNum++ + " 张图片下载完成");
                //流转换
                IOTransUtil.inputStreamToFile(inputStream, imagePath);
            }
            System.err.println("-------第 " + page + " 页图片爬取完成");
        }

    }
    public static InputStream createUrlConnection(String cover) {
        InputStream inputStream = null;
        try {
            URL imgUrl = new URL(cover);
            URLConnection urlConnection = imgUrl.openConnection();
            urlConnection.setConnectTimeout(10 * 1000);
            inputStream = urlConnection.getInputStream();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("URL创建失败");
        }
        return inputStream;

    }



    // 把json转化为只含有 链接 的集合
    public static List<String> JSONObjectToCoverList(JSONObject jsonObject) {
        List<String> coverList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("list");
        for (int i = 0; i < jsonArray.size(); i++) {
            Map<String, String> map = (Map) jsonArray.get(i);
            coverList.add(map.get("cover"));
        }
        return coverList;
    }
}
