package com.cldev.search.cldevsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
 * Build File @date: 2019/10/16 21:55
 * @version 1.0
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class FactorConfigDTO {

    private Float decayFactorByScore;

    private Float decayFactorByTime;

    private Float scoreWeight;

    private Float timeWeight;

    private Float usernameSimilarityChinese;

    private Float usernameSimilarityTraditional;

    private Float usernameSimilarityPinyin;

    private Float pinyinChildWithChinese;

    private Float pinyinChildWithTraditional;

    private Float usernameSimilarityPinyinHead;

    private Float userScoreWeight;

    private Float blogScoreWeight;

    private Integer userResultSize;

    private Integer blogResultSize;

    private Float blogCorrelationScoreWeight;

    private Float blogInfluenceScoreWeight;

}
