package com.retrip.alarm.domain.vo

enum class AlarmType(
    val titleTemplate: String,
    val bodyTemplate: String
) {
    INVITATION("여행 초대", "{sender}님이 {trip} 여행에 초대했습니다."),
    TRIP_CHANGE("여행 정보 수정", "{trip} 여행 정보가 수정되었습니다."),
    TRIP_CONFIRM("여행 확정", "{trip} 여행이 확정되었습니다."),
    KICK("여행 강퇴", "{trip} 여행에서 강퇴되었습니다."),
    DELETE("여행 취소", "{trip} 여행이 삭제되었습니다.");

    fun createTitle(): String {
        return this.titleTemplate
    }

    fun createBody(senderName: String?, tripTitle: String): String {
        var body = this.bodyTemplate
        if (senderName != null) {
            body = body.replace("{sender}", senderName)
        }
        return body.replace("{trip}", tripTitle)
    }
}
