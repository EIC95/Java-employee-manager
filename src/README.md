# Application de Gestion des Employés

Ceci est une application simple de gestion des employés construite avec Java et SQLite. L'application permet d'ajouter, de mettre à jour, de supprimer et de visualiser les employés dans une base de données.

## Fonctionnalités

- Ajouter de nouveaux employés
- Mettre à jour les employés existants
- Supprimer des employés
- Voir tous les employés dans un tableau

## Technologies Utilisées

- Java
- SQLite
- Swing pour l'interface graphique

## Prise en Main

### Prérequis

- Java Development Kit (JDK) 8 ou supérieur
- Un IDE tel que IntelliJ IDEA

### Installation

1. Cloner le dépôt :
    ```sh
    git clone https://github.com/yourusername/employee-management.git
    ```
2. Ouvrir le projet dans votre IDE.

### Exécution de l'Application

1. Naviguer vers le répertoire `src`.
2. Exécuter le fichier `Main.java`.

### Utilisation

- **Ajouter un Employé** : Remplir les détails de l'employé et cliquer sur le bouton "Ajouter".
- **Mettre à Jour un Employé** : Sélectionner un employé dans le tableau, modifier les détails, et cliquer sur le bouton "Modifier".
- **Supprimer un Employé** : Sélectionner un employé dans le tableau et cliquer sur le bouton "Supprimer".
- **Effacer les Champs** : Cliquer sur le bouton "Effacer" pour vider les champs de saisie.

## Structure du Projet

- `src/Database/Database.java` : Interface pour les opérations de base de données.
- `src/Database/Databaseimpl.java` : Implémentation des opérations de base de données utilisant SQLite.
- `src/UI/Interface.java` : Implémentation de l'interface graphique utilisant Swing.
- `src/Main.java` : Classe principale pour exécuter l'application.

## Licence

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.