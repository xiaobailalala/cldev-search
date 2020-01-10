package com.cldev.search.cldevsearch.correlation.process;

import com.cldev.search.cldevsearch.bo.SearchResultTempBO;
import org.elasticsearch.client.Client;

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
 * Build File @date: 2019/9/26 13:59
 * @version 1.0
 * @description
 */
public interface KolCalculation {

    /**
     * Initialize the query constructor,
     * the initialization phase needs to filter the search criteria rows and build the query statement based on them
     *
     * @return Returns the constructor itself, which is used to get the result
     */
    KolCalculation initSearchCondition();

    /**
     * Get elasticsearch's result set after searching conditions and contents,
     * and attribute interpretation. Take a closer look at {@link SearchResultTempBO}
     *
     * @param client Getting this result requires the caller to provide a elasticsearch client
     * @return The resulting set of attributes is explained in detail {@link SearchResultTempBO}
     */
    List<SearchResultTempBO> getResult(Client client);

    /**
     * Gets the packaged log information for a search,
     * and the default implementation for each search is given in the abstract {@link AbstractCalculationBuilder}
     * @return Packaged log information
     */
    String getSearchLogInfo();

}
