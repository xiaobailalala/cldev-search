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
import java.util.stream.Collectors;

import static com.cldev.search.cldevsearch.correlation.weight.CalculationSearchWeight.*;

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
        // 将获取到的信息放入List
        List<SearchResultTempBO> searchResultTempBos = new LinkedList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSourceAsMap();
            searchResultTempBos.add(new SearchResultTempBO(source.get("uid").toString(), null, null,
                    hit.getScore(), source.get("time").toString(), null, null, null, null, null));
        }
        // 对结果集 List 根据 uid 去重，得到uid集合
        List<String> uidList = searchResultTempBos.stream()
                .map(SearchResVO::getUid)
                .distinct()
                .sorted(String::compareTo)
                .collect(Collectors.toCollection(LinkedList::new));
        // 根据uid集合去es的用户索引中获取用户信息集合
        long startUser = System.currentTimeMillis();
        ActionFuture<SearchResponse> esUidInfo = client.search(new SearchRequest("wb-user").source(new SearchSourceBuilder()
                .query(new TermsQueryBuilder("uid", uidList)).size(uidList.size())));
        System.out.println("----------- search user by uid: " + (System.currentTimeMillis() - startUser) + "ms");
        SearchHit[] infoHit = esUidInfo.actionGet().getHits().getHits();
        // 将用户信息存入 Map
        Map<String, SearchResultTempBO> searchResVoMap = new HashMap<>(1400);
        for (SearchHit documentFields : infoHit) {
            Map<String, Object> source = documentFields.getSourceAsMap();
            searchResVoMap.put(source.get("uid").toString(),
                    new SearchResultTempBO(source.get("uid").toString(),
                            Integer.parseInt(source.get("fans").toString()),
                            Float.parseFloat(source.get("score").toString()),
                            null, null, null,
                            (List<Integer>) source.get("label"), source.get("address").toString(),
                            source.get("province").toString(), Integer.parseInt(source.get("sex").toString())));
        }
        List<SearchResultTempBO> result = new LinkedList<>();
        // 对博文结果集进行合并、分权操作
        long uidNum1 = System.currentTimeMillis();
        Float[] floats = new Float[uidList.size()];
        List<SearchResultTempBO> sortByCorrelationScore = searchResultTempBos.stream().sorted((item1, item2) -> {
            if (item1.getUid().compareTo(item2.getUid()) == 0) {
                return item2.getCorrelationScore().compareTo(item1.getCorrelationScore());
            }
            return item1.getUid().compareTo(item2.getUid());
        }).collect(Collectors.toList());
        List<SearchResultTempBO> sortByCreateTime = searchResultTempBos.stream().sorted((item1, item2) -> {
            if (item1.getUid().compareTo(item2.getUid()) == 0) {
                return item2.getCreateTime().compareTo(item1.getCreateTime());
            }
            return item1.getUid().compareTo(item2.getUid());
        }).collect(Collectors.toList());
        String currentUid = sortByCorrelationScore.size() == 0 ? "" : sortByCorrelationScore.get(0).getUid();
        float factorForCorrelationScore = 1.0f, factorForCreateTime = 1.0f,
                resForCorrelationScore = 0.0f, resForCreateTime = 0.0f;
        int sortListIndex = 0;
        for (int item = 0; item < sortByCorrelationScore.size(); item++) {
            if (!currentUid.equals(sortByCorrelationScore.get(item).getUid())) {
                currentUid = sortByCorrelationScore.get(item).getUid();
                floats[sortListIndex++] = resForCorrelationScore * SCORE_WEIGHT +
                        resForCreateTime * TIME_WEIGHT;
                factorForCorrelationScore = 1.0f;
                factorForCreateTime = 1.0f;
                resForCorrelationScore = 0.0f;
                resForCreateTime = 0.0f;
            }
            resForCorrelationScore += sortByCorrelationScore.get(item).getCorrelationScore() * factorForCorrelationScore;
            resForCreateTime += sortByCreateTime.get(item).getCorrelationScore() * factorForCreateTime;
            factorForCorrelationScore *= DECAY_FACTOR_BY_SCORE;
            factorForCreateTime *= DECAY_FACTOR_BY_TIME;
        }
        if (sortByCorrelationScore.size() != 0) {
            floats[sortListIndex] = resForCorrelationScore * SCORE_WEIGHT +
                    resForCreateTime * TIME_WEIGHT;
        }
        System.out.println("----------- uid 归 一: " + (System.currentTimeMillis() - uidNum1) + "ms");
        long mapMerge = System.currentTimeMillis();
        floats = scoreUniformization(floats);
        for (int item = 0; item < uidList.size(); item++) {
            SearchResultTempBO searchResVO = searchResVoMap.get(uidList.get(item));
            if (!ObjectUtils.isEmpty(searchResVO)) {
                result.add(new SearchResultTempBO(uidList.get(item), searchResVO.getWbFans(),
                        searchResVO.getScore(), floats[item], null, null, searchResVO.getLabels(),
                        searchResVO.getAddress(), searchResVO.getProvince(), searchResVO.getSex()));
            }
        }
        System.out.println("----------- map Merge: " + (System.currentTimeMillis() - mapMerge) + "ms");
        System.out.println("blog operator: " + (System.currentTimeMillis() - start) + "ms");
        return result;
    }

}
