package com.cldev.search.cldevsearch.bo;

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
 * Build File @date: 2019/10/25 16:31
 * @version 1.0
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ReportBO {
    /** 博文点赞总量 */
    private Integer attitudeSum;

    /** 博文点赞最大值 */
    private Integer attitudeMax;

    /** 博文点赞中位数 */
    private Integer attitudeMedian;

    /** 博文评论总量 */
    private Integer commentSum;

    /** 博文评论最大值 */
    private Integer commentMax;

    /** 博文评论中位数 */
    private Integer commentMedian;

    /** 博文转发总量 */
    private Integer repostSum;

    /** 博文转发最大值 */
    private Integer repostMax;

    /** 博文转发中位数 */
    private Integer repostMedian;

    /** 博文总量 */
    private Integer mblogTotal;

    /** 周发文频率 */
    private Double releaseMblogFrequency;
}
