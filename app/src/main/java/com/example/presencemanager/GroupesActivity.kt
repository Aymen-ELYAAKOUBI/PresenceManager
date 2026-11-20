package com.example.presencemanager

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GroupesActivity : AppCompatActivity() {

    private lateinit var listeGroupes: List<GroupeCMC>
    private lateinit var listView: ListView

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
                    AlertDialog.Builder(this@GroupesActivity)
                        .setTitle(R.string.menu_cab_afficher)
                        .setMessage(R.string.msg_cab_afficher)
                        .setPositiveButton(R.string.btn_ok, null)
                        .show()
                    true
                }
                R.id.action_supprimer -> {
                    Toast.makeText(this@GroupesActivity, R.string.msg_cab_supprimer, Toast.LENGTH_SHORT).show()
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
        setContentView(R.layout.activity_groupes)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layoutGroupes)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        listeGroupes = listOf(
            GroupeCMC("CMC - DEV101", "Développement Digital", "1ère Année", 28, "Lab Mobile 1"),
            GroupeCMC("CMC - DEV102", "Développement Digital", "1ère Année", 26, "Lab Web 2"),
            GroupeCMC("CMC - DEV201", "Développement Digital (Option Mobile)", "2ème Année", 25, "Lab Mobile 2"),
            GroupeCMC("CMC - DEV202", "Développement Digital (Option Fullstack)", "2ème Année", 27, "Salle Info 3"),
            GroupeCMC("CMC - INF101", "Infrastructure Digitale", "1ère Année", 24, "Lab Réseaux"),
            GroupeCMC("CMC - AI101", "Intelligence Artificielle & Data", "1ère Année", 22, "Salle High-Tech"),
            GroupeCMC("CMC - CYBER101", "Cybersécurité & Cloud", "1ère Année", 20, "Lab Sécurité"),
            GroupeCMC("CMC - DES101", "Design Graphique & Digital", "1ère Année", 23, "Studio Design")
        )

        listView = findViewById(R.id.listGroupesCMC)

        val btnCabGroupes = findViewById<Button>(R.id.btnCabGroupes)
        val btnPopupGroupes = findViewById<Button>(R.id.btnPopupGroupes)

        // Charger avec ArrayAdapter
        val adapter = GroupeArrayAdapter(this, listeGroupes)
        listView.adapter = adapter

        // Bouton Activer CAB
        btnCabGroupes.setOnClickListener {
            if (actionMode == null) {
                actionMode = startSupportActionMode(cabCallback)
            }
        }

        // Bouton Menu Pop-up
        btnPopupGroupes.setOnClickListener {
            val popupMenu = PopupMenu(this, btnPopupGroupes)
            popupMenu.menuInflater.inflate(R.menu.menu_popup_dashboard, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_ouvrir_groupes -> {
                        Toast.makeText(this, "Vous êtes déjà dans la page Groupes", Toast.LENGTH_SHORT).show()
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

        listView.setOnItemClickListener { _, _, position, _ ->
            val groupe = listeGroupes[position]
            Toast.makeText(
                this,
                "Groupe sélectionné : ${groupe.code} (${groupe.effectif} stagiaires - ${groupe.salle})",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

// Custom ArrayAdapter
class GroupeArrayAdapter(
    context: Context,
    private val liste: List<GroupeCMC>
) : ArrayAdapter<GroupeCMC>(context, R.layout.item_groupe_cmc, liste) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_groupe_cmc, parent, false)
        val item = getItem(position)

        if (item != null) {
            val txtBadge = view.findViewById<TextView>(R.id.txtBadgeGroupe)
            val txtNom = view.findViewById<TextView>(R.id.txtNomGroupe)
            val txtFiliere = view.findViewById<TextView>(R.id.txtFiliereGroupe)
            val txtInfo = view.findViewById<TextView>(R.id.txtInfoGroupe)
            val txtEffectif = view.findViewById<TextView>(R.id.txtEffectifGroupe)

            val prefix = item.code.substringAfter("CMC - ").take(3)
            txtBadge.text = prefix
            txtNom.text = item.code
            txtFiliere.text = item.filiere
            txtInfo.text = "${item.annee} • ${item.salle}"
            txtEffectif.text = "${item.effectif} Stagiaires"
        }
        return view
    }
}

// Custom BaseAdapter
class GroupeBaseAdapter(
    private val context: Context,
    private val liste: List<GroupeCMC>
) : BaseAdapter() {

    override fun getCount(): Int = liste.size

    override fun getItem(position: Int): Any = liste[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_groupe_cmc, parent, false)
        val item = liste[position]

        val txtBadge = view.findViewById<TextView>(R.id.txtBadgeGroupe)
        val txtNom = view.findViewById<TextView>(R.id.txtNomGroupe)
        val txtFiliere = view.findViewById<TextView>(R.id.txtFiliereGroupe)
        val txtInfo = view.findViewById<TextView>(R.id.txtInfoGroupe)
        val txtEffectif = view.findViewById<TextView>(R.id.txtEffectifGroupe)

        val prefix = item.code.substringAfter("CMC - ").take(3)
        txtBadge.text = prefix
        txtNom.text = item.code
        txtFiliere.text = item.filiere
        txtInfo.text = "${item.annee} • ${item.salle}"
        txtEffectif.text = "${item.effectif} Stagiaires"

        return view
    }
}
