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
public class Host61LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-27 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_151.csv").setIndices("wb-art-" + dateToStamp("2019-01-27")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_152.csv").setIndices("wb-art-" + dateToStamp("2019-01-27")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_153.csv").setIndices("wb-art-" + dateToStamp("2019-01-27")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_154.csv").setIndices("wb-art-" + dateToStamp("2019-01-27")),
            /* ********************************************* indices 2019-01-28 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_155.csv").setIndices("wb-art-" + dateToStamp("2019-01-28")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_156.csv").setIndices("wb-art-" + dateToStamp("2019-01-28")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_5.csv").setIndices("wb-art-" + dateToStamp("2019-01-28")),
            //5931421
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_6.csv").setIndices("wb-art-" + dateToStamp("2019-01-28")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_7.csv").setIndices("wb-art-" + dateToStamp("2019-01-28")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_86.csv").setIndices("wb-art-" + dateToStamp("2019-01-28")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_87.csv").setIndices("wb-art-" + dateToStamp("2019-01-28")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_88.csv").setIndices("wb-art-" + dateToStamp("2019-01-28")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_89.csv").setIndices("wb-art-" + dateToStamp("2019-01-28")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_8.csv").setIndices("wb-art-" + dateToStamp("2019-01-28")),
            /* ********************************************* indices 2019-01-29 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_90.csv").setIndices("wb-art-" + dateToStamp("2019-01-29")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_91.csv").setIndices("wb-art-" + dateToStamp("2019-01-29")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_92.csv").setIndices("wb-art-" + dateToStamp("2019-01-29")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_93.csv").setIndices("wb-art-" + dateToStamp("2019-01-29")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_94.csv").setIndices("wb-art-" + dateToStamp("2019-01-29")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_95.csv").setIndices("wb-art-" + dateToStamp("2019-01-29")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_96.csv").setIndices("wb-art-" + dateToStamp("2019-01-29")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_97.csv").setIndices("wb-art-" + dateToStamp("2019-01-29")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_98.csv").setIndices("wb-art-" + dateToStamp("2019-01-29")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_99.csv").setIndices("wb-art-" + dateToStamp("2019-01-29")),
            /* ********************************************* indices 2019-01-30 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_9.csv").setIndices("wb-art-" + dateToStamp("2019-01-30"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 13),
                csvMappings.subList(13, 25)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }
}
