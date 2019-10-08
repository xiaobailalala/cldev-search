package com.cldev.search.cldevsearch.common;

import com.cldev.search.cldevsearch.correlation.builder.AddressBoolQueryBuilder;
import com.cldev.search.cldevsearch.ssdb.SSDB;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;

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
 * Build File @date: 2019/9/27 23:18
 * @version 1.0
 * @description
 */
public class SearchTest {

    @Test
    public void test() {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(new AddressBoolQueryBuilder());
        for (QueryBuilder queryBuilder : boolQueryBuilder.must()) {
            if (queryBuilder.getClass().equals(AddressBoolQueryBuilder.class)) {
                System.out.println(((BoolQueryBuilder) queryBuilder).getClass().getName());
            }
        }
    }

    @Test
    public void searchFromSsDb() throws Exception {
        SSDB ssdb = new SSDB("192.168.2.55", 8889);
        byte[] userinfos = ssdb.hget("userinfo", "2150694340");
        String userInfo = new String(userinfos);
        System.out.println(userInfo);
    }

}
