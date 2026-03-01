package com.retrip.alarm.application.out.external

import com.retrip.alarm.application.out.request.PushRequest

interface PushPort {
    fun sendPush(pushRequests: List<PushRequest>)
    fun sendMulticast(tokens: List<String>, title: String, body: String, data: Map<String, String>)
}
