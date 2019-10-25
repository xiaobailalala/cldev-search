package com.cldev.search.cldevsearch.controller;

import com.cldev.search.cldevsearch.dto.BlogDataDTO;
import com.cldev.search.cldevsearch.dto.UserReportDTO;
import com.cldev.search.cldevsearch.dto.UserInfoDTO;
import com.cldev.search.cldevsearch.dto.UserLabelDTO;
import com.cldev.search.cldevsearch.service.EsToolsService;
import com.cldev.search.cldevsearch.util.AsyncTaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private final AsyncTaskUtil asyncTaskUtil;

    @Autowired
    public EsToolsController(EsToolsService esToolsService, AsyncTaskUtil asyncTaskUtil) {
        this.esToolsService = esToolsService;
        this.asyncTaskUtil = asyncTaskUtil;
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

    @GetMapping("/test")
    public String test() {
        Map<String, String> userInfoMap = new HashMap<>(1179648);
        try {
            File userInfoFile = new File("userInfo");
            FileReader fileReader = new FileReader(userInfoFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String msg;
            while ((msg = reader.readLine()) != null) {
                userInfoMap.put(msg.split("-")[0], msg.substring(msg.indexOf("-") + 1));
            }
            reader.close();
            fileReader.close();
            File csvFile = new File("C:\\Users\\cl24\\Desktop\\uid_80w.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(csvFile));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String uid;
            List<UserReportDTO> report = new LinkedList<>();
            int count = 0, limit = 5000;
            while ((uid = bufferedReader.readLine()) != null) {
                String[] infos = userInfoMap.get(uid).split("-");
                report.add(new UserReportDTO(Integer.parseInt(infos[0]),
                        Integer.parseInt(infos[1]),
                        Integer.parseInt(infos[2]),
                        Integer.parseInt(infos[3]),
                        Integer.parseInt(infos[4]),
                        Integer.parseInt(infos[5]),
                        Integer.parseInt(infos[6]),
                        Integer.parseInt(infos[7]),
                        Integer.parseInt(infos[8]),
                        Integer.parseInt(infos[9]),
                        Double.parseDouble(infos[10]), uid, null));
                count++;
                if (count % limit == 0) {
                    List<UserReportDTO> users = new LinkedList<>(report);
                    asyncTaskUtil.asyncCustomTask(() -> esToolsService.dayTaskUpdateUserReports(users));
                    report = new LinkedList<>();
                    System.out.println(count);
                }
            }
            if (count % limit != 0) {
                List<UserReportDTO> users = new LinkedList<>(report);
                asyncTaskUtil.asyncCustomTask(() -> esToolsService.dayTaskUpdateUserReports(users));
                System.out.println(count);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "start";
    }

}
