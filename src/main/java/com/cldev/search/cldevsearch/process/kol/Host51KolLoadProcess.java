package com.cldev.search.cldevsearch.process.kol;

import com.cldev.search.cldevsearch.common.AbstractHostLoadProcess;

import java.util.Arrays;
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
 * Build File @date: 2019/9/30 1:53 下午
 * @version 1.0
 * @description
 */
public class Host51KolLoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog_80w_kol/";

    private List<CsvMapping> csvMappings = Arrays.asList(
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_103.csv").setIndices("wb-art-" + dateToStamp("2016-05-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_104.csv").setIndices("wb-art-" + dateToStamp("2016-05-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_105.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_106.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_107.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_108.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_109.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_110.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_111.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_112.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_113.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_114.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_115.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_116.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_117.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_118.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_119.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_120.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_121.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_122.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_123.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_124.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_125.csv").setIndices("wb-art-" + dateToStamp("2016-08-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_126.csv").setIndices("wb-art-" + dateToStamp("2016-08-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_127.csv").setIndices("wb-art-" + dateToStamp("2016-08-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_128.csv").setIndices("wb-art-" + dateToStamp("2016-08-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_129.csv").setIndices("wb-art-" + dateToStamp("2016-08-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_130.csv").setIndices("wb-art-" + dateToStamp("2016-08-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_131.csv").setIndices("wb-art-" + dateToStamp("2016-08-01")),
            //5214246
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_132.csv").setIndices("wb-art-" + dateToStamp("2017-01-01"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 15),
                csvMappings.subList(15, 30)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }

}
