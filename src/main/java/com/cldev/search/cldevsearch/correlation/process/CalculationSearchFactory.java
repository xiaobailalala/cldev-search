package com.cldev.search.cldevsearch.correlation.process;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.BlogCalculationSearch;
import com.cldev.search.cldevsearch.correlation.UserCalculationSearch;
import com.cldev.search.cldevsearch.correlation.extension.UserCalculationSearchName;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.util.ChinesePinyinUtil;
import com.cldev.search.cldevsearch.util.CommonCallBack;
import com.cldev.search.cldevsearch.util.CommonUtil;
import com.cldev.search.cldevsearch.util.SimilarityUtil;
import com.cldev.search.cldevsearch.vo.SearchResVO;
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

    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();

    private ExecutorService executorService = new ThreadPoolExecutor(10, 10, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), namedThreadFactory);

    private CyclicBarrier cyclicBarrier;

    private SearchConditionDTO condition;

    private Client client;

    private ConcurrentLinkedQueue<SearchResultTempBO> userIndicesResult = new ConcurrentLinkedQueue<>();

    private ConcurrentLinkedQueue<SearchResultTempBO> blogIndicesResult = new ConcurrentLinkedQueue<>();

    private ConcurrentLinkedQueue<SearchResultTempBO> userNameIndicesResult = new ConcurrentLinkedQueue<>();

    private List<SearchResVO> resultUid = new LinkedList<>();

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
        if (StringUtils.isEmpty(condition.getContext())) {
            this.cyclicBarrier = new CyclicBarrier(2, this);
            this.kolCalculationResultProcess(() -> this.userIndicesResult.addAll(new UserCalculationSearch(this.condition).initSearchCondition().getResult(this.client)));
        } else {
            this.cyclicBarrier = new CyclicBarrier(4, this);
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
                    logger.error("----------------- userNameIndicesResult search error -----------------");
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
                    logger.error("----------------- userNameIndicesResult search error -----------------");
                }
            });
        }
    }

    /**
     * 获取最终结果集
     * @return 最终的数据结果集
     */
    public List<SearchResVO> searchEnd() {
        return this.resultUid;
    }

    /**
     * 获取线程屏障对象
     * @return 线程屏障对象
     */
    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    /**
     * 搜索内容是否含有过滤条件
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
        for (SearchResultTempBO userName : userNameResult) {
            /* 优先根据原文中字比对 */
            double compareInChinese = SimilarityUtil.sim(userName.getName(), condition.getContext());
            if (compareInChinese >= weightConfig().getUsernameSimilarityChinese()) {
                userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, compareInChinese));
                continue;
            }
            /* 根据繁-简转化后的中字比对 */
            double compareInTraditional  = SimilarityUtil.sim(HanLP.convertToSimplifiedChinese(userName.getName()),
                    HanLP.convertToSimplifiedChinese(condition.getContext()));
            if (compareInTraditional >= weightConfig().getUsernameSimilarityTraditional()) {
                userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, compareInTraditional));
                continue;
            }
            /* 根据中-拼音转换后的拼音全拼比对 */
            double compareInPinyin  = SimilarityUtil.sim(ChinesePinyinUtil.toHanyuPinyin(userName.getName()),
                    ChinesePinyinUtil.toHanyuPinyin(condition.getContext()));
            if (compareInPinyin >= weightConfig().getUsernameSimilarityPinyin()) {
                userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, compareInPinyin));
                /* 根据中-拼音转换后的拼音首字母比对 */
            } /*else if (SimilarityUtil.sim(ChinesePinyinUtil.getFirstLettersLo(userName.getName()),
                    ChinesePinyinUtil.getFirstLettersLo(condition.getContext())) >= weightConfig().getUsernameSimilarityPinyinHead()) {
                userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, compareInPinyin));
            }*/
        }
        this.searchLogInfo.append("similarity time : ").append(System.currentTimeMillis() - similarityStart).append("ms\n");
    }

    private void influenceScoreWeighting(List<SearchResultTempBO> searchResultTempBOS) {
        Float[] influenceResult = CommonUtil.scoreNormalization(searchResultTempBOS.stream().map(SearchResultTempBO::getScore).toArray(Float[]::new));
        Float[] correlationResult = CommonUtil.scoreNormalization(searchResultTempBOS.stream().map(SearchResultTempBO::getCorrelationScore).toArray(Float[]::new));
        for (int item = 0; item < searchResultTempBOS.size(); item++) {
            searchResultTempBOS.get(item).setCorrelationScore(correlationResult[item] * weightConfig().getBlogCorrelationScoreWeight() +
                    influenceResult[item] * weightConfig().getBlogInfluenceScoreWeight());
        }
    }

    private List<SearchResultTempBO> filterSearchResult(List<SearchResultTempBO> resultTemp) {
        List<SearchResultTempBO> searchResult = new LinkedList<>();
        if (isCondition()) {
            for (SearchResultTempBO item : resultTemp) {
                if (resolverAddress(item.getAddress(), item.getProvince()) &&
                        resolverSex(item.getSex()) &&
                        resolverFansNum(item.getWbFans()) &&
                        resolverInterest(item.getLabels())) {
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

    private class SearchResultTempBoWithSimilarityScore implements Comparable<SearchResultTempBoWithSimilarityScore> {

        @Getter
        private SearchResultTempBO searchResultTempBo;
        private double similarityScore;

        SearchResultTempBoWithSimilarityScore(SearchResultTempBO searchResultTempBo, double similarityScore) {
            this.searchResultTempBo = searchResultTempBo;
            this.similarityScore = similarityScore;
        }

        @Override
        @SuppressWarnings("all")
        public int compareTo(SearchResultTempBoWithSimilarityScore o) {
            int sortBySimilarityScore = Double.compare(o.similarityScore, this.similarityScore);
            if (sortBySimilarityScore == 0) {
                return o.searchResultTempBo.getCorrelationScore().compareTo(this.searchResultTempBo.getCorrelationScore());
            }
            return sortBySimilarityScore;
        }
    }

}
