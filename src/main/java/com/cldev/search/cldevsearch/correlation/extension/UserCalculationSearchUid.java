package com.cldev.search.cldevsearch.correlation.extension;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.UserCalculationSearch;
import com.cldev.search.cldevsearch.correlation.process.KolCalculation;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Arrays;
import java.util.List;
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
 * Build File @date: 2019/9/28 16:55
 * @version 1.0
 * @description
 */
public class UserCalculationSearchUid extends UserCalculationSearch {

    public UserCalculationSearchUid(SearchConditionDTO searchConditionDTO) {
        super(searchConditionDTO);
    }

    @Override
    public UserCalculationSearch initSearchCondition() {
        this.boolQueryBuilder = resolverAndConfirmBoolQueryBuilder(
                resolverAddress(),
                resolverSex(),
                resolverFansNum(),
                resolverFansAge(),
                resolverInterest()
        );
        this.searchRequest = new SearchRequest("wb-user").source(new SearchSourceBuilder()
                .query(this.boolQueryBuilder));
        return this;
    }

}
