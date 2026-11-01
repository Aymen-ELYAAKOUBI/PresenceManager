# 📱 Présence Manager - TP01

> **Module :** M203 - Interfaces utilisateurs  
> **Apprentissage :** A.1 - Éléments graphiques  
> **Technologies :** Kotlin + XML (Android Studio)  

---

## 📋 Table des matières
1. [À propos du projet](#-à-propos-du-projet)
2. [Structure et Design](#-structure-et-design)
3. [Composants et Identifiants](#-composants-et-identifiants)
4. [Captures d'écran](#-captures-décran)
5. [Installation](#-installation)

---

## 🚀 À propos du projet
**Présence Manager** est une application Android conçue pour les établissements de formation afin de faciliter la gestion des présences et des absences des stagiaires.

Dans ce premier travail pratique (**TP01**), l'objectif est de concevoir l'interface graphique de l'écran d'accueil (`MainActivity`) en utilisant des dispositions XML, en respectant les dimensions, couleurs, marges et alignements imposés.

---

## 🎨 Structure et Design

L'interface repose sur une structure `LinearLayout` :
* **Layout Principal :** `LinearLayout` vertical avec un padding de `24dp` et un fond `#F8FAFC`.
* **Contenu Central :** `LinearLayout` vertical centré (`layout_weight="1"`), regroupant le logo, le titre, la description et le bouton.
* **Pied de page :** `TextView` fixé en bas avec le nom de l'application et la version.

### Couleurs et Typographies
* **Titre :** `#1E3A5F` (Gras, `28sp`)
* **Description :** `#64748B` (`16sp`)
* **Bouton :** Fond `#2563EB`, Texte blanc (Gras, `16sp`, Hauteur: `56dp`)
* **Pied de page :** `#94A3B8` (`12sp`)

---

## 🏷️ Composants et Identifiants

| Composant | Élément XML | Identifiant (ID) |
| :--- | :--- | :--- |
| **Layout principal** | `LinearLayout` | `@+id/layoutAccueil` |
| **Logo** | `ImageView` | `@+id/imgLogo` |
| **Titre** | `TextView` | `@+id/txtTitre` |
| **Description** | `TextView` | `@+id/txtDescription` |
| **Bouton** | `Button` | `@+id/btnCommernicer` |
| **Pied de page** | `TextView` | `@+id/txtFooter` |

---

## 📸 Captures d'écran

| Maquette de référence | Résultat obtenu |
| :---: | :---: |
| <img src="screenshots/maquette.png" width="250"/> | <img src="screenshots/resultat.png" width="250"/> |

---

## ⚙️ Installation

1. **Cloner le dépôt :**
   ```bash
   git clone [https://github.com/VOTRE_NOM_UTILISATEUR/PresenceManager.git](https://github.com/VOTRE_NOM_UTILISATEUR/PresenceManager.git)
