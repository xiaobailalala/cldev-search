package com.cldev.search.cldevsearch.correlation.extension;

import com.cldev.search.cldevsearch.correlation.UserCalculationSearch;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.StringUtils;

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
 * Build File @date: 2019/9/28 17:43
 * @version 1.0
 * @description
 */
public class UserCalculationSearchName extends UserCalculationSearch {

    public UserCalculationSearchName(SearchConditionDTO searchConditionDTO) {
        super(searchConditionDTO);
    }

    @Override
    public UserCalculationSearch initSearchCondition() {
        this.boolQueryBuilder = resolverAndConfirmBoolQueryBuilder(
                resolverContext()
        );
        this.searchRequest = new SearchRequest("wb-user").source(new SearchSourceBuilder().query(this.boolQueryBuilder).size(1000));
        return this;
    }

    @Override
    protected BoolQueryBuilder resolverContext() {
        String context = this.searchConditionDTO.getContext();
        if (!StringUtils.isEmpty(context)) {
            this.boolQueryBuilder = this.boolQueryBuilder.must(new MatchPhraseQueryBuilder("name", context));
        }
        return null;
    }
}
