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
 * Build File @date: 2019/9/23 21:59
 * @version 1.0
 * @description
 */
public class Host53LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-02-05 ********************************************* */
            //2377093
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_335.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            /* ********************************************* indices 2019-01-22 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_336.csv").setIndices("wb-art-" + dateToStamp("2019-01-22")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_337.csv").setIndices("wb-art-" + dateToStamp("2019-01-22")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_338.csv").setIndices("wb-art-" + dateToStamp("2019-01-22")),
            /* ********************************************* indices 2019-02-06 ********************************************* */
            //4676093
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_339.csv").setIndices("wb-art-" + dateToStamp("2019-02-06")),
            /* ********************************************* indices 2019-01-22 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_340.csv").setIndices("wb-art-" + dateToStamp("2019-01-22")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_341.csv").setIndices("wb-art-" + dateToStamp("2019-01-22")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_342.csv").setIndices("wb-art-" + dateToStamp("2019-01-22")),
            /* ********************************************* indices 2019-02-05 ********************************************* */
            //187174
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_343.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            /* ********************************************* indices 2019-01-22 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_344.csv").setIndices("wb-art-" + dateToStamp("2019-01-22")),
            /* ********************************************* indices 2019-01-23 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_345.csv").setIndices("wb-art-" + dateToStamp("2019-01-23")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_346.csv").setIndices("wb-art-" + dateToStamp("2019-01-23")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_347.csv").setIndices("wb-art-" + dateToStamp("2019-01-23")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_348.csv").setIndices("wb-art-" + dateToStamp("2019-01-23")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_349.csv").setIndices("wb-art-" + dateToStamp("2019-01-23")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_350.csv").setIndices("wb-art-" + dateToStamp("2019-01-23")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_351.csv").setIndices("wb-art-" + dateToStamp("2019-01-23")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_352.csv").setIndices("wb-art-" + dateToStamp("2019-01-23")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_353.csv").setIndices("wb-art-" + dateToStamp("2019-01-23")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_354.csv").setIndices("wb-art-" + dateToStamp("2019-01-23"))
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
