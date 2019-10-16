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
 * Build File @date: 2019/9/23 22:00
 * @version 1.0
 * @description
 */
public class Host62LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-30 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_221.csv").setIndices("wb-art-" + dateToStamp("2019-01-30")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_222.csv").setIndices("wb-art-" + dateToStamp("2019-01-30")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_223.csv").setIndices("wb-art-" + dateToStamp("2019-01-30")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_224.csv").setIndices("wb-art-" + dateToStamp("2019-01-30")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_225.csv").setIndices("wb-art-" + dateToStamp("2019-01-30")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_226.csv").setIndices("wb-art-" + dateToStamp("2019-01-30")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_227.csv").setIndices("wb-art-" + dateToStamp("2019-01-30")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_228.csv").setIndices("wb-art-" + dateToStamp("2019-01-30")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_229.csv").setIndices("wb-art-" + dateToStamp("2019-01-30")),
            /* ********************************************* indices 2019-01-31 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_230.csv").setIndices("wb-art-" + dateToStamp("2019-01-31")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_231.csv").setIndices("wb-art-" + dateToStamp("2019-01-31")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_232.csv").setIndices("wb-art-" + dateToStamp("2019-01-31")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_282.csv").setIndices("wb-art-" + dateToStamp("2019-01-31")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_283.csv").setIndices("wb-art-" + dateToStamp("2019-01-31")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_45.csv").setIndices("wb-art-" + dateToStamp("2019-01-31")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_46.csv").setIndices("wb-art-" + dateToStamp("2019-01-31")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_47.csv").setIndices("wb-art-" + dateToStamp("2019-01-31")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_48.csv").setIndices("wb-art-" + dateToStamp("2019-01-31")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_49.csv").setIndices("wb-art-" + dateToStamp("2019-01-31")),
            /* ********************************************* indices 2019-02-06 ********************************************* */
            //1323781
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_50.csv").setIndices("wb-art-" + dateToStamp("2019-02-06"))
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
