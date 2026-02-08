package com.retrip.alarm.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class AlarmTypeTest {

    @Test
    fun `제목 생성 테스트`() {
        // given
        val type = AlarmType.INVITATION

        // when
        val title = type.createTitle()

        // then
        assertThat(title).isEqualTo("여행 초대")
    }

    @Test
    fun `내용 생성 테스트 - Sender 포함`() {
        // given
        val type = AlarmType.INVITATION
        val senderName = "김철수"
        val tripTitle = "제주도 여행"

        // when
        val body = type.createBody(senderName, tripTitle)

        // then
        assertThat(body).isEqualTo("김철수님이 제주도 여행 여행에 초대했습니다.")
    }

    @Test
    fun `내용 생성 테스트 - Sender 미포함`() {
        // given
        val type = AlarmType.TRIP_CHANGE
        val tripTitle = "부산 여행"

        // when
        val body = type.createBody(null, tripTitle)

        // then
        assertThat(body).isEqualTo("부산 여행 여행 정보가 수정되었습니다.")
    }

    @ParameterizedTest
    @CsvSource(
        "INVITATION, 여행 초대",
        "TRIP_CHANGE, 여행 정보 수정",
        "TRIP_CONFIRM, 여행 확정",
        "KICK, 여행 강퇴",
        "DELETE, 여행 취소"
    )
    fun `모든 타입 제목 테스트`(type: AlarmType, expectedTitle: String) {
        assertThat(type.createTitle()).isEqualTo(expectedTitle)
    }
}
