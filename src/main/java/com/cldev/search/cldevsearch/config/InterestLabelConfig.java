package com.cldev.search.cldevsearch.config;

import com.cldev.search.cldevsearch.bo.LabelBO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

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
@Configuration
@ConfigurationProperties(prefix = "search-config")
public class InterestLabelConfig {

    private List<LabelBO> labels;

    private String labelMappingFile;

    public String getLabelMappingFile() {
        return labelMappingFile;
    }

    public void setLabelMappingFile(String labelMappingFile) {
        this.labelMappingFile = labelMappingFile;
    }

    private void loadLabelFormFile() {
        File labelFile = new File(this.getLabelMappingFile());
    }

}
