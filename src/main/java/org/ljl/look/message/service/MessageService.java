package org.ljl.look.message.service;

import org.ljl.look.message.entity.Message;
import org.ljl.look.message.feign.UserServiceFeign;
import org.ljl.look.message.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserServiceFeign userServiceFeign;

    public void add(Message message) {
        messageMapper.insert(message);
    }

    public List<Message> get(String toUser) {
        List<Message> messages = messageMapper.selectByToUserAndIsRead(toUser, null);
        return messages.stream()
                .peek(message ->
                    message.setTitle(
                            userServiceFeign.getUser(message.getFromUser()).getAvatar() + "////" + message.getTitle()
                    )
                ).collect(Collectors.toList());
    }

    public int countUnRead(String toUser) {
        return messageMapper.selectCountByToUserAndIsRead(toUser, false);
    }

    public void put(Message message) {
        messageMapper.update(message);
    }
}
