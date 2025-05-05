package com.soaresdev.webchat.services;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {
    private final OllamaChatModel ollamaChatModel;
    private static final String STANDARD_BEHAVIOR = "Você é o bot oficial do web chat online da equipe brasileira de Counter-Strike FURIA. Considerando isso, responda o seguinte: ";

    public OllamaService(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }

    public String sendMessage(String message) {
        String messageToSend = STANDARD_BEHAVIOR + message;
        return ollamaChatModel.call(messageToSend);
    }
}