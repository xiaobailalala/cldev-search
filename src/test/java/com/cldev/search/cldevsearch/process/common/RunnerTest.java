package com.cldev.search.cldevsearch.process.common;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.*;
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
 * Build File @date: 2019/11/4 20:56
 * @version 1.0
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RunnerTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void collectMsg() {
        Object forObject = restTemplate.getForObject("http://192.168.2.205:9000/log/collectMsg", Object.class);
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
