package com.cldev.search.cldevsearch.correlation.process;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.bo.SearchResultTempBoWithSimilarityScore;
import com.cldev.search.cldevsearch.correlation.filter.*;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
 * Build File @date: 2020/1/9 20:03
 * @version 1.0
 * @description
 */
public class ResultFilterFactory {

    private ResultFilterFactory() {}

    private SearchFilter filter;

    private StringBuffer searchLogInfo = new StringBuffer();

    static ResultFilterFactory buildFilter() {
        return new ResultFilterFactory();
    }

    ResultFilterFactory nameSimilarityFilter(Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap,
                                             List<SearchResultTempBO> userNameResult, String name) {
        return this.filterMain(new NameSimilarityFilter(userNameResultMap, userNameResult, name),
                "similarity time : ");
    }

    ResultFilterFactory userInfoFilter(SearchConditionDTO condition, List<SearchResultTempBO> blogResult) {
        return this.filterMain(new UserInfoFilter(condition, blogResult), "condition filter time : ");
    }

    ResultFilterFactory userBlogMergeFilter(List<SearchResultTempBO> blogResultFilter,
                                            Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap,
                                            List<SearchResultTempBO> userResult) {
        return this.filterMain(new UserBlogMergeFilter(blogResultFilter, userNameResultMap, userResult),
                "user blog score list merge time : ");
    }

    ResultFilterFactory removeRepeatUserBlogFilter(Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap,
                                                   List<SearchResultTempBO> blogResultFilter) {
        return this.filterMain(new RemoveRepeatUserBlogFilter(userNameResultMap, blogResultFilter),
                "remove repeat user for user and blog time : ");
    }

    ResultFilterFactory relevanceOptimizationFilter(List<SearchResultTempBO> blogResultFilter, String uid,
                                                    ConcurrentHashMap<Integer, Float> userInterestLabelScore) {
        return this.filterMain(new RelevanceOptimizationFilter(blogResultFilter, uid, userInterestLabelScore),
                "relevance optimization filter time : ");
    }

    public Object getResult() {
        return filter.getResult();
    }

    String getLogInfo() {
        return this.searchLogInfo.toString();
    }

    private ResultFilterFactory filterMain(SearchFilter filter, String logInfo) {
        long start = System.currentTimeMillis();
        this.filter = filter;
        this.filter.filter();
        this.searchLogInfo.append(logInfo).append(System.currentTimeMillis() - start).append("ms\n");
        return this;
    }

}
