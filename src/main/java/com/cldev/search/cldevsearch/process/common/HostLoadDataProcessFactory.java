package com.cldev.search.cldevsearch.process.common;

import com.cldev.search.cldevsearch.util.ProcessUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
 * Build File @date: 2019/9/23 19:12
 * @version 1.0
 * @description
 */
public class HostLoadDataProcessFactory {

    private static final String REFERENCE_PREFIX = "com.cldev.search.cldevsearch.process.Host";
    private static final String REFERENCE_KOL_PREFIX = "com.cldev.search.cldevsearch.process.kol.Host";
    private static final String REFERENCE_SUFFIX = "LoadProcess";
    private static final String REFERENCE_KOL_SUFFIX = "KolLoadProcess";
    private static final String ADAPTATION_ENVIRONMENT = "Linux";
    private static final String HOST_NUM_CMD = "ifconfig | grep 'inet addr:192.168.2' | awk '{print $2}' | awk -F \".\" '{print $4}'";
    private static final Logger LOGGER = LoggerFactory.getLogger(HostLoadDataProcessFactory.class);

    private HostLoadDataProcessFactory() {
    }

    public static HostLoadDataProcessFactory productHostProcess() {
        return new HostLoadDataProcessFactory();
    }

    public AbstractHostLoadProcess getHostProcess() {
        return hostProcess(REFERENCE_PREFIX + generatorHostNum() + REFERENCE_SUFFIX);
    }

    public AbstractHostLoadProcess getKolHostProcess() {
        return hostProcess(REFERENCE_KOL_PREFIX + generatorHostNum() + REFERENCE_KOL_SUFFIX);
    }

    private AbstractHostLoadProcess hostProcess(String processName) {
        String environmentName = "os.name";
        if (ADAPTATION_ENVIRONMENT.equals(System.getProperties().getProperty(environmentName))) {
            try {
                Class<?> clazz = Class.forName(processName);
                Constructor<?> constructor = clazz.getConstructor();
                return (AbstractHostLoadProcess) constructor.newInstance();
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.error("The Class " + processName + " not fount");
                return null;
            }
        }
        return null;
    }

    private String generatorHostNum() {
        String environmentName = "os.name";
        if (ADAPTATION_ENVIRONMENT.equals(System.getProperties().getProperty(environmentName))) {
            return ProcessUtil.executeCmd(HOST_NUM_CMD);
        }
        return null;
    }

}
