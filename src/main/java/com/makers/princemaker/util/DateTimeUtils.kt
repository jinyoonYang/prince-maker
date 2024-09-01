package com.makers.princemaker.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//버전 1 (object 사용)
/*
//object - 싱글톤 패턴으로 생성됨
object DateTimeUtils {
    @JvmStatic //해당 함수를 static으로 생성해줌
    fun getLocalDateTimeString(localDateTime: LocalDateTime): String {
        return localDateTime.format(
            DateTimeFormatter
                .ofPattern("yyyy-MM-dd 탄생")
        )
    }
}*/

//버전 2 (글로벌 함수 - top 레벨)
fun getLocalDateTimeString(localDateTime: LocalDateTime): String {
    return localDateTime.format(
        DateTimeFormatter
            .ofPattern("yyyy-MM-dd 탄생")
    )
}
