package hphuc.recordaudio

import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btnStart : Button
    private val FILE_PATH = Environment.getExternalStorageDirectory().absolutePath

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnStart)

        val mediaRecord = MediaRecorder()
        mediaRecord.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecord.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecord.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecord.setOutputFile("${FILE_PATH}/test.3gp")
        mediaRecord.prepare()

        btnStart.setOnClickListener {
            mediaRecord.start()
        }
    }
}