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
 * Build File @date: 2019/9/30 5:29 下午
 * @version 1.0
 * @description
 */
public class Host44KolLoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog_80w_kol/";

    private List<CsvMapping> csvMappings = Arrays.asList(
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_37.csv").setIndices("wb-art-" + dateToStamp("2016-02-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_38.csv").setIndices("wb-art-" + dateToStamp("2016-02-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_39.csv").setIndices("wb-art-" + dateToStamp("2016-02-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_40.csv").setIndices("wb-art-" + dateToStamp("2016-02-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_41.csv").setIndices("wb-art-" + dateToStamp("2016-02-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_42.csv").setIndices("wb-art-" + dateToStamp("2016-02-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_43.csv").setIndices("wb-art-" + dateToStamp("2016-02-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_44.csv").setIndices("wb-art-" + dateToStamp("2016-02-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_45.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_46.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_47.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_48.csv").setIndices("wb-art-" + dateToStamp("2016-03-01")),
            //546131
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_80w_kol_49.csv").setIndices("wb-art-" + dateToStamp("2017-01-01"))
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
