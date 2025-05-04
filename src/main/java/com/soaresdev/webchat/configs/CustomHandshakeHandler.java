package com.soaresdev.webchat.configs;

import com.soaresdev.webchat.dtos.UserPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import java.net.URI;
import java.security.Principal;
import java.util.Map;

@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        URI uri = request.getURI();
        String query = uri.getQuery();

        String username = "anonymous";
        if (query != null) {
            String[] splitted = query.split("=");
            username = splitted[1];
        }
        return new UserPrincipal(username);
    }
}