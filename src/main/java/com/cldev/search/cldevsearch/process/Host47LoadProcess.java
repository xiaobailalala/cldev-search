package com.cldev.search.cldevsearch.process;

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
 * Build File @date: 2019/9/23 21:58
 * @version 1.0
 * @description
 */
public class Host47LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-11 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_118.csv").setIndices("wb-art-" + dateToStamp("2019-01-11")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_119.csv").setIndices("wb-art-" + dateToStamp("2019-01-11")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_120.csv").setIndices("wb-art-" + dateToStamp("2019-01-11")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_121.csv").setIndices("wb-art-" + dateToStamp("2019-01-11")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_122.csv").setIndices("wb-art-" + dateToStamp("2019-01-11")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_123.csv").setIndices("wb-art-" + dateToStamp("2019-01-11")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_124.csv").setIndices("wb-art-" + dateToStamp("2019-01-11")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_125.csv").setIndices("wb-art-" + dateToStamp("2019-01-11")),
            /* ********************************************* indices 2019-01-12 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_126.csv").setIndices("wb-art-" + dateToStamp("2019-01-12")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_127.csv").setIndices("wb-art-" + dateToStamp("2019-01-12")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_128.csv").setIndices("wb-art-" + dateToStamp("2019-01-12")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_129.csv").setIndices("wb-art-" + dateToStamp("2019-01-12")),
            /* ********************************************* indices 2019-02-06 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_130.csv").setIndices("wb-art-" + dateToStamp("2019-02-06")),
            /* ********************************************* indices 2019-01-12 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_131.csv").setIndices("wb-art-" + dateToStamp("2019-01-12")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_132.csv").setIndices("wb-art-" + dateToStamp("2019-01-12")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_133.csv").setIndices("wb-art-" + dateToStamp("2019-01-12")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_134.csv").setIndices("wb-art-" + dateToStamp("2019-01-12")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_135.csv").setIndices("wb-art-" + dateToStamp("2019-01-12")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_136.csv").setIndices("wb-art-" + dateToStamp("2019-01-12")),
            /* ********************************************* indices 2019-01-13 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_137.csv").setIndices("wb-art-" + dateToStamp("2019-01-13")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_40.csv").setIndices("wb-art-" + dateToStamp("2019-01-13")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_41.csv").setIndices("wb-art-" + dateToStamp("2019-01-13")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_42.csv").setIndices("wb-art-" + dateToStamp("2019-01-13")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_43.csv").setIndices("wb-art-" + dateToStamp("2019-01-13")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_44.csv").setIndices("wb-art-" + dateToStamp("2019-01-13"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 12),
                csvMappings.subList(12, 25)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }
}
