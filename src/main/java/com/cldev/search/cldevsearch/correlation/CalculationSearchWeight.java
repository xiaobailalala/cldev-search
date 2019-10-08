package com.cldev.search.cldevsearch.correlation;

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
 * Build File @date: 2019/10/7 1:50 下午
 * @version 1.0
 * @description
 */
public interface CalculationSearchWeight {

    /**
     * 博文结果集中，针对uid相同的情况，采用衰减因子对结果score进行干预
     * 根据es所得对相关性score进行因子衰减，排序方式为由大到小，其数值为衰减值
     */
    float DECAY_FACTOR_BY_SCORE = 0.5f;

    /**
     * 博文结果集中，针对uid相同的情况，采用衰减因子对结果score进行干预
     * 根据同一个人发表的博文时间为标准进行因子衰减，排序方式为由近到远，其数值为衰减值
     */
    float DECAY_FACTOR_BY_TIME = 0.5f;

    /**
     * 博文结果集中，根据相关性得分（篇数）和博文发表时间两个维度的干预，得出的两组score进行加权
     * 其数值为相关性得分（篇数）的加权因子
     */
    float SCORE_WEIGHT = 0.6f;

    /**
     * 博文结果集中，根据相关性得分（篇数）和博文发表时间两个维度的干预，得出的两组score进行加权
     * 其数值为发表时间的加权因子
     */
    float TIME_WEIGHT = 0.4f;

}
