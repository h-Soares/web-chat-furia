package com.soaresdev.webchat.controllers;

import com.soaresdev.webchat.dtos.Message;
import com.soaresdev.webchat.dtos.SendPrivateMessage;
import com.soaresdev.webchat.services.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class MessagesController {
    private final MessageService messageService;

    public MessagesController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/public")
    public void handlePublicMessage(@Payload Message message) {
        messageService.sendPublicMessage(message);
    }

    @MessageMapping("/private")
    public void handlePrivateMessage(@Payload SendPrivateMessage privateMessage) {
        messageService.sendPrivateMessage(privateMessage);
    }
}