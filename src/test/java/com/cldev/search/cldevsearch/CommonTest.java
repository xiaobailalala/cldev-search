package com.cldev.search.cldevsearch;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
 * Build File @date: 2019/10/21 11:13
 * @version 1.0
 * @description
 */
public class CommonTest {

    @Test
    public void uidRepeat() {
        List<String> uid = new LinkedList<>(), name = new LinkedList<>();
        File csvFile = new File("C:\\Users\\cl24\\Desktop\\Allbirds博文平均互动量.csv");
        try (InputStream inputStream = new FileInputStream(csvFile);
             CSVParser csvRecords = new CSVParser(new InputStreamReader(inputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT.withHeader("uid", "name")
                     .withSkipHeaderRecord(false))) {
            for (CSVRecord record : csvRecords) {
                uid.add(record.get("uid"));
                name.add(record.get("name"));
            }
            String esUid = "6754408111\n" +
                    "1185562995\n" +
                    "2376018404\n" +
                    "1661504722\n" +
                    "1993730873\n" +
                    "1462214970\n" +
                    "1561285607\n" +
                    "2308572915\n" +
                    "1736694147\n" +
                    "6034707888\n" +
                    "1805406782\n" +
                    "1729653661\n" +
                    "1957188991\n" +
                    "1036701494\n" +
                    "2165313080\n" +
                    "1740492340\n" +
                    "2265095951\n" +
                    "2154105251\n" +
                    "1534936623\n" +
                    "1339788483\n" +
                    "1977585731\n" +
                    "1610216735\n" +
                    "1689734537\n" +
                    "1530349343\n" +
                    "1279112843\n" +
                    "1799244901\n" +
                    "2335418472\n" +
                    "1399746707\n" +
                    "2262398242\n" +
                    "1595692642\n" +
                    "1249137735\n" +
                    "1705938664\n" +
                    "2603531977\n" +
                    "2339859697\n" +
                    "2272824813\n" +
                    "2921243792\n" +
                    "5687265477\n" +
                    "1899702230\n" +
                    "1768063327\n" +
                    "1056435982\n" +
                    "1749574500\n" +
                    "1734962884\n" +
                    "1581266912\n" +
                    "2279490271\n" +
                    "1793000440\n" +
                    "1655852940\n" +
                    "6375760521\n" +
                    "1648109805\n" +
                    "2039914185\n" +
                    "5112344529\n" +
                    "2450512472\n" +
                    "1220089451\n" +
                    "2921223352\n" +
                    "1937992873\n" +
                    "1649211301\n" +
                    "1708188321\n" +
                    "3618422977\n" +
                    "1609443432\n" +
                    "2405631373\n" +
                    "1605391770\n" +
                    "3052592005\n" +
                    "1751376711\n" +
                    "1562234952\n" +
                    "2508526094\n" +
                    "1874781737\n" +
                    "6635855427\n" +
                    "5311440602\n" +
                    "2710257782\n" +
                    "1401640857\n" +
                    "2012843891\n" +
                    "1734096331\n" +
                    "1281021921\n" +
                    "1663937380\n" +
                    "1247246202\n" +
                    "2729836251\n" +
                    "5523374065\n" +
                    "2499342795\n" +
                    "2150852464\n" +
                    "2796360662\n" +
                    "1764358002\n" +
                    "1640337222\n" +
                    "6439268150\n" +
                    "1863237024\n" +
                    "1197402387\n" +
                    "5888423072\n" +
                    "3892777446\n" +
                    "1765373140\n" +
                    "1828697841\n" +
                    "6052420830\n" +
                    "5082639067\n" +
                    "2735376703\n" +
                    "5468096283\n" +
                    "1935358520\n" +
                    "2534299501\n" +
                    "5611141132\n" +
                    "1616284504\n" +
                    "1720615243\n" +
                    "1758936572\n" +
                    "1750264021\n" +
                    "1772840105\n" +
                    "5042306996\n" +
                    "1732646195\n" +
                    "1826017320\n" +
                    "1497882593\n" +
                    "1698233740\n" +
                    "1807656282\n" +
                    "1631355214\n" +
                    "2214246370\n" +
                    "1711479641\n" +
                    "1396927662\n" +
                    "2816562611\n" +
                    "1680088585\n" +
                    "2673619603\n" +
                    "5757884031\n" +
                    "1262702714\n" +
                    "1823702565\n" +
                    "1290792232\n" +
                    "1659807993\n" +
                    "1919813345\n" +
                    "1748530745\n" +
                    "1768272524\n" +
                    "1654750387\n" +
                    "6377192239\n" +
                    "1964174453\n" +
                    "2675030687\n" +
                    "5833553635\n" +
                    "5227458836\n" +
                    "5064877615\n" +
                    "3597871823\n" +
                    "1650432831\n" +
                    "1645546774\n" +
                    "5615857843\n" +
                    "1785138617\n" +
                    "1963155610\n" +
                    "1653321604\n" +
                    "2316796262\n" +
                    "2816273382\n" +
                    "6171252746\n" +
                    "1744045133\n" +
                    "1834507152\n" +
                    "1696842411\n" +
                    "3707779610\n" +
                    "1773144431\n" +
                    "1869186150\n" +
                    "6218270864\n" +
                    "1724678141\n" +
                    "5136739626\n" +
                    "1891366595\n" +
                    "3513260533\n" +
                    "1773223960\n" +
                    "3176216853\n" +
                    "6355176023\n" +
                    "2990282517\n" +
                    "6395826330\n" +
                    "5861557377\n" +
                    "6513218908\n" +
                    "7061822800\n" +
                    "3180912004\n" +
                    "1792394333\n" +
                    "5802398534\n" +
                    "3806744564\n" +
                    "2054479803\n" +
                    "1726406904\n" +
                    "3556698631\n" +
                    "1869163015\n" +
                    "1654030464\n" +
                    "6323922373\n" +
                    "5647538248\n" +
                    "1668695260\n" +
                    "3561737261\n" +
                    "5130283857\n" +
                    "6399690007\n" +
                    "2897351520\n" +
                    "1230199810\n" +
                    "1668250082\n" +
                    "7093705848\n" +
                    "1415512914\n" +
                    "2100899447\n" +
                    "1826111431\n" +
                    "1657987915\n" +
                    "6348500265\n" +
                    "1780694173\n" +
                    "1772293130\n" +
                    "6473450951\n" +
                    "1746513664\n" +
                    "5868405743\n" +
                    "2127302773\n" +
                    "2016191211\n" +
                    "2139681373\n" +
                    "2202565533\n" +
                    "5939033611\n" +
                    "6540380150\n" +
                    "3515583601\n" +
                    "3120395837\n" +
                    "2919412342\n" +
                    "1659791311\n" +
                    "2660940103\n" +
                    "1662290763\n" +
                    "1606465074\n" +
                    "5752835041\n" +
                    "3871422312\n" +
                    "2722048351\n" +
                    "1097853864\n" +
                    "5597529833\n" +
                    "5899491047\n" +
                    "5883740809\n" +
                    "2254586404\n" +
                    "6549710243\n" +
                    "1648169835\n" +
                    "6347751082\n" +
                    "1743556312\n" +
                    "3230934022\n" +
                    "6732279288\n" +
                    "2702687021\n" +
                    "6648524996\n" +
                    "5080110731\n" +
                    "1988994403\n" +
                    "5132358261\n" +
                    "5132035353\n" +
                    "5132037784\n" +
                    "1946626054\n" +
                    "6083282777\n" +
                    "6666597903\n" +
                    "1734632451\n" +
                    "2396739430\n" +
                    "5542501220\n" +
                    "3933801885\n" +
                    "6681810407\n" +
                    "5208038222\n" +
                    "6579609378\n" +
                    "6034751579\n" +
                    "5902451859\n" +
                    "3882899082\n" +
                    "6081979703\n" +
                    "5111762492\n" +
                    "5464436033\n" +
                    "6195881902\n" +
                    "2108961042\n" +
                    "3820851251\n" +
                    "5033578466\n" +
                    "6070669272\n" +
                    "3863291291\n" +
                    "2104673894\n" +
                    "1880591884\n" +
                    "2150592715\n" +
                    "6863427219\n" +
                    "5699768109\n" +
                    "6414952804\n" +
                    "6708446862\n" +
                    "3212157783\n" +
                    "6307886515\n" +
                    "5366991323\n" +
                    "5025149372\n" +
                    "6459332040\n" +
                    "1281746743\n" +
                    "2984003911\n" +
                    "5857481611\n" +
                    "6517356960\n" +
                    "1737835461\n" +
                    "6727654564\n" +
                    "1745336222\n" +
                    "2747383815\n" +
                    "5885092824\n" +
                    "6419218390\n" +
                    "6329750418\n" +
                    "6410450642";
            int findCount = 0;
            String[] esUidArr = esUid.split("\n");
            List<String> esNotFountUid = new LinkedList<>(), sortNotFountUid = new LinkedList<>();
            for (int item = 0; item < esUidArr.length; item++) {
                int index = uid.indexOf(esUidArr[item]);
                if (index != -1) {
                    findCount++;
                    System.out.println(uid.get(index) + " " + name.get(index) + " " + (item + 1) + " " + (index + 1));
                } else {
                    sortNotFountUid.add(esUidArr[item]);
                }
            }
            List<String> list = Arrays.asList(esUidArr);
            for (String item : uid) {
                if (list.indexOf(item) == -1) {
                    esNotFountUid.add(item);
                }
            }
            System.out.println(findCount);
            System.out.println("es not fount uid : ");
            esNotFountUid.forEach(System.out::println);
            System.out.println("sort not fount uid : ");
            sortNotFountUid.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void stampToDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long("1562256000000");
        Date date = new Date(lt);
        System.out.println(simpleDateFormat.format(date));
    }

}
