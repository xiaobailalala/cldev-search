package com.cldev.search.cldevsearch.config;

import com.cldev.search.cldevsearch.util.BeanUtil;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
public class SearchRegistryConfig implements CommandLineRunner {

    @Value("${spring.profiles}")
    private String profiles;

    private volatile ConcurrentHashMap<String, Integer> labels;
    private volatile MultiValuedMap<String, String> nameMapping;
    private volatile Set<String> newsMedia;
    private volatile ConcurrentHashMap<String, Integer> onlineWaterAmy;

    private final static String ENVIRONMENT_DEV = "dev";
    private final static String ENVIRONMENT_PRO = "pro";

    @Override
    public void run(String... args) throws Exception {
        if (!BeanUtil.searchConfig().getLoadData()) {
            if (ObjectUtils.isEmpty(BeanUtil.searchConfig().getLabelMappingFile())) {
                throw new RuntimeException("Must specify The label mapping file");
            }
            if (ObjectUtils.isEmpty(BeanUtil.searchConfig().getNameMappingFile())) {
                throw new RuntimeException("Must specify The name mapping file");
            }
            if (ObjectUtils.isEmpty(BeanUtil.searchConfig().getNewsMediaFile())) {
                throw new RuntimeException("Must specify The news media file");
            }
            labels = new ConcurrentHashMap<>(80);
            nameMapping = new ArrayListValuedHashMap<>(2048);
            newsMedia = Collections.synchronizedSet(new HashSet<>(1024));
            onlineWaterAmy = new ConcurrentHashMap<>(2048);
            loadLabelMapping(BeanUtil.searchConfig().getLabelMappingFile());
            loadNameMapping(BeanUtil.searchConfig().getNameMappingFile());
            loadNewsMedia(BeanUtil.searchConfig().getNewsMediaFile());
            loadOnlineWaterAmy(BeanUtil.searchConfig().getWaterAmyFile());
        }
    }

    public void loadLabelMapping(String labelMappingFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileChecked(labelMappingFile)));
        String tempString;
        while ((tempString = reader.readLine()) != null) {
            String[] str = tempString.split(" ");
            labels.put(str[0], Integer.parseInt(str[1]));
        }
        reader.close();
    }

    public void loadNameMapping(String nameMappingFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileChecked(nameMappingFile)));
        String tempString;
        while ((tempString = reader.readLine()) != null) {
            String[] str = tempString.split("@@@@@@@@");
            nameMapping.put(str[0], str[1]);
            nameMapping.put(str[0], str[0]);
            nameMapping.put(str[1], str[0]);
            nameMapping.put(str[1], str[1]);
        }
        reader.close();
    }

    public void loadNewsMedia(String newsMediaFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileChecked(newsMediaFile)));
        String tempString;
        while ((tempString = reader.readLine()) != null) {
            String[] str = tempString.split(" {6}");
            newsMedia.add(str[0]);
        }
        reader.close();
    }

    public void loadOnlineWaterAmy(String waterAmyFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileChecked(waterAmyFile)));
        String tempString;
        while ((tempString = reader.readLine()) != null) {
            String[] str = tempString.split(" {2}");
            onlineWaterAmy.put(str[0], Integer.parseInt(str[1]));
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

    public Integer getInterestOne(String interest) {
        if (!ObjectUtils.isEmpty(interest) && !StringUtils.isEmpty(interest)) {
            return labels.get(interest);
        }
        return null;
    }

    public List<String> getScreenName(String name) {
        Collection<String> filterName = nameMapping.asMap().get(name);
        if (filterName == null) {
            return new ArrayList<>();
        }
        return filterName.stream().distinct().collect(Collectors.toList());
    }

    public Set<String> getNewsMedia() {
        return this.newsMedia;
    }

    public ConcurrentHashMap<String, Integer> getOnlineWaterAmy() {
        return this.onlineWaterAmy;
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

    public String updateNameMapping(String fileName) {
        nameMapping = new ArrayListValuedHashMap<>(2048);
        try {
            loadNameMapping(fileName);
        } catch (IOException e) {
            return "Refresh the name mapping error";
        }
        return "success";
    }

    public String updateNewsMedia(String fileName) {
        newsMedia = Collections.synchronizedSet(new HashSet<>(1024));
        try {
            loadNewsMedia(fileName);
        } catch (IOException e) {
            return "Refresh the news media error";
        }
        return "success";
    }

    public String updateWaterAmy(String fileName) {
        onlineWaterAmy = new ConcurrentHashMap<>(2048);
        try {
            loadOnlineWaterAmy(fileName);
        } catch (IOException e) {
            return "Refresh the online water amy error";
        }
        return "success";
    }

    public ConcurrentHashMap<String, Integer> getLabels() {
        return this.labels;
    }

    private File fileChecked(String fileName) {
        File mappingFile = null;
        if (ENVIRONMENT_DEV.equals(profiles)) {
            mappingFile = new File("config\\" + fileName);
        }
        if (ENVIRONMENT_PRO.equals(profiles)) {
            mappingFile = new File("config/" + fileName);
        }
        if (ObjectUtils.isEmpty(mappingFile)) {
            throw new RuntimeException("Must specify The profile");
        }
        if (!mappingFile.exists()) {
            throw new RuntimeException("The mapping file [" + fileName + "] not found");
        }
        return mappingFile;
    }

}
