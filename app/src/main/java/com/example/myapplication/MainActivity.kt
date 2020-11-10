package com.example.myapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.Const.webSocket.NOTIFICATION_CHANNEL_ID
import com.example.myapplication.wsnotification.NotificationService
import timber.log.Timber

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button=findViewById<Button>(R.id.button)
        button.setOnClickListener(this)

        val button2=findViewById<Button>(R.id.button2)
        button2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Timber.d("onClick")
        when (v?.id) {
            R.id.button -> {
                val notification=Models.Notification(id=1, title = "Тестовое уведомление", content = "Привет!")
                createNotofication(notification)
            }
            R.id.button2 -> {
                Timber.d("button2")
                // Логинемся от тестового пользователя
                val token="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJBY2Nlc3NFZGl0b3IiLCJuYmYiOjE2MDQ5Mjg3MjIsImlhdCI6MTYwNDkyODcyMiwiZXhwIjoxNjA0OTI4ODQyLCJzY29wZXMiOlsiVFlQRV9SRUZFUkVOQ0VfVE9LRU4iXSwidXNlciI6eyJpZCI6MTk1LCJyb2xlX2lkIjoxMTMsInJvb3RfbWVudV9pZCI6NDIwfSwianRpIjoiSFwvZ3kwUHJ1WFBjeWw4bXo5UEY5MllLeldySHNVXC81RkJCVzZBd1wvU005OD0ifQ.b3oJMTNTpBVqJ7m85pJ2R0wUurG_U6i2-elrL-7GY7r-3D4MXtPkD5oqY8wn6zyQ_BZW1jeGojyNQjs5UtaFLTTEJgtCE49U2fc4layF6wMphjH0yDm2vPhkP-l_JFIdGqeztJaZVMT1AUjjWJur_p_b8TM9UA7Ajlp98GzUb8A"
                startService(Intent(this, NotificationService::class.java).putExtra("Token",token))
            }
        }

    }

    private fun createNotofication(notification: Models.Notification) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Создаем канал для управления группами уведомлений
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Your Notifications", NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.description = "Description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
            channel.canBypassDnd()
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)

        // Создаем Intent для вызова активности
        val resultIntent= Intent(this, MainActivity::class.java).putExtra("messageId",notification.id)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        notificationBuilder.setAutoCancel(true)
            .setColor(ContextCompat.getColor(this, R.color.colorAccent))
            .setContentTitle(notification.title)
            .setContentText(notification.content)
            .setDefaults(Notification.DEFAULT_ALL) // Применяем все настройки по-умолчанию
            .setWhen(System.currentTimeMillis()) // Время для создания уведомления
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentIntent(resultPendingIntent)

        notificationManager.notify(1000, notificationBuilder.build())
    }
}