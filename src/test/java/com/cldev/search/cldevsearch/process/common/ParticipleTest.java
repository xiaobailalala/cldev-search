package com.cldev.search.cldevsearch.process.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
 * Build File @date: 2019/10/23 14:05
 * @version 1.0
 * @description
 */
public class ParticipleTest {

    @Test
    public void participleLabel() {
        MultiValueMap<String, String> labelMapping = new LinkedMultiValueMap<>();
        resolverParticipleWithoutLabel(labelMapping);
        resolverParticipleWithLabel(labelMapping);
        System.out.println(labelMapping.size());
    }

    private void resolverParticipleWithoutLabel(MultiValueMap<String, String> labelMapping) {
        File folder = new File("/Users/zhaopeixian/Office/shareFolder/中文分词词库汇总/ToTampopo");
        Set<String> participle = generatorNormalParticiple();
        int count = 0;
        for (String item : participle) {
            boolean isFilter = true;
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.getName().contains("-") && !file.getName().contains("@")) {
                    String label = file.getName().split("-")[1].split("\\.")[0];
                    if (label != null) {
                        try (BufferedReader labelFile = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                            String msg;
                            while ((msg = labelFile.readLine()) != null && !StringUtils.isEmpty(msg) && msg.equals(item)) {
                                labelMapping.add(item, label);
                                System.out.println(msg + ": " + labelMapping.get(item));
                                isFilter = false;
                            }
                        } catch (IOException ignore) {
                        }
                    }
                }
            }
            count++;
            if (isFilter) {
                System.out.println(String.format("[%s] winnow : %.2f%%", item, ((double) count / participle.size()) * 100));
            } else {
                loadVocabularyLabel(item + "-" + labelMapping.get(item));
                System.out.println(String.format("%s-%s : %.2f%%", item, labelMapping.get(item), ((double) count / participle.size()) * 100));
            }
        }
    }

    private void resolverParticipleWithLabel(MultiValueMap<String, String> labelMapping) {
        File folder = new File("/Users/zhaopeixian/Office/shareFolder/中文分词词库汇总/第一轮词库分类");
        Set<String> participle = generatorNormalParticiple();
        Map<String, String> mapping = generatorLabelMapping();
        for (String item : participle) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                try (InputStream inputStream = new FileInputStream(file);
                     CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("context", "kind")
                             .withSkipHeaderRecord(false))) {
                    for (CSVRecord record : csvRecords.getRecords()) {
                        String label = mapping.get(record.get("kind")), context = record.get("context");
                        if (!ObjectUtils.isEmpty(context) && !StringUtils.isEmpty(context) && context.equals(item)) {
                            labelMapping.add(item, label);
                        }
                    }
                } catch (IOException ignore) {
                }
            }
        }
    }

    private Map<String, String> generatorLabelMapping() {
        Map<String, String> labelMapping = new HashMap<>(64);
        File brandWeiboMapping = new File("/Users/zhaopeixian/Office/shareFolder/中文分词词库汇总/brand_weibo_mapping.json");
        File productWeiboMapping = new File("/Users/zhaopeixian/Office/shareFolder/中文分词词库汇总/product_weibo_mapping.json");
        try (BufferedReader brandReader = new BufferedReader(new InputStreamReader(new FileInputStream(brandWeiboMapping), StandardCharsets.UTF_8));
             BufferedReader productReader = new BufferedReader(new InputStreamReader(new FileInputStream(productWeiboMapping), StandardCharsets.UTF_8))) {
            StringBuilder brandJsonStr = new StringBuilder(), productJsonStr = new StringBuilder();
            String msg;
            while ((msg = brandReader.readLine()) != null) {
                brandJsonStr.append(msg);
            }
            while ((msg = productReader.readLine()) != null) {
                productJsonStr.append(msg);
            }
            for (Map.Entry<String, Object> item : JSONObject.parseObject(brandJsonStr.toString()).entrySet()) {
                labelMapping.put(item.getKey(), item.getValue().toString());
            }
            for (Map.Entry<String, Object> item : JSONObject.parseObject(productJsonStr.toString()).entrySet()) {
                labelMapping.put(item.getKey(), item.getValue().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return labelMapping;
    }

    private Set<String> generatorNormalParticiple() {
        File folder = new File("/Users/zhaopeixian/Office/shareFolder/中文分词词库汇总");
        Set<String> normalParticiple = new HashSet<>();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.getName().endsWith(".txt")) {
                try (BufferedReader participleFile = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                    String msg;
                    while ((msg = participleFile.readLine()) != null && !StringUtils.isEmpty(msg)) {
                        normalParticiple.add(msg.trim());
                    }
                } catch (IOException ignore) {
                }
            }
        }
        return normalParticiple;
    }

    private void loadVocabularyLabel(String label) {
        File file = new File("vocabulary-label");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
            byte[] contentInBytes = (label + "\r\n").getBytes();
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

    @Test
    public void commonTest() {
        System.out.println(generatorNormalParticiple().size());
    }

    @Test
    public void charsetTest() {
        try (BufferedReader participleFile = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/zhaopeixian/Office/shareFolder/中文分词词库汇总/中国传统节日大全.txt")), StandardCharsets.UTF_8))) {
            String msg;
            while ((msg = participleFile.readLine()) != null && !StringUtils.isEmpty(msg)) {
//                if (isMessyCode(msg)) {
//                    String s = new String(msg.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_16);
//                    System.out.println(s);
//                }
                System.out.println(msg);
            }
        } catch (IOException ignore) {
        }
    }

}
