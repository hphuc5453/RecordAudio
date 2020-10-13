package hphuc.recordaudio

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnPlay: Button
    private lateinit var btnPause: Button
    private lateinit var tvTime: AppCompatTextView
    private val FILE_PATH = Utils.getDirectoryApp()
    private var fileName = "/test.m4a"
    private val REQUEST_AUDIO_PERMISSION_CODE = 1
    private var mRecord : MediaRecorder? = null
    private var mPlayer : MediaPlayer? = null
    private var countDownTimer: CountDownTimer? = null

    private val onActionClick = View.OnClickListener {
        when(it.id){
            btnStart.id->{
                startRecording()
            }
            btnStop.id->{
                stopRecord()
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

    private fun checkFolderResource(){
        val fileFolder = File(FILE_PATH)
        if(!fileFolder.exists()){
            fileFolder.mkdirs()
        }
    }

    private fun startRecording(){
        if(checkPermission()){
            mRecord = MediaRecorder().apply {
                checkFolderResource()
                fileName = "/${Calendar.getInstance().timeInMillis}.m4a"
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile("$FILE_PATH$fileName")
                try{
                    prepare()
                }catch(ex: IOException){

                }
                start()
                btnStart.isEnabled = false
                btnStop.isEnabled = true
                startCountDownTimer()
            }
        }else{
            requestPermission()
        }
    }

    private fun stopRecord(){
        mRecord.apply {
            mRecord?.stop()
            mRecord?.release()
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            stopCountDownTimer()
        }

        mRecord = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)
        btnPlay = findViewById(R.id.btnPlay)
        btnPause = findViewById(R.id.btnPause)
        tvTime = findViewById(R.id.tvTime)

        btnStart.setOnClickListener(onActionClick)
        btnStop.setOnClickListener(onActionClick)
        btnPlay.setOnClickListener(onActionClick)
        btnPause.setOnClickListener(onActionClick)

        btnStop.isEnabled = false
        btnPlay.isEnabled = false
        btnPause.isEnabled = false
    }

    private fun startCountDownTimer() {
        countDownTimer?.cancel()
        var timeRecord = 0
        countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onFinish() {

            }

            override fun onTick(p0: Long) {
                tvTime.text = Utils.milliSecondsToTimer(timeRecord * 1000L)
                timeRecord++
            }
        }
        countDownTimer?.start()
    }

    private fun stopCountDownTimer(){
        countDownTimer?.cancel()
        tvTime.text = resources.getString(R.string.text_time_start)
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