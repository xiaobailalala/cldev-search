package com.cldev.search.cldevsearch.controller;

import com.cldev.search.cldevsearch.service.EsToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * Build File @date: 2019/9/23 17:35
 * @version 1.0
 * @description
 */
@RestController
@RequestMapping("/es/tools")
public class EsToolsController {

    private final EsToolsService esToolsService;

    @Autowired
    public EsToolsController(EsToolsService esToolsService) {
        this.esToolsService = esToolsService;
    }

    @GetMapping("/loadData")
    public String loadData() {
        return esToolsService.loadData();
    }

    @GetMapping("/loadData/kol")
    public String loadDataForKol() {
        return esToolsService.loadDataForKol();
    }

    @GetMapping("/checkFile")
    public List<String> checkFile() {
        return esToolsService.checkFile();
    }

    @GetMapping("/checkFileCount")
    public String checkFileCount() {
        return esToolsService.checkFileCount();
    }

}
