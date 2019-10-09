package com.cldev.search.cldevsearch.correlation.process;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.BlogCalculationSearch;
import com.cldev.search.cldevsearch.correlation.UserCalculationSearch;
import com.cldev.search.cldevsearch.correlation.extension.UserCalculationSearchName;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.util.CommonCallBack;
import com.cldev.search.cldevsearch.util.SimilarityUtil;
import com.cldev.search.cldevsearch.vo.SearchResVO;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.elasticsearch.client.Client;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;

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
 * Build File @date: 2019/9/26 17:17
 * @version 1.0
 * @description
 */
public class CalculationSearchFactory implements Runnable {

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

    private void kolCalculationResultProcess(CommonCallBack callBack) {
        this.executorService.execute(() -> {
            callBack.run();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
    }

    public void searchStart() {
        if (StringUtils.isEmpty(condition.getContext())) {
            this.cyclicBarrier = new CyclicBarrier(2, this);
            this.kolCalculationResultProcess(() -> this.userIndicesResult.addAll(new UserCalculationSearch(this.condition).initSearchCondition().getResult(this.client)));
        } else {
            this.cyclicBarrier = new CyclicBarrier(4, this);
            this.kolCalculationResultProcess(() -> {
                long start = System.currentTimeMillis();
                this.userNameIndicesResult.addAll(new UserCalculationSearchName(this.condition).initSearchCondition().getResult(this.client));
                System.out.println("search name: " + (System.currentTimeMillis() - start) + "ms");
            });
//            if (isCondition()) {
//                this.kolCalculationResultProcess(() -> this.userIndicesResult.addAll(new UserCalculationSearch(this.condition).initSearchCondition().getResult(this.client)));
//                this.kolCalculationResultProcess(() -> {
//                    long conditionStart = System.currentTimeMillis();
//                    List<String> uidList = new UserCalculationSearchUid(this.condition).initSearchCondition().getResult(this.client).stream()
//                            .map(SearchResVO::getUid).collect(Collectors.toList());
//                    this.blogIndicesResult.addAll(new BlogCalculationSearchUidList(this.condition).setUidList(uidList).initSearchCondition().getResult(this.client));
//                    System.out.println("condition time: " + (System.currentTimeMillis() - conditionStart) + "ms");
//                });
//            } else {
                this.kolCalculationResultProcess(() -> {
                    long start = System.currentTimeMillis();
                    this.userIndicesResult.addAll(new UserCalculationSearch(this.condition).initSearchCondition().getResult(this.client));
                    System.out.println("search user: " + (System.currentTimeMillis() - start) + "ms");
                });
                this.kolCalculationResultProcess(() -> {
                    long start = System.currentTimeMillis();
                    this.blogIndicesResult.addAll(new BlogCalculationSearch(this.condition).initSearchCondition().getResult(this.client));
                    System.out.println("search blog: " + (System.currentTimeMillis() - start) + "ms");
                });
//            }
        }
    }

    public List<SearchResVO> searchEnd() {
        return this.resultUid;
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    private boolean isCondition() {
        int isCondition = 0;
        if (!ObjectUtils.isEmpty(condition.getSex()) && condition.getSex() != 0) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(condition.getAddresses()) && condition.getAddresses().length != 0) {
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
    public void run() {
        long start = System.currentTimeMillis();
        if (StringUtils.isEmpty(condition.getContext())) {
            this.resultUid.addAll(this.userIndicesResult);
        } else {
            List<SearchResultTempBO> userNameResult = new LinkedList<>(this.userNameIndicesResult);
            List<SearchResultTempBO> userResult = new LinkedList<>(this.userIndicesResult);
            List<SearchResultTempBO> blogResult = new LinkedList<>(this.blogIndicesResult);
            Map<String, SearchResultTempBO> userNameResultMap = new HashMap<>(500);
            long similarityStart = System.currentTimeMillis();
            /* 根据用户名进行字符串相似度排除 */
            for (SearchResultTempBO userName : userNameResult) {
                if (SimilarityUtil.sim(userName.getName(), condition.getContext()) >= USERNAME_SIMILARITY) {
                    userNameResultMap.put(userName.getUid(), userName);
                }
            }
            System.out.println("similarity time : " + (System.currentTimeMillis() - similarityStart));
            Map<String, SearchResultTempBO> userResultMap = new HashMap<>(1000);
            for (SearchResultTempBO user : userResult) {
                userResultMap.put(user.getUid(), user);
            }
            for (SearchResultTempBO blog : blogResult) {
                SearchResultTempBO userName = userNameResultMap.get(blog.getUid());
                SearchResultTempBO user = userResultMap.get(blog.getUid());
                if (!ObjectUtils.isEmpty(userName)) {
                    userName.setCorrelationScore(blog.getCorrelationScore() * BLOG_SCORE_WEIGHT + userName.getCorrelationScore() * USER_SCORE_WEIGHT);
                }
                if (!ObjectUtils.isEmpty(user)) {
                    blog.setCorrelationScore(blog.getCorrelationScore() * BLOG_SCORE_WEIGHT + user.getCorrelationScore() * USER_SCORE_WEIGHT);
                }
            }
            List<SearchResultTempBO> resultUserName = new LinkedList<>();
            for (Map.Entry<String, SearchResultTempBO> stringSearchResultTempBoEntry : userNameResultMap.entrySet()) {
                resultUserName.add(stringSearchResultTempBoEntry.getValue());
            }
            Collections.sort(resultUserName);
            List<SearchResultTempBO> resultTemp = new LinkedList<>(resultUserName);
            Collections.sort(blogResult);
            resultTemp.addAll(blogResult);
            this.resultUid.addAll(filterSearchResult(resultTemp));
        }
        System.out.println("result operator: " + (System.currentTimeMillis() - start) + "ms");
    }

    private List<SearchResVO> filterSearchResult(List<SearchResultTempBO> resultTemp) {
        List<SearchResVO> searchResult = new LinkedList<>();
        for (SearchResultTempBO item : resultTemp) {
            if (resolverAddress(item.getAddress()) && resolverSex(item.getSex()) && resolverFansNum(item.getWbFans()) && resolverInterest(item.getLabels())) {
                searchResult.add(item);
            }
        }
        return searchResult;
    }

    private boolean resolverAddress(String address) {
        if (ObjectUtils.isEmpty(this.condition.getAddresses()) || this.condition.getAddresses().length == 0) {
            return true;
        }
        return Arrays.asList(this.condition.getAddresses()).contains(address);
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

}
