package com.retrip.alarm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class AlarmApplication

fun main(args: Array<String>) {
	runApplication<AlarmApplication>(*args)
}
