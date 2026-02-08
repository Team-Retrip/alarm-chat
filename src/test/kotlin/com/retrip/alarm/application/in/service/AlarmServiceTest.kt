package com.retrip.alarm.application.in.service

import com.retrip.alarm.application.out.external.PushService
import com.retrip.alarm.application.out.repository.AlarmRepository
import com.retrip.alarm.domain.entity.Alarm
import com.retrip.alarm.domain.vo.AlarmType
import com.retrip.alarm.domain.exception.common.BusinessException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class AlarmServiceTest {

    @Mock
    lateinit var alarmRepository: AlarmRepository

    @Mock
    lateinit var pushService: PushService

    @InjectMocks
    lateinit var alarmService: AlarmService

    @Test
    fun `알림 생성 및 푸시 전송 테스트`() {
        // given
        val recipientId = 1L
        val senderId = 2L
        val senderName = "홍길동"
        val tripId = 100L
        val tripTitle = "서울 여행"
        val type = AlarmType.INVITATION
        val token = "fcm-token"

        val alarm = Alarm(
            recipientId = recipientId,
            senderId = senderId,
            tripId = tripId,
            title = "여행 초대",
            body = "홍길동님이 서울 여행 여행에 초대했습니다.",
            type = type
        )
        
        // Mocking save to return the alarm object (simulating DB save)
        `when`(alarmRepository.save(any(Alarm::class.java))).thenReturn(alarm)

        // when
        val result = alarmService.createAlarm(
            recipientId, senderId, senderName, tripId, tripTitle, type, token
        )

        // then
        assertThat(result.title).isEqualTo("여행 초대")
        assertThat(result.body).isEqualTo("홍길동님이 서울 여행 여행에 초대했습니다.")
        
        // Verify push service was called
        verify(pushService).sendPush(
            org.mockito.ArgumentMatchers.eq(token),
            org.mockito.ArgumentMatchers.eq("여행 초대"),
            org.mockito.ArgumentMatchers.eq("홍길동님이 서울 여행 여행에 초대했습니다."),
            any()
        )
    }

    @Test
    fun `알림 읽기 처리 테스트`() {
        // given
        val alarmId = 1L
        val recipientId = 1L
        val alarm = Alarm(
            recipientId = recipientId,
            tripId = 100L,
            title = "Title",
            body = "Body",
            type = AlarmType.TRIP_CHANGE
        )

        `when`(alarmRepository.findById(alarmId)).thenReturn(alarm)

        // when
        alarmService.readAlarm(alarmId, recipientId)

        // then
        assertThat(alarm.isRead).isTrue()
    }

    @Test
    fun `다른 사람의 알림을 읽으려 하면 예외 발생`() {
        // given
        val alarmId = 1L
        val recipientId = 1L
        val otherUserId = 2L
        val alarm = Alarm(
            recipientId = recipientId,
            tripId = 100L,
            title = "Title",
            body = "Body",
            type = AlarmType.TRIP_CHANGE
        )

        `when`(alarmRepository.findById(alarmId)).thenReturn(alarm)

        // when & then
        assertThrows(SecurityException::class.java) {
            alarmService.readAlarm(alarmId, otherUserId)
        }
    }
}
