package com.arash.altafi.websocket5.utils

import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
import java.util.concurrent.TimeUnit

class WebSocketClient(
    private val url: String,
    private val statusHandler: (Boolean) -> Unit,
    private val messageHandler: (String) -> Unit
) {

    private lateinit var webSocket: WebSocket
    private val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .pingInterval(10, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    fun connect() {
        val request = Request.Builder()
            .url(url)
            .build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.i(TAG, "WebSocket connected to $url")
                statusHandler(true)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.i(TAG, "Received message: $text")
                messageHandler(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.i(TAG, "Received bytes: ${bytes.hex()}")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.i(TAG, "WebSocket closing: code=$code, reason=$reason")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.i(TAG, "WebSocket closed: code=$code, reason=$reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.i(TAG, "WebSocket failed: ${t.message}")
                statusHandler(false)
            }
        })
    }

    fun send(message: String) {
        webSocket.send(message)
    }

    fun disconnect() {
        webSocket.close(1000, null)
    }

    private companion object {
        const val TAG = "WebSocketClient"
    }
}