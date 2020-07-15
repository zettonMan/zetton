package com.zetton.thymeleaf.service.impl;

import com.zetton.thymeleaf.service.SenderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SenderServiceTest {
    @Autowired
    private SenderService senderService;

    @Test
    public void testCache() {
        // 测试广播模式
        senderService.broadcast("同学们集合啦！");
        // 测试Direct模式
        senderService.direct("定点消息");
    }
}