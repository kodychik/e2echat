package com.e2ec.boing4;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.SessionBuilder;
import org.whispersystems.libsignal.SessionCipher;
import org.whispersystems.libsignal.state.SessionStore;
import org.whispersystems.libsignal.state.PreKeyStore;
import org.whispersystems.libsignal.state.SignedPreKeyStore;
import org.whispersystems.libsignal.state.IdentityKeyStore;
import org.whispersystems.libsignal.protocol.CiphertextMessage;
import org.whispersystems.libsignal.protocol.PreKeySignalMessage;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.util.HashSet;

public class GroupChatWebSocketHandler extends TextWebSocketHandler {
    private final ConcurrentHashMap<String, Set<WebSocketSession>> groupSessions = new ConcurrentHashMap<>();
    private final SessionStore sessionStore = SignalStores.getSessionStore();
    private final PreKeyStore preKeyStore = SignalStores.getPreKeyStore();
    private final SignedPreKeyStore signedPreKeyStore = SignalStores.getSignedPreKeyStore();
    private final IdentityKeyStore identityKeyStore;

    public GroupChatWebSocketHandler(IdentityKeyPair identityKeyPair) {
        this.identityKeyStore = SignalStores.getIdentityKeyStore(identityKeyPair);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = decryptMessage(message.getPayload(), session);
        String[] parts = payload.split(":", 2); // "groupCode:messageContent" format
        String groupCode = parts[0];
        String messageContent = parts[1];

        groupSessions.putIfAbsent(groupCode, new HashSet<>());
        groupSessions.get(groupCode).add(session);

        for (WebSocketSession activeSession : groupSessions.get(groupCode)) {
            if (activeSession.isOpen() && !activeSession.getId().equals(session.getId())) {
                String encryptedMessage = encryptMessage(messageContent, activeSession);
                activeSession.sendMessage(new TextMessage(encryptedMessage));
            }
        }
    }

    private String encryptMessage(String messageContent, WebSocketSession session) throws Exception {
        // String recipientId = session.getId(); // Using session ID as recipient
        // identifier
        // int deviceId = 1; // Assume single device ID for simplicity
        // SessionBuilder sessionBuilder = new SessionBuilder(sessionStore, preKeyStore,
        // signedPreKeyStore,
        // identityKeyStore, recipientId, deviceId);
        // SessionCipher sessionCipher = new SessionCipher(sessionStore,
        // identityKeyStore.getIdentity(recipientId));
        // CiphertextMessage ciphertextMessage =
        // sessionCipher.encrypt(messageContent.getBytes(StandardCharsets.UTF_8));
        // return new String(ciphertextMessage.serialize(), StandardCharsets.UTF_8);
        return messageContent;
    }

    private String decryptMessage(String encryptedMessage, WebSocketSession session) throws Exception {
        // String recipientId = session.getId(); // Using session ID as recipient
        // identifier
        // int deviceId = 1;
        // SessionCipher sessionCipher = new SessionCipher(sessionStore,
        // identityKeyStore.getIdentity(recipientId));
        // byte[] decryptedBytes = sessionCipher
        // .decrypt(new
        // PreKeySignalMessage(encryptedMessage.getBytes(StandardCharsets.UTF_8)));
        // return new String(decryptedBytes, StandardCharsets.UTF_8);
        return encryptedMessage;
    }
}
