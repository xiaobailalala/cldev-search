package com.cldev.search.cldevsearch.correlation.process;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.BlogCalculationSearch;
import com.cldev.search.cldevsearch.correlation.UserCalculationSearch;
import com.cldev.search.cldevsearch.correlation.extension.UserCalculationSearchName;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.entity.UserInterestLabel;
import com.cldev.search.cldevsearch.mapper.UserInterestLabelMapper;
import com.cldev.search.cldevsearch.util.*;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hankcs.hanlp.HanLP;
import lombok.Getter;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

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
@SuppressWarnings("all")
public class CalculationSearchFactory implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CalculationSearchFactory.class);

    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();

    private ExecutorService executorService = new ThreadPoolExecutor(10, 10, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), namedThreadFactory);

    private CyclicBarrier cyclicBarrier;

    private SearchConditionDTO condition;

    private Client client;

    private ConcurrentLinkedQueue<SearchResultTempBO> userIndicesResult = new ConcurrentLinkedQueue<>();

    private ConcurrentLinkedQueue<SearchResultTempBO> blogIndicesResult = new ConcurrentLinkedQueue<>();

    private ConcurrentLinkedQueue<SearchResultTempBO> userNameIndicesResult = new ConcurrentLinkedQueue<>();

    private List<SearchResultTempBO> resultUid = new LinkedList<>();

    private ConcurrentHashMap<Integer, Float> userInterestLabelScore = new ConcurrentHashMap<>(128);

    private StringBuffer searchLogInfo = new StringBuffer("The search process info : \n");

    private long searchTimeStart = System.currentTimeMillis();

    private CalculationSearchFactory() {
    }

    public static CalculationSearchFactory buildCalculation() {
        return new CalculationSearchFactory();
    }

    public CalculationSearchFactory calculationBuilder(SearchConditionDTO condition, Client client) {
        this.condition = condition;
        this.client = client;
        return this;
    }

    /**
     * 异步线程进行多线程搜索
     *
     * @param callBack 回调接口-异步任务主体
     */
    private void kolCalculationResultProcess(CommonCallBack callBack) {
        this.executorService.execute(() -> {
            try {
                callBack.run();
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 开始执行搜索
     */
    public void searchStart() {
        searchLogInfo.append("-------------------- search start, the search context is : ").append(this.condition.getContext()).append(" --------------------\n")
                .append("-------------------- the search condition is : sex-").append(this.condition.getSex()).append(" address-")
                .append(Arrays.toString(this.condition.getAddress())).append(" interest-").append(this.condition.getInterest())
                .append(" fansNum-").append(this.condition.getFansNum()).append(" --------------------\n");
        boolean isUserLogged = !ObjectUtils.isEmpty(this.condition.getUid()) && !StringUtils.isEmpty(this.condition.getUid());
        if (StringUtils.isEmpty(condition.getContext())) {
            this.cyclicBarrier = new CyclicBarrier(isUserLogged ? 3 : 2, this);
            this.userInterestLabelScoreProcess(isUserLogged);
            this.kolCalculationResultProcess(() -> this.userIndicesResult.addAll(new UserCalculationSearch(this.condition).initSearchCondition().getResult(this.client)));
        } else {
            this.cyclicBarrier = new CyclicBarrier(isUserLogged ? 5 : 4, this);
            this.userInterestLabelScoreProcess(isUserLogged);
            // 用户名检索线程
            this.kolCalculationResultProcess(() -> {
                try {
                    long start = System.currentTimeMillis();
                    UserCalculationSearchName userCalculationSearchName = new UserCalculationSearchName(this.condition);
                    this.userNameIndicesResult.addAll(userCalculationSearchName.initSearchCondition().getResult(this.client));
                    searchLogInfo.append(userCalculationSearchName.getSearchLogInfo()).append("search name total: ").append(System.currentTimeMillis() - start).append("ms\n");
                } catch (Exception e) {
                    this.userNameIndicesResult.addAll(new ArrayList<>());
                    e.printStackTrace();
                    logger.error("----------------- userNameIndicesResult search error -----------------");
                }
            });
            // 用户信息检索线程
            this.kolCalculationResultProcess(() -> {
                try {
                    long start = System.currentTimeMillis();
                    UserCalculationSearch userCalculationSearch = new UserCalculationSearch(this.condition);
                    this.userIndicesResult.addAll(userCalculationSearch.initSearchCondition().getResult(this.client));
                    searchLogInfo.append(userCalculationSearch.getSearchLogInfo()).append("search user total: ").append(System.currentTimeMillis() - start).append("ms\n");
                } catch (Exception e) {
                    this.userIndicesResult.addAll(new ArrayList<>());
                    e.printStackTrace();
                    logger.error("----------------- userInfoIndicesResult search error -----------------");
                }
            });
            // 博文文档检索线程
            this.kolCalculationResultProcess(() -> {
                try {
                    long start = System.currentTimeMillis();
                    BlogCalculationSearch blogCalculationSearch = new BlogCalculationSearch(this.condition);
                    this.blogIndicesResult.addAll(blogCalculationSearch.initSearchCondition().getResult(this.client));
                    searchLogInfo.append(blogCalculationSearch.getSearchLogInfo()).append("search blog total: ").append(System.currentTimeMillis() - start).append("ms\n");
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
    private void userInterestLabelScoreProcess(boolean isLogged) {
        if (isLogged) {
            this.kolCalculationResultProcess(() -> {
                try {
                    long start = System.currentTimeMillis();
                    UserInterestLabelMapper mapper = BeanUtil.userInterestLabelMapper();
                    for (UserInterestLabel item : mapper
                            .select(new UserInterestLabel().setUid(this.condition.getUid()))) {
                        userInterestLabelScore.put(searchRegistryConfig().getInterestOne(item.getLabel()), item.getRes());
                    }
                    searchLogInfo.append("search user interest label : ").append(System.currentTimeMillis() - start).append("ms\n");
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
    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    /**
     * 搜索内容是否含有过滤条件
     *
     * @return 是否含有过滤条件，true为含有，false为不含
     */
    private boolean isCondition() {
        int isCondition = 0;
        if (!ObjectUtils.isEmpty(condition.getSex()) && condition.getSex() != 0) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(condition.getAddress()) && condition.getAddress().length != 0) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(condition.getFansAge()) && condition.getFansAge().size() != 0) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(condition.getInterest()) && condition.getInterest().size() != 0) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(condition.getFansNum()) && condition.getFansNum().size() != 0) {
            isCondition++;
        }
        return isCondition != 0;
    }


    @Override
    @SuppressWarnings("all")
    public void run() {
        long start = System.currentTimeMillis();
        if (StringUtils.isEmpty(condition.getContext())) {
            this.resultUid.addAll(new ArrayList<>());
        } else {
            List<SearchResultTempBO> userNameResult = new LinkedList<>(this.userNameIndicesResult);
            List<SearchResultTempBO> userResult = new LinkedList<>(this.userIndicesResult);
            List<SearchResultTempBO> blogResult = new LinkedList<>(this.blogIndicesResult);
            Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap = new HashMap<>(500);
            similarityExclusion(userNameResultMap, userNameResult);
            Map<String, SearchResultTempBO> userResultMap = new HashMap<>(1000);
            for (SearchResultTempBO user : userResult) {
                userResultMap.put(user.getUid(), user);
            }
            List<SearchResultTempBO> blogResultFilter = filterSearchResult(blogResult);
            for (SearchResultTempBO blog : blogResultFilter) {
                SearchResultTempBoWithSimilarityScore userName = userNameResultMap.get(blog.getUid());
                SearchResultTempBO user = userResultMap.get(blog.getUid());
                if (!ObjectUtils.isEmpty(userName)) {
                    SearchResultTempBO tempUserName = userName.getSearchResultTempBo();
                    tempUserName.setCorrelationScore(blog.getCorrelationScore() * weightConfig().getBlogScoreWeight() +
                            tempUserName.getCorrelationScore() * weightConfig().getUserScoreWeight());
                }
                if (!ObjectUtils.isEmpty(user)) {
                    blog.setCorrelationScore(blog.getCorrelationScore() * weightConfig().getBlogScoreWeight() +
                            user.getCorrelationScore() * weightConfig().getUserScoreWeight());
                }
            }
            List<SearchResultTempBoWithSimilarityScore> resultUserName = new LinkedList<>();
            for (Map.Entry<String, SearchResultTempBoWithSimilarityScore> stringSearchResultTempBoEntry : userNameResultMap.entrySet()) {
                resultUserName.add(stringSearchResultTempBoEntry.getValue());
            }
            Collections.sort(resultUserName);
            long filterTime = System.currentTimeMillis();
            List<SearchResultTempBO> resultTemp = resultUserName.stream()
                    .map(SearchResultTempBoWithSimilarityScore::getSearchResultTempBo).collect(Collectors.toList());
            Set<String> nameUidSet = resultTemp.stream().map(SearchResultTempBO::getUid).collect(Collectors.toSet());
            for (int item = 0; item < blogResultFilter.size(); item++) {
                if (nameUidSet.contains(blogResultFilter.get(item).getUid())) {
                    blogResultFilter.remove(item);
                }
            }
            influenceScoreWeighting(blogResultFilter);
            long sortTimeStart = System.currentTimeMillis();
            relevanceOptimizationRanking(blogResultFilter);
            this.searchLogInfo.append("sort time : ").append(System.currentTimeMillis() - sortTimeStart).append("ms\n");
            Collections.sort(blogResultFilter);
            resultTemp.addAll(blogResultFilter);
            this.resultUid.addAll(resultTemp);
            this.searchLogInfo.append("filter and add influence time : ").append(System.currentTimeMillis() - filterTime).append("ms\n");
        }
        this.searchLogInfo.append("result operator total : ").append(System.currentTimeMillis() - start).append("ms\n")
                .append("-------------------- The total time of this search is ").append(System.currentTimeMillis() - this.searchTimeStart).append("ms --------------------");
        logger.info(this.searchLogInfo.toString());
    }

    private void similarityExclusion(Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap, List<SearchResultTempBO> userNameResult) {
        /* 根据用户名进行字符串相似度排除 */
        long similarityStart = System.currentTimeMillis();
        List<String> screenName = searchRegistryConfig().getScreenName(condition.getContext());
        if (screenName.size() == 0) {
            screenName.add(condition.getContext());
        }
        item:
        for (SearchResultTempBO userName : userNameResult) {
            for (String name : screenName) {
                /* 优先根据原文中字比对 */
                double compareInChinese = SimilarityUtil.sim(userName.getName().toLowerCase(), name.toLowerCase());
                if (compareInChinese >= weightConfig().getUsernameSimilarityChinese()) {
                    userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, compareInChinese));
                    continue item;
                }
                /* 去除原文和结果中的特殊字符后进行比对 */
                if (name.toLowerCase().replaceAll(weightConfig().getNameRegex(), "")
                        .equals(userName.getName().toLowerCase().replaceAll(weightConfig().getNameRegex(), ""))) {
                    userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, 1));
                    continue item;
                }
                /* 根据繁-简转化后的中字比对 */
                double compareInTraditional = SimilarityUtil.sim(HanLP.convertToSimplifiedChinese(userName.getName()).toLowerCase(),
                        HanLP.convertToSimplifiedChinese(name).toLowerCase());
                if (compareInTraditional >= weightConfig().getUsernameSimilarityTraditional()) {
                    userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, compareInTraditional));
                    continue item;
                }
                /* 根据中-拼音转换后的拼音全拼比对 */
//            double compareInPinyin  = SimilarityUtil.sim(ChinesePinyinUtil.toHanyuPinyin(userName.getName()),
//                    ChinesePinyinUtil.toHanyuPinyin(condition.getContext()));
//            if (compareInPinyin >= weightConfig().getUsernameSimilarityPinyin() &&
//                    compareInChinese >= weightConfig().getPinyinChildWithChinese() &&
//                    compareInTraditional >= weightConfig().getPinyinChildWithTraditional()) {
//                userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, compareInPinyin));
//            }
                /* 根据中-拼音转换后的拼音首字母比对 */
             /*else if (SimilarityUtil.sim(ChinesePinyinUtil.getFirstLettersLo(userName.getName()),
                    ChinesePinyinUtil.getFirstLettersLo(condition.getContext())) >= weightConfig().getUsernameSimilarityPinyinHead()) {
                userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, compareInPinyin));
            }*/
            }
        }
        this.searchLogInfo.append("similarity time : ").append(System.currentTimeMillis() - similarityStart).append("ms\n");
    }

    private void influenceScoreWeighting(List<SearchResultTempBO> searchResultTempBOS) {
        Float[] influenceResult = CommonUtil.scoreNormalization(searchResultTempBOS.stream().map(SearchResultTempBO::getScore).toArray(Float[]::new));
        Float[] correlationResult = CommonUtil.scoreNormalization(searchResultTempBOS.stream().map(SearchResultTempBO::getCorrelationScore).toArray(Float[]::new));
        if (weightConfig().isInfluenceWithWeight()) {
            for (int item = 0; item < searchResultTempBOS.size(); item++) {
                searchResultTempBOS.get(item).setCorrelationScore(correlationResult[item] * weightConfig().getBlogCorrelationScoreWeight() +
                        influenceResult[item] * weightConfig().getBlogInfluenceScoreWeight());
            }
        } else {
            for (int item = 0; item < searchResultTempBOS.size(); item++) {
                searchResultTempBOS.get(item).setCorrelationScore(correlationResult[item] * influenceResult[item]);
            }
        }
    }

    private List<SearchResultTempBO> filterSearchResult(List<SearchResultTempBO> resultTemp) {
        List<SearchResultTempBO> searchResult = new LinkedList<>();
        if (isCondition()) {
            for (SearchResultTempBO item : resultTemp) {
                if (resolverAddress(item.getAddress(), item.getProvince()) &&
                        resolverSex(item.getSex()) &&
                        resolverFansNum(item.getWbFans()) &&
                        resolverInterest(item.getShowLabels())) {
                    searchResult.add(item);
                }
            }
        } else {
            searchResult.addAll(resultTemp);
        }
        return searchResult;
    }

    private boolean resolverAddress(String address, String province) {
        if (ObjectUtils.isEmpty(this.condition.getAddress()) || this.condition.getAddress().length == 0) {
            return true;
        }
        return Arrays.asList(this.condition.getAddress()).contains(address) || Arrays.asList(this.condition.getAddress()).contains(province);
    }

    private boolean resolverSex(Integer sex) {
        if (ObjectUtils.isEmpty(this.condition.getSex()) || this.condition.getSex() == 0) {
            return true;
        }
        return this.condition.getSex().equals(sex);
    }

    private boolean resolverFansNum(Integer fans) {
        if (this.condition.getFansNum().size() == 0) {
            return true;
        }
        for (SearchConditionDTO.FansNum fansNum : this.condition.getFansNum()) {
            if (fans > fansNum.from && fans <= fansNum.to) {
                return true;
            }
        }
        return false;
    }

    private boolean resolverInterest(List<Integer> interest) {
        if (this.condition.getInterest().size() == 0) {
            return true;
        }
        Set<Integer> tempSet = new HashSet<>(interest);
        for (Integer item : this.condition.getInterest()) {
            if (tempSet.contains(item)) {
                return true;
            }
        }
        return false;
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
            Set<Integer> interest = this.userInterestLabelScore.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toSet());
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

    private class SearchResultTempBoWithSimilarityScore implements Comparable<SearchResultTempBoWithSimilarityScore> {

        @Getter
        private SearchResultTempBO searchResultTempBo;
        private double similarityScore;

        SearchResultTempBoWithSimilarityScore(SearchResultTempBO searchResultTempBo, double similarityScore) {
            this.searchResultTempBo = searchResultTempBo;
            this.similarityScore = similarityScore;
        }

        @Override
        public int compareTo(SearchResultTempBoWithSimilarityScore o) {
            int sortBySimilarityScore = Double.compare(o.similarityScore, this.similarityScore);
            if (sortBySimilarityScore == 0) {
                return o.searchResultTempBo.getCorrelationScore().compareTo(this.searchResultTempBo.getCorrelationScore());
            }
            return sortBySimilarityScore;
        }
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
            Float thisSumScore = this.attitudeSum * weightConfig().getAttitudeWeight() / this.blogTotal +
                    this.commentSum * weightConfig().getCommentWeight() / this.blogTotal +
                    this.repostSum * weightConfig().getRepostWeight() / this.blogTotal;
            Float otherSumScore = o.attitudeSum * weightConfig().getAttitudeWeight() / o.blogTotal +
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
            return o.score.compareTo(o.score);
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
