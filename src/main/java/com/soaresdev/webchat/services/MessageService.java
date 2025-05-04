package com.soaresdev.webchat.services;

import com.soaresdev.webchat.dtos.Message;
import com.soaresdev.webchat.dtos.SendPrivateMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendPublicMessage(Message message) {
        messagingTemplate.convertAndSend("/topic/public", message);
    }

    public void sendPrivateMessage(SendPrivateMessage privateMessage) {
        Message messageSent = new Message(privateMessage.from() + " enviou para você", privateMessage.content());
        Message messageReceived = new Message("Você enviou para " + privateMessage.to(), privateMessage.content());
        messagingTemplate.convertAndSendToUser(privateMessage.to(),"/queue/private", messageSent);
        messagingTemplate.convertAndSendToUser(privateMessage.from(),"/queue/private", messageReceived);
    }
}
