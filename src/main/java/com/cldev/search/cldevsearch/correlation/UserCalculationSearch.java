package com.cldev.search.cldevsearch.correlation;

import com.cldev.search.cldevsearch.bo.ReportBO;
import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.builder.AddressBoolQueryBuilder;
import com.cldev.search.cldevsearch.correlation.builder.FansAgeBoolQueryBuilder;
import com.cldev.search.cldevsearch.correlation.builder.FansNumBoolQueryBuilder;
import com.cldev.search.cldevsearch.correlation.builder.InterestBoolQueryBuilder;
import com.cldev.search.cldevsearch.correlation.process.AbstractCalculationBuilder;
import com.cldev.search.cldevsearch.correlation.process.KolCalculation;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import com.cldev.search.cldevsearch.util.BeanUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
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
 * Build File @date: 2019/9/26 16:53
 * @version 1.0
 * @description
 */
public class UserCalculationSearch extends AbstractCalculationBuilder implements KolCalculation {

    public UserCalculationSearch(SearchConditionDTO searchConditionDTO) {
        super(searchConditionDTO);
    }

    @Override
    public UserCalculationSearch initSearchCondition() {
        this.boolQueryBuilder = resolverAndConfirmBoolQueryBuilder(
                resolverContext(),
                resolverAddress(),
                resolverSex(),
                resolverFansNum(),
                resolverFansAge(),
                resolverInterest()
        );
        this.searchRequest = new SearchRequest("wb-user").source(new SearchSourceBuilder()
                .query(this.boolQueryBuilder)
                .size(weightConfig().getUserResultSize()));
        return this;
    }

    protected BoolQueryBuilder resolverContext() {
        String context = this.searchConditionDTO.getContext();
        if (!StringUtils.isEmpty(context)) {
            List<String> screenName = BeanUtil.searchRegistryConfig().getScreenName(context);
            if (screenName.size() == 0) {
                return this.boolQueryBuilder.must(new MatchQueryBuilder("description", context));
            }
            for (String name : screenName) {
                this.boolQueryBuilder = this.boolQueryBuilder.should(new MatchPhraseQueryBuilder("description", name));
            }
            return this.boolQueryBuilder.should(new MatchQueryBuilder("description", context));
        }
        return null;
    }

    protected BoolQueryBuilder resolverAddress() {
        String[] addresses = this.searchConditionDTO.getAddress();
        if (!ObjectUtils.isEmpty(addresses)) {
            BoolQueryBuilder boolQueryBuilder = childBoolQueryBuilder(AddressBoolQueryBuilder.class);
            for (String address : addresses) {
                boolQueryBuilder = boolQueryBuilder.should(new TermQueryBuilder(address.length() == 7 ? "address" : "province", address));
            }
            return this.boolQueryBuilder.must(boolQueryBuilder);
        }
        return null;
    }

    protected BoolQueryBuilder resolverSex() {
        Integer sex = this.searchConditionDTO.getSex();
        if (!ObjectUtils.isEmpty(sex) && sex != 0) {
            return this.boolQueryBuilder.must(new TermQueryBuilder("sex", sex));
        }
        return null;
    }

    protected BoolQueryBuilder resolverFansNum() {
        List<SearchConditionDTO.FansNum> fansNum = this.searchConditionDTO.getFansNum();
        if (!ObjectUtils.isEmpty(fansNum) && fansNum.size() != 0) {
            BoolQueryBuilder boolQueryBuilder = childBoolQueryBuilder(FansNumBoolQueryBuilder.class);
            for (SearchConditionDTO.FansNum num : fansNum) {
                boolQueryBuilder = boolQueryBuilder.should(new RangeQueryBuilder("fans").gte(num.from).lt(num.to));
            }
            return this.boolQueryBuilder.must(boolQueryBuilder);
        }
        return null;
    }

    protected BoolQueryBuilder resolverFansAge() {
        List<SearchConditionDTO.FansAge> fansAge = searchConditionDTO.getFansAge();
        if (!ObjectUtils.isEmpty(fansAge) && fansAge.size() != 0) {
            BoolQueryBuilder boolQueryBuilder = childBoolQueryBuilder(FansAgeBoolQueryBuilder.class);
            for (SearchConditionDTO.FansAge age : fansAge) {
                boolQueryBuilder = boolQueryBuilder.should(new RangeQueryBuilder("fansAge").gte(age.from).lt(age.to));
            }
            return this.boolQueryBuilder.must(boolQueryBuilder);
        }
        return null;
    }

    protected BoolQueryBuilder resolverInterest() {
        List<Integer> interest = searchConditionDTO.getInterest();
        if (!ObjectUtils.isEmpty(interest) && interest.size() != 0) {
            BoolQueryBuilder boolQueryBuilder = childBoolQueryBuilder(InterestBoolQueryBuilder.class);
            for (Integer integer : interest) {
                boolQueryBuilder = boolQueryBuilder.should(new TermQueryBuilder("label.id", integer));
            }
            return this.boolQueryBuilder.must(boolQueryBuilder);
        }
        return null;
    }

    @Override
    @SuppressWarnings("all")
    public List<SearchResultTempBO> getResult(Client client) {
        long search = System.currentTimeMillis();
        SearchHit[] hits = client.search(searchRequest).actionGet().getHits().getHits();
        this.searchLogInfo.append("------ The search : ").append(System.currentTimeMillis() - search).append("ms\n");
        List<SearchResultTempBO> searchResultTempBOS = new LinkedList<>();
        long start = System.currentTimeMillis();
        Float[] score = scoreNormalization(Arrays.stream(hits).map(SearchHit::getScore).toArray(Float[]::new));
        this.searchLogInfo.append("------ score normalization : ").append(System.currentTimeMillis() - start).append("ms\n");
        for (int item = 0; item < hits.length; item++) {
            Map<String, Object> source = hits[item].getSourceAsMap();
            String[] infos = source.get("info").toString().split("@");
            searchResultTempBOS.add(new SearchResultTempBO(hits[item].getId(),
                    Integer.parseInt(source.get("fans").toString()),
                    Float.parseFloat(source.get("score").toString()),
                    score[item], null, source.get("name").toString(),
                    (List<Integer>) source.get("label.id"), (List<Double>) source.get("label.score"),
                    (List<Integer>) source.get("label.show"), source.get("address").toString(),
                    source.get("province").toString(), Integer.parseInt(source.get("sex").toString()),
                    new ReportBO().setAttitudeSum(Long.parseLong(infos[0]))
                            .setAttitudeMax(Long.parseLong(infos[1]))
                            .setAttitudeMedian(Long.parseLong(infos[2]))
                            .setCommentSum(Long.parseLong(infos[3]))
                            .setCommentMax(Long.parseLong(infos[4]))
                            .setCommentMedian(Long.parseLong(infos[5]))
                            .setRepostSum(Long.parseLong(infos[6]))
                            .setRepostMax(Long.parseLong(infos[7]))
                            .setRepostMedian(Long.parseLong(infos[8]))
                            .setMblogTotal(Long.parseLong(infos[9]))
                            .setReleaseMblogFrequency(Double.parseDouble(infos[10]))
                            .setRepostRatio(Float.parseFloat(infos[11]))));
        }
        return searchResultTempBOS;
    }

    @Override
    public String getSearchLogInfo() {
        return super.getSearchLogInfo();
    }

}
