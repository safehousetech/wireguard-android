package com.safehouse.core.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateHelper {
  @JvmStatic
  val currentDate: String
    get() {
      val c = Calendar.getInstance().time
      val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
      return df.format(c)
    }
  val reviewDate: String
    get() {
      val c = Calendar.getInstance().time
      val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
      return df.format(c)
    }

  /**
   * Get a diff between two dates
   *
   * @param oldDate the old date
   * @param newDate the new date
   * @return the diff value, in the days
   */
  fun getDateDiff(oldDate: String?, newDate: String?): Long {
    return try {
      val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
      TimeUnit.DAYS.convert(
        format.parse(newDate).time - format.parse(oldDate).time,
        TimeUnit.MILLISECONDS
      )
    } catch (e: Exception) {
      e.printStackTrace()
      0
    }
  }

  /**
   * Get a diff between two dates
   *
   * @param oldDate the old date
   * @param newDate the new date
   * @return the diff value, in the days
   */
  @JvmStatic
  fun getDateDiffInDays(oldDate: String?, newDate: String?): Long {
    return try {
      val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
      TimeUnit.DAYS.convert(
        format.parse(newDate).time - format.parse(oldDate).time,
        TimeUnit.MILLISECONDS
      )
    } catch (e: Exception) {
      e.printStackTrace()
      0
    }
  }

  /**
   * Get a diff between two dates
   *
   * @param oldDate the old date
   * @param newDate the new date
   * @return the diff value, in months
   */
  @JvmStatic
  fun getDateDiffInMonths(oldDate: Date, newDate: Date): Int {
    val calendar = Calendar.getInstance().apply { time = oldDate }
    val nMonth1 = 12 * calendar[Calendar.YEAR] + calendar[Calendar.MONTH]
    val nDays1 = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = newDate
    val nMonth2 = 12 * calendar[Calendar.YEAR] + calendar[Calendar.MONTH]
    val nDays2 = calendar[Calendar.DAY_OF_MONTH]
    return Math.abs(nMonth2 - nMonth1) - if (nDays1 > nDays2) 1 else 0
  }

  fun convertDate(date: String, fromFormat: String, toFormat: String): String {
    val date = SimpleDateFormat(fromFormat, Locale.getDefault()).parse(date.substring(0, 10))
    return SimpleDateFormat(toFormat, Locale.getDefault()).format(date)
  }
}
