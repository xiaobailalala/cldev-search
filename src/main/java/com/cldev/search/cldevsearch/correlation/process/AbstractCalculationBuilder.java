package com.cldev.search.cldevsearch.correlation.process;

import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.util.CommonUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
    protected StringBuilder searchLogInfo;

    protected AbstractCalculationBuilder(SearchConditionDTO searchConditionDTO) {
        this.searchConditionDTO = searchConditionDTO;
        this.boolQueryBuilder = new BoolQueryBuilder();
        this.searchLogInfo = new StringBuilder();
    }

    protected String getSearchLogInfo() {
        return this.searchLogInfo.toString();
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

    protected BoolQueryBuilder resolverAndConfirmBoolQueryBuilder(BoolQueryBuilder... boolQueryBuilders) {
        for (BoolQueryBuilder queryBuilder : boolQueryBuilders) {
            if (!ObjectUtils.isEmpty(queryBuilder)) {
                boolQueryBuilder = queryBuilder;
            }
        }
        return boolQueryBuilder;
    }

    protected Float[] scoreNormalization(Float[] score) {
        return CommonUtil.scoreNormalization(score);
    }

}
