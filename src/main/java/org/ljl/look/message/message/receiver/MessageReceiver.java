package org.ljl.look.message.message.receiver;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ljl.look.message.configuration.ConstConfig;
import org.ljl.look.message.entity.Message;
import org.ljl.look.message.message.wrapper.MessageWrapper;
import org.ljl.look.message.service.MessageService;
import org.ljl.look.message.util.JsonTool;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = ConstConfig.QUEUE_MESSAGE)
public class MessageReceiver {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitHandler
    public void process(String messageJson) {
        MessageWrapper<Message> topicMessageWrapper = JsonTool.fromJson(messageJson, new TypeReference<MessageWrapper<Message>>() {});
        Message message = topicMessageWrapper.getBody();
        switch (topicMessageWrapper.getMethod()) {
            case POST:
                messagingTemplate.convertAndSendToUser(message.getToUser(), "/message-count", -1);
                messagingTemplate.convertAndSendToUser(message.getToUser(), "/message", message);
                // 将token转换为openid
                message.setToUser(stringRedisTemplate.opsForValue().get(message.getToUser()));
                messageService.add(message);
                break;
        }
    }

}
