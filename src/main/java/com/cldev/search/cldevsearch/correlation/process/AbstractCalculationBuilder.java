package com.cldev.search.cldevsearch.correlation.process;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
 * Build File @date: 2019/9/27 23:58
 * @version 1.0
 * @description
 */
public abstract class AbstractCalculationBuilder {

    protected BoolQueryBuilder boolQueryBuilder;
    protected SearchRequest searchRequest;
    protected SearchConditionDTO searchConditionDTO;

    protected AbstractCalculationBuilder(SearchConditionDTO searchConditionDTO) {
        this.searchConditionDTO = searchConditionDTO;
        this.boolQueryBuilder = new BoolQueryBuilder();
    }

    protected BoolQueryBuilder childBoolQueryBuilder(Class<? extends BoolQueryBuilder> bool) {
        for (QueryBuilder queryBuilder : boolQueryBuilder.must()) {
            if (queryBuilder instanceof BoolQueryBuilder) {
                if (queryBuilder.getClass().equals(bool)) {
                    return (BoolQueryBuilder) queryBuilder;
                }
            }
        }
        BoolQueryBuilder boolQueryBuilder = null;
        try {
            Constructor<? extends BoolQueryBuilder> constructor = bool.getConstructor();
            boolQueryBuilder = constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return boolQueryBuilder;
    }

    protected BoolQueryBuilder resolverAndConfirmBoolQueryBuilder(BoolQueryBuilder ...boolQueryBuilders) {
        for (BoolQueryBuilder queryBuilder : boolQueryBuilders) {
            if (!ObjectUtils.isEmpty(queryBuilder)) {
                boolQueryBuilder = queryBuilder;
            }
        }
        return boolQueryBuilder;
    }

    protected Float[] scoreUniformization(Float[] score) {
        Float[] temp = new Float[score.length];
        System.arraycopy(score, 0, temp, 0, score.length);
        Arrays.sort(score);
        Float[] result = new Float[score.length];
        for (int item = 0; item < score.length; item++) {
            result[item] = (temp[item] - score[0]) / (score[score.length - 1] - score[0]);
        }
        return result;
    }

}
