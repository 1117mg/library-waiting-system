package com.example.librarywatingsystem.controller;

import com.example.librarywatingsystem.model.Message;
import com.example.librarywatingsystem.model.User;
import com.example.librarywatingsystem.service.MessageService;
import com.example.librarywatingsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final UserService userService;
    private final MessageService messageService;

    public ChatController(UserService userService, MessageService messageService, UserService userService1, MessageService messageService1){
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping("/users")
    public User registerUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PostMapping("/messages")
    public Message sendMessage(@RequestBody Message message){
        return messageService.saveMessage(message);
    }

    @GetMapping("/messages/{userId}")
    public List<Message> getMessages(@PathVariable Long userId){
        return messageService.findMessageByUser(userId);
    }
}
