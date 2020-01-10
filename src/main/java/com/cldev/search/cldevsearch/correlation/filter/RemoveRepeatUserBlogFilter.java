package com.cldev.search.cldevsearch.correlation.filter;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.bo.SearchResultTempBoWithSimilarityScore;
import com.cldev.search.cldevsearch.correlation.process.SearchFilter;

import java.util.*;
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
 * Build File @date: 2020/1/10 20:16
 * @version 1.0
 * @description
 */
public class RemoveRepeatUserBlogFilter implements SearchFilter {

    private Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap;
    private List<SearchResultTempBO> blogResultFilter;
    private List<SearchResultTempBO> resultTemp;

    public RemoveRepeatUserBlogFilter(Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap,
                                      List<SearchResultTempBO> blogResultFilter) {
        this.userNameResultMap = userNameResultMap;
        this.blogResultFilter = blogResultFilter;
    }

    @Override
    @SuppressWarnings("unused")
    public void filter() {
        List<SearchResultTempBoWithSimilarityScore> resultUserName = new LinkedList<>(userNameResultMap.values());
        Collections.sort(resultUserName);
        long filterTime = System.currentTimeMillis();
        this.resultTemp = resultUserName.stream()
                .map(SearchResultTempBoWithSimilarityScore::getSearchResultTempBo).collect(Collectors.toList());
        Set<String> nameUidSet = this.resultTemp.stream().map(SearchResultTempBO::getUid).collect(Collectors.toSet());
        for (int item = 0; item < blogResultFilter.size(); item++) {
            if (nameUidSet.contains(blogResultFilter.get(item).getUid())) {
                SearchResultTempBO remove = blogResultFilter.remove(item);
            }
        }
    }

    @Override
    public Object getResult() {
        return this.resultTemp;
    }
}
