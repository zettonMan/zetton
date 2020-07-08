package com.zetton.thymeleaf.listener;

import com.zetton.thymeleaf.event.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailListener implements ApplicationListener<OrderEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Async
    public void onApplicationEvent(OrderEvent event) {
        System.out.println(Thread.currentThread() + "...邮件监听到..." + event.getMessage()+ "......" + event.getSource());
    }
}
