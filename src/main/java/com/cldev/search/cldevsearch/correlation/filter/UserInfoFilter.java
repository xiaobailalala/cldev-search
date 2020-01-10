package com.cldev.search.cldevsearch.correlation.filter;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import com.cldev.search.cldevsearch.correlation.process.SearchFilter;
import com.cldev.search.cldevsearch.dto.SearchConditionDTO;
import org.springframework.util.ObjectUtils;

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
 * Build File @date: 2020/1/10 17:42
 * @version 1.0
 * @description
 */
public class UserInfoFilter implements SearchFilter<List<SearchResultTempBO>> {

    private List<SearchResultTempBO> searchResult = new LinkedList<>();

    private SearchConditionDTO condition;

    private List<SearchResultTempBO> blogResult;

    public UserInfoFilter(SearchConditionDTO condition, List<SearchResultTempBO> blogResult) {
        this.condition = condition;
        this.blogResult = blogResult;
    }

    @Override
    public void filter() {
        if (isCondition()) {
            for (SearchResultTempBO item : this.blogResult) {
                if (resolverAddress(item.getAddress(), item.getProvince()) &&
                        resolverSex(item.getSex()) &&
                        resolverFansNum(item.getWbFans()) &&
                        resolverInterest(item.getShowLabels())) {
                    this.searchResult.add(item);
                }
            }
        } else {
            this.searchResult.addAll(this.blogResult);
        }
    }

    @Override
    public List<SearchResultTempBO> getResult() {
        return this.searchResult;
    }

    /**
     * 搜索内容是否含有过滤条件
     *
     * @return 是否含有过滤条件，true为含有，false为不含
     */
    private boolean isCondition() {
        int isCondition = 0;
        if (!ObjectUtils.isEmpty(this.condition.getSex()) && this.condition.getSex() != 0) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(this.condition.getAddress()) && this.condition.getAddress().length != 0) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(this.condition.getFansAge()) && this.condition.getFansAge().size() != 0) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(this.condition.getInterest()) && this.condition.getInterest().size() != 0) {
            isCondition++;
        }
        if (!ObjectUtils.isEmpty(this.condition.getFansNum()) && this.condition.getFansNum().size() != 0) {
            isCondition++;
        }
        return isCondition != 0;
    }

    private boolean resolverAddress(String address, String province) {
        if (ObjectUtils.isEmpty(this.condition.getAddress()) || this.condition.getAddress().length == 0) {
            return true;
        }
        return Arrays.asList(this.condition.getAddress()).contains(address) || Arrays.asList(this.condition.getAddress()).contains(province);
    }

    private boolean resolverSex(Integer sex) {
        if (ObjectUtils.isEmpty(this.condition.getSex()) || this.condition.getSex() == 0) {
            return true;
        }
        return this.condition.getSex().equals(sex);
    }

    private boolean resolverFansNum(Integer fans) {
        if (this.condition.getFansNum().size() == 0) {
            return true;
        }
        for (SearchConditionDTO.FansNum fansNum : this.condition.getFansNum()) {
            if (fans > fansNum.from && fans <= fansNum.to) {
                return true;
            }
        }
        return false;
    }

    private boolean resolverInterest(List<Integer> interest) {
        if (this.condition.getInterest().size() == 0) {
            return true;
        }
        Set<Integer> tempSet = new HashSet<>(interest);
        for (Integer item : this.condition.getInterest()) {
            if (tempSet.contains(item)) {
                return true;
            }
        }
        return false;
    }

}
