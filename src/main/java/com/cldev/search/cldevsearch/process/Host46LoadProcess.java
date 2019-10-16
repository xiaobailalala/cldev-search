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
public class Host46LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-09 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_286.csv").setIndices("wb-art-" + dateToStamp("2019-01-09")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_287.csv").setIndices("wb-art-" + dateToStamp("2019-01-09")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_288.csv").setIndices("wb-art-" + dateToStamp("2019-01-09")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_289.csv").setIndices("wb-art-" + dateToStamp("2019-01-09")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_290.csv").setIndices("wb-art-" + dateToStamp("2019-01-09")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_291.csv").setIndices("wb-art-" + dateToStamp("2019-01-09")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_292.csv").setIndices("wb-art-" + dateToStamp("2019-01-09")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_293.csv").setIndices("wb-art-" + dateToStamp("2019-01-09")),
            /* ********************************************* indices 2019-01-10 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_294.csv").setIndices("wb-art-" + dateToStamp("2019-01-10")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_295.csv").setIndices("wb-art-" + dateToStamp("2019-01-10")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_296.csv").setIndices("wb-art-" + dateToStamp("2019-01-10")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_297.csv").setIndices("wb-art-" + dateToStamp("2019-01-10")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_298.csv").setIndices("wb-art-" + dateToStamp("2019-01-10")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_299.csv").setIndices("wb-art-" + dateToStamp("2019-01-10")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_300.csv").setIndices("wb-art-" + dateToStamp("2019-01-10")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_301.csv").setIndices("wb-art-" + dateToStamp("2019-01-10")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_302.csv").setIndices("wb-art-" + dateToStamp("2019-01-10")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_303.csv").setIndices("wb-art-" + dateToStamp("2019-01-10")),
            /* ********************************************* indices 2019-01-11 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_304.csv").setIndices("wb-art-" + dateToStamp("2019-01-11")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_305.csv").setIndices("wb-art-" + dateToStamp("2019-01-11"))
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
