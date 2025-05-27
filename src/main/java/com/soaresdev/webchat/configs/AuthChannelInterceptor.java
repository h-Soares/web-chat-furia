package com.soaresdev.webchat.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthChannelInterceptor.class);
    private static final Map<String, String> SESSIONID_USERNAME = new ConcurrentHashMap<>();

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String username = accessor.getFirstNativeHeader("username");
            logger.info("User {} trying to connect with session ID {}", username, sessionId);

            if (username != null) {
                synchronized (SESSIONID_USERNAME) {
                    if (SESSIONID_USERNAME.containsValue(username)) {
                        logger.warn("Username {} is already in use. Rejecting connection.", username);
                        throw new MessagingException("Username " + username + " already in use");
                    }
                }
                SESSIONID_USERNAME.put(sessionId, username);
                logger.info("User {} successfully connected with session ID {}", username, sessionId);
            }
        }

        if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            String removedUsername = SESSIONID_USERNAME.remove(sessionId);
            if(removedUsername != null)
                logger.info("User {} successfully disconnected with session ID {}", removedUsername, sessionId);
        }

        if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
            logger.info("User {} subscribed to destination {}", SESSIONID_USERNAME.get(sessionId), accessor.getDestination());
        }

        if(StompCommand.SEND.equals(accessor.getCommand())){
            logger.info("User {} sent message to destination {} with message {}", SESSIONID_USERNAME.get(sessionId), accessor.getDestination(), new String((byte[]) message.getPayload(), StandardCharsets.UTF_8));
        }

        return message;
    }
}