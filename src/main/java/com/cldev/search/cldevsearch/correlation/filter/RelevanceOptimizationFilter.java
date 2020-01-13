package com.cldev.search.cldevsearch.correlation.filter;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.process.SearchFilter;
import com.cldev.search.cldevsearch.util.CommonUtil;
import lombok.Getter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.cldev.search.cldevsearch.util.BeanUtil.searchRegistryConfig;
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
 * Build File @date: 2020/1/12 4:50 下午
 * @version 1.0
 * @description
 */
public class RelevanceOptimizationFilter implements SearchFilter {

    private ConcurrentHashMap<Integer, Float> userInterestLabelScore;
    private List<SearchResultTempBO> blogResultFilter;
    private String uid;

    public RelevanceOptimizationFilter(List<SearchResultTempBO> blogResultFilter, String uid,
                                       ConcurrentHashMap<Integer, Float> userInterestLabelScore) {
        this.blogResultFilter = blogResultFilter;
        this.uid = uid;
        this.userInterestLabelScore = userInterestLabelScore;
    }

    @Override
    public void filter() {
        this.influenceScoreWeighting();
        this.relevanceOptimizationRanking();
    }

    /**
     * 影响力分数计算方式 乘/权重加法
     */
    private void influenceScoreWeighting() {
        Float[] influenceResult = CommonUtil.scoreNormalization(
                this.blogResultFilter.stream().map(SearchResultTempBO::getScore).toArray(Float[]::new));
        Float[] correlationResult = CommonUtil.scoreNormalization(
                this.blogResultFilter.stream().map(SearchResultTempBO::getCorrelationScore).toArray(Float[]::new));
        if (weightConfig().isInfluenceWithWeight()) {
            // 根据权重比进行分数相加
            for (int item = 0; item < this.blogResultFilter.size(); item++) {
                this.blogResultFilter.get(item).setCorrelationScore(
                        correlationResult[item] * weightConfig().getBlogCorrelationScoreWeight() +
                        influenceResult[item] * weightConfig().getBlogInfluenceScoreWeight());
            }
        } else {
            // 根据原始分和影响力分数的乘积作为基础分
            for (int item = 0; item < this.blogResultFilter.size(); item++) {
                this.blogResultFilter.get(item).setCorrelationScore(correlationResult[item] * influenceResult[item]);
            }
        }
    }

    /**
     * 需求方向的相关性优化
     */
    private void relevanceOptimizationRanking() {
        List<ResultSortByInteractionAbility> tempResult = new LinkedList<>();
        for (SearchResultTempBO item : this.blogResultFilter) {
            tempResult.add(new ResultSortByInteractionAbility(item.getUid(),
                    item.getReport().getAttitudeMedian(), item.getReport().getCommentMedian(), item.getReport().getRepostMedian(),
                    item.getReport().getMblogTotal(), item.getReport().getAttitudeSum(), item.getReport().getCommentSum(), item.getReport().getRepostSum()));
        }
        Map<String, ScoreWithRank> scoreMapWithInteraction = getScoreMapWithInteraction(tempResult);
        Map<String, ScoreWithRank> scoreMapWithFrequency = getScoreMapWithDifference(scoreMapWithInteraction, this.blogResultFilter);
        Map<String, ScoreWithRank> scoreMapWithRePostRatio = getScoreMapWithRePostRatio(this.blogResultFilter);
        Map<String, ScoreWithRank> scoreMapWithInterestLabel = getScoreMapWithInterestLabel(this.blogResultFilter);
        Set<String> newsMedia = searchRegistryConfig().getNewsMedia();
        ConcurrentHashMap<String, Integer> onlineWaterAmy = searchRegistryConfig().getOnlineWaterAmy();
        float waterAmyDecayPercentUpperLimit = weightConfig().getWaterAmyDecayPercentUpperLimit(),
                differenceValue = waterAmyDecayPercentUpperLimit - weightConfig().getWaterAmyDecayPercentLowerLimit(),
                differencePercent = differenceValue / 100;
        for (SearchResultTempBO item : this.blogResultFilter) {
            float score = item.getCorrelationScore() +
                    scoreMapWithInteraction.get(item.getUid()).getScore() +
                    scoreMapWithFrequency.get(item.getUid()).getScore() +
                    scoreMapWithRePostRatio.get(item.getUid()).getScore() +
                    (ObjectUtils.isEmpty(this.uid) || StringUtils.isEmpty(this.uid) ? 0.0f
                            : scoreMapWithInterestLabel.get(item.getUid()).getScore());
            if (newsMedia.contains(item.getUid())) {
                score *= weightConfig().getNewsMediaDecayFactor();
            }
            Integer waterAmy = onlineWaterAmy.get(item.getUid());
            if (waterAmy != null) {
                score *= waterAmyDecayPercentUpperLimit - waterAmy * differencePercent;
            }
            item.setCorrelationScore(score);
        }
    }

