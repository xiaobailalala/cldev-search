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
public class Host53KolLoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog_80w_kol/";

    private List<CsvMapping> csvMappings = Arrays.asList(
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_100.csv").setIndices("wb-art-" + dateToStamp("2016-10-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_101.csv").setIndices("wb-art-" + dateToStamp("2016-10-01")),
            //2763905
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_102.csv").setIndices("wb-art-" + dateToStamp("2017-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_76.csv").setIndices("wb-art-" + dateToStamp("2016-10-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_77.csv").setIndices("wb-art-" + dateToStamp("2016-10-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_78.csv").setIndices("wb-art-" + dateToStamp("2016-10-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_79.csv").setIndices("wb-art-" + dateToStamp("2016-11-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_80.csv").setIndices("wb-art-" + dateToStamp("2016-11-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_81.csv").setIndices("wb-art-" + dateToStamp("2016-11-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_82.csv").setIndices("wb-art-" + dateToStamp("2016-11-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_83.csv").setIndices("wb-art-" + dateToStamp("2016-11-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_84.csv").setIndices("wb-art-" + dateToStamp("2016-11-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_85.csv").setIndices("wb-art-" + dateToStamp("2016-11-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_86.csv").setIndices("wb-art-" + dateToStamp("2016-11-01"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 7),
                csvMappings.subList(7, 14)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }

}
