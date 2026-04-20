package com.retrip.alarm.infra.adapter.out.persistence.util

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

object PageUtils {
    fun <T : Any> checkEndPage(pageable: Pageable, results: MutableList<T>): PageImpl<T> {
        val hasNext = results.size > pageable.pageSize
        if (hasNext) {
            results.removeAt(pageable.pageSize)
        }
        val total = pageable.offset + results.size + (if (hasNext) 1 else 0)
        return PageImpl(results, pageable, total)
    }
}
