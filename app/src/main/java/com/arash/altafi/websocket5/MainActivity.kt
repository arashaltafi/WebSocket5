package com.arash.altafi.websocket5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.arash.altafi.websocket5.databinding.ActivityMainBinding
import com.arash.altafi.websocket5.model.NotificationResponse
import com.arash.altafi.websocket5.utils.NotificationUtils
import com.arash.altafi.websocket5.utils.WebSocketClient
import com.arash.altafi.websocket5.utils.getBitmap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var webSocket: WebSocketClient? = null
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() = binding.apply {
        setupWebSocket()

        btnSend.setOnClickListener {
            webSocket?.send(
                "{\"title\":\"arash\",\"description\":\"altafi\",\"image\":\"https://www" +
                        ".arashaltafi.ir/arash.jpg\",\"to\":\"95\"}"
            )
        }
    }

    private fun setupWebSocket() = binding.apply {
        webSocket = WebSocketClient(URL, {
            chkStatus.isChecked = it
            Log.i("WebSocketClient", "status: $it")
        }) { message ->
            runOnUiThread {
                // Update textview with incoming message
                Log.i("WebSocketClient", "message: $message")
                val notifResponse =
                    gson.fromJson<NotificationResponse>(
                        message,
                        object : TypeToken<NotificationResponse>() {}.type
                    )

                notifResponse?.let { notif ->
                    getBitmap(url = notif.image, result = { bitmap ->
                        NotificationUtils.sendNotification(
                            this@MainActivity,
                            (1..999999).random(),
                            notif.title,
                            notif.description,
                            bitmap
                        )
                    })
                }
            }
        }
        webSocket?.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocket?.disconnect()
    }

    private companion object {
        const val URL = "ws://192.168.1.101:8080"
    }
}