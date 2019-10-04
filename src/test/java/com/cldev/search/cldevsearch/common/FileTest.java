package com.cldev.search.cldevsearch.common;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

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
 * Build File @date: 2019/10/2 3:41 下午
 * @version 1.0
 * @description
 */
public class FileTest {

    @Test
    public void readCsv() {
        File csvFile = new File("/Users/zhaopeixian/Office/officeFile/res.csv");
        try (InputStream inputStream = new FileInputStream(csvFile);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid", "type", "label1", "label2", "label3", "labelw")
                     .withSkipHeaderRecord(true))) {
            Set<String> label = new LinkedHashSet<>();
            for (CSVRecord record : csvRecords) {
                if (!StringUtils.isEmpty(record.get("label1"))) {
                    label.add(record.get("label1"));
                }
                if (!StringUtils.isEmpty(record.get("label2"))) {
                    label.add(record.get("label2"));
                }
                if (!StringUtils.isEmpty(record.get("label3"))) {
                    label.add(record.get("label3"));
                }
                if (!StringUtils.isEmpty(record.get("labelw"))) {
                    label.add(record.get("labelw"));
                }
            }

            File file = new File("label-mapping");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file, true);
                int index = 0;
                for (String s : label) {
                    byte[] contentInBytes = (s + " " + (index++) + "\r\n").getBytes();
                    fileOutputStream.write(contentInBytes);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(label.size());
        } catch (IOException ignored) {
        }
    }

    @Test
    public void insertRedis() throws IOException {
        Jedis jedis = new Jedis("192.168.2.11", 6399);
        jedis.auth("cldev");
        File csvFile = new File("/Users/zhaopeixian/Office/officeFile/res.csv");
        File mappingFile = new File("label-mapping");
        BufferedReader reader = new BufferedReader(new FileReader(mappingFile));
        String tempString;
        Map<String, String> mapping = new HashMap<>(40);
        while ((tempString = reader.readLine()) != null) {
            String[] str = tempString.split(" ");
            mapping.put(str[0], str[1]);
        }
        reader.close();
        try (InputStream inputStream = new FileInputStream(csvFile);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid", "type", "label1", "label2", "label3", "labelw")
                     .withSkipHeaderRecord(true))) {
            for (CSVRecord record : csvRecords) {
                int labelCount = 0;
                String label = "";
                String label1 = record.get("label1");
                if (!StringUtils.isEmpty(label1)) {
                    label += mapping.get(label1) + ",";
                    labelCount++;
                }
                String label2 = record.get("label2");
                if (!StringUtils.isEmpty(label2)) {
                    label += mapping.get(label2) + ",";
                    labelCount++;
                }
                String label3 = record.get("label3");
                if (!StringUtils.isEmpty(label3)) {
                    label += mapping.get(label3) + ",";
                    labelCount++;
                }
                if (labelCount < 3) {
                    String labelw = record.get("labelw");
                    if (!StringUtils.isEmpty(labelw)) {
                        label += mapping.get(labelw) + ",";
                    }
                }
                String res = label.length() == 0 ? "" : label.substring(0, label.length() - 1);
                jedis.set(record.get("uid"), res);
            }
        }
    }

}