    private Map<String, ScoreWithRank> getScoreMapWithInteraction(List<ResultSortByInteractionAbility> tempResult) {
        Collections.sort(tempResult);
        Map<String, ScoreWithRank> scoreMap = new HashMap<>(9216);
        float upperLimit = weightConfig().getInteractionUpperLimit();
        float decayFactor = upperLimit / tempResult.size();
        for (int index = 0; index < tempResult.size(); index++) {
            scoreMap.put(tempResult.get(index).getUid(), new ScoreWithRank(upperLimit - decayFactor * index, index + 1));
        }
        return scoreMap;
    }

    private Map<String, ScoreWithRank> getScoreMapWithDifference(Map<String, ScoreWithRank> interactionResultMap, List<SearchResultTempBO> tempResult) {
        List<SearchResultTempBO> tempList = new LinkedList<>(tempResult);
        tempList.sort((o1, o2) -> o2.getReport().getReleaseMblogFrequency().compareTo(o1.getReport().getReleaseMblogFrequency()));
        List<ResultSortByInteractionDifferenceValue> differenceValues = new LinkedList<>();
        for (int index = 0; index < tempList.size(); index++) {
            differenceValues.add(new ResultSortByInteractionDifferenceValue(tempList.get(index).getUid(),
                    interactionResultMap.get(tempList.get(index).getUid()).getRank() - (index + 1)));
        }
        Collections.sort(differenceValues);
        Map<String, ScoreWithRank> scoreMap = new HashMap<>(9216);
        int limitPercentDifference = (int) Math.ceil(tempList.size() * weightConfig().getDifferenceValueDefaultPercent());
        int percentIndex = 0;
        float upperLimit = weightConfig().getFrequencyUpperLimit();
        float decayFactor = upperLimit / tempResult.size();
        for (int index = 0; index < differenceValues.size(); index++) {
            Integer difference = differenceValues.get(index).getDifference();
            scoreMap.put(differenceValues.get(index).getUid(), new ScoreWithRank(difference < limitPercentDifference ?
                    upperLimit : upperLimit - decayFactor * ++percentIndex, (index + 1)));
        }
        return scoreMap;
    }

    private Map<String, ScoreWithRank> getScoreMapWithRePostRatio(List<SearchResultTempBO> tempResult) {
        List<SearchResultTempBO> tempList = new LinkedList<>(tempResult);
        tempList.sort(Comparator.comparing(o -> o.getReport().getRepostRatio()));
        Map<String, ScoreWithRank> scoreMap = new HashMap<>(9216);
        float upperLimit = weightConfig().getInteractionUpperLimit();
        float decayFactor = upperLimit / tempList.size();
        float ratioDefaultPercent = weightConfig().getRePostRatioDefaultPercent();
        int percentIndex = 0;
        for (int index = 0; index < tempList.size(); index++) {
            Float repostRatio = tempList.get(index).getReport().getRepostRatio();
            scoreMap.put(tempList.get(index).getUid(), new ScoreWithRank(repostRatio < ratioDefaultPercent ? upperLimit :
                    upperLimit - decayFactor * ++percentIndex, index + 1));
        }
        return scoreMap;
    }

