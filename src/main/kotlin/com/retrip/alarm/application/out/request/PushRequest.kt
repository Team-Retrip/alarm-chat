package com.retrip.alarm.application.out.request

data class PushRequest(
    val token: String,
    val title: String,
    val body: String,
    val data: Map<String, String>? = null
) {
}
