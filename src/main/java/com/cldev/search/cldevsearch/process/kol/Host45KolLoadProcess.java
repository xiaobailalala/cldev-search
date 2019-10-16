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
 * Build File @date: 2019/9/30 5:36 下午
 * @version 1.0
 * @description
 */
public class Host45KolLoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog_80w_kol/";

    private List<CsvMapping> csvMappings = Arrays.asList(
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_25.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_26.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_27.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_28.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_29.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //3908624
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_30.csv").setIndices("wb-art-" + dateToStamp("2017-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_60.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_61.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //1132392
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_62.csv").setIndices("wb-art-" + dateToStamp("2017-01-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_73.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_123.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_124.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_125.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_126.csv").setIndices("wb-art-" + dateToStamp("2016-04-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_127.csv").setIndices("wb-art-" + dateToStamp("2016-04-01"))
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
