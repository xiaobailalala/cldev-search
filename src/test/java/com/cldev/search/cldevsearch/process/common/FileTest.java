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
        File labelFile = new File("C:\\Users\\cl24\\Desktop\\allBlogfiled(1).csv");
        try (InputStream inputStream = new FileInputStream(labelFile);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid", "label")
                     .withSkipHeaderRecord(true))) {
            Set<String> labelSet = new LinkedHashSet<>();
            for (CSVRecord item : csvRecords) {
                String labelStr = item.get("label");
                if (!StringUtils.isEmpty(labelStr)) {
                    for (String label : labelStr.split("_")) {
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
    public void newsOperator() {
        File csvFile = new File("C:\\Users\\cl24\\Desktop\\uid_name_frequency_gte_100_blue.csv");
        try (InputStream inputStream = new FileInputStream(csvFile);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8),
                     CSVFormat.DEFAULT.withHeader("uid", "name", "frequency")
                             .withSkipHeaderRecord(false))) {
            int count = 0;
            for (CSVRecord record : csvRecords.getRecords()) {
                String name = record.get("name");
                if (name.contains("报") || name.contains("刊") || name.contains("新闻") || name.contains("国") || name.contains("财经") || name.contains("邪教")) {
                    printLoadLog("newsMedia", record.get("uid") + "      " + name + "      " + record.get("frequency"));
                    count++;
                }
            }
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void csvParse() {
        File csvFile = new File("C:\\Users\\cl24\\Desktop\\kol无效博文百分比.csv");
        try (InputStream inputStream = new FileInputStream(csvFile);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8),
                     CSVFormat.DEFAULT.withHeader("uid", "count")
                             .withSkipHeaderRecord(true))) {
            for (CSVRecord record : csvRecords.getRecords()) {
                printLoadLog("online-water-amy", record.get("uid") + "  " + record.get("count"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readCsv() {
        File csvFile = new File("C:\\Users\\cl24\\Desktop\\allBlog_sum_res(3).csv");
        try (InputStream inputStream = new FileInputStream(csvFile);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid", "label")
                     .withSkipHeaderRecord(false))) {
            Set<String> label = new LinkedHashSet<>();
            for (CSVRecord record : csvRecords) {
                String labelStr = record.get("label");
                if (!StringUtils.isEmpty(labelStr)) {
                    for (String item : labelStr.split("\\|")) {
                        if (!ObjectUtils.isEmpty(item) && !StringUtils.isEmpty(item)) {
                            label.add(item.split(":")[0]);
                        }
                    }
                }
            }
            System.out.println(label.size());
            System.out.println(label);
        } catch (IOException ignored) {
        }
    }

    @Test
    public void searchLabelFromRedis() {
        Jedis jedis = new Jedis("192.168.2.11", 6399);
        jedis.auth("cldev");
        String item = jedis.get("6226234826");
        System.out.println(item);
    }

    @Test
    public void insertRedis() throws IOException {
        Jedis jedis = new Jedis("192.168.2.11", 6399);
        jedis.auth("cldev");
        File mappingFile = new File("config\\label-mapping-02");
        BufferedReader reader = new BufferedReader(new FileReader(mappingFile));
        String tempString;
        Map<String, String> mapping = new HashMap<>(60);
        while ((tempString = reader.readLine()) != null) {
            String[] str = tempString.split(" ");
            mapping.put(str[0], str[1]);
        }
        reader.close();
        try (InputStream inputStream = new FileInputStream(new File("C:\\Users\\cl24\\Desktop\\allBlog_sum_res(3).csv"));
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
                    String[] label = item.split(":");
                    result.append(mapping.get(label[0])).append("-").append(label[1]).append(",");
                }
                String res = result.length() == 0 ? "" : result.substring(0, result.length() - 1);
                jedis.set(record.get("uid"), res);
            }
        }
        Jedis jedisWithoutScore = new Jedis("192.168.2.11", 6409);
        jedisWithoutScore.auth("cldev");
        try (InputStream inputStream = new FileInputStream(new File("C:\\Users\\cl24\\Desktop\\allBlogfiled(1).csv"));
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid", "label")
                     .withSkipHeaderRecord(true))) {
            for (CSVRecord record : csvRecords) {
                String labelStr = record.get("label");
                Set<String> labelSet = new HashSet<>();
                if (!StringUtils.isEmpty(labelStr)) {
                    for (String label : labelStr.split("_")) {
                        if (!StringUtils.isEmpty(label) && !ObjectUtils.isEmpty(label)) {
                            labelSet.add(label);
                        }
                    }
                }
                StringBuilder result = new StringBuilder();
                for (String item : labelSet) {
                    result.append(mapping.get(item)).append("-");
                }
                String res = result.length() == 0 ? "" : result.substring(0, result.length() - 1);
                jedisWithoutScore.set(record.get("uid"), res);
            }
        }
    }

    private void printLoadLog(String fileName, String msg) {
        File file = new File("config\\" + fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
            byte[] contentInBytes = (msg + "\r\n").getBytes();
            fileOutputStream.write(contentInBytes);
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
    }

}
