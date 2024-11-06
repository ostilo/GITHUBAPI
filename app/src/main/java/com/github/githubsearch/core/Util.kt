package com.github.githubsearch.core

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object Util {

    fun getRelativeTimeSpan(dateString: String): String {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")

            val date = format.parse(dateString)
            val timeMillis = date?.time
            val now = System.currentTimeMillis()

            val relativeTime = timeMillis?.let {
                DateUtils.getRelativeTimeSpanString(
                    it,
                    now,
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
                )
            }

            "Updated $relativeTime"
        } catch (e: Exception) {
            "Date unknown"
        }
    }
}