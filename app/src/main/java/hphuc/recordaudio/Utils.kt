package hphuc.recordaudio

import android.Manifest
import android.os.Environment

class Utils {
    companion object{
        fun milliSecondsToTimer(milliseconds: Long): String? {
            var finalTimerString = ""
            var secondsString = ""
            // Convert total duration into time
            val hours = (milliseconds / (1000 * 60 * 60)).toInt()
            val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
            val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
            // Add hours if there
            if (hours > 0 ) {
                finalTimerString = "$hours:"
            }
            // Prepending 0 to seconds if it is one digit
            secondsString = if (seconds < 10) {
                "0$seconds"
            } else {
                "" + seconds
            }
            finalTimerString = "$finalTimerString$minutes:$secondsString"
            // return timer string
            return finalTimerString
        }

        fun getDirectoryApp(): String{
            return Environment.getExternalStorageDirectory().path + "/RecordAudio"
        }
    }
}