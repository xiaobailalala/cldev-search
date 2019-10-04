package com.cldev.search.cldevsearch.service.impl;

import com.cldev.search.cldevsearch.common.AbstractHostLoadProcess;
import com.cldev.search.cldevsearch.common.HostLoadDataProcessFactory;
import com.cldev.search.cldevsearch.service.EsToolsService;
import org.springframework.stereotype.Service;

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
 * Build File @date: 2019/9/23 17:40
 * @version 1.0
 * @description
 */
@Service
public class EsToolsServiceImpl implements EsToolsService {

    private AbstractHostLoadProcess hostLoadProcess = HostLoadDataProcessFactory.productHostProcess().getHostProcess();
    private AbstractHostLoadProcess hostLoadProcessForKol = HostLoadDataProcessFactory.productHostProcess().getKolHostProcess();

    @Override
    public String loadData() {
        hostLoadProcess.loadData();
        return "start";
    }

    @Override
    public List<String> checkFile() {
        return hostLoadProcess.checkFile();
    }

    @Override
    public String checkFileCount() {
        return hostLoadProcess.checkFileCount();
    }

    @Override
    public String loadDataForKol() {
        hostLoadProcessForKol.loadData();
        return "start";
    }

}
