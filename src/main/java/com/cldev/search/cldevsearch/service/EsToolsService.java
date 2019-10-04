package com.cldev.search.cldevsearch.service;

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
 * Build File @date: 2019/9/23 17:40
 * @version 1.0
 * @description
 */
public interface EsToolsService {

    /**
     * load data to elasticsearch
     * @return load status
     */
    String loadData();

    /**
     * check file exist
     * @return the file name list of do not exist files
     */
    List<String> checkFile();

    /**
     * check file count with csvMapping
     * @return status
     */
    String checkFileCount();

    /**
     * load data to elasticsearch for kol
     * @return load status
     */
    String loadDataForKol();
}
