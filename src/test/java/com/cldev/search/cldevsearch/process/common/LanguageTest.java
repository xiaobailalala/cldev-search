package com.cldev.search.cldevsearch.process.common;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.dictionary.py.PinyinDictionary;
import com.hankcs.hanlp.dictionary.py.PinyinUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
 * Build File @date: 2019/10/10 14:52
 * @version 1.0
 * @description
 */
public class LanguageTest {

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        String[] text = {"用笔记本电脑写程序afgg s", "架飛機", "望各位去污粉棋逢對手", "廢棄物方全給人網關", "富商大賈我過生日", "剛發生的該任務光污染", "訪問各位"};
        for (String s : text) {
            System.out.println(HanLP.convertToSimplifiedChinese(s));
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void pinyin() {
        String text = "xatx";
        List<Pinyin> pinyin = PinyinDictionary.convertToPinyin(text);
        Character[] characters = pinyin.stream().map(Pinyin::getFirstChar).toArray(Character[]::new);
        StringBuilder str = new StringBuilder();
        for (Character character : characters) {
            str.append(character.toString());
        }
        System.out.println(str);
        for (Pinyin item : pinyin) {
            System.out.println(item.getFirstChar());
        }
    }

}
