@file:Suppress("DEPRECATION")

package com.example.comunicaa.utils.helper

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import android.widget.Toast
import com.example.comunicaa.R
import com.example.comunicaa.utils.createAudioFilePath
import java.io.IOException

class AudioHelper(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    var audioFilePath: String? = null
    var audioUrl: String? = null

    fun startRecording(onStop: () -> Unit) {
        audioFilePath = context.createAudioFilePath()
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFilePath)
            setMaxDuration(3000)
            try {
                prepare()
            } catch (e: IOException) {
                Log.e("AudioRecord", "prepare() failed")
            }
            start()

            setOnInfoListener { _, what, _ ->
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    onStop.invoke()
                }
            }
        }
    }

    fun stopRecording(onStop: (() -> Unit)) {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null

        onStop.invoke()
    }

    fun playRecording(onStart: (() -> Unit), onComplete: (() -> Unit)) {
        try {
            mediaPlayer = MediaPlayer().apply {
                try {
                    if (!audioFilePath.isNullOrEmpty()) setDataSource(audioFilePath)
                    else if (!audioUrl.isNullOrEmpty()) setDataSource(audioUrl)

                    prepareAsync()
                    setOnPreparedListener {
                        start()
                        onStart.invoke()
                    }

                    setOnCompletionListener { onComplete.invoke() }
                } catch (e: IOException) {
                    Log.e("AudioRecord", "prepare() failed")
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.record_audio_error), Toast.LENGTH_SHORT).show()
        }
    }

    fun stop() {
        mediaRecorder?.release()
        mediaRecorder = null
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun destroy() {
        mediaRecorder?.release()
        mediaRecorder = null
        mediaPlayer?.release()
        mediaPlayer = null
    }
}