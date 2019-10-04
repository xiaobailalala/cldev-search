package com.cldev.search.cldevsearch.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright © 2018 eSunny Info. Developer Stu. All rights reserved.
 * <p>
 * code is far away from bug with the animal protecting
 * <p>
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author zpx
 * Build File @date: 2019/9/24 15:41
 * @version 1.0
 * @description
 */
public class ProcessUtil {

    public static String executeCmd(String cmd) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(new String[]{"/bin/sh", "-c", cmd});
            InputStream inputStream = process.getInputStream();
            StringBuilder builder = new StringBuilder();
            byte[] bytes = new byte[1024];
            for (int i; (i = inputStream.read(bytes)) != -1; ) {
                builder.append(new String(bytes, 0, i));
            }
            inputStream.close();
            process.destroy();
            return builder.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
