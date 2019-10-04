package com.cldev.search.cldevsearch.bo;

import com.cldev.search.cldevsearch.vo.SearchResVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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
 * Build File @date: 2019/9/28 11:16
 * @version 1.0
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SearchResultTempBO extends SearchResVO implements Comparable<SearchResultTempBO> {

    /**
     * The number of people tags, on the results of the order of impact
     */
    private Integer labelCount;

    /**
     * Elasticsearch retrieves a correlation score that is obtained when there are only filtering conditions {@code null}
     */
    private Float correlationScore;

    /**
     * The time of publication of a blog post, used for sorting
     */
    private String createTime;

    public SearchResultTempBO(String uid, Integer wbFans, Integer score, Integer labelCount, Float correlationScore, String createTime) {
        super(uid, wbFans, score);
        this.labelCount = labelCount;
        this.correlationScore = correlationScore;
        this.createTime = createTime;
    }

    @Override
    public int compareTo(SearchResultTempBO o) {
        if (o.getLabelCount() > this.getLabelCount()) {
            return 1;
        } else if (o.getLabelCount() < this.getLabelCount()) {
            return -1;
        } else {
            if (o.getCorrelationScore() > this.getCorrelationScore()) {
                return 1;
            } else if (o.getCorrelationScore() < this.getCorrelationScore()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
