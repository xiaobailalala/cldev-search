package com.cldev.search.cldevsearch.correlation;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.process.AbstractCalculationBuilder;
import com.cldev.search.cldevsearch.correlation.process.KolCalculation;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.vo.SearchResVO;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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
 * Build File @date: 2019/9/26 15:25
 * @version 1.0
 * @description
 */
public class BlogCalculationSearch extends AbstractCalculationBuilder implements KolCalculation {

    private static final float DECAY_FACTOR_BY_SCORE = 0.5f;
    private static final float DECAY_FACTOR_BY_TIME = 0.5f;

    private static final float SCORE_WEIGHT = 0.6f;
    private static final float TIME_WEIGHT = 0.4f;

    public BlogCalculationSearch(SearchConditionDTO searchConditionDTO) {
        super(searchConditionDTO);
    }

    @Override
    public KolCalculation initSearchCondition() {
        this.boolQueryBuilder = resolverAndConfirmBoolQueryBuilder(
                resolverContext()
        );
        this.searchRequest = new SearchRequest("wb-art").source(new SearchSourceBuilder().query(this.boolQueryBuilder).size(5000));
        return this;
    }

    protected BoolQueryBuilder resolverContext() {
        String article = searchConditionDTO.getContext();
        if (!StringUtils.isEmpty(article)) {
            return this.boolQueryBuilder.must(new MatchQueryBuilder("article", article));
        }
        return null;
    }

    @Override
    public List<SearchResultTempBO> getResult(Client client) {
        // 在es中获取博文检索信息
        SearchHit[] hits = client.search(searchRequest).actionGet().getHits().getHits();
        long start = System.currentTimeMillis();
        // 将获取到的信息放入List 和 Map

        List<SearchResultTempBO> searchResultTempBOS = new LinkedList<>();
        Map<String, SearchResultTempBO> searchResultTempBOMap = new HashMap<>(7000);
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSourceAsMap();
            SearchResultTempBO searchResultTempBO = new SearchResultTempBO(source.get("uid").toString(), null, null,
                    3, hit.getScore(), source.get("time").toString());
            searchResultTempBOS.add(searchResultTempBO);
            searchResultTempBOMap.put(source.get("uid").toString(), searchResultTempBO);
        }

        // 对结果集 List 根据 uid 去重，得到uid集合
        List<String> uidList = searchResultTempBOS.stream()
                .map(SearchResVO::getUid)
                .distinct()
                .collect(Collectors.toCollection(LinkedList::new));


        // 根据uid集合去es的用户索引中获取用户信息集合
        long startUser = System.currentTimeMillis();
        ActionFuture<SearchResponse> esUidInfo = client.search(new SearchRequest("wb-user").source(new SearchSourceBuilder()
                .query(new TermsQueryBuilder("uid", uidList))));
        System.out.println("----------- search user by uid: " + (System.currentTimeMillis() - startUser) + "ms");
        SearchHit[] infoHit = esUidInfo.actionGet().getHits().getHits();
        // 将用户信息存入 Map
        Map<String, SearchResVO> searchResVOMap = new HashMap<>(1400);
        for (SearchHit documentFields : infoHit) {
            Map<String, Object> source = documentFields.getSourceAsMap();
            searchResVOMap.put(source.get("uid").toString(),
                    new SearchResVO().setScore(Integer.parseInt(source.get("score").toString()))
                            .setWbFans(Integer.parseInt(source.get("fans").toString())));
        }
        List<SearchResultTempBO> result = new LinkedList<>();
        // 对博文结果集进行合并、分权操作

        long uidNum1 = System.currentTimeMillis();

        Float[] floats = new Float[uidList.size()];
        for (int item = 0; item < uidList.size(); item++) {
            floats[item] = calculateAScoreByScore(searchResultTempBOS, uidList.get(item)) * SCORE_WEIGHT +
                    calculateAScoreByCreateTime(searchResultTempBOS, uidList.get(item)) * TIME_WEIGHT;
        }
        System.out.println("----------- uid 归 一: " + (System.currentTimeMillis() - uidNum1) + "ms");

        long mapMerge = System.currentTimeMillis();

        floats = scoreUniformization(floats);
        for (int item = 0; item < uidList.size(); item++) {
            SearchResultTempBO tempBO = searchResultTempBOMap.get(uidList.get(item));
            SearchResVO searchResVO = searchResVOMap.get(uidList.get(item));
            if (!ObjectUtils.isEmpty(searchResVO)) {
                result.add(new SearchResultTempBO(uidList.get(item), searchResVO.getWbFans(),
                        searchResVO.getScore(), tempBO.getLabelCount(),
                        floats[item], null));
            }
        }

        System.out.println("----------- map Merge: " + (System.currentTimeMillis() - mapMerge) + "ms");

        System.out.println("blog operator: " + (System.currentTimeMillis() - start) + "ms");
        return result;
    }

    private Float calculateAScoreByScore(List<SearchResultTempBO> searchResultTempBOS, String uid) {
        AtomicReference<Float> factor = new AtomicReference<>(1.0f),
                res = new AtomicReference<>(0.0f);
        searchResultTempBOS.stream()
                .filter(item -> item.getUid().equals(uid))
                .sorted((o1, o2) -> o2.getCorrelationScore().compareTo(o1.getCorrelationScore())).collect(Collectors.toList())
                .forEach(item -> {
                    res.updateAndGet(v -> v + item.getCorrelationScore() * factor.get());
                    factor.updateAndGet(v -> v * DECAY_FACTOR_BY_SCORE);
                });
        return res.get();
    }

    private Float calculateAScoreByCreateTime(List<SearchResultTempBO> searchResultTempBOS, String uid) {
        AtomicReference<Float> factor = new AtomicReference<>(1.0f),
                res = new AtomicReference<>(0.0f);
        searchResultTempBOS.stream()
                .filter(item -> item.getUid().equals(uid))
                .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())).collect(Collectors.toList())
                .forEach(item -> {
                    res.updateAndGet(v -> v + item.getCorrelationScore() * factor.get());
                    factor.updateAndGet(v -> v * DECAY_FACTOR_BY_TIME);
                });
        return res.get();
    }

}
