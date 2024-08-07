package com.tutorial.backend.handler;
import com.tutorial.backend.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    private final TokenProvider tokenProvider;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        log.info("들어옴");
        String token = request.getURI().getQuery().split("token=")[1];
        return tokenProvider.getAuthentication(token);
    }
}
