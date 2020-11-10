package com.example.myapplication.wsnotification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.myapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import timber.log.Timber


// Сервис, который следит за поступлением новых сообщений в фоне
class NotificationService : Service() {
    var wsService: WebSocket? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.d("СЕРВИС_Уведомлений_СТАРТОВАЛ")

        //Получим токен пользователя
        val token=intent.getStringExtra("Token")
        if (token.isNullOrEmpty()) {
            Timber.d("ТокенПустой")
        }

        val client = OkHttpClient()
        // Создаем WebSocket, который работает в фоне
        val webSocketUrl= BuildConfig.url_socket
        Timber.d("WebSockect $webSocketUrl")
        val request =
            Request.Builder()
                .url(webSocketUrl)
                .addHeader("Sec-Websocket-Protocol",token)
                .build()
        val listener = EchoWebSocketListener(this)
        wsService = client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("СЕРВИС_ОСТАНОВЛЕН")

    }

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
}


