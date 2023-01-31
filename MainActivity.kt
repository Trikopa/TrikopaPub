package org.hyperskill.stopwatch

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri.Builder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handler = Handler(Looper.getMainLooper())
        var timeOfStart = System.currentTimeMillis()
        var timerRunning = false
        var alertTime = 100000.toLong()
        var notNotified = true
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelID = "org.hyperskill"
        val name = "Delivery status"
        val descriptionText = "Your delivery status"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelID )
            .setSmallIcon(R.drawable.progress)
            .setContentTitle("Delivery")
            .setContentText("Food is ready!")
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setTimeoutAfter(700)
        val notification: Notification = notificationBuilder.build()
        notification.flags = Notification.FLAG_INSISTENT or Notification.FLAG_ONLY_ALERT_ONCE
        val changeColor: Runnable = object: Runnable {
            override fun run() {
                progressBar.indeterminateTintList = ColorStateList.valueOf(Color.BLUE)
                handler.postDelayed(this,2000)

            }
        }
        val changeColorState: Runnable = object: Runnable {
            override fun run() {
                progressBar.indeterminateTintList = ColorStateList.valueOf(Color.GREEN)
                handler.postDelayed(this,2000)
            }
        }
        val updateLight: Runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
                val time = (System.currentTimeMillis() - timeOfStart) / 1000
                val minutes = (time % 3600) / 60
                val seconds = time % 60
                val timeString = String.format("%02d:%02d", minutes, seconds)
                textView.text = timeString
                if(alertTime == time && alertTime!= 0.toLong()) {
                    textView.setTextColor(Color.RED)
                    val notificationManager1 = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager1.notify(393939,notification)

                }
            }
        }
            startButton.setOnClickListener {
                fun onStart() {
                    super.onStart()
                    progressBar.visibility = View.VISIBLE
                    handler.postDelayed(updateLight, 0)
                    handler.postDelayed(changeColor,0)
                    handler.postDelayed(changeColorState,1000)
                    settingsButton.isEnabled = false
                }
                if(!timerRunning) {
                    timerRunning = true
                    timeOfStart = System.currentTimeMillis()
                    onStart()
                }
            }
            resetButton.setOnClickListener {
                fun onStop() {
                    super.onStop()
                    handler.removeCallbacks(updateLight)
                    handler.removeCallbacks(changeColor)
                }
                onStop()
                textView.text = "00:00"
                progressBar.visibility = View.INVISIBLE
                timerRunning = false
                timeOfStart = System.currentTimeMillis()
                textView.setTextColor(Color.GRAY)
                settingsButton.isEnabled = true
                alertTime = 100000

            }
        settingsButton.setOnClickListener {
            val editText = EditText(this)
            editText.inputType = InputType.TYPE_CLASS_PHONE
            editText.id = R.id.upperLimitEditText
            AlertDialog.Builder(this)
                .setView(editText)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    if(editText.text.isNotBlank()) {
                        alertTime = editText.text.toString().toLong()
                    }
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }
        }


}


