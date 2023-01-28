package org.hyperskill.secretdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        val logBtn = findViewById<TextView>(R.id.btnLogin)
        val pin = findViewById<EditText>(R.id.etPin)
        logBtn.setOnClickListener {if(pin.text.toString() == "1234") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            pin.error = "Wrong PIN!"
        }
        }
    }
}