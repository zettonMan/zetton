package com.zetton.thymeleaf.asyn;

import cn.hutool.core.date.DateUtil;
import com.zetton.thymeleaf.async.AsyncTask;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsynTests {
    private static final Logger log = LoggerFactory.getLogger(AsynTests.class);
    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void testAsync() throws InterruptedException, ExecutionException {
        asyncTask.dealNoReturnTask();
        System.out.println("thread1 start " + DateUtil.date());
        Future<String> f1 = asyncTask.dealHaveReturnTask(5);
        log.info("主线程1执行finished");
        System.out.println("thread2 start " + DateUtil.date());
        Future<String> f2 = asyncTask.dealHaveReturnTask(5);
        log.info("主线程2执行finished");
        System.out.println("thread processing " + DateUtil.date());
        log.info(f1.get());
        log.info(f2.get());
        System.out.println("thread end " + DateUtil.date());
        Assert.assertEquals(f1.get(), "success:" + 5);
        Assert.assertEquals(f2.get(), "success:" + 5);

    }
}
