package com.example.librarywatingsystem.service;

import com.example.librarywatingsystem.model.Message;
import com.example.librarywatingsystem.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository=messageRepository;
    }

    public Message saveMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> findMessageByUser(Long userId){
        return messageRepository.findByUserId(userId);
    }
}
