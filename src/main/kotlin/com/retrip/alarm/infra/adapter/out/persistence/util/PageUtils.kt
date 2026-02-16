package com.retrip.alarm.infra.adapter.out.persistence.util

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

object PageUtils {
    fun <T : Any> checkEndPage(pageable: Pageable, results: MutableList<T>): PageImpl<T> {
        if (results.size > pageable.pageSize) {
            results.removeAt(pageable.pageSize)
        }
        return PageImpl(results, pageable, results.size.toLong())
    }
}
