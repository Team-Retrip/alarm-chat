package com.retrip.alarm.application.out.external

interface PushService {
    fun sendPush(token: String, title: String, body: String, data: Map<String, String>)
    fun sendMulticast(tokens: List<String>, title: String, body: String, data: Map<String, String>)
}
