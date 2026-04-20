package com.retrip.alarm.domain.vo

enum class AlarmType(
    val titleTemplate: String,
    val bodyTemplate: String
) {
    DEMAND("여행 요청", "{senderName}님이 {tripName} 여행에 요청했습니다."),
    INVITATION("여행 초대", "{senderName}님이 {tripName} 여행에 초대했습니다."),
    TRIP_CHANGE("여행 정보 수정", "{tripName} 여행 정보가 수정되었습니다."),
    TRIP_CONFIRM("여행 확정", "{tripName} 여행이 확정되었습니다."),
    KICK("여행 강퇴", "{tripName} 여행에서 강퇴되었습니다."),
    DELETE("여행 취소", "{tripName} 여행이 삭제되었습니다.");

    fun createTitle(): String {
        return this.titleTemplate
    }

    fun createBody(parameters: Map<String, Any>?): String {
        val senderName = parameters?.get("senderName") as? String ?: "Unknown"
        val tripName = parameters?.get("tripName") as? String ?: "Unknown"
        return when (this) {
            DEMAND -> this.bodyTemplate
                .replace("{senderName}", senderName)
                .replace("{tripName}", tripName)
            INVITATION -> this.bodyTemplate
                .replace("{senderName}", senderName)
                .replace("{tripName}", tripName)
            TRIP_CHANGE -> this.bodyTemplate.replace("{tripName}", tripName)
            TRIP_CONFIRM -> this.bodyTemplate.replace("{tripName}", tripName)
            KICK -> this.bodyTemplate.replace("{tripName}", tripName)
            DELETE -> this.bodyTemplate.replace("{tripName}", tripName)
        }
    }
}
