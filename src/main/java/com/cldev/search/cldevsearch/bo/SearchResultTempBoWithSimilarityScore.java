package com.cldev.search.cldevsearch.bo;

import com.cldev.search.cldevsearch.correlation.process.CalculationSearchFactory;
import lombok.Getter;

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
 * Build File @date: 2020/1/10 15:49
 * @version 1.0
 * @description
 */
public class SearchResultTempBoWithSimilarityScore implements Comparable<SearchResultTempBoWithSimilarityScore> {

    @Getter
    private SearchResultTempBO searchResultTempBo;
    private double similarityScore;

    public SearchResultTempBoWithSimilarityScore(SearchResultTempBO searchResultTempBo, double similarityScore) {
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
