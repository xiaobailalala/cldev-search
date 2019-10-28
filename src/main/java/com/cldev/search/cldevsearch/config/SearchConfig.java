package com.cldev.search.cldevsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
 * Build File @date: 2019/10/17 1:58 上午
 * @version 1.0
 * @description
 */
@ConfigurationProperties(prefix = "search-config")
public class SearchConfig {

    private String labelMappingFile;
    private String nameMappingFile;
    private String newsMediaFile;
    private boolean loadData;

    public String getNameMappingFile() {
        return nameMappingFile;
    }

    public void setNameMappingFile(String nameMappingFile) {
        this.nameMappingFile = nameMappingFile;
    }

    public String getNewsMediaFile() {
        return newsMediaFile;
    }

    public void setNewsMediaFile(String newsMediaFile) {
        this.newsMediaFile = newsMediaFile;
    }

    public String getLabelMappingFile() {
        return labelMappingFile;
    }

    public void setLabelMappingFile(String labelMappingFile) {
        this.labelMappingFile = labelMappingFile;
    }

    public boolean getLoadData() {
        return loadData;
    }

    public void setLoadData(boolean loadData) {
        this.loadData = loadData;
    }
}
