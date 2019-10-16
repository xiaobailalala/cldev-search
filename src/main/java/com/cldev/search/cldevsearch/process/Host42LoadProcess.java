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
 * Build File @date: 2019/9/23 21:58
 * @version 1.0
 * @description
 */
public class Host42LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-03 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_175.csv").setIndices("wb-art-" + dateToStamp("2019-01-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_176.csv").setIndices("wb-art-" + dateToStamp("2019-01-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_177.csv").setIndices("wb-art-" + dateToStamp("2019-01-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_178.csv").setIndices("wb-art-" + dateToStamp("2019-01-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_179.csv").setIndices("wb-art-" + dateToStamp("2019-01-03")),
            /* ********************************************* indices 2019-02-06 ********************************************* */
            //1627993
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_17.csv").setIndices("wb-art-" + dateToStamp("2019-02-06")),
            /* ********************************************* indices 2019-01-03 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_180.csv").setIndices("wb-art-" + dateToStamp("2019-01-03")),
            /* ********************************************* indices 2019-01-04 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_181.csv").setIndices("wb-art-" + dateToStamp("2019-01-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_182.csv").setIndices("wb-art-" + dateToStamp("2019-01-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_183.csv").setIndices("wb-art-" + dateToStamp("2019-01-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_184.csv").setIndices("wb-art-" + dateToStamp("2019-01-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_185.csv").setIndices("wb-art-" + dateToStamp("2019-01-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_186.csv").setIndices("wb-art-" + dateToStamp("2019-01-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_187.csv").setIndices("wb-art-" + dateToStamp("2019-01-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_188.csv").setIndices("wb-art-" + dateToStamp("2019-01-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_189.csv").setIndices("wb-art-" + dateToStamp("2019-01-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_18.csv").setIndices("wb-art-" + dateToStamp("2019-01-04")),
            /* ********************************************* indices 2019-01-05 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_190.csv").setIndices("wb-art-" + dateToStamp("2019-01-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_191.csv").setIndices("wb-art-" + dateToStamp("2019-01-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_192.csv").setIndices("wb-art-" + dateToStamp("2019-01-05"))
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
