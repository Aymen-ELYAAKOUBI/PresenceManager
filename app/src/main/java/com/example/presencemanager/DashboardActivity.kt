package com.example.presencemanager

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutDashboard)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        // Récupération des 4 cartes
        val cardGroupes = findViewById<View>(R.id.cardGroupes)
        val cardAppel = findViewById<View>(R.id.cardAppel)
        val cardHistorique = findViewById<View>(R.id.cardHistorique)
        val cardStatistiques = findViewById<View>(R.id.cardStatistiques)

        // Étape 5 — Clic simple sur chaque carte
        cardGroupes.setOnClickListener {
            startActivity(Intent(this, GroupesActivity::class.java))
        }
        cardAppel.setOnClickListener {
            startActivity(Intent(this, AppelActivity::class.java))
        }
        cardHistorique.setOnClickListener {
            startActivity(Intent(this, HistoriqueActivity::class.java))
        }
        cardStatistiques.setOnClickListener {
            startActivity(Intent(this, StatistiquesActivity::class.java))
        }

        // Étape 6 — Appui long sur cardGroupes et cardAppel
        cardGroupes.setOnLongClickListener {
            Toast.makeText(this, "Groupes : sélectionner ou consulter les groupes", Toast.LENGTH_SHORT).show()
            true
        }
        cardAppel.setOnLongClickListener {
            Toast.makeText(this, "Faire l'appel : démarrer une séance", Toast.LENGTH_SHORT).show()
            true
        }

        // Étape 7 — Double-clic sur l'icône du profil imgProfil
        val imgProfil = findViewById<ImageView>(R.id.imgProfil)
        val detecteur = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean = true

            override fun onDoubleTap(e: MotionEvent): Boolean {
                AlertDialog.Builder(this@DashboardActivity)
                    .setTitle("À propos de PrésenceManager")
                    .setMessage("Application de gestion des présences — Version 1.0")
                    .setPositiveButton("OK", null)
                    .show()
                return true
            }
        })

        imgProfil.setOnTouchListener { _, evenement ->
            detecteur.onTouchEvent(evenement)
            true
        }

        // Étape 8 — Informations du développeur via txtFooterDashboard
        val txtFooterDashboard = findViewById<TextView>(R.id.txtFooterDashboard)
        txtFooterDashboard.setOnClickListener {
            afficherInfosDeveloppeur()
        }
    }

    private fun afficherInfosDeveloppeur() {
        val dialogue = AlertDialog.Builder(this)
            .setTitle("Informations du développeur")
            .setMessage(
                "Application : PrésenceManager\n" +
                "Version : 1.0\n" +
                "Développeur : Aymen El yaakoubi\n" +
                "GitHub : https://github.com/Aymen5285356/PresenceManager"
            )
            .setPositiveButton("OK", null)
            .show()

        val messageView = dialogue.findViewById<TextView>(android.R.id.message)
        messageView?.let { texte ->
            Linkify.addLinks(texte, Linkify.WEB_URLS)
            texte.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
