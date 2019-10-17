package com.cldev.search.cldevsearch.config;

import com.cldev.search.cldevsearch.util.BeanUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
 * Build File @date: 2019/10/17 10:34
 * @version 1.0
 * @description
 */
@Configuration
@EnableConfigurationProperties(SearchConfig.class)
public class LabelRegistryConfig implements CommandLineRunner {

    private volatile ConcurrentHashMap<String, Integer> labels;

    @Override
    public void run(String... args) throws Exception {
        if (ObjectUtils.isEmpty(BeanUtil.searchConfig().getLabelMappingFile())) {
            throw new RuntimeException("Must specify The label mapping file");
        }
        labels = new ConcurrentHashMap<>(80);
        loadLabelMapping(BeanUtil.searchConfig().getLabelMappingFile());
    }

    public void loadLabelMapping(String labelMappingFile) throws IOException {
        File mappingFile = new File(labelMappingFile);
        if (!mappingFile.exists()) {
            throw new RuntimeException("The label mapping file [" + labelMappingFile + "] not found");
        }
        BufferedReader reader = new BufferedReader(new FileReader(mappingFile));
        String tempString;
        while ((tempString = reader.readLine()) != null) {
            String[] str = tempString.split(" ");
            labels.put(str[0], Integer.parseInt(str[1]));
        }
        reader.close();
    }

    public List<Integer> getInterest(String[] interest) {
        LinkedList<Integer> integers = new LinkedList<>();
        if (!ObjectUtils.isEmpty(interest)) {
            for (String item : interest) {
                Integer code = labels.get(item);
                if (!ObjectUtils.isEmpty(code)) {
                    integers.add(code);
                }
            }
        }
        return integers;
    }

    public String updateLabelMapping(String fileName) {
        labels = new ConcurrentHashMap<>(80);
        try {
            loadLabelMapping(fileName);
        } catch (IOException e) {
            return "Refresh the label mapping error";
        }
        return "success";
    }

    public ConcurrentHashMap<String, Integer> getLabels() {
        return this.labels;
    }

}
