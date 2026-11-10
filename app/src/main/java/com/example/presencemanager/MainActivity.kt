package com.example.presencemanager

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutAccueil)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnCommencer = findViewById<Button>(R.id.btnCommencer)
        btnCommencer.setOnClickListener {
            val intention = Intent(this, DashboardActivity::class.java)
            startActivity(intention)
        }

        findViewById<TextView>(R.id.txtFooter).setOnClickListener {
            afficherInfosDeveloppeur()
        }
    }

    private fun afficherInfosDeveloppeur() {
        val dialogue = AlertDialog.Builder(this)
            .setTitle(R.string.dev_titre)
            .setMessage(R.string.dev_message)
            .setPositiveButton(R.string.btn_ok, null)
            .show()

        val messageView = dialogue.findViewById<TextView>(android.R.id.message)
        messageView?.let { texte ->
            Linkify.addLinks(texte, Linkify.WEB_URLS)
            texte.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
