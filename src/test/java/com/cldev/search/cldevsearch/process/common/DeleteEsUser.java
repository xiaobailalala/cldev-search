package com.cldev.search.cldevsearch.process.common;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
 * Build File @date: 2019/11/11 10:43
 * @version 1.0
 * @description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DeleteEsUser {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void deleteByUidFromRest() throws IOException {
        File deleteUid = new File("delete-uid");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(deleteUid));
        Set<String> uidList = new HashSet<>();
        String uid;
        while ((uid = bufferedReader.readLine()) != null) {
            uidList.add(uid);
        }
        List<String> uids = new LinkedList<>();
        int count = 0;
        for (String item : uidList) {
            uids.add(item);
            count++;
            if (count % 10000 == 0) {
                delete(uids.toArray(new String[0]));
                System.out.println("-----------------------" + count + "-----------------------");
                uids = new LinkedList<>();
            }
        }
        if (count % 10000 != 0) {
            delete(uids.toArray(new String[0]));
            System.out.println("-----------------------" + count + "-----------------------");
        }
        List indicesList = restTemplate.getForObject("http://192.168.2.76:9200/_cat/indices/?h=index,docs.count&format=json", List.class);
        assert indicesList != null;
        List<String> indices = new LinkedList<>();
        for (Object item : indicesList) {
            String index = ((LinkedHashMap) item).get("index").toString();
            if (index.startsWith("wb-art")) {
                indices.add(index);
            }
        }
        for (String index : indices) {
            System.out.println(restTemplate.postForObject("http://192.168.2.76:9200/" + index + "/_forcemerge?max_num_segments=1",
                    null,
                    Object.class));
        }
        System.out.println(restTemplate.postForObject("http://192.168.2.76:9200/wb-user-1569427200000/_forcemerge?max_num_segments=1",
                null,
                Object.class));
    }

    @Test
    public void test() {
        JSONObject bodyForArt = new JSONObject();
        bodyForArt.put("query", new JSONObject()
                .fluentPut("term", new JSONObject()
                        .fluentPut("uid", "5432672141")));
        System.out.println(restTemplate.postForObject("http://192.168.2.76:9200/wb-art/_delete_by_query",
                getRequestBody(bodyForArt.toJSONString()),
                JSONObject.class));
    }

    private void delete(String[] uids) {
        // 删除指定uid的博文
        JSONObject bodyForArt = new JSONObject();
        bodyForArt.put("query", new JSONObject()
                .fluentPut("terms", new JSONObject()
                        .fluentPut("uid", uids)));
        System.out.println(restTemplate.postForObject("http://192.168.2.76:9200/wb-art/_delete_by_query",
                getRequestBody(bodyForArt.toJSONString()),
                Object.class));
        // 删除指定uid的用户
        JSONObject bodyForUser = new JSONObject();
        bodyForUser.put("query", new JSONObject()
                .fluentPut("terms", new JSONObject()
                        .fluentPut("_id", uids)));
        System.out.println(restTemplate.postForObject("http://192.168.2.76:9200/wb-user/_delete_by_query",
                getRequestBody(bodyForUser.toJSONString()),
                Object.class));
    }

    private synchronized <T> HttpEntity<T> getRequestBody(T requestStr) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        return new HttpEntity<>(requestStr, headers);
    }

}

