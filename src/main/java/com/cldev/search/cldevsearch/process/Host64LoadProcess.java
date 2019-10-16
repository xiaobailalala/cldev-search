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
 * Build File @date: 2019/9/23 22:00
 * @version 1.0
 * @description
 */
public class Host64LoadProcess extends AbstractHostLoadProcess {

    private String dataFolder = "/data6/uid_mid_blog/";
    private List<CsvMapping> csvMappings = Arrays.asList(
            /* ********************************************* indices 2019-02-03 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_106.csv").setIndices("wb-art-" + dateToStamp("2019-02-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_107.csv").setIndices("wb-art-" + dateToStamp("2019-02-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_108.csv").setIndices("wb-art-" + dateToStamp("2019-02-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_109.csv").setIndices("wb-art-" + dateToStamp("2019-02-03")),
            /* ********************************************* indices 2019-02-05 ********************************************* */
            //4787249
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_110.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            /* ********************************************* indices 2019-02-03 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_111.csv").setIndices("wb-art-" + dateToStamp("2019-02-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_112.csv").setIndices("wb-art-" + dateToStamp("2019-02-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_113.csv").setIndices("wb-art-" + dateToStamp("2019-02-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_114.csv").setIndices("wb-art-" + dateToStamp("2019-02-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_115.csv").setIndices("wb-art-" + dateToStamp("2019-02-03")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_116.csv").setIndices("wb-art-" + dateToStamp("2019-02-03")),
            /* ********************************************* indices 2019-02-04 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_117.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255_0.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255_1.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255_2.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255_3.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255_4.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255_5.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255_6.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255_7.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255_8.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_255_9.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257_0.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257_1.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257_2.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257_3.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257_4.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257_5.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257_6.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257_7.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257_8.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_257_9.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258_0.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258_1.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258_2.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258_3.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258_4.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258_5.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258_6.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258_7.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258_8.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_258_9.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260_0.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260_1.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260_2.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260_3.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260_4.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260_5.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260_6.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260_7.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260_8.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //600000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_260_9.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_284.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_285.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_319.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_355.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_356.csv").setIndices("wb-art-" + dateToStamp("2019-02-04")),
            /* ********************************************* indices 2019-02-05 ********************************************* */
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_357.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_358.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_359.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_360.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_361.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_362.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            //6000000
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_363.csv").setIndices("wb-art-" + dateToStamp("2019-02-05")),
            /* ********************************************* indices 2019-02-05 ********************************************* */
            //3699333
            new CsvMapping(dataFolder).setFileName("uid_mid_blog_364.csv").setIndices("wb-art-" + dateToStamp("2019-02-05"))
    );

    @Override
    public List<String> checkFile() {
        return super.checkFile(csvMappings);
    }

    @Override
    public void loadData() {
        super.loadData(Arrays.asList(
                csvMappings.subList(0, 32),
                csvMappings.subList(32, 65)
        ));
    }

    @Override
    public String checkFileCount() {
        return super.checkFileCount(dataFolder, csvMappings.size());
    }

}
