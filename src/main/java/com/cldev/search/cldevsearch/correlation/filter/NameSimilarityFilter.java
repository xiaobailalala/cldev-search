package com.cldev.search.cldevsearch.correlation.filter;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.bo.SearchResultTempBoWithSimilarityScore;
import com.cldev.search.cldevsearch.correlation.process.SearchFilter;
import com.cldev.search.cldevsearch.util.SimilarityUtil;
import com.hankcs.hanlp.HanLP;

import java.util.List;
import java.util.Map;

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
 * Build File @date: 2020/1/10 10:48
 * @version 1.0
 * @description
 */
public class NameSimilarityFilter implements SearchFilter {

    private Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap;

    private List<SearchResultTempBO> userNameResult;

    private String name;

    public NameSimilarityFilter(Map<String, SearchResultTempBoWithSimilarityScore> userNameResultMap,
                         List<SearchResultTempBO> userNameResult,
                         String name) {
        this.userNameResultMap = userNameResultMap;
        this.userNameResult = userNameResult;
        this.name = name;
    }

    @Override
    public void filter() {
        // 根据用户名进行字符串相似度排除
        List<String> screenName = searchRegistryConfig().getScreenName(this.name);
        if (screenName.size() == 0) {
            screenName.add(this.name);
        }
        item:
        for (SearchResultTempBO userName : userNameResult) {
            for (String name : screenName) {
                // 优先根据原文中字比对
                double compareInChinese = SimilarityUtil.sim(userName.getName().toLowerCase(), name.toLowerCase());
                if (compareInChinese >= weightConfig().getUsernameSimilarityChinese()) {
                    userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, compareInChinese));
                    continue item;
                }
                // 去除原文和结果中的特殊字符后进行比对
                if (name.toLowerCase().replaceAll(weightConfig().getNameRegex(), "")
                        .equals(userName.getName().toLowerCase().replaceAll(weightConfig().getNameRegex(), ""))) {
                    userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, 1));
                    continue item;
                }
                // 根据繁-简转化后的中字比对
                double compareInTraditional = SimilarityUtil.sim(HanLP.convertToSimplifiedChinese(userName.getName()).toLowerCase(),
                        HanLP.convertToSimplifiedChinese(name).toLowerCase());
                if (compareInTraditional >= weightConfig().getUsernameSimilarityTraditional()) {
                    userNameResultMap.put(userName.getUid(), new SearchResultTempBoWithSimilarityScore(userName, compareInTraditional));
                    continue item;
                }
            }
        }
    }

    @Override
    public Object getResult() {
        return null;
    }

}