    private Map<String, ScoreWithRank> getScoreMapWithInterestLabel(List<SearchResultTempBO> tempResult) {
        Map<String, ScoreWithRank> scoreMap = new HashMap<>(9216);
        if (!ObjectUtils.isEmpty(this.uid) && !StringUtils.isEmpty(this.uid)) {
            Set<Integer> interest = new HashSet<>(this.userInterestLabelScore.keySet());
            List<ResultSortByInterestLabelScore> sort = new LinkedList<>();
            for (SearchResultTempBO searchResult : tempResult) {
                float scoreSum = 0.0f;
                for (int item = 0; item < searchResult.getLabels().size(); item++) {
                    if (interest.contains(searchResult.getLabels().get(item))) {
                        Float userScore = this.userInterestLabelScore.get(searchResult.getLabels().get(item));
                        Double kolScore = searchResult.getScores().get(item);
                        scoreSum += userScore * kolScore;
                    }
                }
                sort.add(new ResultSortByInterestLabelScore(searchResult.getUid(), scoreSum));
            }
            Collections.sort(sort);
            float upperLimit = weightConfig().getRelevanceLabelUpperLimit();
            float decayFactor = (upperLimit - weightConfig().getRelevanceLabelLowerLimit()) / tempResult.size();
            for (int item = 0; item < sort.size(); item++) {
                scoreMap.put(sort.get(item).getUid(), new ScoreWithRank(upperLimit - decayFactor * (item + 1)
                        , item + 1));
            }
        }
        return scoreMap;
    }

    private static class ResultSortByInteractionAbility implements Comparable<ResultSortByInteractionAbility> {

        @Getter
        private String uid;
        private Long attitudeMedian;
        private Long commentMedian;
        private Long repostMedian;
        private Long blogTotal;
        private Long attitudeSum;
        private Long commentSum;
        private Long repostSum;
        private Float medianScore;

        ResultSortByInteractionAbility(String uid, Long attitudeMedian, Long commentMedian, Long repostMedian, Long blogTotal, Long attitudeSum, Long commentSum, Long repostSum) {
            this.uid = uid;
            this.attitudeMedian = attitudeMedian;
            this.commentMedian = commentMedian;
            this.repostMedian = repostMedian;
            this.blogTotal = blogTotal;
            this.attitudeSum = attitudeSum;
            this.commentSum = commentSum;
            this.repostSum = repostSum;
            this.medianScore = weightConfig().getAttitudeWeight() * this.attitudeMedian +
                    weightConfig().getCommentWeight() * this.commentMedian +
                    weightConfig().getRepostWeight() * this.repostMedian;
        }

        @Override
        public int compareTo(ResultSortByInteractionAbility o) {
            int medianScoreCompare = Float.compare(o.medianScore, this.medianScore);
            if (medianScoreCompare != 0) {
                return medianScoreCompare;
            }
            if (this.blogTotal == 0) {
                return -1;
            }
            float thisSumScore = this.attitudeSum * weightConfig().getAttitudeWeight() / this.blogTotal +
                    this.commentSum * weightConfig().getCommentWeight() / this.blogTotal +
                    this.repostSum * weightConfig().getRepostWeight() / this.blogTotal;
            float otherSumScore = o.attitudeSum * weightConfig().getAttitudeWeight() / o.blogTotal +
                    o.commentSum * weightConfig().getCommentWeight() / o.blogTotal +
                    o.repostSum * weightConfig().getCommentWeight() / o.blogTotal;
            return Float.compare(otherSumScore, thisSumScore);
        }
    }

    private static class ResultSortByInteractionDifferenceValue implements Comparable<ResultSortByInteractionDifferenceValue> {

        @Getter
        private String uid;
        @Getter
        private Integer difference;

        ResultSortByInteractionDifferenceValue(String uid, Integer difference) {
            this.uid = uid;
            this.difference = difference;
        }

        @Override
        public int compareTo(ResultSortByInteractionDifferenceValue o) {
            return this.difference.compareTo(o.difference);
        }
    }

    private static class ResultSortByInterestLabelScore implements Comparable<ResultSortByInterestLabelScore> {

        @Getter
        private String uid;
        @Getter
        private Float score;

        ResultSortByInterestLabelScore(String uid, Float score) {
            this.uid = uid;
            this.score = score;
        }

        @Override
        public int compareTo(ResultSortByInterestLabelScore o) {
            return o.score.compareTo(this.score);
        }
    }

    private static class ScoreWithRank {

        @Getter
        private Float score;
        @Getter
        private Integer rank;

        ScoreWithRank(Float score, Integer rank) {
            this.score = score;
            this.rank = rank;
        }
    }

    @Override
    public Object getResult() {
        return null;
    }
}
