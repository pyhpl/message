package org.ljl.look.message.service;

import org.ljl.look.message.entity.Message;
import org.ljl.look.message.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public void add(Message message) {
        messageMapper.insert(message);
    }

    public List<Message> get(String toUser) {
        return messageMapper.selectByToUserAndIsRead(toUser, null);
    }

    public int countUnRead(String toUser) {
        return messageMapper.selectCountByToUserAndIsRead(toUser, false);
    }

}
