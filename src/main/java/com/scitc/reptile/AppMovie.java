package com.scitc.reptile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scitc.utils.IOTransUtil;
import com.scitc.utils.PathUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
/**
 * 请在下createConnection()方填写自己 的cookie;
 */
public class AppMovie {
    //文件名称
    private static String title;
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //输入av号
        Integer avid = 69726730;
        //建立连接，先获取到 cid
        String cidJson = createConnectionToJson(avid);
        Integer cid = JsonGetCid(cidJson);
        // 根据cid拼接成完整的请求参数,并执行下载操作
        downloadMovie(avid, cid);
        long end = System.currentTimeMillis();
        System.err.println("总共耗时：" + (end - start) / 1000 + "s");
    }

    // 3-2  建立URL连接请求
    private static InputStream createInputStream(String movieUrl,Integer avid) {
        InputStream inputStream = null;
        try {
            URL url = new URL(movieUrl);
            URLConnection urlConnection = url.openConnection();
            String refererUrl = "https://www.bilibili.com/video/av" + avid;
            urlConnection.setRequestProperty("Referer",refererUrl );
            urlConnection.setRequestProperty("Sec-Fetch-Mode", "no-cors");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
            urlConnection.setConnectTimeout(10 * 1000);

            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("获取inputStream失败");
        }
        return inputStream;
    }

    public static void downloadMovie(Integer avid, Integer cid) {
        //qn ： 视频质量         112 -> 高清 1080P+,   80 -> 高清 1080P,   64 -> 高清 720P,  32  -> 清晰 480P,  16 -> 流畅 360P
        // 最高支持 1080p,  1080P+是不支持的
        Integer qn = 80;
        String paraUrl = "https://api.bilibili.com/x/player/playurl?cid=" + cid + "&fnver=0&qn=" + qn + "&otype=json&avid=" + avid + "&fnval=2&player=1";
        System.out.println("构建的url为：" + paraUrl);
        // 获取到的是json，然后筛选出里面的视频资源：url
        String jsonText = createConnection(paraUrl);

        JSONObject jsonObject = JSON.parseObject(jsonText);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("durl");

        Map<String, String> dUrlMap = (Map) jsonArray.get(0);
        String movieUrl = dUrlMap.get("url");

        System.out.println("视频资源url为：" + movieUrl);
        // 根据获取的title 创建文件
        String moviePath = PathUtil.createMoviePath(title);
        //建立连接
        InputStream inputStream = createInputStream(movieUrl,avid);
        //开始流转换
        IOTransUtil.inputStreamToFile(inputStream, moviePath);
    }
    // 2. 获取到的json选择出cid，只能选择出一个cid，还有标题
    public static Integer JsonGetCid(String cidJson) {
        //转换成json
        JSONObject jsonObject = JSON.parseObject(cidJson);
        //cid
        JSONObject jsonData = jsonObject.getJSONObject("data");

        JSONArray jsonArray = jsonData.getJSONArray("pages");
        Map<String, Object> pageMap = (Map) jsonArray.get(0);
        Integer cid = (Integer) pageMap.get("cid");
        System.out.println("cid: " + cid);
        //title
        title = jsonData.getString("title").replace("/","");
        System.out.println("title:" + title);
        return cid;
    }

    // 1. 建立连接拿到 json 数据
    public static String createConnectionToJson(Integer avid) {
        String cidUrl = "https://api.bilibili.com/x/web-interface/view?aid=" + avid;
        //放完 movie地址
        String cidJson = createConnection(cidUrl);
        return cidJson;
    }

    //0. 建立连接,返回页面中的json
    public static String createConnection(String url) {
        String jsonText = null;
        Connection connection = Jsoup.connect(url).timeout(3000).ignoreContentType(true);
        Map<String, String> heads = new HashMap<>();
        heads.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        heads.put("Accept-Encoding", "gzip, deflate, br");
        heads.put("Accept-Language", "en,zh-CN;q=0.9,zh;q=0.8");
        heads.put("Cache-Control", "max-age=0");
        heads.put("Connection", "keep-alive");

        //TODO 请在这里填写自己的cookie,没有cookid将会请求不到
        heads.put("Cookie", "buvid3=D56986F6-B5F3-4CB4-9F06-AD2249DABBAF110240infoc; LIVE_BUVID=AUTO3115635914121666; sid=al8wlkag; CURRENT_FNVAL=16; stardustvideo=1; rpdid=|(u~JYRJ~~ku0J'ulYJlmu~Jm; fts=1563591901; UM_distinctid=16c0d5975ab402-01f11862f3fd34-c343162-1fa400-16c0d5975ac7cf; _uuid=C4B6D2BB-7B77-4BED-7A8D-09F1FB99F69364553infoc; im_notify_type_435099730=2; im_notify_type_28569524=0; stardustpgcv=0606; CURRENT_QUALITY=116; im_seqno_28569524=4605; im_local_unread_28569524=0; pgv_pvi=725309440; DedeUserID=28569524; DedeUserID__ckMd5=ec1c9a1d56dcb4cb; SESSDATA=12f3317c%2C1571299325%2C4df3b891; bili_jct=6148bb2fdfcefbabff339bffe404b594; laboratory=1-1; bp_t_offset_0=304444539124544340; flash_player_gray=false; html5_player_gray=false; bp_t_offset_28569524=306356662856769168");

        heads.put("Host", "api.bilibili.com");
        heads.put("Sec-Fetch-Mode", "navigate");
        heads.put("Sec-Fetch-Site", "none");
        heads.put("Sec-Fetch-User", "?1");
        heads.put("Upgrade-Insecure-Requests", "1");
        heads.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
        connection.headers(heads);
        try {
            jsonText = connection.get().text();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("建立获取cid连接失败");
        }
        return jsonText;
    }
}
