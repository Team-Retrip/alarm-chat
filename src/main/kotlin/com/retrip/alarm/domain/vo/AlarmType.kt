package com.retrip.alarm.domain.vo

enum class AlarmType(
    val titleTemplate: String,
    val bodyTemplate: String
) {
    INVITATION("여행 초대", "{sender}님이 {tripName} 여행에 초대했습니다."),
    TRIP_CHANGE("여행 정보 수정", "{tripName} 여행 정보가 수정되었습니다."),
    TRIP_CONFIRM("여행 확정", "{tripName} 여행이 확정되었습니다."),
    KICK("여행 강퇴", "{tripName} 여행에서 강퇴되었습니다."),
    DELETE("여행 취소", "{tripName} 여행이 삭제되었습니다.");

    fun createTitle(): String {
        return this.titleTemplate
    }

    fun createBody(parameters: List<String>?): String {
        return when(this){
            INVITATION -> this.bodyTemplate.replace("{sender}", parameters?.getOrNull(0) ?: "Unknown")
                .replace("{tripName}", parameters?.getOrNull(1) ?: "Unknown")
            TRIP_CHANGE -> this.bodyTemplate.replace("{tripName}", parameters?.getOrNull(0) ?: "Unknown")
            TRIP_CONFIRM -> this.bodyTemplate.replace("{tripName}", parameters?.getOrNull(0) ?: "Unknown")
            KICK -> this.bodyTemplate.replace("{tripName}", parameters?.getOrNull(0) ?: "Unknown")
            DELETE -> this.bodyTemplate.replace("{tripName}", parameters?.getOrNull(0) ?: "Unknown")
        }
    }
}
