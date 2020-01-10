package com.cldev.search.cldevsearch.service.impl;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.process.CalculationSearchFactory;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.service.EsSearchService;
import com.cldev.search.cldevsearch.vo.SearchResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
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
    public List<SearchResultTempBO> uidSearch(SearchConditionDTO searchConditionDTO) {
        // 创建核心计算工厂
        CalculationSearchFactory calculationSearchFactory = CalculationSearchFactory.buildCalculation();
        // 构建计算过程，并开始计算
        calculationSearchFactory.calculationBuilder(searchConditionDTO, elasticsearchTemplate.getClient()).searchStart();
        // 启用父线程的线程栅栏，放置核心操作的异步导致方法提前结束
        calculationSearchFactory.getCyclicBarrier().barrierMainThreadWait();
        // 线程等待结束，获取最终结果集合进行返回
        return calculationSearchFactory.searchEnd();
    }

}
