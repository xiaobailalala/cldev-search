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
 * Build File @date: 2019/9/23 17:49
 * @version 1.0
 * @description
 */
public class Host29LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6/uid_mid_blog/";

    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-01 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_10.csv").setIndices("wb-art-" + dateToStamp("2019-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_193.csv").setIndices("wb-art-" + dateToStamp("2019-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_194.csv").setIndices("wb-art-" + dateToStamp("2019-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_195.csv").setIndices("wb-art-" + dateToStamp("2019-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_196.csv").setIndices("wb-art-" + dateToStamp("2019-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_197.csv").setIndices("wb-art-" + dateToStamp("2019-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_198.csv").setIndices("wb-art-" + dateToStamp("2019-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_199.csv").setIndices("wb-art-" + dateToStamp("2019-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_19.csv").setIndices("wb-art-" + dateToStamp("2019-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_1.csv").setIndices("wb-art-" + dateToStamp("2019-01-01")),
            /* ********************************************* indices 2019-01-02 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_200.csv").setIndices("wb-art-" + dateToStamp("2019-01-02")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_201.csv").setIndices("wb-art-" + dateToStamp("2019-01-02")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_202.csv").setIndices("wb-art-" + dateToStamp("2019-01-02")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_203.csv").setIndices("wb-art-" + dateToStamp("2019-01-02")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_204.csv").setIndices("wb-art-" + dateToStamp("2019-01-02")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_205.csv").setIndices("wb-art-" + dateToStamp("2019-01-02")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_206.csv").setIndices("wb-art-" + dateToStamp("2019-01-02")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_207.csv").setIndices("wb-art-" + dateToStamp("2019-01-02")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_208.csv").setIndices("wb-art-" + dateToStamp("2019-01-02")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_209.csv").setIndices("wb-art-" + dateToStamp("2019-01-02")),
            /* ********************************************* indices 2019-01-03 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_253.csv").setIndices("wb-art-" + dateToStamp("2019-01-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_259.csv").setIndices("wb-art-" + dateToStamp("2019-01-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_314.csv").setIndices("wb-art-" + dateToStamp("2019-01-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_315.csv").setIndices("wb-art-" + dateToStamp("2019-01-03"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 12),
                csvMappings.subList(12, 24)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }

}
