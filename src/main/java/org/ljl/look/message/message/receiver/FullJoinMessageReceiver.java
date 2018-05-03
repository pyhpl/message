package org.ljl.look.message.message.receiver;

import com.fasterxml.jackson.core.type.TypeReference;
import org.ljl.look.message.configuration.ConstConfig;
import org.ljl.look.message.entity.Message;
import org.ljl.look.message.message.wrapper.FullJoinWrapper;
import org.ljl.look.message.message.wrapper.MessageWrapper;
import org.ljl.look.message.service.MessageService;
import org.ljl.look.message.util.JsonTool;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = ConstConfig.QUEUE_JOIN_MESSAGE)
public class FullJoinMessageReceiver {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitHandler
    public void process(String fullJoinWrapperJson) {
        MessageWrapper<FullJoinWrapper> topicMessageWrapper = JsonTool.fromJson(fullJoinWrapperJson, new TypeReference<MessageWrapper<FullJoinWrapper>>() {});
        FullJoinWrapper fullJoinWrapper = topicMessageWrapper.getBody();
        switch (topicMessageWrapper.getMethod()) {
            case POST:
                String title = fullJoinWrapper.getName() + "加入" + fullJoinWrapper.getActivityTitle();
                Message message = Message.builder()
                        .fromUser(fullJoinWrapper.getOpenId())
                        .toUser(fullJoinWrapper.getActivityPublishUser())
                        .title(title)
                        .content(fullJoinWrapper.getActivityUuid())
                        .type(ConstConfig.JOIN_MESSAGE).build();
                messageService.add(message);
                String toUser = stringRedisTemplate.opsForValue().get(message.getToUser());
                if (toUser != null) {
                    message.setTitle(fullJoinWrapper.getAvatar() + "////" + message.getTitle());
                    messagingTemplate.convertAndSendToUser(toUser, "/message-count", -1);
                    messagingTemplate.convertAndSendToUser(toUser, "/message", message);
                }
                break;
        }
    }

}
