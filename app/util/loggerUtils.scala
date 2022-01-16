package util

import org.joda.time.DateTime
import java.util.Date
import org.joda.time.DateTimeZone

object loggerUtils {
    def getFormatedUTCTime(): String = {
         val now = new Date()
         return new DateTime(now).withZone(DateTimeZone.UTC).toString()
    }
}
