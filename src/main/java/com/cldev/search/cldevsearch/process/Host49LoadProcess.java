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
public class Host49LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6_1/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-01-15 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_12.csv").setIndices("wb-art-" + dateToStamp("2019-01-15")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_253.csv").setIndices("wb-art-" + dateToStamp("2019-01-15")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_254.csv").setIndices("wb-art-" + dateToStamp("2019-01-15")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255.csv").setIndices("wb-art-" + dateToStamp("2019-01-15")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_256.csv").setIndices("wb-art-" + dateToStamp("2019-01-15")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257.csv").setIndices("wb-art-" + dateToStamp("2019-01-15")),
            /* ********************************************* indices 2019-01-16 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258.csv").setIndices("wb-art-" + dateToStamp("2019-01-16")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260.csv").setIndices("wb-art-" + dateToStamp("2019-01-16"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 4),
                csvMappings.subList(4, 8)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }
}
