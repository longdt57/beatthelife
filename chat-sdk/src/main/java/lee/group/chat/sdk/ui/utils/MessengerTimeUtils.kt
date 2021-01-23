package lee.group.chat.sdk.ui.utils

import android.content.Context
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.WEEK_OF_YEAR
import java.util.Calendar.YEAR
import java.util.Locale
import lee.group.chat.sdk.R

internal object MessengerTimeUtils {
    private const val ONE_MINUTE = 60_000L
    private const val ONE_HOUR = 3600_000L
    private const val ONE_DAY = 86400_000L
    // Fix In case last Friday 8PM and this Friday < 8PM
    private const val ONE_WEEK = 7 * ONE_DAY
    private const val ONE_MONTH = 30 * ONE_DAY

    private val locale = Locale("vi")

    private val DATE_FORMAT_LESS_ONE_MONTH = SimpleDateFormat("dd/MM", locale)
    private val DATE_FORMAT_MORE_ONE_MONTH = SimpleDateFormat("dd/MM/yyyy", locale)

    private val DATE_FORMAT_MESSAGING_TODAY = SimpleDateFormat("HH:mm", locale)
    private val DATE_FORMAT_MESSAGING_THIS_WEEK = SimpleDateFormat("EEEE", locale)
    private val DATE_FORMAT_MESSAGING_THIS_YEAR = SimpleDateFormat("dd MMMM", locale)
    private val DATE_FORMAT_MESSAGING_OTHER = SimpleDateFormat("dd/MM/yyyy", locale)

    fun getDisplayTime(context: Context, showTime: Long): String {
        val duration = System.currentTimeMillis() - showTime
        return when {
            duration < ONE_MINUTE -> {
                context.getString(R.string.chat_home_label_time_receive_just_now)
            }
            duration < ONE_HOUR -> context.getString(
                R.string.chat_home_label_time_receive_minutes,
                (duration / ONE_MINUTE).toString()
            )
            DateUtils.isToday(showTime) -> context.getString(
                R.string.chat_home_label_time_receive_hours,
                (duration / ONE_HOUR).toString()
            )
            duration < ONE_WEEK -> getDayOfWeekInString(context, showTime)
            duration < ONE_MONTH -> DATE_FORMAT_LESS_ONE_MONTH.format(showTime).toString()
            else -> DATE_FORMAT_MORE_ONE_MONTH.format(showTime).toString()
        }
    }

    /**
     * Get display time in messaging screen
     */
    fun getMessagingDisplayTime(showTime: Long): String {
        return when {
            DateUtils.isToday(showTime) -> DATE_FORMAT_MESSAGING_TODAY.format(showTime)
            isThisWeek(showTime) -> DATE_FORMAT_MESSAGING_THIS_WEEK.format(showTime)
            isThisYear(showTime) -> DATE_FORMAT_MESSAGING_THIS_YEAR.format(showTime)
            else -> DATE_FORMAT_MESSAGING_OTHER.format(showTime).toString()
        }
    }

    fun getMessagingDetailTime(showTime: Long): String {
        return DATE_FORMAT_MESSAGING_TODAY.format(showTime)
    }

    private fun getDayOfWeekInString(context: Context, showTime: Long): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = showTime
        }

        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> context.getString(R.string.chat_home_label_time_receive_sunday)
            Calendar.MONDAY -> context.getString(R.string.chat_home_label_time_receive_monday)
            Calendar.TUESDAY -> context.getString(R.string.chat_home_label_time_receive_tuesday)
            Calendar.WEDNESDAY -> context.getString(R.string.chat_home_label_time_receive_wednesday)
            Calendar.THURSDAY -> context.getString(R.string.chat_home_label_time_receive_thursday)
            Calendar.FRIDAY -> context.getString(R.string.chat_home_label_time_receive_friday)
            else -> context.getString(R.string.chat_home_label_time_receive_saturday)
        }
    }

    private fun isThisYear(timeStamp: Long): Boolean {
        val calendar = Calendar.getInstance()
        val thisYear = calendar.get(YEAR)
        calendar.timeInMillis = timeStamp
        return calendar.get(YEAR) == thisYear
    }

    private fun isThisWeek(timeStamp: Long): Boolean {
        val currentCalendar = Calendar.getInstance()
        val currentWeekOfYear = currentCalendar.get(WEEK_OF_YEAR)

        val messageCalendar = Calendar.getInstance().apply { timeInMillis = timeStamp }
        val messageWeekOfYear = messageCalendar.get(WEEK_OF_YEAR)
        return currentWeekOfYear == messageWeekOfYear
    }

    fun isTheSameDay(firstTimestamp: Long, secondTimeStamp: Long): Boolean {
        val dayFirst = DATE_FORMAT_MESSAGING_OTHER.format(firstTimestamp).toString()
        val daySecond = DATE_FORMAT_MESSAGING_OTHER.format(secondTimeStamp).toString()
        return dayFirst == daySecond
    }
}
