package com.cldev.search.cldevsearch.process.common;

import com.cldev.search.cldevsearch.correlation.builder.AddressBoolQueryBuilder;
import com.cldev.search.cldevsearch.ssdb.SSDB;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
        byte[] userinfos = ssdb.hget("userinfo", "1258859614");
        String userInfo = new String(userinfos);
        System.out.println(userInfo);
    }

    @Test
    public void searchFromMysql() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.2.75:3306/weibo", "root", "cldev");
        String sql = "SELECT fans_total FROM user_state WHERE uid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        String[] uid = {"10052", "11075", "33624"};
        for (String s : uid) {
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            resultSet.close();
        }
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void uidInfo() {
        List<Report> reports = new LinkedList<>();
        try (InputStream inputStream = new FileInputStream(new File("C:\\Users\\cl24\\Desktop\\user_state.csv"));
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid",
                     "attitudeMax", "attitudeMedian", "commentMax", "commentMedian", "repostMax", "repostMedian", "releaseMblogFrequency")
                     .withSkipHeaderRecord(false))) {
            for (CSVRecord record : csvRecords.getRecords()) {
                String attitudeMax = record.get("attitudeMax");
                String attitudeMedian = record.get("attitudeMedian");
                String commentMax = record.get("commentMax");
                String commentMedian = record.get("commentMedian");
                String repostMax = record.get("repostMax");
                String repostMedian = record.get("repostMedian");
                String releaseMblogFrequency = record.get("releaseMblogFrequency");
                reports.add(new Report(record.get("uid"), attitudeMax == null || "".equals(attitudeMax) ? 0 : Integer.parseInt(attitudeMax),
                        attitudeMedian == null || "".equals(attitudeMedian) ? 0 : Integer.parseInt(attitudeMedian),
                        commentMax == null || "".equals(commentMax) ? 0 : Integer.parseInt(commentMax),
                        commentMedian == null || "".equals(commentMedian) ? 0 : Integer.parseInt(commentMedian),
                        repostMax == null || "".equals(repostMax) ? 0 : Integer.parseInt(repostMax),
                        repostMedian == null || "".equals(repostMedian) ? 0 : Integer.parseInt(repostMedian),
                        releaseMblogFrequency == null || "".equals(releaseMblogFrequency) ? 0.0d : Double.parseDouble(releaseMblogFrequency)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("C:\\Users\\cl24\\Desktop\\uid_80w.csv");
        Set<String> kolUid = new HashSet<>();
        try (InputStream inputStream = new FileInputStream(file);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid")
                     .withSkipHeaderRecord(false))) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (CSVRecord record : csvRecords.getRecords()) {
                kolUid.add(record.get("uid"));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (Report report : reports.stream().filter(item -> kolUid.contains(item.uid)).collect(Collectors.toList())) {
            printLoadLog("userInfo", report.uid + "-" +
                    report.attitudeMax + "-" +
                    report.attitudeMedian + "-" +
                    report.commentMax + "-" +
                    report.commentMedian + "-" +
                    report.repostMax + "-" +
                    report.repostMedian + "-" +
                    report.releaseMblogFrequency);
        }
    }

    private class Report {
        String uid;
        Integer attitudeMax;
        Integer attitudeMedian;
        Integer commentMax;
        Integer commentMedian;
        Integer repostMax;
        Integer repostMedian;
        Double releaseMblogFrequency;

        Report(String uid, Integer attitudeMax, Integer attitudeMedian, Integer commentMax, Integer commentMedian, Integer repostMax, Integer repostMedian, Double releaseMblogFrequency) {
            this.uid = uid;
            this.attitudeMax = attitudeMax;
            this.attitudeMedian = attitudeMedian;
            this.commentMax = commentMax;
            this.commentMedian = commentMedian;
            this.repostMax = repostMax;
            this.repostMedian = repostMedian;
            this.releaseMblogFrequency = releaseMblogFrequency;
        }
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

}
