package com.cldev.search.cldevsearch.correlation.process;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.bo.SearchResultTempBoWithSimilarityScore;
import com.cldev.search.cldevsearch.correlation.BlogCalculationSearch;
import com.cldev.search.cldevsearch.correlation.UserCalculationSearch;
import com.cldev.search.cldevsearch.correlation.extension.UserCalculationSearchName;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.entity.UserInterestLabel;
import com.cldev.search.cldevsearch.mapper.UserInterestLabelMapper;
import com.cldev.search.cldevsearch.util.BeanUtil;
import com.cldev.search.cldevsearch.util.CommonUtil;
import com.cldev.search.cldevsearch.util.CyclicBarrierUtil;
import lombok.Getter;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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
 * Build File @date: 2019/9/26 17:17
 * @version 1.0
 * @description
 */
public class CalculationSearchFactory implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CalculationSearchFactory.class);

    /**
     * 初始化线程栅栏工具类，创建线程栅栏
     */
    private CyclicBarrierUtil cyclicBarrier = CyclicBarrierUtil.buildBarrier(this,
            "thread-call-runner-%d", 10, 10, 0L);

    /**
     * 声明搜索条件
     */
    private SearchConditionDTO condition;

    /**
     * 声明es transport客户端
     */
    private Client client;

    /**
     * 初始化用于存储用户信息搜索通道得到的用户结果列表集合，该集合支持并发
     */
    private ConcurrentLinkedQueue<SearchResultTempBO> userIndicesResult = new ConcurrentLinkedQueue<>();

    /**
     * 初始化用于存储博文搜索通道得到的用户结果列表集合，该集合支持并发
     */
    private ConcurrentLinkedQueue<SearchResultTempBO> blogIndicesResult = new ConcurrentLinkedQueue<>();

    /**
     * 初始化用于存储用户名搜索通道得到的用户结果列表集合，该集合支持并发
     */
    private ConcurrentLinkedQueue<SearchResultTempBO> userNameIndicesResult = new ConcurrentLinkedQueue<>();

    /**
     * 初始化最终用户列表结果集合
     */
    private List<SearchResultTempBO> resultUid = new LinkedList<>();

    /**
     * 初始化用户兴趣领域标签及其分数映射字典，该字典支持并发
     */
    private ConcurrentHashMap<Integer, Float> userInterestLabelScore = new ConcurrentHashMap<>(128);

    /**
     * 初始化日志存储容器，在一次请求的线程中，为避免日志串扰，故该容器支持并发
     */
    private StringBuffer searchLogInfo = new StringBuffer("The search process info : \n");

    /**
     * 初始化全局操作计时器的起始时间
     */
    private long searchTimeStart = System.currentTimeMillis();

    /**
     * 声明用于判断用户是否登录的标记
     */
    private boolean isUserLogged;

    private CalculationSearchFactory() {
    }

    public static CalculationSearchFactory buildCalculation() {
        return new CalculationSearchFactory();
    }

    public CalculationSearchFactory calculationBuilder(SearchConditionDTO condition, Client client) {
        this.condition = condition;
        this.client = client;
        this.isUserLogged = !ObjectUtils.isEmpty(this.condition.getUid()) &&
                !StringUtils.isEmpty(this.condition.getUid());
        // 判断搜索内容是否为空
        boolean isContext = StringUtils.isEmpty(condition.getContext());
        this.cyclicBarrier.createBarrier(isContext ? (this.isUserLogged ? 3 : 2) : (this.isUserLogged ? 5 : 4));
        return this;
    }

    /**
     * 开始执行搜索
     */
    public void searchStart() {
        searchLogInfo.append("-------------------- search start, the search context is : ")
                .append(this.condition.getContext()).append(" --------------------\n")
                .append("-------------------- the search condition is : sex-")
                .append(this.condition.getSex()).append(" address-")
                .append(Arrays.toString(this.condition.getAddress())).append(" interest-")
                .append(this.condition.getInterest()).append(" fansNum-").append(this.condition.getFansNum())
                .append(" --------------------\n");
        // 若用户有登录，则获取用户兴趣领域标签及其分数
        this.userInterestLabelScoreProcess();
        if (StringUtils.isEmpty(condition.getContext())) {
            // 若搜索条件为空，则只进行基于用户信息的搜索
            this.cyclicBarrier.barrierOperationThreadRun(() -> {
                KolCalculation kolCalculation = new UserCalculationSearch(this.condition);
                this.userIndicesResult.addAll(kolCalculation.initSearchCondition().getResult(this.client));
            });
        } else {
            // 用户名检索线程
            this.cyclicBarrier.barrierOperationThreadRun(() -> {
                try {
                    long start = System.currentTimeMillis();
                    KolCalculation kolCalculation = new UserCalculationSearchName(this.condition);
                    this.userNameIndicesResult.addAll(kolCalculation.initSearchCondition().getResult(this.client));
                    searchLogInfo.append(kolCalculation.getSearchLogInfo()).append("search name total: ")
                            .append(System.currentTimeMillis() - start).append("ms\n");
                } catch (Exception e) {
                    this.userNameIndicesResult.addAll(new ArrayList<>());
                    e.printStackTrace();
                    logger.error("----------------- userNameIndicesResult search error -----------------");
                }
            });
            // 用户信息检索线程
            this.cyclicBarrier.barrierOperationThreadRun(() -> {
                try {
                    long start = System.currentTimeMillis();
                    KolCalculation kolCalculation = new UserCalculationSearch(this.condition);
                    this.userIndicesResult.addAll(kolCalculation.initSearchCondition().getResult(this.client));
                    searchLogInfo.append(kolCalculation.getSearchLogInfo()).append("search user total: ")
                            .append(System.currentTimeMillis() - start).append("ms\n");
                } catch (Exception e) {
                    this.userIndicesResult.addAll(new ArrayList<>());
                    e.printStackTrace();
                    logger.error("----------------- userInfoIndicesResult search error -----------------");
                }
            });
            // 博文文档检索线程
            this.cyclicBarrier.barrierOperationThreadRun(() -> {
                try {
                    long start = System.currentTimeMillis();
                    KolCalculation kolCalculation = new BlogCalculationSearch(this.condition);
                    this.blogIndicesResult.addAll(kolCalculation.initSearchCondition().getResult(this.client));
                    searchLogInfo.append(kolCalculation.getSearchLogInfo()).append("search blog total: ")
                            .append(System.currentTimeMillis() - start).append("ms\n");
                } catch (Exception e) {
                    this.blogIndicesResult.addAll(new ArrayList<>());
                    e.printStackTrace();
                    logger.error("----------------- blogIndicesResult search error -----------------");
                }
            });
        }
    }

    /**
     * 若用户处于登录状态，获取用户的兴趣标签信息及分数
     */
    private void userInterestLabelScoreProcess() {
        if (this.isUserLogged) {
            this.cyclicBarrier.barrierOperationThreadRun(() -> {
                try {
                    long start = System.currentTimeMillis();
                    UserInterestLabelMapper mapper = BeanUtil.userInterestLabelMapper();
                    for (UserInterestLabel item : mapper
                            .select(new UserInterestLabel().setUid(this.condition.getUid()))) {
                        userInterestLabelScore.put(searchRegistryConfig().getInterestOne(item.getLabel()), item.getRes());
                    }
                    this.searchLogInfo.append("search user interest label : ")
                            .append(System.currentTimeMillis() - start).append("ms\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("----------------- user interest label search error -----------------");
                }
            });
        }
    }

    /**
     * 获取最终结果集
     *
     * @return 最终的数据结果集
     */
    public List<SearchResultTempBO> searchEnd() {
        return this.resultUid;
    }

    /**
     * 获取线程屏障对象
     *
     * @return 线程屏障对象
     */
    public CyclicBarrierUtil getCyclicBarrier() {
        return cyclicBarrier;
    }

    @Override
    @SuppressWarnings("all")
    public void run() {
        long start = System.currentTimeMillis();
        if (StringUtils.isEmpty(condition.getContext())) {
            this.resultUid.addAll(new ArrayList<>());
        } else {
            // 将三个线程中的结果集合进行转换为List集合，便于后续的操作
            List<SearchResultTempBO> userNameResult = new LinkedList<>(this.userNameIndicesResult);
            List<SearchResultTempBO> userResult = new LinkedList<>(this.userIndicesResult);
            List<SearchResultTempBO> blogResult = new LinkedList<>(this.blogIndicesResult);
            // 存储用户名线程中得到的结果集，主要用于用户名过滤后的操作
            Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap = new HashMap<>(500);
            // 根据搜索词与用户名相似度匹配过滤
            this.searchLogInfo.append(ResultFilterFactory.buildFilter().nameSimilarityFilter(
                    userNameResultMap, userNameResult, condition.getContext()).getLogInfo());
            // 根据用户信息过滤（如果搜索时需要进行条件过滤时）
            ResultFilterFactory userInfoFilter = ResultFilterFactory.buildFilter().userInfoFilter(condition, blogResult);
            List<SearchResultTempBO> blogResultFilter = (List<SearchResultTempBO>) userInfoFilter.getResult();
            this.searchLogInfo.append(userInfoFilter.getLogInfo());
            // 根据用户结果分数和博文结果分数重合的部分按照一定权重进行合并
            this.searchLogInfo.append(ResultFilterFactory.buildFilter()
                    .userBlogMergeFilter(blogResultFilter, userNameResultMap, userResult).getLogInfo());
            // 去除博文结果集中和用户结果集中重复的部分
            ResultFilterFactory removeRepeatFilter = ResultFilterFactory.buildFilter().removeRepeatUserBlogFilter(
                    userNameResultMap, blogResultFilter);
            List<SearchResultTempBO> resultTemp = (List<SearchResultTempBO>) removeRepeatFilter.getResult();
            this.searchLogInfo.append(removeRepeatFilter.getLogInfo());


            influenceScoreWeighting(blogResultFilter);
            long sortTimeStart = System.currentTimeMillis();
            relevanceOptimizationRanking(blogResultFilter);
            this.searchLogInfo.append("sort time : ").append(System.currentTimeMillis() - sortTimeStart).append("ms\n");
            Collections.sort(blogResultFilter);
            resultTemp.addAll(blogResultFilter);
            this.resultUid.addAll(resultTemp);
//            this.searchLogInfo.append("filter and add influence time : ").append(System.currentTimeMillis() - filterTime).append("ms\n");
        }
        this.searchLogInfo.append("result operator total : ").append(System.currentTimeMillis() - start).append("ms\n")
                .append("-------------------- The total time of this search is ").append(System.currentTimeMillis() - this.searchTimeStart).append("ms --------------------");
        logger.info(this.searchLogInfo.toString());
    }

    private void influenceScoreWeighting(List<SearchResultTempBO> searchResultTemp) {
        Float[] influenceResult = CommonUtil.scoreNormalization(searchResultTemp.stream().map(SearchResultTempBO::getScore).toArray(Float[]::new));
        Float[] correlationResult = CommonUtil.scoreNormalization(searchResultTemp.stream().map(SearchResultTempBO::getCorrelationScore).toArray(Float[]::new));
        if (weightConfig().isInfluenceWithWeight()) {
            for (int item = 0; item < searchResultTemp.size(); item++) {
                searchResultTemp.get(item).setCorrelationScore(correlationResult[item] * weightConfig().getBlogCorrelationScoreWeight() +
                        influenceResult[item] * weightConfig().getBlogInfluenceScoreWeight());
            }
        } else {
            for (int item = 0; item < searchResultTemp.size(); item++) {
                searchResultTemp.get(item).setCorrelationScore(correlationResult[item] * influenceResult[item]);
            }
        }
    }

    private void relevanceOptimizationRanking(List<SearchResultTempBO> blogResultFilter) {
        List<ResultSortByInteractionAbility> tempResult = new LinkedList<>();
        for (SearchResultTempBO item : blogResultFilter) {
            tempResult.add(new ResultSortByInteractionAbility(item.getUid(),
                    item.getReport().getAttitudeMedian(), item.getReport().getCommentMedian(), item.getReport().getRepostMedian(),
                    item.getReport().getMblogTotal(), item.getReport().getAttitudeSum(), item.getReport().getCommentSum(), item.getReport().getRepostSum()));
        }
        Map<String, ScoreWithRank> scoreMapWithInteraction = getScoreMapWithInteraction(tempResult);
        Map<String, ScoreWithRank> scoreMapWithFrequency = getScoreMapWithDifference(scoreMapWithInteraction, blogResultFilter);
        Map<String, ScoreWithRank> scoreMapWithRePostRatio = getScoreMapWithRePostRatio(blogResultFilter);
        Map<String, ScoreWithRank> scoreMapWithInterestLabel = getScoreMapWithInterestLabel(blogResultFilter);
        Set<String> newsMedia = searchRegistryConfig().getNewsMedia();
        ConcurrentHashMap<String, Integer> onlineWaterAmy = searchRegistryConfig().getOnlineWaterAmy();
        float waterAmyDecayPercentUpperLimit = weightConfig().getWaterAmyDecayPercentUpperLimit(),
                differenceValue = waterAmyDecayPercentUpperLimit - weightConfig().getWaterAmyDecayPercentLowerLimit(),
                differencePercent = differenceValue / 100;
        for (SearchResultTempBO item : blogResultFilter) {
            float score = item.getCorrelationScore() +
                    scoreMapWithInteraction.get(item.getUid()).getScore() +
                    scoreMapWithFrequency.get(item.getUid()).getScore() +
                    scoreMapWithRePostRatio.get(item.getUid()).getScore() +
                    (ObjectUtils.isEmpty(this.condition.getUid()) || StringUtils.isEmpty(this.condition.getUid()) ? 0.0f
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
        long interactionStart = System.currentTimeMillis();
        Collections.sort(tempResult);
        Map<String, ScoreWithRank> scoreMap = new HashMap<>(9216);
        float upperLimit = weightConfig().getInteractionUpperLimit();
        float decayFactor = upperLimit / tempResult.size();
        for (int index = 0; index < tempResult.size(); index++) {
            scoreMap.put(tempResult.get(index).getUid(), new ScoreWithRank(upperLimit - decayFactor * index, index + 1));
        }
        this.searchLogInfo.append("------ interaction filter time : ").append(System.currentTimeMillis() - interactionStart).append("ms\n");
        return scoreMap;
    }

    private Map<String, ScoreWithRank> getScoreMapWithDifference(Map<String, ScoreWithRank> interactionResultMap, List<SearchResultTempBO> tempResult) {
        long differenceStart = System.currentTimeMillis();
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
        this.searchLogInfo.append("------ difference filter time : ").append(System.currentTimeMillis() - differenceStart).append("ms\n");
        return scoreMap;
    }

    private Map<String, ScoreWithRank> getScoreMapWithRePostRatio(List<SearchResultTempBO> tempResult) {
        long rePostRatioStart = System.currentTimeMillis();
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
        this.searchLogInfo.append("------ rePost ratio filter time : ").append(System.currentTimeMillis() - rePostRatioStart).append("ms\n");
        return scoreMap;
    }

    private Map<String, ScoreWithRank> getScoreMapWithInterestLabel(List<SearchResultTempBO> tempResult) {
        long interestLabelStart = System.currentTimeMillis();
        Map<String, ScoreWithRank> scoreMap = new HashMap<>(9216);
        if (!ObjectUtils.isEmpty(this.condition.getUid()) && !StringUtils.isEmpty(this.condition.getUid())) {
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
        this.searchLogInfo.append("------ interest label operator time : ").append(System.currentTimeMillis() - interestLabelStart).append("ms\n");
        return scoreMap;
    }

    private class ResultSortByInteractionAbility implements Comparable<ResultSortByInteractionAbility> {

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

    private class ResultSortByInteractionDifferenceValue implements Comparable<ResultSortByInteractionDifferenceValue> {

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

    private class ResultSortByInterestLabelScore implements Comparable<ResultSortByInterestLabelScore> {

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

    private class ScoreWithRank {

        @Getter
        private Float score;
        @Getter
        private Integer rank;

        ScoreWithRank(Float score, Integer rank) {
            this.score = score;
            this.rank = rank;
        }
    }

}
