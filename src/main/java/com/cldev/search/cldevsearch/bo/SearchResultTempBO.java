package com.cldev.search.cldevsearch.bo;

import com.cldev.search.cldevsearch.vo.SearchResVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
     * Elasticsearch retrieves a correlation score that is obtained when there are only filtering conditions {@code null}
     */
    private Float correlationScore;

    /**
     * The time of publication of a blog post, used for sorting
     */
    private String createTime;

    /**
     * User name information stored in Elasticsearch
     */
    private String name;

    /**
     * Labels information stored in Elasticsearch
     */
    private List<Integer> labels;

    /**
     * Labels score
     */
    private List<Double> scores;

    /**
     * The labels for filter and show
     */
    private List<Integer> showLabels;

    /**
     * Address information stored in Elasticsearch
     */
    private String address;

    /**
     * Province information stored in Elasticsearch
     */
    private String province;

    /**
     * Sex information stored in Elasticsearch
     */
    private Integer sex;

    /**
     * User interaction data
     */
    private ReportBO report;

    public SearchResultTempBO(String uid, Integer wbFans, Float score, Float correlationScore, String createTime, String name, List<Integer> labels, List<Double> scores, List<Integer> showLabels, String address, String province, Integer sex, ReportBO report) {
        super(uid, wbFans, score);
        this.correlationScore = correlationScore;
        this.createTime = createTime;
        this.name = name;
        this.labels = labels;
        this.scores = scores;
        this.showLabels = showLabels;
        this.address = address;
        this.province = province;
        this.sex = sex;
        this.report = report;
    }

    @Override
    @SuppressWarnings("all")
    public int compareTo(SearchResultTempBO o) {
        if (o.getCorrelationScore() > this.getCorrelationScore()) {
            return 1;
        } else if (o.getCorrelationScore() < this.getCorrelationScore()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "SearchResultTempBO{" +
                "correlationScore=" + correlationScore +
                ", createTime='" + createTime + '\'' +
                ", name='" + name + '\'' +
                ", labels=" + labels +
                ", address='" + address + '\'' +
                ", province='" + province + '\'' +
                ", sex=" + sex +
                ", uid=" + getUid() +
                ", fans=" + getWbFans() +
                ", score=" + getScore() +
                ", report=" + getReport().toString() +
                '}';
    }

}
