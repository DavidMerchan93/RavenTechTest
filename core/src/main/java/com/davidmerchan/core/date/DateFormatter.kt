package com.davidmerchan.core.date

import android.content.Context
import com.davidmerchan.core.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DateFormatter {

    private const val DATE_FORMAT = "dd MMM yyyy"

    fun parseIsoStringToLocalDateTime(isoString: String): LocalDateTime {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val zonedDateTime = ZonedDateTime.parse(isoString, formatter)
        return zonedDateTime.toLocalDateTime()
    }

    fun formatRelativeDate(context: Context, dateTime: LocalDateTime): String {
        val now = LocalDateTime.now(ZoneId.systemDefault())

        val daysDiff = ChronoUnit.DAYS.between(dateTime.toLocalDate(), now.toLocalDate())
        val hoursDiff = ChronoUnit.HOURS.between(dateTime, now)
        val minutesDiff = ChronoUnit.MINUTES.between(dateTime, now)

        return when (daysDiff) {
            0L -> when {
                hoursDiff > 0 -> context.getString(R.string.hours_ago, hoursDiff)
                minutesDiff > 0 -> context.getString(R.string.minutes_ago, minutesDiff)
                else -> context.getString(R.string.seconds_ago)
            }
            1L -> context.getString(R.string.yesterday)
            in 2..6 -> context.getString(R.string.days_ago, daysDiff)
            else -> dateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
        }
    }
}
