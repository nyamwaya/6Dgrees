package com.app.a6dgrees.Utils

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.view.View
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.app.a6dgrees.R
import com.app.a6dgrees.common.showSnackBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TimerService @Inject constructor(
    val textView: TextView,
    val rootView: View
) : Service() {
    private val timer = object : CountDownTimer(120000, 1000) {
        override fun onFinish() {
            // Do something when the timer finishes
            showSnackBar("Time ran out, request a new code", rootView, Snackbar.LENGTH_LONG)
        }

        override fun onTick(millisUntilFinished: Long) {
            // Do something on each tick of the timer
            textView.text = millisUntilFinished.toString()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start the timer
        timer.start()

        // Start the service in the foreground
        val notification = NotificationCompat.Builder(this, "TimerService")
            .setContentTitle("OTP Timer")
            .setContentText("The otp was sent to your phone")
            .setSmallIcon(R.drawable.notification)
            .build()
        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        // Cancel the timer when the service is destroyed
        timer.cancel()

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
