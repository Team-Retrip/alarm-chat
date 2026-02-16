package com.retrip.alarm.application.out.external

import com.retrip.alarm.application.out.request.PushRequest
import com.retrip.alarm.domain.entity.Alarm
import com.retrip.alarm.domain.entity.AlarmMember

interface PushPort {
    fun sendPush(pushRequests: List<PushRequest>)
    fun sendMulticast(tokens: List<String>, title: String, body: String, data: Map<String, String>)
}
