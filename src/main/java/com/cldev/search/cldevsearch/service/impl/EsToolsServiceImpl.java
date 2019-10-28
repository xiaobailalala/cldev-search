package com.cldev.search.cldevsearch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cldev.search.cldevsearch.bo.BlogBO;
import com.cldev.search.cldevsearch.dto.BlogDataDTO;
import com.cldev.search.cldevsearch.dto.UserReportDTO;
import com.cldev.search.cldevsearch.dto.UserInfoDTO;
import com.cldev.search.cldevsearch.dto.UserLabelDTO;
import com.cldev.search.cldevsearch.process.common.AbstractHostLoadProcess;
import com.cldev.search.cldevsearch.process.common.HostLoadDataProcessFactory;
import com.cldev.search.cldevsearch.service.EsToolsService;
import com.cldev.search.cldevsearch.ssdb.SSDB;
import com.cldev.search.cldevsearch.util.AsyncTaskUtil;
import com.cldev.search.cldevsearch.util.CommonUtil;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
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
 * Build File @date: 2019/9/23 17:40
 * @version 1.0
 * @description
 */
@Service
public class EsToolsServiceImpl implements EsToolsService {

    private AbstractHostLoadProcess hostLoadProcess = HostLoadDataProcessFactory.productHostProcess().getHostProcess();
    private AbstractHostLoadProcess hostLoadProcessForKol = HostLoadDataProcessFactory.productHostProcess().getKolHostProcess();

    private final ElasticsearchTemplate esTemplate;
    private final RestTemplate restTemplate;
    private final AsyncTaskUtil asyncTaskUtil;

    @Autowired
    public EsToolsServiceImpl(ElasticsearchTemplate elasticsearchTemplate, RestTemplate restTemplate, AsyncTaskUtil asyncTaskUtil) {
        this.esTemplate = elasticsearchTemplate;
        this.restTemplate = restTemplate;
        this.asyncTaskUtil = asyncTaskUtil;
    }

    @Override
    public String loadData() {
        hostLoadProcess.loadData();
        return "start";
    }

    @Override
    public List<String> checkFile() {
        return hostLoadProcess.checkFile();
    }

    @Override
    public String checkFileCount() {
        return hostLoadProcess.checkFileCount();
    }

    @Override
    public String loadDataForKol() {
        hostLoadProcessForKol.loadData();
        return "start";
    }

