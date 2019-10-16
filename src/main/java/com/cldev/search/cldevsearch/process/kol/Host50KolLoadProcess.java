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
public class Host50KolLoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog_80w_kol/";

    private List<CsvMapping> csvMappings = Arrays.asList(
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_63.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_64.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_65.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_66.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_67.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_68.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_69.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_70.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_71.csv").setIndices("wb-art-" + dateToStamp("2016-06-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_72.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_5.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_6.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_7.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_8.csv").setIndices("wb-art-" + dateToStamp("2016-07-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_9.csv").setIndices("wb-art-" + dateToStamp("2016-07-01"))
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
