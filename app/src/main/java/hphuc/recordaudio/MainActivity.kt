package hphuc.recordaudio

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    lateinit var btnPlay: Button
    lateinit var btnPause: Button
    private val FILE_PATH = Environment.getExternalStorageDirectory().absolutePath
    private val fileName = "/test.3gp"
    private val REQUEST_AUDIO_PERMISSION_CODE = 1
    private var mRecord : MediaRecorder? = null
    private var mPlayer : MediaPlayer? = null

    private val onActionClick = View.OnClickListener {
        when(it.id){
            btnStart.id->{
                if(checkPermission()){
                    mRecord = MediaRecorder()
                    mRecord?.setAudioSource(MediaRecorder.AudioSource.MIC)
                    mRecord?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    mRecord?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                    mRecord?.setOutputFile("$FILE_PATH$fileName")
                    mRecord?.prepare()
                    mRecord?.start()
                    Toast.makeText(this, "Recording Start", Toast.LENGTH_LONG).show()
                }else{
                    requestPermission()
                }
            }
            btnStop.id->{
                mRecord?.stop()
                mRecord?.release()
                mRecord = null
                Toast.makeText(this, "Recording Stopped", Toast.LENGTH_LONG).show()
            }
            btnPlay.id->{
                mPlayer = MediaPlayer()
                try{
                    mPlayer?.setDataSource(fileName)
                    mPlayer?.prepare()
                    mPlayer?.start()
                    Toast.makeText(this, "Recording Started Playing", Toast.LENGTH_LONG).show()
                }catch(e: IOException){
                    Toast.makeText(this, "Failed !!!", Toast.LENGTH_LONG).show()
                }
            }
            btnPause.id->{
                mPlayer?.stop()
                mPlayer?.release()
                mPlayer = null
                Toast.makeText(this, "Playing Audio Stopped", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnPlay = findViewById(R.id.btnPlay)
        btnPause = findViewById(R.id.btnPause)

        btnStart.setOnClickListener(onActionClick)
        btnStop.setOnClickListener(onActionClick)
        btnPlay.setOnClickListener(onActionClick)
        btnPause.setOnClickListener(onActionClick)
    }

    private fun checkPermission(): Boolean{
        val result = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO)
        val result2 = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_AUDIO_PERMISSION_CODE
        )
    }
}