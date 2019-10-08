package com.cldev.search.cldevsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
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
 * Build File @date: 2019/9/25 10:44
 * @version 1.0
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SearchConditionDTO {

    /**
     * Search content, from the search box content
     */
    private String context;

    /**
     * The gender of the character comes from the filter options
     * 0 or {@code null} for [all], 1 for [male], 2 for [female]
     */
    private Integer sex;

    /**
     * Character region, from the filter condition option
     * If no regional filtering is performed, the value is {@code null}.
     * Otherwise, it is a 7-digit regional code: the first 3 digits are the provincial administrative division code,
     * and the last 4 digits are the urban administrative division code
     * Less than 3 and 4 bits of code are preceded by 0
     */
    private String[] addresses;

    /**
     * Character age, from the filter condition options
     * If no age filtering is performed, the value is {@code null}.
     * Otherwise, correspond to the age range according to the {@link EnumFansAge} {@code code} attribute
     * Where {@code from} is the lower limit of age, {@code to} is the upper limit of age
     * Multiple age groups can be specified
     */
    private Integer[] fansAge;

    /**
     * 用户兴趣领域，搜索过滤选项
     */
    private String[] interest;

    /**
     * Number of followers, the filter option of search.
     * If the number of followers is not filtered, the value is null {@code null}.
     * Otherwise, correspond to the fans number range according to the {@link EnumFansNum} {@code code} attribute
     * Where {@code from} is the lower limit of fans number, {@code to} is the upper limit of fans number
     * Multiple fans number groups can be specified
     */
    private Integer[] fansNum;

    /**
     * Gets the age set in the filter condition
     *
     * @return A collection of age groups that need to be filtered,
     * or an empty collection if no filtering is required
     */
    public List<FansAge> getFansAge() {
        List<FansAge> fansAges = new LinkedList<>();
        if (!ObjectUtils.isEmpty(fansAge)) {
            for (Integer age : fansAge) {
                EnumFansAge enumFansAge = EnumFansAge.getAge(age);
                if (!ObjectUtils.isEmpty(enumFansAge)) {
                    fansAges.add(new FansAge(enumFansAge.getFrom(), enumFansAge.getTo()));
                }
            }
        }
        return fansAges;
    }

    /**
     * Gets the fans number set in the filter condition
     *
     * @return A collection of fans number groups that need to be filtered,
     * or an empty collection if no filtering is required
     */
    public List<FansNum> getFansNum() {
        List<FansNum> fansNums = new LinkedList<>();
        if (!ObjectUtils.isEmpty(fansNum)) {
            for (Integer num : fansNum) {
                EnumFansNum enumFansNum = EnumFansNum.getNumber(num);
                if (!ObjectUtils.isEmpty(enumFansNum)) {
                    fansNums.add(new FansNum(enumFansNum.getFrom(), enumFansNum.getTo()));
                }
            }
        }
        return fansNums;
    }

    public List<Integer> getInterest() {
        LinkedList<Integer> integers = new LinkedList<>();
        if (!ObjectUtils.isEmpty(interest)) {
            for (String item : interest) {
                EnumInterest enumInterest = EnumInterest.getInterest(item);
                if (!ObjectUtils.isEmpty(enumInterest)) {
                    integers.add(enumInterest.code);
                }
            }
        }
        return integers;
    }

    @Getter
    private enum EnumFansAge {

        /**
         * The first age group represents people aged between 16 and 20
         */
        AGE_ONE(1, 16, 20),

        /**
         * The second age group represents people aged between 20 and 30
         */
        AGE_TWO(2, 20, 30),

        /**
         * The third age group represents people aged between 30 and 40
         */
        AGE_THREE(3, 30, 40),

        /**
         * The fourth age group represents people aged between 40 and 200
         */
        AGE_FOUR(4, 40, 200);

        private Integer code;
        private Integer from;
        private Integer to;

        EnumFansAge(Integer code, Integer from, Integer to) {
            this.code = code;
            this.from = from;
            this.to = to;
        }

        static EnumFansAge getAge(Integer code) {
            for (EnumFansAge age : EnumFansAge.values()) {
                if (age.code.equals(code)) {
                    return age;
                }
            }
            return null;
        }

    }

    public class FansAge {
        public Integer from;
        public Integer to;

        FansAge(Integer from, Integer to) {
            this.from = from;
            this.to = to;
        }
    }

    @Getter
    private enum EnumFansNum {
        /**
         * The first age group represents fans number between 16 and 20
         */
        AGE_ONE(1, 10000, 100000),

        /**
         * The second age group represents fans number between 20 and 30
         */
        AGE_TWO(2, 100000, 300000),

        /**
         * The third age group represents fans number between 30 and 40
         */
        AGE_THREE(3, 300000, 1000000),

        /**
         * The fourth age group represents fans number between 40 and 200
         */
        AGE_FOUR(4, 1000000, 2000000000);

        private Integer code;
        private Integer from;
        private Integer to;

        EnumFansNum(Integer code, Integer from, Integer to) {
            this.code = code;
            this.from = from;
            this.to = to;
        }

        static EnumFansNum getNumber(Integer code) {
            for (EnumFansNum age : EnumFansNum.values()) {
                if (age.code.equals(code)) {
                    return age;
                }
            }
            return null;
        }
    }

    public class FansNum {
        public Integer from;
        public Integer to;

        FansNum(Integer from, Integer to) {
            this.from = from;
            this.to = to;
        }
    }

    @Getter
    private enum EnumInterest {

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_1("日用百货", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_2("互联网", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_3("美食", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_4("汽车", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_5("家居", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_6("数码", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_7("房地产", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_8("工农贸易", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_9("生活服务", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_10("时尚美妆", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_11("机构场所", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_12("健康医疗", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_13("运动健身", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_14("商务服务", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_15("婚庆服务", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_16("三农", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_17("财经", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_18("读书", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_19("教育", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_20("摄影", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_21("母婴", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_22("娱乐", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_23("媒体", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_24("音乐", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_25("游戏", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_26("旅游", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_27("法律", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_28("职场", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_29("公益", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_30("电影", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_31("海外", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_32("萌宠", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_33("收藏", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_34("家电办公", 0),

        /**
         * For filtering areas of interest when searching
         */
        INTEREST_35("动漫", 0);

        private String interestName;
        private Integer code;

        EnumInterest(String interestName, Integer code) {
            this.interestName = interestName;
            this.code = code;
        }

        static EnumInterest getInterest(String interestName) {
            for (EnumInterest interest : EnumInterest.values()) {
                if (interestName.equals(interest.interestName)) {
                    return interest;
                }
            }
            return null;
        }

    }

}
