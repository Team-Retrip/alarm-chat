package com.retrip.alarm.infra.config

import com.retrip.alarm.application.`in`.usecase.AlarmUseCase
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class SchedulerConfig(
    private val alarmUseCase: AlarmUseCase
) {

    // Run every day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    fun deleteOldAlarms() {
        println("Executing scheduled task: Delete old alarms")
        alarmUseCase.deleteOldAlarms()
    }
}
