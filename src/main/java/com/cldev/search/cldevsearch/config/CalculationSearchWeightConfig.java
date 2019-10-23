package com.cldev.search.cldevsearch.config;

import com.alibaba.fastjson.JSONObject;
import com.cldev.search.cldevsearch.dto.FactorConfigDTO;
import com.cldev.search.cldevsearch.util.BeanUtil;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

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
 * Build File @date: 2019/10/16 20:41
 * @version 1.0
 * @description
 */
@Configuration
public class CalculationSearchWeightConfig {

    /**
     * In the result set of blog posts,
     * attenuation factor was used to intervene the result score for the same uid
     * factor decay of correlation score is carried out according to the results of es,
     * ranking is from large to small, and its value is the attenuation value
     */
    @Getter
    private volatile float decayFactorByScore = 0.5f;

    /**
     * In the result set of blog posts,
     * attenuation factor was used to intervene the result score for the same uid
     * According to the time of the same person's blog post, factor attenuation is carried out as the standard,
     * and the sorting mode is from near to far, and its value is the attenuation value
     */
    @Getter
    private volatile float decayFactorByTime = 0.5f;

    /**
     * In the result set of blog posts,
     * the score of the two groups was weighted according to the intervention of
     * correlation score (number of posts) and blog post publishing time
     * Its value is the weighted factor of correlation score (number of articles)
     */
    @Getter
    private volatile float scoreWeight = 1.0f;

    /**
     * In the result set of blog posts, the score of the two groups was weighted according to the intervention of
     * correlation score (number of posts) and blog post publishing time
     * Its value is a weighted factor of publication time
     */
    @Getter
    private volatile float timeWeight = 0.0f;

    /**
     * In the search result set, the search term and the name in the result set are matched by string similarity
     * Its value is name similarity
     * Based on Chinese characters
     */
    @Getter
    private volatile float usernameSimilarityChinese = 0.79f;

    /**
     * In the search result set, the search term and the name in the result set are matched by string similarity
     * Its value is name similarity
     * Based on Traditional characters
     */
    @Getter
    private volatile float usernameSimilarityTraditional = 0.79f;

    /**
     * In the search result set, the search term and the name in the result set are matched by string similarity
     * Its value is name similarity
     * Based on pinyin
     */
    @Getter
    private volatile float usernameSimilarityPinyin = 0.89f;

    /**
     * On the premise of satisfying the limitation of pinyin factor, the original text needs to be sub filtered
     */
    @Getter
    private volatile float pinyinChildWithChinese = 0.59f;

    /**
     * On the premise of satisfying the limitation of pinyin factor, the original text needs to be sub filtered
     */
    @Getter
    private volatile float pinyinChildWithTraditional = 0.59f;

    /**
     * In the search result set, the search term and the name in the result set are matched by string similarity
     * Its value is name similarity
     * Based on pinyin head
     */
    @Getter
    private volatile float usernameSimilarityPinyinHead = 0.79f;

    /**
     * For the same search term,
     * the result sets in the blog index and the kol index need to have different weighting factors
     * This value is a weighted factor of the score in the user index
     */
    @Getter
    private volatile float userScoreWeight = 0.6f;

    /**
     * For the same search term,
     * the result sets in the blog index and the kol index need to have different weighting factors
     * This value is a weighted factor of the score in the blog index
     */
    @Getter
    private volatile float blogScoreWeight = 0.4f;

    /**
     * The total number of search results in the user index of es,
     * which will directly affect the speed of the search, as well as the relevance of the results
     */
    @Getter
    private volatile int userResultSize = 1000;

    /**
     * The total number of search results in the blog index of es,
     * which will directly affect the speed of the search, as well as the relevance of the results
     */
    @Getter
    private volatile int blogResultSize = 5500;

    /**
     * On the final result set need into influence score,
     * the influence of the impact will not be applied in the user name of the result set,
     * but the user information and post information integration in the result set to join the influence,
     * will the relevance score and the influence of the result set scores weighted association,
     * the weight of the factor scores for correlation, the correlation factor,
     * the higher the score, the higher the proportion of the opposite
     */
    @Getter
    private volatile float blogCorrelationScoreWeight = 0.5f;

    /**
     * On the final result set need into influence score,
     * the influence of the impact will not be applied in the user name of the result set,
     * but the user information and post information integration in the result set to join the influence,
     * will the relevance score and the influence of the result set scores weighted association,
     * the weight of the factors to influence the score, the influence factor,
     * the higher the score, the higher the proportion of the opposite
     */
    @Getter
    private volatile float blogInfluenceScoreWeight = 0.5f;

    /**
     * Influence score and relevance score are calculated by means of indicating whether the right of use is enabled or not.
     * Is [true] means that the influence score and correlation score are regarded as a parallel relationship,
     * and the final score is calculated in the way of heavy use.
     * Where [false] means that the influence score and correlation score are regarded as the overall relationship,
     * and the final score is calculated by product
     */
    @Getter
    private volatile boolean influenceWithWeight = false;

    /**
     * Some weights require a sum of 1,
     * so define the complementary factor to determine the sum of complementary weights
     */
    private float complementaryFactor = 1.0f;

