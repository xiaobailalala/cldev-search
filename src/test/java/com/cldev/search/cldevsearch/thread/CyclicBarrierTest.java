package com.cldev.search.cldevsearch.thread;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

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
 * Build File @date: 2019/9/28 15:54
 * @version 1.0
 * @description
 */
public class CyclicBarrierTest implements Runnable {


    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private CyclicBarrier cyclicBarrier = new CyclicBarrier(3, this);

    private ConcurrentLinkedQueue<Integer> result = new ConcurrentLinkedQueue<>();

    private List<Integer> res = new LinkedList<>();

    private void resolver() {
        threadPool.execute(() -> {
            result.addAll(Arrays.asList(1, 9, 4, 2));
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        threadPool.execute(() -> {
            result.addAll(Arrays.asList(3, 7, 5, 8));
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void run() {
        this.res.addAll(this.result);
        this.res.sort(Comparator.comparing(item -> item));
    }

    @Test
    public void test() throws BrokenBarrierException, InterruptedException {
        CyclicBarrierTest cyclicBarrierTest = new CyclicBarrierTest();
        cyclicBarrierTest.resolver();
        cyclicBarrierTest.cyclicBarrier.await();
        for (Integer re : cyclicBarrierTest.res) {
            System.out.println(re);
        }
    }

}
