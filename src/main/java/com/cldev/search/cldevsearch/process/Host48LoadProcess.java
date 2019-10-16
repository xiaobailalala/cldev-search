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
public class Host48LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-13 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_11.csv").setIndices("wb-art-" + dateToStamp("2019-01-13")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_264.csv").setIndices("wb-art-" + dateToStamp("2019-01-13")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_265.csv").setIndices("wb-art-" + dateToStamp("2019-01-13")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_266.csv").setIndices("wb-art-" + dateToStamp("2019-01-13")),
            /* ********************************************* indices 2019-01-14 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_267.csv").setIndices("wb-art-" + dateToStamp("2019-01-14")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_268.csv").setIndices("wb-art-" + dateToStamp("2019-01-14")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_269.csv").setIndices("wb-art-" + dateToStamp("2019-01-14")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_26.csv").setIndices("wb-art-" + dateToStamp("2019-01-14")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_270.csv").setIndices("wb-art-" + dateToStamp("2019-01-14")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_271.csv").setIndices("wb-art-" + dateToStamp("2019-01-14")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_272.csv").setIndices("wb-art-" + dateToStamp("2019-01-14")),
            /* ********************************************* indices 2019-02-06 ********************************************* */
            //3374924
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_273.csv").setIndices("wb-art-" + dateToStamp("2019-02-06")),
            /* ********************************************* indices 2019-01-14 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_27.csv").setIndices("wb-art-" + dateToStamp("2019-01-14")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_28.csv").setIndices("wb-art-" + dateToStamp("2019-01-14")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_29.csv").setIndices("wb-art-" + dateToStamp("2019-01-14")),
            /* ********************************************* indices 2019-02-06 ********************************************* */
            //1279058
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_30.csv").setIndices("wb-art-" + dateToStamp("2019-02-06")),
            /* ********************************************* indices 2019-01-15 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_82.csv").setIndices("wb-art-" + dateToStamp("2019-01-15")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_83.csv").setIndices("wb-art-" + dateToStamp("2019-01-15")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_84.csv").setIndices("wb-art-" + dateToStamp("2019-01-15")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_85.csv").setIndices("wb-art-" + dateToStamp("2019-01-15"))
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