    public JSONObject getConfig() {
        JSONObject config = new JSONObject();
        config.put("blog-decay", decay());
        config.put("name-similarity", nameSimilarity());
        config.put("comprehensive-process", comprehensive());
        config.put("label-mapping", BeanUtil.labelRegistryConfig().getLabels());
        return config;
    }

    private JSONObject decay() {
        JSONObject decay = new JSONObject();
        decay.put("decay-factor", JSONObject.parseObject("{\"score\": " + this.decayFactorByScore + ", \"time\": " + this.decayFactorByTime + "}"));
        decay.put("join-weight", JSONObject.parseObject("{\"score\": " + this.scoreWeight + ", \"time\": " + this.timeWeight + "}"));
        return decay;
    }

    private JSONObject nameSimilarity() {
        JSONObject similarity = new JSONObject();
        similarity.put("chinese", this.usernameSimilarityChinese);
        similarity.put("traditional", this.usernameSimilarityTraditional);
        JSONObject pinyinJson = new JSONObject();
        pinyinJson.put("base", this.usernameSimilarityPinyin);
        pinyinJson.put("child", JSONObject.parseObject("{\"chinese\": " + this.pinyinChildWithChinese + ", \"traditional\": " + this.pinyinChildWithTraditional + "}"));
        similarity.put("pinyin", pinyinJson);
        similarity.put("pinyin-head", this.usernameSimilarityPinyinHead);
        return similarity;
    }

    private JSONObject comprehensive() {
        JSONObject comprehensive = new JSONObject();
        comprehensive.put("score-weight", JSONObject.parseObject("{\"user\": " + this.userScoreWeight + ", \"blog\": " + this.blogScoreWeight + "}"));
        comprehensive.put("result-size", JSONObject.parseObject("{\"user\": " + this.userResultSize + ", \"blog\": " + this.blogResultSize + "}"));
        comprehensive.put("influence-correlation", JSONObject.parseObject("{\"score-with-weight\": \"" + this.influenceWithWeight + "\", \"weight\": {\"correlation\": " + this.blogCorrelationScoreWeight + ", \"influence\": " + this.blogInfluenceScoreWeight + "}}"));
        return comprehensive;
    }

    public String configureFactor(FactorConfigDTO config) {
        Status status = executeAndVerify(
                configureDecayFactor(config),
                configureSimilarity(config),
                configureDecayWeight(config),
                configureComprehensiveScoreWeight(config),
                configureComprehensiveResultSize(config),
                configureComprehensiveBlogScoreWeight(config),
                configureInfluenceWithWeight(config)
        );
        if (status.success) {
            return "success";
        }
        return status.msg;
    }

    private Status executeAndVerify(Status ...condition) {
        for (Status status : condition) {
            if (!status.success) {
                return status;
            }
        }
        return new Status(true);
    }

    private Status configureDecayFactor(FactorConfigDTO config) {
        if (!ObjectUtils.isEmpty(config.getDecayFactorByScore()) && !ObjectUtils.isEmpty(config.getDecayFactorByTime())) {
            if (config.getDecayFactorByScore() + config.getDecayFactorByTime() == this.complementaryFactor) {
                this.decayFactorByScore = config.getDecayFactorByScore();
                this.decayFactorByTime = config.getDecayFactorByTime();
            } else {
                return new Status("[decayFactorByScore] and [decayFactorByTime] are complementary factors, and their sum needs to be 1", false);
            }
        } else if (ObjectUtils.isEmpty(config.getDecayFactorByScore()) && !ObjectUtils.isEmpty(config.getDecayFactorByTime())) {
            this.decayFactorByTime = config.getDecayFactorByTime();
            this.decayFactorByScore = this.complementaryFactor - config.getDecayFactorByTime();
        } else if (!ObjectUtils.isEmpty(config.getDecayFactorByScore()) && ObjectUtils.isEmpty(config.getDecayFactorByTime())) {
            this.decayFactorByScore = config.getDecayFactorByScore();
            this.decayFactorByTime = this.complementaryFactor - config.getDecayFactorByScore();
        }
        return new Status(true);
    }

    private Status configureSimilarity(FactorConfigDTO config) {
        if (!ObjectUtils.isEmpty(config.getUsernameSimilarityChinese())) {
            this.usernameSimilarityChinese = config.getUsernameSimilarityChinese();
        }
        if (!ObjectUtils.isEmpty(config.getUsernameSimilarityTraditional())) {
            this.usernameSimilarityTraditional = config.getUsernameSimilarityTraditional();
        }
        if (!ObjectUtils.isEmpty(config.getUsernameSimilarityPinyin())) {
            this.usernameSimilarityPinyin = config.getUsernameSimilarityPinyin();
        }
        if (!ObjectUtils.isEmpty(config.getUsernameSimilarityPinyinHead())) {
            this.usernameSimilarityPinyinHead = config.getUsernameSimilarityPinyinHead();
        }
        if (!ObjectUtils.isEmpty(config.getPinyinChildWithChinese())) {
            this.pinyinChildWithChinese = config.getPinyinChildWithChinese();
        }
        if (!ObjectUtils.isEmpty(config.getPinyinChildWithTraditional())) {
            this.pinyinChildWithTraditional = config.getPinyinChildWithTraditional();
        }
        return new Status(true);
    }

