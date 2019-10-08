package com.cldev.search.cldevsearch.service.impl;

import com.cldev.search.cldevsearch.correlation.process.CalculationSearchFactory;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.service.EsSearchService;
import com.cldev.search.cldevsearch.vo.SearchResVO;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

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
 * Build File @date: 2019/9/25 14:51
 * @version 1.0
 * @description
 */
@Service
public class EsSearchServiceImpl implements EsSearchService {

    private final ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public EsSearchServiceImpl(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public List<SearchResVO> uidSearch(SearchConditionDTO searchConditionDTO) {
        CalculationSearchFactory calculationSearchFactory = CalculationSearchFactory.buildCalculation();
        calculationSearchFactory.calculationBuilder(searchConditionDTO, elasticsearchTemplate.getClient()).searchStart();
        try {
            calculationSearchFactory.getCyclicBarrier().await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
//        return calculationSearchFactory.searchEnd();
        return null;
    }



}
