package com.example.presencemanager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GroupesActivity : AppCompatActivity() {

    private lateinit var listeGroupes: List<GroupeCMC>
    private lateinit var listView: ListView

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

        val btnArrayAdapter = findViewById<Button>(R.id.btnArrayAdapter)
        val btnBaseAdapter = findViewById<Button>(R.id.btnBaseAdapter)

        // Configuration initiale avec ArrayAdapter
        chargerAvecArrayAdapter()

        // Bouton Pill 1 : ArrayAdapter
        btnArrayAdapter.setOnClickListener {
            chargerAvecArrayAdapter()
            Toast.makeText(this, "Liste chargée avec ArrayAdapter", Toast.LENGTH_SHORT).show()
        }

        // Bouton Pill 2 : BaseAdapter
        btnBaseAdapter.setOnClickListener {
            chargerAvecBaseAdapter()
            Toast.makeText(this, "Liste chargée avec BaseAdapter", Toast.LENGTH_SHORT).show()
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

    private fun chargerAvecArrayAdapter() {
        val adapter = GroupeArrayAdapter(this, listeGroupes)
        listView.adapter = adapter
    }

    private fun chargerAvecBaseAdapter() {
        val adapter = GroupeBaseAdapter(this, listeGroupes)
        listView.adapter = adapter
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
