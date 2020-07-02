package com.zetton.thymeleaf.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.DateParser;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ctc.wstx.util.DataUtil;
import com.zetton.thymeleaf.vo.BaseRes;
import com.zetton.thymeleaf.vo.RequestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.util.Date;

@Controller
public class WebsocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebsocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/welcome")
    @SendTo("/topic/say")
    public String say(RequestMessage message) {
        System.out.println(message.getName());
        BaseRes baseRes = new BaseRes<>(200,"success",message.getName());
        JSONObject json = JSONUtil.parseObj(baseRes);
        return json.toStringPretty();
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void callback() {
        // 发现消息
        messagingTemplate.convertAndSend("/topic/callback",
                "定时推送消息时间: " + DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
    }
}
