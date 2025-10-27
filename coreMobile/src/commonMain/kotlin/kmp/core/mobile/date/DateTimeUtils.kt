package kmp.core.mobile.date


import kmp.core.mobile.utils.extensions.getSystemLanguage
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
fun Long.toLocalDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
}

fun Long.toLocalDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return this.toLocalDateTime(timeZone).date
}

fun Long.toLocalTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return this.toLocalDateTime(timeZone).time
}

@OptIn(ExperimentalTime::class)
fun LocalDate.toMillis(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    return this.atStartOfDayIn(timeZone).toEpochMilliseconds()
}

fun LocalDateTime.toMillis(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    return this.date.toMillis(timeZone)
}

@OptIn(ExperimentalTime::class)
fun LocalDateTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return kotlin.time.Clock.System.now().toLocalDateTime(timeZone)
}

fun LocalDate.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return LocalDateTime.now(timeZone).date
}

fun LocalTime.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalTime {
    return LocalDateTime.now(timeZone).time
}

fun LocalDate.isPast(): Boolean {
    return this < LocalDate.now()
}

expect fun LocalDateTime.format(format: String, language: String = getSystemLanguage().code): String

expect fun LocalDate.format(format: String, language: String = getSystemLanguage().code): String

expect fun LocalTime.format(format: String, language: String = getSystemLanguage().code): String

expect fun String.parseLocalDateTime(format: String, language: String = getSystemLanguage().code): LocalDateTime?

expect fun String.parseLocalDate(format: String, language: String = getSystemLanguage().code): LocalDate?

expect fun String.parseLocalTime(format: String, language: String = getSystemLanguage().code): LocalTime?