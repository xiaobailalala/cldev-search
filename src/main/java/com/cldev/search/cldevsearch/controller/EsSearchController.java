package com.cldev.search.cldevsearch.controller;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.service.EsSearchService;
import com.cldev.search.cldevsearch.util.ResultApi;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.vo.SearchResVO;
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
 * Build File @date: 2019/9/25 10:22
 * @version 1.0
 * @description
 */
@RestController
@RequestMapping("/es/search")
public class EsSearchController {

    private final EsSearchService esSearchService;

    @Autowired
    public EsSearchController(EsSearchService esSearchService) {
        this.esSearchService = esSearchService;
    }

    @PostMapping("/uid/search")
    public ResultApi<List<SearchResultTempBO>> uidSearch(@RequestBody SearchConditionDTO searchConditionDTO) {
        return ResultApi.ok(esSearchService.uidSearch(searchConditionDTO));
    }

}
