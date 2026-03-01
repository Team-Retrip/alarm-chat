package com.retrip.alarm.infra.adapter.out.external

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import com.retrip.alarm.application.out.external.PushPort
import com.retrip.alarm.application.out.request.PushRequest
import org.springframework.stereotype.Service

@Service
class FcmGateway(
    private val firebaseMessaging: FirebaseMessaging
) : PushPort {

    override fun sendPush(pushRequests: List<PushRequest>) {
        try {
            val messages = pushRequests.map {
                Message.builder()
                    .setToken(it.token)
                    .setNotification(
                        Notification.builder()
                            .setTitle(it.title)
                            .setBody(it.body)
                            .build()
                    )
                    .putAllData(it.data)
                    .build()
            }
            firebaseMessaging.sendEachAsync(messages)
        } catch (e: Exception) {
            // Log error but don't stop execution
            println("Failed to send FCM message: \${e.message}")
        }
    }


    override fun sendMulticast(tokens: List<String>, title: String, body: String, data: Map<String, String>) {
        if (tokens.isEmpty()) return

        try {
            val message = MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(
                    Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build()
                )
                .putAllData(data)
                .build()

            firebaseMessaging.sendEachForMulticast(message)
        } catch (e: Exception) {
            println("Failed to send multicast FCM message: \${e.message}")
        }
    }
}
