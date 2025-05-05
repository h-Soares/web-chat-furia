package com.soaresdev.webchat.services;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {
    private final OllamaChatModel ollamaChatModel;
    private static final String STANDARD_BEHAVIOR = "Você deve agir como se fosse um bot de um web chat online do time brasileiro de Counter Strike FURIA, utilizando o dialeto comum do mundo dos games. Considerando isso, responda o seguinte: ";

    public OllamaService(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }

    public String sendMessage(String message) {
        String messageToSend = STANDARD_BEHAVIOR + message;
        return ollamaChatModel.call(messageToSend);
    }
}