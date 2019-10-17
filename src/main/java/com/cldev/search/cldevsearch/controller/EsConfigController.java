package com.cldev.search.cldevsearch.controller;

import com.alibaba.fastjson.JSONObject;
import com.cldev.search.cldevsearch.config.CalculationSearchWeightConfig;
import com.cldev.search.cldevsearch.dto.FactorConfigDTO;
import com.cldev.search.cldevsearch.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * Build File @date: 2019/10/16 20:46
 * @version 1.0
 * @description
 */
@RestController
@RequestMapping("/es/config")
public class EsConfigController {

    private final CalculationSearchWeightConfig config;

    @Autowired
    public EsConfigController(CalculationSearchWeightConfig config) {
        this.config = config;
    }

    @GetMapping("/get/factorConfig")
    public JSONObject getFactorConfig() {
        return config.getConfig();
    }

    @PostMapping("/configure")
    public String configure(@RequestBody FactorConfigDTO factorConfigDTO) {
        return config.configureFactor(factorConfigDTO);
    }

    @GetMapping("/refresh/labelMapping")
    public String refreshLabelMapping(@RequestParam String fileName) {
        return BeanUtil.labelRegistryConfig().updateLabelMapping(fileName);
    }

}
