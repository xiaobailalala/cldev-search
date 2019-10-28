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
    private Long attitudeSum;

    /** 博文点赞最大值 */
    private Long attitudeMax;

    /** 博文点赞中位数 */
    private Long attitudeMedian;

    /** 博文评论总量 */
    private Long commentSum;

    /** 博文评论最大值 */
    private Long commentMax;

    /** 博文评论中位数 */
    private Long commentMedian;

    /** 博文转发总量 */
    private Long repostSum;

    /** 博文转发最大值 */
    private Long repostMax;

    /** 博文转发中位数 */
    private Long repostMedian;

    /** 博文总量 */
    private Long mblogTotal;

    /** 周发文频率 */
    private Double releaseMblogFrequency;
}
