package com.cldev.search.cldevsearch.dto;

import com.cldev.search.cldevsearch.bo.ReportBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
 * Build File @date: 2019/10/16 15:05
 * @version 1.0
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserReportDTO extends ReportBO {

    private String uid;
    private Integer fans;

    public UserReportDTO(String uid, Integer fans) {
        this.uid = uid;
        this.fans = fans;
    }

    public UserReportDTO(Integer attitudeSum, Integer attitudeMax, Integer attitudeMedian, Integer commentSum, Integer commentMax, Integer commentMedian, Integer repostSum, Integer repostMax, Integer repostMedian, Integer mblogTotal, Double releaseMblogFrequency, String uid, Integer fans) {
        super(attitudeSum, attitudeMax, attitudeMedian, commentSum, commentMax, commentMedian, repostSum, repostMax, repostMedian, mblogTotal, releaseMblogFrequency);
        this.uid = uid;
        this.fans = fans;
    }
}
