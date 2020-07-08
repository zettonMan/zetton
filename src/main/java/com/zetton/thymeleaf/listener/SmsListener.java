package com.zetton.thymeleaf.listener;

import com.zetton.thymeleaf.event.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SmsListener implements ApplicationListener<OrderEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Async  //异步
    public void onApplicationEvent(OrderEvent event) {
        System.out.println(Thread.currentThread() + "...短信监听到..." + event.getMessage()+ "......" + event.getSource());
    }
}