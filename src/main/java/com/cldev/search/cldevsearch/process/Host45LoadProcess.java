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
public class Host45LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-07 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_233.csv").setIndices("wb-art-" + dateToStamp("2019-01-07")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_234.csv").setIndices("wb-art-" + dateToStamp("2019-01-07")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_235.csv").setIndices("wb-art-" + dateToStamp("2019-01-07")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_236.csv").setIndices("wb-art-" + dateToStamp("2019-01-07")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_237.csv").setIndices("wb-art-" + dateToStamp("2019-01-07")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_238.csv").setIndices("wb-art-" + dateToStamp("2019-01-07")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_239.csv").setIndices("wb-art-" + dateToStamp("2019-01-07")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_240.csv").setIndices("wb-art-" + dateToStamp("2019-01-07")),
            /* ********************************************* indices 2019-01-08 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_241.csv").setIndices("wb-art-" + dateToStamp("2019-01-08")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_242.csv").setIndices("wb-art-" + dateToStamp("2019-01-08")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_243.csv").setIndices("wb-art-" + dateToStamp("2019-01-08")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_244.csv").setIndices("wb-art-" + dateToStamp("2019-01-08")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_245.csv").setIndices("wb-art-" + dateToStamp("2019-01-08")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_246.csv").setIndices("wb-art-" + dateToStamp("2019-01-08")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_247.csv").setIndices("wb-art-" + dateToStamp("2019-01-08")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_248.csv").setIndices("wb-art-" + dateToStamp("2019-01-08")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_249.csv").setIndices("wb-art-" + dateToStamp("2019-01-08")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_250.csv").setIndices("wb-art-" + dateToStamp("2019-01-08")),
            /* ********************************************* indices 2019-01-09 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_251.csv").setIndices("wb-art-" + dateToStamp("2019-01-09")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_252.csv").setIndices("wb-art-" + dateToStamp("2019-01-09"))
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
