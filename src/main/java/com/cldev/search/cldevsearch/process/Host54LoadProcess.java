package com.cldev.search.cldevsearch.process;

import com.cldev.search.cldevsearch.process.common.AbstractHostLoadProcess;

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
 * Build File @date: 2019/9/23 21:59
 * @version 1.0
 * @description
 */
public class Host54LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-24 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_100.csv").setIndices("wb-art-" + dateToStamp("2019-01-24")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_101.csv").setIndices("wb-art-" + dateToStamp("2019-01-24")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_102.csv").setIndices("wb-art-" + dateToStamp("2019-01-24")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_103.csv").setIndices("wb-art-" + dateToStamp("2019-01-24")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_104.csv").setIndices("wb-art-" + dateToStamp("2019-01-24")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_105.csv").setIndices("wb-art-" + dateToStamp("2019-01-24")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_15.csv").setIndices("wb-art-" + dateToStamp("2019-01-24")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_16.csv").setIndices("wb-art-" + dateToStamp("2019-01-24")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_170.csv").setIndices("wb-art-" + dateToStamp("2019-01-24")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_171.csv").setIndices("wb-art-" + dateToStamp("2019-01-24")),
            /* ********************************************* indices 2019-01-25 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_172.csv").setIndices("wb-art-" + dateToStamp("2019-01-25")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_173.csv").setIndices("wb-art-" + dateToStamp("2019-01-25")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_274.csv").setIndices("wb-art-" + dateToStamp("2019-01-25")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_275.csv").setIndices("wb-art-" + dateToStamp("2019-01-25")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_276.csv").setIndices("wb-art-" + dateToStamp("2019-01-25")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_277.csv").setIndices("wb-art-" + dateToStamp("2019-01-25")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_278.csv").setIndices("wb-art-" + dateToStamp("2019-01-25")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_279.csv").setIndices("wb-art-" + dateToStamp("2019-01-25")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_280.csv").setIndices("wb-art-" + dateToStamp("2019-01-25")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_281.csv").setIndices("wb-art-" + dateToStamp("2019-01-25"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 10),
                csvMappings.subList(10, 20)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }
}
