package com.cldev.search.cldevsearch.util;

import com.cldev.search.cldevsearch.config.CalculationSearchWeightConfig;
import com.cldev.search.cldevsearch.config.SearchRegistryConfig;
import com.cldev.search.cldevsearch.config.SearchConfig;

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
 * Build File @date: 2019/10/16 21:33
 * @version 1.0
 * @description
 */
public class BeanUtil {

    public static CalculationSearchWeightConfig weightConfig() {
        return SpringContextUtil.getContext().getBean(CalculationSearchWeightConfig.class);
    }

    public static SearchConfig searchConfig() {
        return SpringContextUtil.getContext().getBean(SearchConfig.class);
    }

    public static SearchRegistryConfig searchRegistryConfig() {
        return SpringContextUtil.getContext().getBean(SearchRegistryConfig.class);
    }

}
