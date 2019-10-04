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
public class Host44LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-05 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_157.csv").setIndices("wb-art-" + dateToStamp("2019-01-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_158.csv").setIndices("wb-art-" + dateToStamp("2019-01-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_159.csv").setIndices("wb-art-" + dateToStamp("2019-01-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_160.csv").setIndices("wb-art-" + dateToStamp("2019-01-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_161.csv").setIndices("wb-art-" + dateToStamp("2019-01-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_162.csv").setIndices("wb-art-" + dateToStamp("2019-01-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_163.csv").setIndices("wb-art-" + dateToStamp("2019-01-05")),
            /* ********************************************* indices 2019-01-06 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_164.csv").setIndices("wb-art-" + dateToStamp("2019-01-06")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_165.csv").setIndices("wb-art-" + dateToStamp("2019-01-06")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_166.csv").setIndices("wb-art-" + dateToStamp("2019-01-06")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_167.csv").setIndices("wb-art-" + dateToStamp("2019-01-06")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_168.csv").setIndices("wb-art-" + dateToStamp("2019-01-06")),
            /* ********************************************* indices 2019-02-06 ********************************************* */
            //3369381
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_169.csv").setIndices("wb-art-" + dateToStamp("2019-02-06")),
            /* ********************************************* indices 2019-01-06 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_22.csv").setIndices("wb-art-" + dateToStamp("2019-01-06")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_23.csv").setIndices("wb-art-" + dateToStamp("2019-01-06")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_24.csv").setIndices("wb-art-" + dateToStamp("2019-01-06")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_25.csv").setIndices("wb-art-" + dateToStamp("2019-01-06")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_261.csv").setIndices("wb-art-" + dateToStamp("2019-01-06")),
            /* ********************************************* indices 2019-01-07 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_262.csv").setIndices("wb-art-" + dateToStamp("2019-01-07")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_263.csv").setIndices("wb-art-" + dateToStamp("2019-01-07"))
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
