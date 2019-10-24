package com.cldev.search.cldevsearch.service;

import com.cldev.search.cldevsearch.dto.BlogDataDTO;
import com.cldev.search.cldevsearch.dto.UserFansDTO;
import com.cldev.search.cldevsearch.dto.UserInfoDTO;
import com.cldev.search.cldevsearch.dto.UserLabelDTO;

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
     *
     * @return load status
     */
    String loadData();

    /**
     * check file exist
     *
     * @return the file name list of do not exist files
     */
    List<String> checkFile();

    /**
     * check file count with csvMapping
     *
     * @return status
     */
    String checkFileCount();

    /**
     * load data to elasticsearch for kol
     *
     * @return load status
     */
    String loadDataForKol();

    /**
     * create indices for user into es
     *
     * @return execute status
     */
    Object createIndicesUser();

    /**
     * load data to elasticsearch for user
     *
     * @return load status
     */
    String loadDataForUser();

    /**
     * create indices for blog into es
     *
     * @return execute status
     */
    Object createIndicesBlog();

    /**
     * Tasks that need to be performed every day
     * Determine if a new index is needed based on the number of new posts per day
     *
     * @param count The number of new posts per day
     * @return Execution status
     */
    String dayTaskCreateBlogIndices(Integer count);

    /**
     * Tasks that need to be performed every day
     * Import the daily generated posts into es
     *
     * @param blogDataDTO blog object
     * @return Execution status
     */
    String dayTaskLoadBlogData(BlogDataDTO blogDataDTO);

    /**
     * Tasks that need to be performed every day
     * The updated number of users' fans per day is imported into es,
     * and the original number of users' fans is updated
     *
     * @param userFansDTOList User fans set
     * @return Execution status
     */
    String dayTaskUpdateUserFans(List<UserFansDTO> userFansDTOList);

    /**
     * Tasks that need to be performed every day
     * The updated number of users' labels per day is imported into es,
     * and the original number of users' labels is updated
     *
     * @param userLabelDTOList User label set
     * @return Execution status
     */
    String dayTaskUpdateUserLabels(List<UserLabelDTO> userLabelDTOList);

    /**
     * After the end of the user day, you need to merge segments
     * @return Execution status
     */
    String dayTaskForceMergeUser();

    /**
     * After the end of the blog day, you need to merge segments
     * @return Execution status
     */
    String dayTaskForceMergeBlog();

    /**
     * create indices for mid into es
     * @return execute status
     */
    Object createIndicesMid();

    /**
     * Tasks that need to be performed every day
     * The updated number of users' info per day is imported into es,
     * and the original number of users' info is updated
     * @param userInfoDTOS User info
     * @return Execution status
     */
    String dayTaskUpdateUserInfo(List<UserInfoDTO> userInfoDTOS);
}
