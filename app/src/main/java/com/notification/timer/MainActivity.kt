package com.notification.timer

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.module.notification.timer.NotificationTimer
import com.notification.timer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var notiTimer: NotificationTimer.Builder

    private val pendingIntent by lazy {
        Intent(this, MainActivity::class.java).let {
            PendingIntent.getActivity(this, 0, it, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buildBtn.setOnClickListener {
            notiTimer =
                NotificationTimer.Builder(this).setSmallIcon(R.drawable.ic_timer).setPlayButtonIcon(R.drawable.ic_play_noti)
                    .setPauseButtonIcon(R.drawable.ic_pause_noti).setStopButtonIcon(R.drawable.ic_stop_noti).setControlMode(true)
                    .setColor(R.color.sexy_blue).setShowWhen(false).setAutoCancel(false).setOnlyAlertOnce(true)
                    .setPriority(NotificationCompat.PRIORITY_LOW).setContentIntent(pendingIntent)
                    .setOnTickListener { binding.timeUntilFinishText.text = it.toString() }
                    .setOnFinishListener { Toast.makeText(this, "timer finished", Toast.LENGTH_SHORT).show() }
                    .setContentTitle("Timer :)")
        }

        binding.playBtn.setOnClickListener {
            notiTimer.play(binding.timeEditText.text.toString().toLong())
        }

        binding.pauseBtn.setOnClickListener {
            notiTimer.pause()
        }

        binding.stopBtn.setOnClickListener {
            notiTimer.stop()
            binding.timeUntilFinishText.text = null
        }

        binding.terminateBtn.setOnClickListener {
            notiTimer.terminate()
        }
    }
}