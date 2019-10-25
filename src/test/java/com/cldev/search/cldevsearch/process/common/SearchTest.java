package com.cldev.search.cldevsearch.process.common;

import com.cldev.search.cldevsearch.correlation.builder.AddressBoolQueryBuilder;
import com.cldev.search.cldevsearch.ssdb.SSDB;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
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
        byte[] userinfos = ssdb.hget("userinfo", "1255795640");
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
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid", "mblogTotal",
                     "attitudeSum", "commentSum", "repostSum",
                     "attitudeMax", "commentMax", "repostMax",
                     "attitudeMedian", "commentMedian", "repostMedian", "releaseMblogFrequency")
                     .withSkipHeaderRecord(true))) {
            for (CSVRecord record : csvRecords.getRecords()) {
                String attitudeMax = record.get("attitudeMax");
                String attitudeMedian = record.get("attitudeMedian");
                String attitudeSum = record.get("attitudeSum");
                String commentMax = record.get("commentMax");
                String commentMedian = record.get("commentMedian");
                String commentSum = record.get("commentSum");
                String repostMax = record.get("repostMax");
                String repostMedian = record.get("repostMedian");
                String repostSum = record.get("repostSum");
                String mblogTotal = record.get("mblogTotal");
                String releaseMblogFrequency = record.get("releaseMblogFrequency");
                reports.add(new Report(record.get("uid"),
                        attitudeSum == null || "".equals(attitudeSum) ? 0 : Integer.parseInt(attitudeSum),
                        attitudeMax == null || "".equals(attitudeMax) ? 0 : Integer.parseInt(attitudeMax),
                        attitudeMedian == null || "".equals(attitudeMedian) ? 0 : Integer.parseInt(attitudeMedian),
                        commentSum == null || "".equals(commentSum) ? 0 : Integer.parseInt(commentSum),
                        commentMax == null || "".equals(commentMax) ? 0 : Integer.parseInt(commentMax),
                        commentMedian == null || "".equals(commentMedian) ? 0 : Integer.parseInt(commentMedian),
                        repostSum == null || "".equals(repostSum) ? 0 : Integer.parseInt(repostSum),
                        repostMax == null || "".equals(repostMax) ? 0 : Integer.parseInt(repostMax),
                        repostMedian == null || "".equals(repostMedian) ? 0 : Integer.parseInt(repostMedian),
                        mblogTotal == null || "".equals(mblogTotal) ? 0 : Integer.parseInt(mblogTotal),
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
        List<Report> collect = reports.stream().filter(item -> kolUid.contains(item.uid)).collect(Collectors.toList());
        int count = 0;
        for (Report report : collect) {
            printLoadLog("userInfo", report.uid + "-" +
                    report.attitudeSum + "-" +
                    report.attitudeMax + "-" +
                    report.attitudeMedian + "-" +
                    report.commentSum + "-" +
                    report.commentMax + "-" +
                    report.commentMedian + "-" +
                    report.repostSum + "-" +
                    report.repostMax + "-" +
                    report.repostMedian + "-" +
                    report.mblogTotal + "-" +
                    report.releaseMblogFrequency);
            count++;
            System.out.println(String.format("%.2f%%", (count / (double) collect.size()) * 100));
        }
    }

    @Test
    public void userInfoOperation() throws IOException {
        File userInfoFile = new File("userInfo");
        FileReader fileReader = new FileReader(userInfoFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String msg;
        Map<String, String> userInfoMap = new HashMap<>(1179648);
        while ((msg = bufferedReader.readLine()) != null) {
            userInfoMap.put(msg.split("-")[0], msg.substring(msg.indexOf("-") + 1));
        }
        bufferedReader.close();
        fileReader.close();
        for (Map.Entry<String, String> entry : userInfoMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    private class Report {
        String uid;
        Integer attitudeSum;
        Integer attitudeMax;
        Integer attitudeMedian;
        Integer commentSum;
        Integer commentMax;
        Integer commentMedian;
        Integer repostSum;
        Integer repostMax;
        Integer repostMedian;
        Integer mblogTotal;
        Double releaseMblogFrequency;

        Report(String uid, Integer attitudeSum, Integer attitudeMax, Integer attitudeMedian,
               Integer commentSum, Integer commentMax, Integer commentMedian,
               Integer repostSum, Integer repostMax, Integer repostMedian,
               Integer mblogTotal, Double releaseMblogFrequency) {
            this.uid = uid;
            this.attitudeMax = attitudeMax;
            this.attitudeMedian = attitudeMedian;
            this.commentMax = commentMax;
            this.commentMedian = commentMedian;
            this.repostMax = repostMax;
            this.repostMedian = repostMedian;
            this.releaseMblogFrequency = releaseMblogFrequency;
            this.attitudeSum = attitudeSum;
            this.commentSum = commentSum;
            this.repostSum = repostSum;
            this.mblogTotal = mblogTotal;
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
