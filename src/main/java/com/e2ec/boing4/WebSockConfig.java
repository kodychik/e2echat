package com.e2ec.boing4;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.whispersystems.libsignal.IdentityKeyPair;

@Configuration
@EnableWebSocket
public class WebSockConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        IdentityKeyPair identityKeyPair = SignalStores.generateIdentityKeyPair(); // Make sure you have a method to
                                                                                  // fetch/generate this.
        registry.addHandler(new GroupChatWebSocketHandler(identityKeyPair), "/groupChat").setAllowedOrigins("*");
    }
}
