package com.cldev.search.cldevsearch.thread;

import com.cldev.search.cldevsearch.util.CyclicBarrierUtil;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

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
 * Build File @date: 2020/1/9 16:10
 * @version 1.0
 * @description
 */
public class CyclicBarrierUtilTest implements Runnable {

    private CyclicBarrierUtil cyclicBarrier = CyclicBarrierUtil.buildBarrier(this,
            "thread-call-runner-%d", 10, 10, 0L);

    @Test
    public void test() throws InterruptedException, BrokenBarrierException {
        CyclicBarrierUtilTest test = new CyclicBarrierUtilTest();
        CountDownLatch countDownLatch = new CountDownLatch(3);

    }

    @Override
    public void run() {
        System.out.println("execute finish");
    }
}
