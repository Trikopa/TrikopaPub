package org.hyperskill.secretdiary

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

const val PREFERENCES_NAME = "PREF_DIARY"
class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val saveBtn = findViewById<Button>(R.id.btnSave)
        val editText = findViewById<EditText>(R.id.etNewWriting)
        val savedDiary = findViewById<TextView>(R.id.tvDiary)
        val alertDialogButton = findViewById<Button>(R.id.btnUndo)
        var messages = mutableListOf<String>()
        savedDiary.text = sharedPreferences.getString("KEY_DIARY_TEXT","")
        saveBtn.setOnClickListener {
            val time = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
            val timeNow  = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).format(time).toString()
            val msg = editText.text.toString()
            if(msg.isBlank()) {
                Toast.makeText(applicationContext, "Empty or blank input cannot be saved", Toast.LENGTH_SHORT).show()
            } else {
                val lastSaved = savedDiary.text
                savedDiary.text = "$timeNow\n${editText.text}"
                messages.add(0,savedDiary.text.toString())
                if(lastSaved.isNotEmpty()) {
                    savedDiary.append("\n\n")
                }
                savedDiary.append(lastSaved)
                editText.text.clear()
                editor.putString("KEY_DIARY_TEXT",savedDiary.text.toString()).apply()
            }
        }
        alertDialogButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Remove last note")
                .setMessage("Do you really want to remove the last writing? This operation cannot be undone!")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    val retrievedString = sharedPreferences.getString("KEY_DIARY_TEXT","")
                    messages = retrievedString!!.split("\n\n").toMutableList()
                    messages.removeAt(0)
                    savedDiary.text = ""
                    for (i in messages.indices) {
                        if(i>0) {
                            savedDiary.append("\n\n")
                        }
                        savedDiary.append(messages[i])
                    }
                    editor.putString("KEY_DIARY_TEXT",savedDiary.text.toString()).apply()

                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }

    }
}




