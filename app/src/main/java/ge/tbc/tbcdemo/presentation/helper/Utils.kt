package ge.tbc.tbcdemo.presentation.helper

import android.content.res.Resources
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.*


fun getDateFromMills(time: Long, dateFormat: String): String {
    val outFormat = createDateFormat(dateFormat)
    val date = Date(time)
    return outFormat.format(date)
}

fun createDateFormat(format: String): SimpleDateFormat {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat
}

fun getPxFromDp(dip: Float) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dip,
    Resources.getSystem().displayMetrics
)

fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels