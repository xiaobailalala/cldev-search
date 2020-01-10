package com.cldev.search.cldevsearch.correlation.filter;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.bo.SearchResultTempBoWithSimilarityScore;
import com.cldev.search.cldevsearch.correlation.process.SearchFilter;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Build File @date: 2020/1/10 19:54
 * @version 1.0
 * @description
 */
public class UserBlogMergeFilter implements SearchFilter {

    private List<SearchResultTempBO> blogResultFilter;
    private Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap;
    private List<SearchResultTempBO> userResult;

    public UserBlogMergeFilter(List<SearchResultTempBO> blogResultFilter,
                               Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap,
                               List<SearchResultTempBO> userResult) {
        this.blogResultFilter = blogResultFilter;
        this.userNameResultMap = userNameResultMap;
        this.userResult = userResult;
    }

    @Override
    public void filter() {
        Map<String, SearchResultTempBO> userResultMap = new HashMap<>(1000);
        for (SearchResultTempBO user : this.userResult) {
            userResultMap.put(user.getUid(), user);
        }
        for (SearchResultTempBO blog : this.blogResultFilter) {
            SearchResultTempBoWithSimilarityScore userName = this.userNameResultMap.get(blog.getUid());
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
    }

    @Override
    public Object getResult() {
        return null;
    }
}
