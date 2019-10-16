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
public class Host52KolLoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog_80w_kol/";

    private List<CsvMapping> csvMappings = Arrays.asList(
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_10.csv").setIndices("wb-art-" + dateToStamp("2016-09-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_11.csv").setIndices("wb-art-" + dateToStamp("2016-09-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_12.csv").setIndices("wb-art-" + dateToStamp("2016-09-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_13.csv").setIndices("wb-art-" + dateToStamp("2016-09-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_14.csv").setIndices("wb-art-" + dateToStamp("2016-09-01")),
            //4767399
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_15.csv").setIndices("wb-art-" + dateToStamp("2017-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_1.csv").setIndices("wb-art-" + dateToStamp("2016-09-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_2.csv").setIndices("wb-art-" + dateToStamp("2016-09-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_31.csv").setIndices("wb-art-" + dateToStamp("2016-09-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_32.csv").setIndices("wb-art-" + dateToStamp("2016-09-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_33.csv").setIndices("wb-art-" + dateToStamp("2016-10-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_34.csv").setIndices("wb-art-" + dateToStamp("2016-10-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_35.csv").setIndices("wb-art-" + dateToStamp("2016-10-01")),
            //648950
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_36.csv").setIndices("wb-art-" + dateToStamp("2017-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_3.csv").setIndices("wb-art-" + dateToStamp("2016-10-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_4.csv").setIndices("wb-art-" + dateToStamp("2016-10-01"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 8),
                csvMappings.subList(8, 16)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }

}
