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
public class Host52LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-20 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_216.csv").setIndices("wb-art-" + dateToStamp("2019-01-20")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_217.csv").setIndices("wb-art-" + dateToStamp("2019-01-20")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_218.csv").setIndices("wb-art-" + dateToStamp("2019-01-20")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_219.csv").setIndices("wb-art-" + dateToStamp("2019-01-20")),
            /* ********************************************* indices 2019-02-05 ********************************************* */
            //1218618
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_220.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            /* ********************************************* indices 2019-01-20 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_320.csv").setIndices("wb-art-" + dateToStamp("2019-01-18")),
            /* ********************************************* indices 2019-01-21 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_321.csv").setIndices("wb-art-" + dateToStamp("2019-01-21")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_322.csv").setIndices("wb-art-" + dateToStamp("2019-01-21")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_323.csv").setIndices("wb-art-" + dateToStamp("2019-01-21")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_324.csv").setIndices("wb-art-" + dateToStamp("2019-01-21")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_325.csv").setIndices("wb-art-" + dateToStamp("2019-01-21")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_326.csv").setIndices("wb-art-" + dateToStamp("2019-01-21")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_327.csv").setIndices("wb-art-" + dateToStamp("2019-01-21")),
            /* ********************************************* indices 2019-02-06 ********************************************* */
            //2290960
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_328.csv").setIndices("wb-art-" + dateToStamp("2019-02-06")),
            /* ********************************************* indices 2019-01-21 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_329.csv").setIndices("wb-art-" + dateToStamp("2019-01-21")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_330.csv").setIndices("wb-art-" + dateToStamp("2019-01-21")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_331.csv").setIndices("wb-art-" + dateToStamp("2019-01-21")),
            /* ********************************************* indices 2019-01-22 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_332.csv").setIndices("wb-art-" + dateToStamp("2019-01-22")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_333.csv").setIndices("wb-art-" + dateToStamp("2019-01-22")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_334.csv").setIndices("wb-art-" + dateToStamp("2019-01-22"))
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
