package com.cldev.search.cldevsearch.correlation.extension;

import com.cldev.search.cldevsearch.correlation.UserCalculationSearch;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.util.BeanUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.springframework.util.StringUtils;

import java.util.List;

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
        return super.initSearchCondition();
    }

    @Override
    protected BoolQueryBuilder resolverContext() {
        String context = this.searchConditionDTO.getContext();
        if (!StringUtils.isEmpty(context)) {
            List<String> screenName = BeanUtil.searchRegistryConfig().getScreenName(context);
            if (screenName.size() == 0) {
                return this.boolQueryBuilder.should(new MatchPhraseQueryBuilder("name", context));
            }
            for (String name : screenName) {
                this.boolQueryBuilder = this.boolQueryBuilder.should(new MatchPhraseQueryBuilder("name", name));
            }
            return this.boolQueryBuilder;
        }
        return null;
    }
}
