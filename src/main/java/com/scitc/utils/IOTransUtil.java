package com.scitc.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class IOTransUtil {

    public static void inputStreamToFile(InputStream inputStream, String imagePath) {
        int i = 1;
        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imagePath));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
                System.out.println("写入第 " + i++ + "次" );
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("inputStream转换异常");
        }

    }
}
