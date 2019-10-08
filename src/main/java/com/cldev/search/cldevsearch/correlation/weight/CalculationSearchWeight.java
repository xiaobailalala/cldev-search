package com.cldev.search.cldevsearch.correlation.weight;

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
     * In the result set of blog posts,
     * attenuation factor was used to intervene the result score for the same uid
     * factor decay of correlation score is carried out according to the results of es,
     * ranking is from large to small, and its value is the attenuation value
     */
    float DECAY_FACTOR_BY_SCORE = 0.5f;

    /**
     * In the result set of blog posts,
     * attenuation factor was used to intervene the result score for the same uid
     * According to the time of the same person's blog post, factor attenuation is carried out as the standard,
     * and the sorting mode is from near to far, and its value is the attenuation value
     */
    float DECAY_FACTOR_BY_TIME = 0.5f;

    /**
     * In the result set of blog posts,
     * the score of the two groups was weighted according to the intervention of
     * correlation score (number of posts) and blog post publishing time
     * Its value is the weighted factor of correlation score (number of articles)
     */
    float SCORE_WEIGHT = 0.6f;

    /**
     * In the result set of blog posts, the score of the two groups was weighted according to the intervention of
     * correlation score (number of posts) and blog post publishing time
     * Its value is a weighted factor of publication time
     */
    float TIME_WEIGHT = 0.4f;

    /**
     * In the search result set, the search term and the name in the result set are matched by string similarity
     * Its value is name similarity
     */
    float USERNAME_SIMILARITY = 0.8f;

    float USER_SCORE_WEIGHT = 0.6f;

    float BLOG_SCORE_WEIGHT = 0.4f;

}
