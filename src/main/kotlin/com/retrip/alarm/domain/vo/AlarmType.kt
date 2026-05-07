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
    DELETE("여행 취소", "{tripName} 여행이 삭제되었습니다."),

    SETTLEMENT_CREATE("정산 생성", "{senderName}님이 {tripName} 여행의 정산을 생성했습니다."),
    SETTLEMENT_AMOUNT_UPDATE("정산 금액 수정", "{tripName} 여행 정산 금액이 {amount}원으로 수정되었습니다."),
    SETTLEMENT_CANCEL("정산 취소", "{tripName} 여행 정산이 취소되었습니다."),
    SETTLEMENT_EQUAL_SPLIT("1/N 정산 분할", "{senderName}님이 {tripName} 여행 정산을 1/N으로 분할했습니다."),
    SETTLEMENT_INDIVIDUAL_SPLIT("개별 금액 정산 분할", "{senderName}님이 {tripName} 여행 정산을 개별 금액으로 분할했습니다.");

    fun createTitle(): String {
        return this.titleTemplate
    }

    fun createBody(parameters: Map<String, Any>?): String {
        val senderName = parameters?.get("senderName") as? String ?: "Unknown"
        val tripName = parameters?.get("tripName") as? String ?: "Unknown"
        val amount = parameters?.get("amount")?.toString() ?: "0"
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
            SETTLEMENT_CREATE -> this.bodyTemplate
                .replace("{senderName}", senderName)
                .replace("{tripName}", tripName)
            SETTLEMENT_AMOUNT_UPDATE -> this.bodyTemplate
                .replace("{tripName}", tripName)
                .replace("{amount}", amount)
            SETTLEMENT_CANCEL -> this.bodyTemplate.replace("{tripName}", tripName)
            SETTLEMENT_EQUAL_SPLIT -> this.bodyTemplate
                .replace("{senderName}", senderName)
                .replace("{tripName}", tripName)
            SETTLEMENT_INDIVIDUAL_SPLIT -> this.bodyTemplate
                .replace("{senderName}", senderName)
                .replace("{tripName}", tripName)
        }
    }
}
