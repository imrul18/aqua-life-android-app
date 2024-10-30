import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun formatTimestamp(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("HH:MM, d. MMMM yy", Locale.GERMAN)
    return sdf.format(timestamp)
}