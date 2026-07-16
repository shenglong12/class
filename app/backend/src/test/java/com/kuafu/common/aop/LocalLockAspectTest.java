package com.kuafu.common.aop;

import com.kuafu.common.annotation.DistributedLock;
import com.kuafu.common.schedule.test.TimeSchedule;
import com.kuafu.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class LocalLockAspectTest {


    @Resource
    private TimeSchedule timeSchedule;


    @Test
    public void test() throws Throwable {




        // 1. 创建一个固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(5);

//        try {
//            // 2. 创建任务列表
//            List<Callable<Integer>> tasks = new ArrayList<>();
//            for (int i = 1; i <= 100; i++) {
//                int taskId = i;
//                tasks.add(() -> {
//                    return timeSchedule.test1(1) * 10;
//                });
//            }
//
//            final List<Future<Integer>> futures = executor.invokeAll(tasks);
//
//            // 4. 处理每个任务的结果
//            for (Future<Integer> future : futures) {
//                System.out.println("获取到结果: " + future.get());
//            }
//
//        }finally {
//            executor.shutdown();
//        }






    }




}