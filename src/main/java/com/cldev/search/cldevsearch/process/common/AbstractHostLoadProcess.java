package com.cldev.search.cldevsearch.process.common;


import com.cldev.search.cldevsearch.util.AsyncTaskUtil;
import com.cldev.search.cldevsearch.util.CommonUtil;
import com.cldev.search.cldevsearch.util.ProcessUtil;
import com.cldev.search.cldevsearch.util.SpringContextUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
 * Build File @date: 2019/9/23 19:13
 * @version 1.0
 * @description
 */
public abstract class AbstractHostLoadProcess {

    protected String dateToStamp(String time) {
        return CommonUtil.dateToStamp(time);
    }

    /**
     * check file exist
     *
     * @return the file name list of do not exist files
     */
    public abstract List<String> checkFile();

    /**
     * load data to elasticsearch
     */
    public abstract void loadData();

    /**
     * check file count with csvMapping
     * @return status
     */
    public abstract String checkFileCount();

    protected String checkFileCount(String folder, int csvMappingSize) {
        String csvFileCount = String.format("ls %s*.csv | wc -l", folder);
        if (StringUtils.isEmpty(ProcessUtil.executeCmd(csvFileCount))) {
            return "read csv count exception";
        }
        if (csvMappingSize == Integer.parseInt(Objects.requireNonNull(ProcessUtil.executeCmd(csvFileCount)))) {
            return null;
        }
        return "read count failure";
    }

    protected List<String> checkFile(List<CsvMapping> csvMappings) {
        String found = "found", notFound = "not found";
        List<String> notFoundFileName = new LinkedList<>();
        for (CsvMapping csvMapping : csvMappings) {
            String isFileExist = String.format("test -f %s && echo \"%s\" || echo \"%s\"", csvMapping.getFileName(), found, notFound);
            if (!found.equals(ProcessUtil.executeCmd(isFileExist))) {
                notFoundFileName.add(csvMapping.getFileName());
            }
        }
        return notFoundFileName;
    }

    protected void loadData(List<List<CsvMapping>> dataList) {
        AsyncTaskUtil asyncTaskUtil = SpringContextUtil.getContext().getBean(AsyncTaskUtil.class);
        for (int index = 0; index < dataList.size(); index++) {
            int finalIndex = index;
            asyncTaskUtil.asyncCustomTask(() -> loadDataToElasticSearch(dataList.get(finalIndex), "thread-" + finalIndex));
        }
    }

    private void loadDataToElasticSearch(List<CsvMapping> csvMappings, String threadName) {
        printLoadLog("log-" + threadName, "load thread start\r\n");
        for (CsvMapping csvFileMapping : csvMappings) {
            File csvFile = new File(csvFileMapping.getFileName());
            try (InputStream inputStream = new FileInputStream(csvFile);
                 CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid", "mid", "article", "tweeted_article", "created_at")
                         .withSkipHeaderRecord(true))) {
                ElasticsearchTemplate elasticsearchTemplate = SpringContextUtil.getContext().getBean(ElasticsearchTemplate.class);
                BulkRequestBuilder builder = elasticsearchTemplate.getClient().prepareBulk();
                int count = 0;
                printLoadLog("log-" + threadName, "read and load start, file is " + csvFileMapping.getFileName());
                for (CSVRecord record : csvRecords) {
                    count++;
                    String createdAt = record.get("created_at");
                    builder.add(elasticsearchTemplate.getClient().
                            prepareIndex(csvFileMapping.getIndices(), "article").setSource(
                            XContentFactory.jsonBuilder().startObject()
                                    .field("uid", record.get("uid"))
                                    .field("article", record.get("article"))
                                    .field("time", createdAt.split(" ").length == 1 ? dateToStamp(createdAt + " 00:00:00") : dateToStamp(createdAt))
                                    .endObject()));
                    if (count % 100000 == 0) {
                        load(builder, threadName);
                        builder = elasticsearchTemplate.getClient().prepareBulk();
                    }
                }
                if (count % 100000 != 0) {
                    load(builder, threadName);
                }
                printLoadLog("log-" + threadName, "read and load finish, file is " + csvFileMapping.getFileName());
            } catch (IOException ignored) {
            }
        }
        printLoadLog("log-" + threadName, "\r\nload thread finish");
    }

    private void load(BulkRequestBuilder builder, String threadName) {
        long start = System.currentTimeMillis();
        printLoadLog("log-" + threadName, "load start:");
        builder.get();
        printLoadLog("log-" + threadName, "load end: " + (System.currentTimeMillis() - start));
    }

    private void printLoadLog(String fileName, String msg) {
        File file = new File(fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
            byte[] contentInBytes = (msg + "\r\n").getBytes();
            fileOutputStream.write(contentInBytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Data
    @Accessors(chain = true)
    public class CsvMapping {

        private String fileName;
        private String indices;
        private String folder;

        public CsvMapping(String folder) {
            this.folder = folder;
        }

        String getFileName() {
            return this.folder + this.fileName;
        }

    }

}