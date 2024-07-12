package com.api.pickle.infra.config.fcm;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FcmService {

    public ApiFuture<BatchResponse> sendGroupMessageAsync(
            List<String> tokenList, String title, String content) {
        MulticastMessage multicast = MulticastMessage.builder()
                .addAllTokens(tokenList)
                .setNotification(
                        Notification.builder().setTitle(title).setBody(content).build())
                .build();
        return FirebaseMessaging.getInstance().sendEachForMulticastAsync(multicast);
    }

    public ApiFuture<String> sendMessageSync(String token, String title, String content) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        Message message = Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder().setTitle(title).setBody(content).build())
                .build();
        return FirebaseMessaging.getInstance().sendAsync(message);
    }
}
