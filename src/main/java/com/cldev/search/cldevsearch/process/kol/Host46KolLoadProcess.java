package com.cldev.search.cldevsearch.process.kol;

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
 * Build File @date: 2019/9/30 5:40 下午
 * @version 1.0
 * @description
 */
public class Host46KolLoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog_80w_kol/";

    private List<CsvMapping> csvMappings = Arrays.asList(
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_50.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_51.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_52.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_53.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_54.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_55.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_56.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //2656010
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_57.csv").setIndices("wb-art-" + dateToStamp("2017-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_58.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_59.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_60.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_61.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //1132392
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_62.csv").setIndices("wb-art-" + dateToStamp("2017-01-01"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 7),
                csvMappings.subList(7, 13)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }

}
