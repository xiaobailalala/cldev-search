package com.cldev.search.cldevsearch.process.common;

import ch.ethz.ssh2.SCPInputStream;
import com.alibaba.fastjson.JSONObject;
import com.cldev.search.cldevsearch.bo.BlogBO;
import com.cldev.search.cldevsearch.util.AsyncTaskUtil;
import com.cldev.search.cldevsearch.util.SpringContextUtil;
import org.database.neo4j.base.common.LinuxAction;
import org.database.neo4j.base.common.ResultApi;
import org.database.neo4j.base.util.LinuxUtil;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
 * Build File @date: 2019/11/4 20:56
 * @version 1.0
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RunnerTest {

    private static final Logger logger = LoggerFactory.getLogger(RunnerTest.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private AsyncTaskUtil asyncTaskUtil;

    @Test
    public void collectMsg() {
        Object forObject = restTemplate.getForObject("http://192.168.2.205:9000/log/collectMsg", Object.class);
    }

    @Test
    public void testUpdate() throws IOException {
        Client client = template.getClient();
        BulkRequestBuilder builder = template.getClient().prepareBulk();
        XContentBuilder jsonBuilder = XContentFactory.jsonBuilder().startObject();
        Map<String, String> doc = new HashMap<>(8);
        doc.put("name", "快乐肥宅庆先森");
        client.prepareUpdate("wb-user-1569427200000", "user", "2987585965").setDoc(doc).execute().actionGet();
    }

    private volatile AtomicInteger deleteCount = new AtomicInteger(0);
    @Test
    public void deleteMid() {
        Client client = template.getClient();
        LinuxAction linuxAction = LinuxUtil.getSingletonLinuxAction("192.168.2.76", "cldev", "cldev");
        String mid;
        int count = 0;
        List<String> midList = new ArrayList<>();
        try (SCPInputStream scpInputStream = linuxAction.downloadFileIS("/home/cldev/Documents/mid-result.json");
             InputStreamReader inputStreamReader = new InputStreamReader(scpInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            while ((mid = bufferedReader.readLine()) != null) {
                midList.add(mid.split(",")[2].split(":")[1].split("\"")[1]);
                count++;
                if (count % 10000 == 0) {
                    deleteMid(client, midList, count);
                    midList = new ArrayList<>();
                }
            }
            if (count % 10000 != 0) {
                deleteMid(client, midList, count);
            }
        } catch (IOException ignored) {}
    }


    private void deleteMid(Client client, List<String> deleteMid, int count) {
        asyncTaskUtil.asyncCustomTask(() -> {
            long deleteStart = System.currentTimeMillis();
            ActionFuture<SearchResponse> esUidInfo = client.search(new SearchRequest("wb-art").source(new SearchSourceBuilder()
                    .query(new TermsQueryBuilder("_id", deleteMid)).size(deleteMid.size())));
            List<String> collect = Arrays.stream(esUidInfo.actionGet().getHits().getHits()).map(SearchHit::getId).collect(Collectors.toList());
            List<String> delete = deleteMid.stream().filter(item -> !collect.contains(item)).collect(Collectors.toList());
            restTemplate.postForObject("http://192.168.2.76:9200/mid/_delete_by_query",
                    getRequestBody(new JSONObject()
                            .fluentPut("query", new JSONObject()
                                    .fluentPut("terms", new JSONObject()
                                            .fluentPut("_id", delete))).toJSONString()),
                    JSONObject.class);
            int addAndGet = deleteCount.addAndGet(delete.size());
            System.out.println("statistics num:" + count + "    real num:" + delete.size() +
                    "    delete total:" + addAndGet +
                    "    rate:" + (((float)addAndGet / 118020163) * 100) + "%" +
                    "    elapsed time:" + (System.currentTimeMillis() - deleteStart) + "ms");
        });
    }

    private synchronized <T> HttpEntity<T> getRequestBody(T requestStr) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        return new HttpEntity<>(requestStr, headers);
    }

    @Test
    public void downloadForRest() {
        HttpHeaders headers = new HttpHeaders();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            headers.setAccept(Collections.singletonList(MediaType.valueOf("application/x-msdownload")));
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    "http://192.168.2.205:9000/log/collectMsg",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    byte[].class
            );
            byte[] result = response.getBody();
            assert result != null;
            inputStream = new ByteArrayInputStream(result);
            File file = new File("collect.txt");
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    throw new IOException("create error");
                }
            }
            for (Map.Entry<String, List<String>> item : headers.entrySet()) {
                System.out.println(item.getKey() + " " + item.getValue());
            }
            outputStream = new FileOutputStream(file);
            int len;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void updateInfluenceScore() throws IOException {
        File scoreMap = new File("C:\\Users\\cl24\\Desktop\\mysql.csv");
        Set<String> userId50w = new HashSet<>(1048576);
        Map<String, String> scoreMapping = new HashMap<>(800000);
        BufferedReader bufferedReader50w = new BufferedReader(new FileReader("C:\\Users\\cl24\\Desktop\\uid_50w.csv"));
        String uid;
        while ((uid = bufferedReader50w.readLine()) != null) {
            userId50w.add(uid);
        }
        bufferedReader50w.close();
        InputStreamReader input = new InputStreamReader(new FileInputStream(scoreMap));
        BufferedReader bufferedReader = new BufferedReader(input);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            if (userId50w.contains(str.split(",")[0])) {
                scoreMapping.put(str.split(",")[0], str.split(",")[1]);
            }
        }
        bufferedReader.close();
        input.close();
        Client client = template.getClient();
        BulkRequestBuilder builder = client.prepareBulk();
        int count = 0, error = 1;
        for (Map.Entry<String, String> item : scoreMapping.entrySet()) {
            builder.add(client.prepareUpdate("wb-user-1569427200000", "user", item.getKey()).setDoc(XContentFactory.jsonBuilder()
                    .startObject()
                    .field("score", item.getValue())
                    .endObject()));
            count++;
            if (count % 10000 == 0) {
                while (builder.get().hasFailures()) {
                    System.out.println("update reports error" + error++);
                }
                System.out.println(count);
                builder = client.prepareBulk();
            }
        }
        if (count % 10000 != 0) {
            while (builder.get().hasFailures()) {
                System.out.println("update reports error" + error++);
            }
            System.out.println(count);
        }
    }

}
