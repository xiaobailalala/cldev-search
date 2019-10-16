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
public class Host51LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-18 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_138.csv").setIndices("wb-art-" + dateToStamp("2019-01-18")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_139.csv").setIndices("wb-art-" + dateToStamp("2019-01-18")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_140.csv").setIndices("wb-art-" + dateToStamp("2019-01-18")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_141.csv").setIndices("wb-art-" + dateToStamp("2019-01-18")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_142.csv").setIndices("wb-art-" + dateToStamp("2019-01-18")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_143.csv").setIndices("wb-art-" + dateToStamp("2019-01-18")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_144.csv").setIndices("wb-art-" + dateToStamp("2019-01-18")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_145.csv").setIndices("wb-art-" + dateToStamp("2019-01-18")),
            /* ********************************************* indices 2019-01-19 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_146.csv").setIndices("wb-art-" + dateToStamp("2019-01-19")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_147.csv").setIndices("wb-art-" + dateToStamp("2019-01-19")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_148.csv").setIndices("wb-art-" + dateToStamp("2019-01-19")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_149.csv").setIndices("wb-art-" + dateToStamp("2019-01-19")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_14.csv").setIndices("wb-art-" + dateToStamp("2019-01-19")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_150.csv").setIndices("wb-art-" + dateToStamp("2019-01-19")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_210.csv").setIndices("wb-art-" + dateToStamp("2019-01-19")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_211.csv").setIndices("wb-art-" + dateToStamp("2019-01-19")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_212.csv").setIndices("wb-art-" + dateToStamp("2019-01-19")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_213.csv").setIndices("wb-art-" + dateToStamp("2019-01-19")),
            /* ********************************************* indices 2019-01-20 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_214.csv").setIndices("wb-art-" + dateToStamp("2019-01-20")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_215.csv").setIndices("wb-art-" + dateToStamp("2019-01-20")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_316.csv").setIndices("wb-art-" + dateToStamp("2019-01-20")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_317.csv").setIndices("wb-art-" + dateToStamp("2019-01-20")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_318.csv").setIndices("wb-art-" + dateToStamp("2019-01-20"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 11),
                csvMappings.subList(11, 23)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }
}
