package Database;

// Interface pour les opérations de base de données
public interface Database {
    void addEmployee(String nom, String departement, double salaire); // Ajouter un employé
    void deleteEmployee(int id); // Supprimer un employé
    void updateEmployee(int id, String nom, String departement, double salaire); // Mettre à jour un employé
    void showTable(); // Afficher la table des employés
}