    private Status configureDecayWeight(FactorConfigDTO config) {
        if (!ObjectUtils.isEmpty(config.getScoreWeight()) && !ObjectUtils.isEmpty(config.getTimeWeight())) {
            if (config.getScoreWeight() + config.getTimeWeight() == this.complementaryFactor) {
                this.scoreWeight = config.getScoreWeight();
                this.timeWeight = config.getTimeWeight();
            } else {
                return new Status("[scoreWeight] and [timeWeight] are complementary factors, and their sum needs to be 1", false);
            }
        } else if (ObjectUtils.isEmpty(config.getScoreWeight()) && !ObjectUtils.isEmpty(config.getTimeWeight())) {
            this.timeWeight = config.getTimeWeight();
            this.scoreWeight = this.complementaryFactor - config.getTimeWeight();
        } else if (!ObjectUtils.isEmpty(config.getScoreWeight()) && ObjectUtils.isEmpty(config.getTimeWeight())) {
            this.scoreWeight = config.getScoreWeight();
            this.timeWeight = this.complementaryFactor - config.getScoreWeight();
        }
        return new Status(true);
    }

    private Status configureComprehensiveScoreWeight(FactorConfigDTO config) {
        if (!ObjectUtils.isEmpty(config.getUserScoreWeight()) && !ObjectUtils.isEmpty(config.getBlogScoreWeight())) {
            if (config.getUserScoreWeight() + config.getBlogScoreWeight() == this.complementaryFactor) {
                this.userScoreWeight = config.getUserScoreWeight();
                this.blogScoreWeight = config.getBlogScoreWeight();
            } else {
                return new Status("[userScoreWeight] and [blogScoreWeight] are complementary factors, and their sum needs to be 1", false);
            }
        } else if (ObjectUtils.isEmpty(config.getUserScoreWeight()) && !ObjectUtils.isEmpty(config.getBlogScoreWeight())) {
            this.blogScoreWeight = config.getBlogScoreWeight();
            this.userScoreWeight = this.complementaryFactor - config.getBlogScoreWeight();
        } else if (!ObjectUtils.isEmpty(config.getUserScoreWeight()) && ObjectUtils.isEmpty(config.getBlogScoreWeight())) {
            this.userScoreWeight = config.getUserScoreWeight();
            this.blogScoreWeight = this.complementaryFactor - config.getUserScoreWeight();
        }
        return new Status(true);
    }

    private Status configureComprehensiveResultSize(FactorConfigDTO config) {
        if (!ObjectUtils.isEmpty(config.getUserResultSize())) {
            this.userResultSize = config.getUserResultSize();
        }
        if (!ObjectUtils.isEmpty(config.getBlogResultSize())) {
            this.blogResultSize = config.getBlogResultSize();
        }
        return new Status(true);
    }

    private Status configureComprehensiveBlogScoreWeight(FactorConfigDTO config) {
        if (!ObjectUtils.isEmpty(config.getBlogCorrelationScoreWeight()) && !ObjectUtils.isEmpty(config.getBlogInfluenceScoreWeight())) {
            if (config.getBlogCorrelationScoreWeight() + config.getBlogInfluenceScoreWeight() == this.complementaryFactor) {
                this.blogCorrelationScoreWeight = config.getBlogCorrelationScoreWeight();
                this.blogInfluenceScoreWeight = config.getBlogInfluenceScoreWeight();
            } else {
                return new Status("[userScoreWeight] and [blogScoreWeight] are complementary factors, and their sum needs to be 1", false);
            }
        } else if (ObjectUtils.isEmpty(config.getBlogCorrelationScoreWeight()) && !ObjectUtils.isEmpty(config.getBlogInfluenceScoreWeight())) {
            this.blogInfluenceScoreWeight = config.getBlogInfluenceScoreWeight();
            this.blogCorrelationScoreWeight = this.complementaryFactor - config.getBlogInfluenceScoreWeight();
        } else if (!ObjectUtils.isEmpty(config.getBlogCorrelationScoreWeight()) && ObjectUtils.isEmpty(config.getBlogInfluenceScoreWeight())) {
            this.blogCorrelationScoreWeight = config.getBlogCorrelationScoreWeight();
            this.blogInfluenceScoreWeight = this.complementaryFactor - config.getBlogCorrelationScoreWeight();
        }
        return new Status(true);
    }

    private Status configureInfluenceWithWeight(FactorConfigDTO config) {
        if (!ObjectUtils.isEmpty(config.getInfluenceWithWeight())) {
            this.influenceWithWeight = config.getInfluenceWithWeight();
        }
        return new Status(true);
    }

    private class Status {
        String msg;
        Boolean success;

        Status(String msg, Boolean success) {
            this.msg = msg;
            this.success = success;
        }

        Status(Boolean success) {
            this.success = success;
        }
    }

}
