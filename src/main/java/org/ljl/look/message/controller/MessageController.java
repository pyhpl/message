package org.ljl.look.message.controller;

import org.ljl.look.message.entity.Message;
import org.ljl.look.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/message")
public class MessageController {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MessageService messageService;

    @SubscribeMapping("/message-count")
    @SendToUser("/message-count")
    public int messageCountSubscribe(Principal principal) {
        return messageService.countUnRead(
                stringRedisTemplate.opsForValue().get(principal.getName())
        );
    }

    @SubscribeMapping("/message")
    @SendToUser("/message")
    public List<Message> messageSubscribe(Principal principal) {
        return messageService.get(
                stringRedisTemplate.opsForValue().get(principal.getName())
        );
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void put(@RequestBody Message message) {
        messageService.put(message);
    }

}
