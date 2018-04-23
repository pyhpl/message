package com.ljl.look.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageCenterController {

    @Autowired
    private SimpUserRegistry userRegistry;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 订阅私人信道
     */
    @SubscribeMapping("/message")
    public void subscribe(Principal principal) {
        System.out.println("hello");
        Map headers = new HashMap() {{
            put("type", "message"); // 设置消息类型
            put("from", principal.getName()); // 设置消息来源
            put("time", new Date()); // 设置接收时间
        }};
        messagingTemplate.convertAndSendToUser(principal.getName(), "/message", "hello", headers);
    }

    @GetMapping("/hello")
    public void hello(@RequestParam String principal) {
        Map headers = new HashMap() {{
            put("type", "message"); // 设置消息类型
            put("from", principal); // 设置消息来源
            put("time", new Date()); // 设置接收时间
        }};
        messagingTemplate.convertAndSendToUser(principal, "/message", "hello", headers);
    }

}
