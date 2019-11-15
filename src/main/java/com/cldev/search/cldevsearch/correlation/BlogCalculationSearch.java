package com.cldev.search.cldevsearch.correlation;

import com.cldev.search.cldevsearch.bo.ReportBO;
import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.process.AbstractCalculationBuilder;
import com.cldev.search.cldevsearch.correlation.process.KolCalculation;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.util.BeanUtil;
import com.cldev.search.cldevsearch.vo.SearchResVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.cldev.search.cldevsearch.util.BeanUtil.weightConfig;


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
        this.searchRequest = new SearchRequest("wb-art").source(new SearchSourceBuilder().query(this.boolQueryBuilder)
                .size(weightConfig().getBlogResultSize()));
        return this;
    }

    protected BoolQueryBuilder resolverContext() {
        String context = searchConditionDTO.getContext();
        if (!StringUtils.isEmpty(context)) {
            List<String> screenName = BeanUtil.searchRegistryConfig().getScreenName(context);
            if (screenName.size() == 0) {
                this.boolQueryBuilder = this.boolQueryBuilder.should(new MatchQueryBuilder("article", context));
                return this.boolQueryBuilder.should(new MatchPhraseQueryBuilder("article", context));
            }
            for (String name : screenName) {
                this.boolQueryBuilder = this.boolQueryBuilder.should(new MatchQueryBuilder("article", name));
                this.boolQueryBuilder = this.boolQueryBuilder.should(new MatchPhraseQueryBuilder("article", name));
            }
            return this.boolQueryBuilder;
        }
        return null;
    }

    @Override
    @SuppressWarnings("all")
    public List<SearchResultTempBO> getResult(Client client) {
        // 在es中获取博文检索信息
        long search = System.currentTimeMillis();
        SearchHit[] hits = client.search(searchRequest).actionGet().getHits().getHits();
        this.searchLogInfo.append("------ The search : ").append(System.currentTimeMillis() - search).append("ms\n");
        long start = System.currentTimeMillis();
        // 将获取到的信息放入List
        List<SearchResultTempBO> searchResultTempBos = new LinkedList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSourceAsMap();
            searchResultTempBos.add(new SearchResultTempBO(source.get("uid").toString(), null, null,
                    hit.getScore(), source.get("time").toString(),
                    null, null, null, null, null, null, null, null));
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
                .query(new TermsQueryBuilder("_id", uidList)).size(uidList.size())));
        SearchHit[] infoHit = esUidInfo.actionGet().getHits().getHits();
        this.searchLogInfo.append("------ search user by uid : ").append(System.currentTimeMillis() - startUser).append("ms\n");
        // 将用户信息存入 Map
        Map<String, SearchResultTempBO> searchResVoMap = new HashMap<>(1400);
        for (SearchHit documentFields : infoHit) {
            Map<String, Object> source = documentFields.getSourceAsMap();
            String[] infos = source.get("info").toString().split("@");
            searchResVoMap.put(documentFields.getId(),
                    new SearchResultTempBO(documentFields.getId(),
                            Integer.parseInt(source.get("fans").toString()),
                            Float.parseFloat(source.get("score").toString()),
                            null, null, source.get("name").toString(),
                            (List<Integer>) source.get("label.id"), (List<Double>) source.get("label.score"),
                            (List<Integer>) source.get("label.show"), source.get("address").toString(),
                            source.get("province").toString(), Integer.parseInt(source.get("sex").toString()),
                            new ReportBO().setAttitudeSum(Long.parseLong(infos[0])).setAttitudeMax(Long.parseLong(infos[1])).setAttitudeMedian(Long.parseLong(infos[2]))
                                    .setCommentSum(Long.parseLong(infos[3])).setCommentMax(Long.parseLong(infos[4])).setCommentMedian(Long.parseLong(infos[5]))
                                    .setRepostSum(Long.parseLong(infos[6])).setRepostMax(Long.parseLong(infos[7])).setRepostMedian(Long.parseLong(infos[8]))
                                    .setMblogTotal(Long.parseLong(infos[9])).setReleaseMblogFrequency(Double.parseDouble(infos[10]))
                                    .setRepostRatio(Float.parseFloat(infos[11]))));
        }
        List<SearchResultTempBO> result = new LinkedList<>();
        Float[] floats = operatorRankByScore(mergeUid(searchResVoMap, searchResultTempBos));
        long scoreNormalization = System.currentTimeMillis();
        floats = scoreNormalization(floats);
        this.searchLogInfo.append("------ score normalization : ").append(System.currentTimeMillis() - scoreNormalization).append("ms\n");
        for (int item = 0; item < uidList.size(); item++) {
            SearchResultTempBO searchResVO = searchResVoMap.get(uidList.get(item));
            if (!ObjectUtils.isEmpty(searchResVO)) {
                result.add(new SearchResultTempBO(uidList.get(item), searchResVO.getWbFans(),
                        searchResVO.getScore(), floats[item], null, searchResVO.getName(), searchResVO.getLabels(), searchResVO.getScores(),
                        searchResVO.getShowLabels(), searchResVO.getAddress(), searchResVO.getProvince(), searchResVO.getSex(), searchResVO.getReport()));
            }
        }
        this.searchLogInfo.append("-------- blog operator total : ").append(System.currentTimeMillis() - start).append("ms\n");
        return result;
    }

    private List<ScoreOperation> mergeUid(Map<String, SearchResultTempBO> searchResVoMap, List<SearchResultTempBO> searchResultTempBos) {
        // 对博文结果集进行合并、分权操作
        long uidNum = System.currentTimeMillis();
        List<ScoreOperation> scoreOperations = new LinkedList<>();
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
        int sortListIndex = 0, sameBlogCount = 0;
        for (int item = 0; item < sortByCorrelationScore.size(); item++) {
            if (!currentUid.equals(sortByCorrelationScore.get(item).getUid())) {
                SearchResultTempBO searchResultTemp = searchResVoMap.get(currentUid);
                currentUid = sortByCorrelationScore.get(item).getUid();
                scoreOperations.add(new ScoreOperation().setIndex(sortListIndex++)
                        .setScore(resForCorrelationScore * weightConfig().getScoreWeight() + resForCreateTime * weightConfig().getTimeWeight())
                        .setFrequency((float) sameBlogCount / searchResultTemp.getReport().getMblogTotal()));
                factorForCorrelationScore = 1.0f;
                factorForCreateTime = 1.0f;
                resForCorrelationScore = 0.0f;
                resForCreateTime = 0.0f;
                sameBlogCount = 0;
            }
            resForCorrelationScore += sortByCorrelationScore.get(item).getCorrelationScore() * factorForCorrelationScore;
            resForCreateTime += sortByCreateTime.get(item).getCorrelationScore() * factorForCreateTime;
            factorForCorrelationScore *= weightConfig().getDecayFactorByScore();
            factorForCreateTime *= weightConfig().getDecayFactorByTime();
            sameBlogCount++;
        }
        if (sortByCorrelationScore.size() != 0) {
            SearchResultTempBO searchResultTemp = searchResVoMap.get(currentUid);
            scoreOperations.add(new ScoreOperation()
                    .setIndex(sortListIndex)
                    .setScore(resForCorrelationScore * weightConfig().getScoreWeight() + resForCreateTime * weightConfig().getTimeWeight())
                    .setFrequency((float) sameBlogCount / searchResultTemp.getReport().getMblogTotal()));
        }
        this.searchLogInfo.append("------ the same uid merge : ").append(System.currentTimeMillis() - uidNum).append("ms\n");
        return scoreOperations;
    }

    private Float[] operatorRankByScore(List<ScoreOperation> scoreOperations) {
        scoreOperations.sort((o1, o2) -> o2.getFrequency().compareTo(o1.getFrequency()));
        float upper = weightConfig().getHitPercentUpperLimit(),
                differentPercent = (upper - weightConfig().getHitPercentLowerLimit()) / scoreOperations.size();
        for (int item = 0; item < scoreOperations.size(); item++) {
            scoreOperations.get(item).setScore(scoreOperations.get(item).getScore() * (upper - differentPercent * (item + 1)));
        }
        scoreOperations.sort(Comparator.comparing(ScoreOperation::getIndex));
        return scoreOperations.stream().map(ScoreOperation::getScore).toArray(Float[]::new);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    private class ScoreOperation {
        private Integer index;
        private Float score;
        private Float frequency;
    }

}
