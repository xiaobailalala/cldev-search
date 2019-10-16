package com.cldev.search.cldevsearch.process.common;

import org.junit.Test;

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
 * Build File @date: 2019/10/14 15:23
 * @version 1.0
 * @description
 */
public class AddressTest {

    @Test
    public void pauseAddress() {
        String address = "海外-400 美国-1,英国-2,法国-3,俄罗斯-4,加拿大-5,巴西-6,澳大利亚-7,印尼-8,泰国-9,马来西亚-10,新加坡-11,菲律宾-12,越南-13,印度-14,日本-15,新西兰-17,韩国-18,德国-19,意大利-20,爱尔兰-21,荷兰-22,瑞士-23,乌克兰-24,南非-25,芬兰-26,瑞典-27,奥地利-28,西班牙-29,比利时-30,挪威-31,丹麦-32,波兰-33,阿根廷-34,白俄罗斯-35,哥伦比亚-36,古巴-37,埃及-38,希腊-39,匈牙利-40,伊朗-41,蒙古-42,墨西哥-43,葡萄牙-44,沙特阿拉伯-45,土耳其-46,其他-16";
        String province = address.split(" ")[0].split("-")[0];
        String provinceValue = address.split(" ")[0].split("-")[1];
        String result = "{\"label\":\"" + province + "\",\"value\":\"" + provinceValue + "\",\"children\":[";
        StringBuilder cityStr = new StringBuilder();
        for (String item : address.split(" ")[1].split(",")) {
            cityStr.append("{\"label\":\"")
                    .append(item.split("-")[0])
                    .append("\",\"value\":\"")
                    .append(generatorCity(item.split("-")[1]))
                    .append("\"},");
        }
        System.out.println(result + cityStr.substring(0, cityStr.length() - 1) + "]}");
    }

    private String generatorCity(String city) {
        String[] supple = {"", "0", "00", "000"};
        return supple[4 - city.length()] + city;
    }


}
