package com.cldev.search.cldevsearch.correlation.process;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.BlogCalculationSearch;
import com.cldev.search.cldevsearch.correlation.UserCalculationSearch;
import com.cldev.search.cldevsearch.correlation.extension.BlogCalculationSearchUidList;
import com.cldev.search.cldevsearch.correlation.extension.UserCalculationSearchName;
import com.cldev.search.cldevsearch.correlation.extension.UserCalculationSearchUid;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.util.CommonCallBack;
import com.cldev.search.cldevsearch.vo.SearchResVO;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.elasticsearch.client.Client;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;
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
 * Build File @date: 2019/9/26 17:17
 * @version 1.0
 * @description
 */
public class CalculationSearchFactory implements Runnable {

    private final static float USER_SCORE_WEIGHT = 0.6f;

    private final static float BLOG_SCORE_WEIGHT = 0.4f;

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
            if (isCondition()) {
                this.kolCalculationResultProcess(() -> this.userIndicesResult.addAll(new UserCalculationSearch(this.condition).initSearchCondition().getResult(this.client)));
                this.kolCalculationResultProcess(() -> {
                    List<String> uidList = new UserCalculationSearchUid(this.condition).initSearchCondition().getResult(this.client).stream()
                            .map(SearchResVO::getUid).collect(Collectors.toList());
                    this.blogIndicesResult.addAll(new BlogCalculationSearchUidList(this.condition).setUidList(uidList).initSearchCondition().getResult(this.client));
                });
            } else {
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
            }
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
        if (!ObjectUtils.isEmpty(condition.getAddresses())) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(condition.getFansAge())) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(condition.getInterest())) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(condition.getFansNum())) {
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
            for (SearchResultTempBO userName : userNameResult) {
                userNameResultMap.put(userName.getUid(), userName);
            }
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
            for (Map.Entry<String, SearchResultTempBO> stringSearchResultTempBOEntry : userNameResultMap.entrySet()) {
                resultUserName.add(stringSearchResultTempBOEntry.getValue());
            }
            Collections.sort(resultUserName);
            this.resultUid.addAll(resultUserName);
            Collections.sort(blogResult);
            this.resultUid.addAll(blogResult);
        }
        System.out.println("result operator: " + (System.currentTimeMillis() - start) + "ms");
    }

}