    @Override
    public Object createIndicesUser() {
        List<Object> res = new LinkedList<>();
        String customIndices = "wb-user-" + CommonUtil.dateToStamp("2019-9-26"), customType = "user", customAliases = "wb-user";
        esTemplate.getClient().admin().indices().prepareCreate(customIndices)
                .setSettings(builderUserSetting())
                .execute().actionGet();
        try {
            esTemplate.getClient().admin().indices()
                    .putMapping(Requests.putMappingRequest(customIndices).type(customType)
                            .source(XContentFactory.jsonBuilder().startObject()
                                    .startObject("_all")
                                    .field("enabled", false)
                                    .endObject()
                                    .startObject("_source")
                                    .field("excludes", Arrays.asList("description", "reason"))
                                    .endObject()
                                    .startObject("properties")
                                    .startObject("uid")
                                    .field("type", "long")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("description")
                                    .field("type", "text")
                                    .field("analyzer", "ik_tsconvert_pinyin_analyzer")
                                    .field("search_analyzer", "ik_tsconvert_pinyin_analyzer_search")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("name")
                                    .field("type", "text")
                                    .field("analyzer", "ik_tsconvert_pinyin_analyzer")
                                    .field("search_analyzer", "ik_tsconvert_pinyin_analyzer_search")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("reason")
                                    .field("type", "text")
                                    .field("analyzer", "ik_tsconvert_pinyin_analyzer")
                                    .field("search_analyzer", "ik_tsconvert_pinyin_analyzer_search")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("address")
                                    .field("type", "integer")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("province")
                                    .field("type", "integer")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("sex")
                                    .field("type", "byte")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("fans")
                                    .field("type", "integer")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("score")
                                    .field("type", "float")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("label")
                                    .field("type", "byte")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("info")
                                    .field("type", "keyword")
                                    .field("doc_values", false)
                                    .endObject()
                                    .endObject()
                                    .endObject())).actionGet();
            res.add(esTemplate.getClient().admin().indices().prepareAliases()
                    .addAlias(customIndices, customAliases).execute().actionGet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    @SuppressWarnings("all")
    public String loadDataForUser() {
        SSDB ssdb;
        File scoreMap = new File("C:\\Users\\cl24\\Desktop\\mysql.csv");
        InputStreamReader input;
        Map<String, String> scoreMapping = new HashMap<>(800000);
        Map<String, String> userInfoMap = new HashMap<>(1179648);
        try {
            input = new InputStreamReader(new FileInputStream(scoreMap));
            BufferedReader bufferedReader = new BufferedReader(input);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                scoreMapping.put(str.split(",")[0], str.split(",")[1]);
            }
            bufferedReader.close();
            input.close();
            File userInfoFile = new File("userInfo");
            FileReader fileReader = new FileReader(userInfoFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String msg;
            while ((msg = reader.readLine()) != null) {
                userInfoMap.put(msg.split("-")[0], msg.substring(msg.indexOf("-") + 1));
            }
            reader.close();
            fileReader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.2.75:3306/weibo", "root", "cldev");
            String sql = "SELECT fans_total FROM user_state WHERE uid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ssdb = new SSDB("192.168.2.55", 8889);
            String[] files = {"uid_80w.csv"};
            int count = 0;
            Jedis jedis = new Jedis("192.168.2.11", 6399);
            jedis.auth("cldev");
            BulkRequestBuilder builder = esTemplate.getClient().prepareBulk();
            int limit = 100000;
            for (String file : files) {
                File csvFile = new File("C:\\Users\\cl24\\Desktop\\" + file);
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(csvFile));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String uid;
                while ((uid = bufferedReader.readLine()) != null) {
                    byte[] userInfo = ssdb.hget("userinfo", uid);
                    JSONObject jsonObject = JSONObject.parseObject(new String(userInfo));
                    String labels = jedis.get(uid);
                    int[] labelArr;
                    if (labels != null && !"".equals(labels)) {
                        String[] split = labels.split(",");
                        labelArr = new int[split.length];
                        for (int i = 0; i < split.length; i++) {
                            labelArr[i] = Integer.parseInt(split[i]);
                        }
                    } else {
                        labelArr = new int[0];
                    }
                    String fans = null;
                    preparedStatement.setString(1, uid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        fans = resultSet.getString(1);
                    }
                    resultSet.close();
                    String score = scoreMapping.get(uid);
                    builder.add(esTemplate.getClient().
                            prepareIndex("wb-user-1569427200000", "user", uid).setSource(
                            XContentFactory.jsonBuilder().startObject()
                                    .field("address", generatorAddr(jsonObject.getString("province"), jsonObject.getString("city")))
                                    .field("province", generatorProvince(jsonObject.getString("province")))
                                    .field("description", jsonObject.getString("description"))
                                    .field("name", jsonObject.getString("name"))
                                    .field("sex", "f".equals(jsonObject.getString("gender")) ? "2" : "1")
                                    .field("fans", fans == null ? 0 : fans)
                                    .field("score", score == null ? 0.0f : Float.parseFloat(score))
                                    .field("label", labelArr)
                                    .field("reason", jsonObject.getString("verified_reason"))
                                    .field("info", userInfoMap.get(uid))
                                    .endObject()));
                    count++;
                    if (count % limit == 0) {
                        int failureCount = 0;
                        while (builder.get().hasFailures()) {
                            System.out.println("failure count : " + ++failureCount);
                        }
                        builder = esTemplate.getClient().prepareBulk();
                        System.out.println(count);
                    }
                }
                bufferedReader.close();
                inputStreamReader.close();
            }
            if (count % limit != 0) {
                int failureCount = 0;
                while (builder.get().hasFailures()) {
                    System.out.println("failure count : " + ++failureCount);
                }
            }
            System.out.println(count);
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "load start";
    }

    @Override
    public Object createIndicesBlog() {
        List<Object> res = new LinkedList<>();
        String[][] indicesTime = {{"2016", "1"}, {"2016", "2"}, {"2016", "3"}, {"2016", "4"},
                {"2016", "5"}, {"2016", "6"}, {"2016", "7"}, {"2016", "8"},
                {"2016", "9"}, {"2016", "10"}, {"2016", "11"}, {"2016", "12"},
                {"2017", "1"}};
        for (String[] strings : indicesTime) {
            res.add(createBlogIndices(strings[0] + "-" + strings[1] + "-01"));
        }
        return res;
    }

    @Override
    public String dayTaskCreateBlogIndices(Integer count) {
        try {
            Indices blogIndices = getNewBlogIndices();
            int limit = 60000000;
            int dyna = 1000000;
            if (blogIndices.count + count <= limit) {
                return blogIndices.indices;
            } else if (blogIndices.count + count - limit <= dyna) {
                return blogIndices.indices;
            } else {
                return createBlogIndices(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String dayTaskLoadBlogData(BlogDataDTO blogDataDTO) {
        BulkRequestBuilder builder = esTemplate.getClient().prepareBulk();
        List<BlogBO> blogTempList = new LinkedList<>();
        try {
            for (BlogBO blog : blogDataDTO.getBlog()) {
                String time = blog.getTime();
                blogTempList.add(new BlogBO().setArticle(blog.getArticle())
                        .setTime(time.split(" ").length == 1 ?
                                CommonUtil.dateToStamp(time + " 00:00:00") :
                                CommonUtil.dateToStamp(time))
                        .setUid(blog.getUid()).setMid(blog.getMid()));
                builder.add(esTemplate.getClient().
                        prepareIndex("wb-blog-mid", "mid", blog.getMid()).setSource(XContentFactory.jsonBuilder().startObject().endObject()));
            }
            AbstractHostLoadProcess.load(builder, "main", esTemplate, blogTempList, blogDataDTO.getIndices());
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @Override
    public String dayTaskUpdateUserReports(List<UserReportDTO> userReportDTOList) {
        Client client = esTemplate.getClient();
        BulkRequestBuilder builder = client.prepareBulk();
        for (UserReportDTO item : userReportDTOList) {
            String info = item.getAttitudeSum() + "-" +
                    item.getAttitudeMax() + "-" +
                    item.getAttitudeMedian() + "-" +
                    item.getCommentSum() + "-" +
                    item.getCommentMax() + "-" +
                    item.getCommentMedian() + "-" +
                    item.getRepostSum() + "-" +
                    item.getRepostMax() + "-" +
                    item.getRepostMedian() + "-" +
                    item.getMblogTotal() + "-" +
                    item.getReleaseMblogFrequency();
            try {
                XContentBuilder jsonBuilder = XContentFactory.jsonBuilder().startObject();
                if (!ObjectUtils.isEmpty(item.getFans()) && !StringUtils.isEmpty(item.getFans())) {
                    jsonBuilder = jsonBuilder.field("fans", item.getFans());
                }
                builder.add(client.prepareUpdate("wb-user-1569427200000", "user", item.getUid()).setDoc(jsonBuilder
                        .field("info", info)
                        .endObject()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int count = 0;
        while (builder.get().hasFailures()) {
            System.out.println("update reports error" + count++);
        }
        return "success";
    }

    @Override
    public String dayTaskUpdateUserLabels(List<UserLabelDTO> userLabelDTOList) {
        Client client = esTemplate.getClient();
        BulkRequestBuilder builder = client.prepareBulk();
        for (UserLabelDTO item : userLabelDTOList) {
            try {
                builder.add(client.prepareUpdate("wb-user-1569427200000", "user", item.getUid()).setDoc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("labels", Arrays.asList(item.getLabels()))
                        .endObject()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        builder.get();
        return "success";
    }

    @Override
    public String dayTaskForceMergeUser() {
        asyncTaskUtil.asyncCustomTask(() -> restTemplate.postForObject("http://192.168.2.76:9200/wb-user-1569427200000/_forcemerge?max_num_segments=1", null, Object.class));
        return "force merge for user indices start";
    }

    @Override
    public String dayTaskForceMergeBlog() {
        Indices blogIndices = getNewBlogIndices();
        asyncTaskUtil.asyncCustomTask(() -> restTemplate.postForObject("http://192.168.2.76:9200/" + blogIndices.indices + "/_forcemerge?max_num_segments=1", null, Object.class));
        return "force merge for blog indices start";
    }

    @Override
    public Object createIndicesMid() {
        List<Object> res = new LinkedList<>();
        String customIndices = "wb-blog-mid", customType = "mid", customAliases = "mid";
        esTemplate.getClient().admin().indices().prepareCreate(customIndices)
                .setSettings(Settings.builder()
                        .put("index.number_of_shards", 1)
                        .put("index.number_of_replicas", 2))
                .execute().actionGet();
        try {
            esTemplate.getClient().admin().indices()
                    .putMapping(Requests.putMappingRequest(customIndices).type(customType)
                            .source(XContentFactory.jsonBuilder().startObject()
                                    .startObject("_all")
                                    .field("enabled", false)
                                    .endObject()
                                    .startObject("_source")
                                    .field("enabled", false)
                                    .endObject()
                                    .endObject())).actionGet();
            res.add(esTemplate.getClient().admin().indices().prepareAliases()
                    .addAlias(customIndices, customAliases).execute().actionGet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String dayTaskUpdateUserInfo(List<UserInfoDTO> userInfoDTOS) {
        Client client = esTemplate.getClient();
        BulkRequestBuilder builder = client.prepareBulk();
        for (UserInfoDTO item : userInfoDTOS) {
            try {
                XContentBuilder jsonBuilder = XContentFactory.jsonBuilder().startObject();
                if (!ObjectUtils.isEmpty(item.getName()) && !StringUtils.isEmpty(item.getName())) {
                    jsonBuilder = jsonBuilder.field("name", item.getName());
                }
                if (!ObjectUtils.isEmpty(item.getCity()) && !ObjectUtils.isEmpty(item.getProvince())) {
                    jsonBuilder = jsonBuilder.field("address", generatorAddr(item.getProvince().toString(), item.getCity().toString()));
                    jsonBuilder = jsonBuilder.field("province", generatorProvince(item.getProvince().toString()));
                }
                if (!ObjectUtils.isEmpty(item.getSex()) && !StringUtils.isEmpty(item.getSex())) {
                    jsonBuilder = jsonBuilder.field("sex", item.getSex());
                }
                if (!ObjectUtils.isEmpty(item.getDescription()) && !StringUtils.isEmpty(item.getDescription())) {
                    jsonBuilder = jsonBuilder.field("description", item.getDescription());
                }
                if (!ObjectUtils.isEmpty(item.getReason()) && !StringUtils.isEmpty(item.getReason())) {
                    jsonBuilder = jsonBuilder.field("reason", item.getReason());
                }
                builder.add(client.prepareUpdate("wb-user-1569427200000", "user", item.getUid()).setDoc(jsonBuilder.endObject()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        builder.get();
        return "success";
    }

    private Indices getNewBlogIndices() {
        List indicesList = restTemplate.getForObject("http://192.168.2.76:9200/_cat/indices/?h=index,docs.count&format=json", List.class);
        assert indicesList != null;
        List<Indices> indices = new LinkedList<>();
        for (Object item : indicesList) {
            String index = ((LinkedHashMap) item).get("index").toString();
            Integer docsCount = Integer.parseInt(((LinkedHashMap) item).get("docs.count").toString());
            if (index.startsWith("wb-art")) {
                indices.add(new Indices(index, docsCount));
            }
        }
        Collections.sort(indices);
        return indices.get(0);
    }

    private String generatorAddr(String province, String city) {
        String[] supple = {"", "0", "00", "000"};
        return supple[3 - province.length()] + province + supple[4 - city.length()] + city;
    }

    private String generatorProvince(String province) {
        String[] supple = {"", "0", "00", "000"};
        return supple[3 - province.length()] + province;
    }

    private String createBlogIndices(String indicesDate) {
        String customIndices = "wb-art-" + CommonUtil.dateToStamp(indicesDate);
        esTemplate.getClient().admin().indices().prepareCreate(customIndices)
                .setSettings(builderBlogSetting())
                .execute().actionGet();
        try {
            esTemplate.getClient().admin().indices()
                    .putMapping(Requests.putMappingRequest(customIndices).type("article")
                            .source(XContentFactory.jsonBuilder()
                                    .startObject()
                                    .startObject("_all")
                                    .field("enabled", false)
                                    .endObject()
                                    .startObject("_source")
                                    .field("includes", Arrays.asList("uid", "time"))
                                    .endObject()
                                    .startObject("properties")
                                    .startObject("uid")
                                    .field("type", "long")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("article")
                                    .field("type", "text")
                                    .field("analyzer", "ik_smart")
                                    .field("search_analyzer", "ik_smart")
                                    .field("doc_values", false)
                                    .endObject()
                                    .startObject("time")
                                    .field("type", "long")
                                    .field("doc_values", false)
                                    .endObject()
                                    .endObject()
                                    .endObject())).actionGet();
            esTemplate.getClient().admin().indices().prepareAliases().addAlias(customIndices, "wb-art").execute().actionGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customIndices;
    }

    private Settings.Builder builderBlogSetting() {
        return Settings.builder()
                /* setting index properties
                    "index": {
                      "number_of_shards": 1,
                      "number_of_replicas": 1
                    },
                 */
                .put("index.number_of_shards", 2)
                .put("index.number_of_replicas", 2);
                /* setting analysis properties
                 "analyzer" : {
                    "ik_tsconvert_pinyin_analyzer": {
                      "type": "custom",
                      "tokenizer": "ik_max_word",
                      "filter": ["tsconvert"],
                      "char_filter": "tsconvert"
                    },
                    "ik_tsconvert_pinyin_analyzer_search": {
                      "type": "custom",
                      "tokenizer": "ik_smart",
                      "filter": ["tsconvert"],
                      "char_filter": "tsconvert"
                    }
                  }
                 */
//                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer.type", "custom")
//                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer.tokenizer", "ik_max_word")
//                .putList("analysis.analyzer.ik_tsconvert_pinyin_analyzer.filter", "tsconvert")
//                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer.char_filter", "tsconvert")
//                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer_search.type", "custom")
//                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer_search.tokenizer", "ik_smart")
//                .putList("analysis.analyzer.ik_tsconvert_pinyin_analyzer_search.filter", "tsconvert")
//                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer_search.char_filter", "tsconvert")
                /* setting filter properties
                  "filter": {
                    "tsconvert" : {
                      "type" : "stconvert",
                      "delimiter" : "#",
                      "keep_both" : false,
                      "convert_type" : "t2s"
                    }
                  }
                * */
//                .put("analysis.filter.tsconvert.type", "stconvert")
//                .put("analysis.filter.tsconvert.delimiter", "#")
//                .put("analysis.filter.tsconvert.keep_both", false)
//                .put("analysis.filter.tsconvert.convert_type", "t2s")
                /* setting char_filter properties
                  "char_filter": {
                    "tsconvert": {
                      "type": "stconvert",
                      "convert_type": "t2s"
                    }
                  }
                 *  */
//                .put("analysis.char_filter.tsconvert.type", "stconvert")
//                .put("analysis.char_filter.tsconvert.convert_type", "t2s");
    }

    private Settings.Builder builderUserSetting() {
        return Settings.builder()
                /* setting index properties
                    "index": {
                      "number_of_shards": 1,
                      "number_of_replicas": 1
                    },
                 */
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 2)
                /* setting analysis properties
                 "analyzer" : {
                    "ik_tsconvert_pinyin_analyzer": {
                      "type": "custom",
                      "tokenizer": "ik_smart",
                      "filter": ["tsconvert", "pinyin_filter"],
                      "char_filter": "tsconvert"
                    },
                    "ik_tsconvert_pinyin_analyzer_search": {
                      "type": "custom",
                      "tokenizer": "ik_max_word",
                      "filter": ["tsconvert", "pinyin_filter"],
                      "char_filter": "tsconvert"
                    }
                  }
                 */
                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer.type", "custom")
                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer.tokenizer", "ik_smart")
//                .putList("analysis.analyzer.ik_tsconvert_pinyin_analyzer.filter", "tsconvert", "pinyin_filter")
                .putList("analysis.analyzer.ik_tsconvert_pinyin_analyzer.filter", "tsconvert")
                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer.char_filter", "tsconvert")
                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer_search.type", "custom")
                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer_search.tokenizer", "ik_smart")
//                .putList("analysis.analyzer.ik_tsconvert_pinyin_analyzer_search.filter", "tsconvert", "pinyin_filter")
                .putList("analysis.analyzer.ik_tsconvert_pinyin_analyzer_search.filter", "tsconvert")
                .put("analysis.analyzer.ik_tsconvert_pinyin_analyzer_search.char_filter", "tsconvert")
                /* setting filter properties
                  "filter": {
                    "tsconvert" : {
                      "type" : "stconvert",
                      "delimiter" : "#",
                      "keep_both" : false,
                      "convert_type" : "t2s"
                    },
                    "pinyin_filter":{
                      "type":"pinyin",
                      "keep_first_letter": true,
                      "keep_separate_first_letter": true,
                      "keep_original": false,
                      "lowercase": true
                    }
                  }
                * */
                .put("analysis.filter.tsconvert.type", "stconvert")
                .put("analysis.filter.tsconvert.delimiter", "#")
                .put("analysis.filter.tsconvert.keep_both", false)
                .put("analysis.filter.tsconvert.convert_type", "t2s")
//                .put("analysis.filter.pinyin_filter.type", "pinyin")
//                .put("analysis.filter.pinyin_filter.keep_first_letter", true)
//                .put("analysis.filter.pinyin_filter.keep_separate_first_letter", true)
//                .put("analysis.filter.pinyin_filter.keep_original", false)
//                .put("analysis.filter.pinyin_filter.lowercase", true)
                /* setting char_filter properties
                  "char_filter": {
                    "tsconvert": {
                      "type": "stconvert",
                      "convert_type": "t2s"
                    }
                  }
                 *  */
                .put("analysis.char_filter.tsconvert.type", "stconvert")
                .put("analysis.char_filter.tsconvert.convert_type", "t2s");
    }

    private class Indices implements Comparable<Indices> {
        String indices;
        Integer count;

        Indices(String indices, Integer count) {
            this.indices = indices;
            this.count = count;
        }

        @Override
        @SuppressWarnings("all")
        public int compareTo(Indices o) {
            return o.indices.compareTo(this.indices);
        }
    }

}
