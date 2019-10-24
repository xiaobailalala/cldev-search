package com.cldev.search.cldevsearch.process.common;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


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
    public void labelOperator() {
        File labelFile = new File("C:\\Users\\cl24\\Desktop\\deploy-script\\ubuntu\\kol_csv\\allBlog_sum_res.csv");
        try (InputStream inputStream = new FileInputStream(labelFile);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid", "label")
                     .withSkipHeaderRecord(false))) {
            Set<String> labelSet = new LinkedHashSet<>();
            for (CSVRecord item : csvRecords) {
                String labelStr = item.get("label");
                if (!StringUtils.isEmpty(labelStr)) {
                    for (String label : labelStr.split("\\|")) {
                        if (!StringUtils.isEmpty(label) && !ObjectUtils.isEmpty(label)) {
                            labelSet.add(label);
                        }
                    }
                }
            }
            File file = new File("label-mapping-02");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file, true);
                int index = 0;
                for (String s : labelSet) {
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

            System.out.println(labelSet.size());
        } catch (IOException ignored) {
        }
    }

    @Test
    public void csvParse() {
        File csvFile = new File("C:\\Users\\cl24\\Desktop\\record.csv");
        try (InputStream inputStream = new FileInputStream(csvFile);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("time", "ip", "userId", "title", "msg", "equipment")
                     .withSkipHeaderRecord(false))) {
            System.out.println(csvRecords.getRecords().size());
            for (CSVRecord record : csvRecords.getRecords()) {
                System.out.println(record.get("time") + " " + record.get("ip") + " " + record.get("userId") + " " + record.get("title") + " " + record.get("msg") + " " + record.get("equipment"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readCsv() {
        File csvFile = new File("C:\\Users\\cl24\\Desktop\\res.csv");
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
    public void searchLabelFromRedis() {
        Jedis jedis = new Jedis("192.168.2.11", 6399);
        jedis.auth("cldev");
        String item = jedis.get("6760832163");
        System.out.println(item);
    }

    @Test
    public void insertRedis() throws IOException {
        Jedis jedis = new Jedis("192.168.2.11", 6399);
        jedis.auth("cldev");
        File csvFile = new File("C:\\Users\\cl24\\Desktop\\deploy-script\\ubuntu\\kol_csv\\allBlog_sum_res.csv");
        File mappingFile = new File("label-mapping-02");
        BufferedReader reader = new BufferedReader(new FileReader(mappingFile));
        String tempString;
        Map<String, String> mapping = new HashMap<>(60);
        while ((tempString = reader.readLine()) != null) {
            String[] str = tempString.split(" ");
            mapping.put(str[0], str[1]);
        }
        reader.close();
        try (InputStream inputStream = new FileInputStream(csvFile);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid", "label")
                     .withSkipHeaderRecord(false))) {
            for (CSVRecord record : csvRecords) {
                String labelStr = record.get("label");
                Set<String> labelSet = new HashSet<>();
                if (!StringUtils.isEmpty(labelStr)) {
                    for (String label : labelStr.split("\\|")) {
                        if (!StringUtils.isEmpty(label) && !ObjectUtils.isEmpty(label)) {
                            labelSet.add(label);
                        }
                    }
                }
                StringBuilder result = new StringBuilder();
                for (String item : labelSet) {
                    result.append(mapping.get(item)).append(",");
                }
                String res = result.length() == 0 ? "" : result.substring(0, result.length() - 1);
                jedis.set(record.get("uid"), res);
            }
        }
    }

}
