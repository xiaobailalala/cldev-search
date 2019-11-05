package com.cldev.search.cldevsearch.controller;

import com.cldev.search.cldevsearch.dto.BlogDataDTO;
import com.cldev.search.cldevsearch.dto.UserInfoDTO;
import com.cldev.search.cldevsearch.dto.UserLabelDTO;
import com.cldev.search.cldevsearch.dto.UserReportDTO;
import com.cldev.search.cldevsearch.service.EsToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/create/indices/user")
    public Object createIndicesUser() {
        return esToolsService.createIndicesUser();
    }

    @GetMapping("/create/indices/blog")
    public Object createIndicesBlog() {
        return esToolsService.createIndicesBlog();
    }

    @GetMapping("/create/indices/mid")
    public Object createIndicesMid() {
        return esToolsService.createIndicesMid();
    }

    @GetMapping("/loadData/user")
    public String loadDataForUser() {
        return esToolsService.loadDataForUser();
    }

    @PostMapping("/dayTask/create/blogIndices")
    public String dayTaskCreateBlogIndices(@RequestParam("count") Integer count) {
        return esToolsService.dayTaskCreateBlogIndices(count);
    }

    @PostMapping("/dayTask/load/blogData")
    public String dayTaskLoadBlogData(@RequestBody BlogDataDTO blogDataDTO) {
        return esToolsService.dayTaskLoadBlogData(blogDataDTO);
    }

    @PostMapping("/dayTask/update/userReports")
    public String dayTaskUpdateUserReports(@RequestBody List<UserReportDTO> userReportDTOList) {
        return esToolsService.dayTaskUpdateUserReports(userReportDTOList);
    }

    @PostMapping("/dayTask/update/userLabels")
    public String dayTaskUpdateUserLabels(@RequestBody List<UserLabelDTO> userLabelDTOList) {
        return esToolsService.dayTaskUpdateUserLabels(userLabelDTOList);
    }

    @GetMapping("/dayTask/forceMerge/user")
    public String dayTaskForceMergeUser() {
        return esToolsService.dayTaskForceMergeUser();
    }

    @GetMapping("/dayTask/forceMerge/blog")
    public String dayTaskForceMergeBlog() {
        return esToolsService.dayTaskForceMergeBlog();
    }

    @PostMapping("/dayTask/update/userInfo")
    public String dayTaskUpdateUserInfo(@RequestBody List<UserInfoDTO> userInfoDTOS) {
        return esToolsService.dayTaskUpdateUserInfo(userInfoDTOS);
    }

}
