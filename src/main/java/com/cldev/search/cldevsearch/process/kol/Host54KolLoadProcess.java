package com.cldev.search.cldevsearch.process.kol;

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
 * Build File @date: 2019/9/30 1:53 下午
 * @version 1.0
 * @description
 */
public class Host54KolLoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog_80w_kol/";

    private List<CsvMapping> csvMappings = Arrays.asList(
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_90.csv").setIndices("wb-art-" + dateToStamp("2016-11-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_91.csv").setIndices("wb-art-" + dateToStamp("2016-11-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_92.csv").setIndices("wb-art-" + dateToStamp("2016-12-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_93.csv").setIndices("wb-art-" + dateToStamp("2016-12-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_94.csv").setIndices("wb-art-" + dateToStamp("2016-12-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_95.csv").setIndices("wb-art-" + dateToStamp("2016-12-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_96.csv").setIndices("wb-art-" + dateToStamp("2016-12-01")),
            //2935381
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_97.csv").setIndices("wb-art-" + dateToStamp("2017-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_98.csv").setIndices("wb-art-" + dateToStamp("2016-12-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_99.csv").setIndices("wb-art-" + dateToStamp("2016-12-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_74.csv").setIndices("wb-art-" + dateToStamp("2016-12-01")),
            //3608495
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_75.csv").setIndices("wb-art-" + dateToStamp("2017-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_87.csv").setIndices("wb-art-" + dateToStamp("2016-12-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_88.csv").setIndices("wb-art-" + dateToStamp("2016-12-01")),
            //90961
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_89.csv").setIndices("wb-art-" + dateToStamp("2017-01-01"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 8),
                csvMappings.subList(8, 15)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }

}
