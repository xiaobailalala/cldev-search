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
public class Host50LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-16 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_13.csv").setIndices("wb-art-" + dateToStamp("2019-01-16")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_306.csv").setIndices("wb-art-" + dateToStamp("2019-01-16")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_307.csv").setIndices("wb-art-" + dateToStamp("2019-01-16")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_308.csv").setIndices("wb-art-" + dateToStamp("2019-01-16")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_309.csv").setIndices("wb-art-" + dateToStamp("2019-01-16")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_310.csv").setIndices("wb-art-" + dateToStamp("2019-01-16")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_311.csv").setIndices("wb-art-" + dateToStamp("2019-01-16")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_312.csv").setIndices("wb-art-" + dateToStamp("2019-01-16")),
            /* ********************************************* indices 2019-01-17 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_313.csv").setIndices("wb-art-" + dateToStamp("2019-01-17")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_51.csv").setIndices("wb-art-" + dateToStamp("2019-01-17")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_52.csv").setIndices("wb-art-" + dateToStamp("2019-01-17")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_53.csv").setIndices("wb-art-" + dateToStamp("2019-01-17")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_54.csv").setIndices("wb-art-" + dateToStamp("2019-01-17")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_55.csv").setIndices("wb-art-" + dateToStamp("2019-01-17")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_56.csv").setIndices("wb-art-" + dateToStamp("2019-01-17")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_57.csv").setIndices("wb-art-" + dateToStamp("2019-01-17")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_58.csv").setIndices("wb-art-" + dateToStamp("2019-01-17")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_59.csv").setIndices("wb-art-" + dateToStamp("2019-01-17")),
            /* ********************************************* indices 2019-01-18 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_60.csv").setIndices("wb-art-" + dateToStamp("2019-01-18")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_61.csv").setIndices("wb-art-" + dateToStamp("2019-01-18"))
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
