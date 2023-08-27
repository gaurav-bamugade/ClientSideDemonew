package com.example.clientsidedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.clientsidedemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.net.Socket

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.buttonSend.setOnClickListener {
            sendMessage( )
        }
    }
    fun sendMessage( ) {
        CoroutineScope(Dispatchers.IO).launch {
            val serverAddress = "192.168.1.10"
            val portNumber = 9000

            val message = binding.editTextMessage.text.toString()

            try {
                val socket = Socket(serverAddress, portNumber)
                val outputStream: OutputStream = socket.getOutputStream()
                outputStream.write(message.toByteArray())
                outputStream.flush()
                println("Message sent to server: $message")
                socket.close()
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}