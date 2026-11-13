package com.example.presencemanager

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.ContextMenu
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {

    private var actionMode: ActionMode? = null

    // Callback pour le Contextual Action Mode (CAB - TP04)
    private val cabCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_cab_dashboard, menu)
            mode.title = getString(R.string.menu_cab_titre)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean = false

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_afficher -> {
                    AlertDialog.Builder(this@DashboardActivity)
                        .setTitle(R.string.menu_cab_afficher)
                        .setMessage(R.string.msg_cab_afficher)
                        .setPositiveButton(R.string.btn_ok, null)
                        .show()
                    true
                }
                R.id.action_supprimer -> {
                    Toast.makeText(this@DashboardActivity, R.string.msg_cab_supprimer, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_terminer -> {
                    mode.finish()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutDashboard)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuration de la Toolbar pour le Menu d'options (TP04 - Étape 1)
        val toolbar = findViewById<Toolbar>(R.id.toolbarDashboard)
        setSupportActionBar(toolbar)

        // Récupération des 4 cartes
        val cardGroupes = findViewById<View>(R.id.cardGroupes)
        val cardAppel = findViewById<View>(R.id.cardAppel)
        val cardHistorique = findViewById<View>(R.id.cardHistorique)
        val cardStatistiques = findViewById<View>(R.id.cardStatistiques)

        // Clic simple sur chaque carte pour la navigation
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

        // Enregistrement des vues pour le Menu Contextuel Flottant (TP04 - Étape 3)
        registerForContextMenu(cardGroupes)
        registerForContextMenu(cardAppel)
        registerForContextMenu(cardHistorique)
        registerForContextMenu(cardStatistiques)

        // Bouton CAB (TP04 - Étape 4 & 5)
        val btnCab = findViewById<Button>(R.id.btnCab)
        btnCab.setOnClickListener {
            if (actionMode == null) {
                actionMode = startSupportActionMode(cabCallback)
            }
        }

        // Bouton Pop-up (TP04 - Étape 6)
        val btnPopup = findViewById<Button>(R.id.btnPopup)
        btnPopup.setOnClickListener {
            val popupMenu = PopupMenu(this, btnPopup)
            popupMenu.menuInflater.inflate(R.menu.menu_popup_dashboard, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_ouvrir_groupes -> {
                        startActivity(Intent(this, GroupesActivity::class.java))
                        true
                    }
                    R.id.action_informations -> {
                        AlertDialog.Builder(this)
                            .setTitle(R.string.dashboard_app_name)
                            .setMessage(R.string.msg_popup_info)
                            .setPositiveButton(R.string.btn_ok, null)
                            .show()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        // Double-clic sur l'icône du profil imgProfil (TP03)
        val imgProfil = findViewById<ImageView>(R.id.imgProfil)
        val detecteur = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean = true

            override fun onDoubleTap(e: MotionEvent): Boolean {
                afficherApropos()
                return true
            }
        })

        imgProfil.setOnTouchListener { v, evenement ->
            if (detecteur.onTouchEvent(evenement)) {
                true
            } else {
                v.performClick()
            }
        }

        // Informations du développeur via txtFooterDashboard (TP03)
        val txtFooterDashboard = findViewById<TextView>(R.id.txtFooterDashboard)
        txtFooterDashboard.setOnClickListener {
            afficherInfosDeveloppeur()
        }
    }

    // --- Menu d'options (TP04 - Étape 1) ---
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_options_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_accueil -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                true
            }
            R.id.action_groupes -> {
                startActivity(Intent(this, GroupesActivity::class.java))
                true
            }
            R.id.action_appel -> {
                startActivity(Intent(this, AppelActivity::class.java))
                true
            }
            R.id.action_apropos -> {
                afficherApropos()
                true
            }
            R.id.action_quitter -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // --- Menu Contextuel Flottant (TP04 - Étape 3) ---
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context_dashboard, menu)
        menu?.setHeaderTitle(getString(R.string.menu_ctx_titre))
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_ctx_consulter -> {
                Toast.makeText(this, R.string.menu_ctx_consulter, Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_ctx_partager -> {
                Toast.makeText(this, R.string.menu_ctx_partager, Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_ctx_supprimer -> {
                Toast.makeText(this, R.string.menu_ctx_supprimer, Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun afficherApropos() {
        AlertDialog.Builder(this)
            .setTitle(R.string.apropos_titre)
            .setMessage(R.string.apropos_message)
            .setPositiveButton(R.string.btn_ok, null)
            .show()
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
