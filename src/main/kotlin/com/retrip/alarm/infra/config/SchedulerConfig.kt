package com.retrip.alarm.infra.config

import com.retrip.alarm.application.`in`.usecase.AlarmUseCase
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class SchedulerConfig(
    private val alarmUseCase: AlarmUseCase
) {
    companion object {
        val log = LoggerFactory.getLogger(SchedulerConfig::class.java)
    }

    // Run every day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    fun deleteOldAlarms() {
        log.info("Executing scheduled task: Delete old alarms")
        alarmUseCase.deleteOldAlarms()
    }
}
