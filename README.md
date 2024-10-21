# Mini_projet_gestion_des_etudiants

## Description

Ce projet montre comment créer et consommer des services web en PHP depuis une application mobile. Le projet est divisé en deux parties :
1. Une application backend en PHP permettant de gérer les étudiants à travers une base de données.
2. Une application mobile Android qui communique avec le backend en utilisant la bibliothèque Volley pour envoyer des requêtes HTTP et traiter les réponses.

Le projet inclut des fonctionnalités de création, modification, suppression et récupération des informations des étudiants, le tout implémenté en PHP et consommé via des services web RESTful dans une application Android.

## Fonctionnalités

### Application Web
- **Gestion des Étudiants** : Ajouter, modifier, supprimer et afficher des étudiants.
- **Services Web** : Fournit des services pour ajouter et charger des étudiants sous forme de JSON.

### Application Mobile (Android)
- **Ajouter un étudiant** : Ajouter un nouvel étudiant via un formulaire.
- **Afficher la liste des étudiants** : Récupérer et afficher la liste des étudiants.
- **Modifier/Supprimer un étudiant** : Modifier ou supprimer un étudiant de la liste.

## Prérequis

- **XAMPP** (ou tout autre serveur LAMP) : Pour exécuter le serveur PHP et la base de données MySQL.
- **NetBeans** ou tout autre IDE PHP : Pour développer l'application backend.
- **Android Studio** : Pour développer et exécuter l'application Android.
- **PostMan** : Pour tester les services web.
- **Un appareil Android ou un émulateur** : Pour exécuter l'application mobile.

## Installation

### Backend (Services Web PHP)

1. **Installer XAMPP** :
   - Télécharger et installer [XAMPP](https://www.apachefriends.org/fr/index.html).
   - Lancer les services Apache et MySQL.

2. **Créer la base de données** :
   - Accéder à [phpMyAdmin](http://localhost/phpmyadmin).
   - Créer une nouvelle base de données nommée `school1`.
   - Dans la base de données `school1`, créer une table nommée `Etudiant` avec les colonnes suivantes : `id`, `nom`, `prenom`, `ville`, `sexe`.

3. **Configurer le projet** :
   - Créer un nouveau projet PHP dans NetBeans ou votre IDE préféré.
   - Ajouter les fichiers PHP fournis dans le projet :
     - `racine.php`
     - Classe `Etudiant`
     - Classe `Connexion`
     - Interface `IDao`
     - Classe `EtudiantService`
     - Fichier `index.php`
     - Services web (`createEtudiant.php`, `loadEtudiant.php`)
   - Configurer les détails de la connexion à la base de données dans le fichier `Connexion.php`.

4. **Exécuter le serveur web** :
   - Placer le projet dans le dossier `htdocs` de XAMPP (`C:\xampp\htdocs\projet`).
   - Accéder au projet via [http://localhost/projet](http://localhost/projet).

### Application Mobile (Android)

1. **Configurer Android Studio** :
   - Télécharger et installer [Android Studio](https://developer.android.com/studio?hl=fr).
   - Créer un nouveau projet nommé `projetws`.

2. **Configurer les permissions** :
   - Ajouter la permission internet dans le fichier `AndroidManifest.xml` :
     ```xml
     <uses-permission android:name="android.permission.INTERNET" />
     ```

3. **Ajouter la dépendance Volley** :
   - Dans le fichier `build.gradle`, ajouter la dépendance suivante :
     ```gradle
     implementation 'com.mcxiaoke.volley:library:1.0.19'
     ```

4. **Créer l'interface utilisateur** :
   - Créer les fichiers de mise en page comme `activity_add_etudiant.xml` pour le formulaire d'ajout d'étudiant.
   - Concevoir une `ListView` ou `RecyclerView` pour afficher la liste des étudiants.

5. **Implémenter les classes Java** :
   - Créer les classes `AddEtudiant.java` et `Etudiant.java` pour gérer les données des étudiants.
   - Utiliser Volley pour envoyer des requêtes HTTP POST vers les services web PHP pour créer et récupérer les étudiants.

6. **Exécuter l'application** :
   - Connecter votre appareil Android ou utiliser un émulateur.
   - Exécuter l'application et tester l'ajout, la récupération et la modification des étudiants.

## Demonstration
https://github.com/user-attachments/assets/403ab722-6ecd-4233-9206-0df0e8e5e80f

