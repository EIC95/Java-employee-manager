package Database;
import UI.Interface;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Implémentation de l'interface Database utilisant SQLite
public class Databaseimpl implements Database {
    private Connection conn; // Connexion à la base de données

    // Constructeur pour initialiser la connexion à la base de données
    public Databaseimpl() {
        try {
            String url = "jdbc:sqlite:./Employee.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connexion réussie à la base de données");

            // Création de la table des employés si elle n'existe pas
            String creationDeTable = "CREATE TABLE IF NOT EXISTS employees (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "department TEXT, " +
                    "salary DOUBLE)";
            Statement statement = conn.createStatement();
            statement.executeUpdate(creationDeTable);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données: " + e.getMessage());
        }
    }

    // Ajouter un employé à la base de données
    @Override
    public void addEmployee(String name, String department, double salary) {
        try {
            String requete = "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(requete);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, department);
            preparedStatement.setDouble(3, salary);
            preparedStatement.executeUpdate();
            Interface.champNom.setText("");
            Interface.champDepartement.setSelectedIndex(0);
            Interface.champSalaire.setText("");
            System.out.println("Employé ajouté avec succès");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de l'employé: " + e.getMessage());
        }
    }

    // Supprimer un employé de la base de données
    @Override
    public void deleteEmployee(int id) {
        try {
            // Suppression de l'employé
            String requete = "DELETE FROM employees WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(requete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            for(int i = id + 1; i <= Interface.tableau.getRowCount(); i++) {
                String updateQuery = "UPDATE employees SET id = id - 1 WHERE id = ?";
                PreparedStatement updatePs = conn.prepareStatement(updateQuery);
                updatePs.setInt(1, i);
                updatePs.executeUpdate();
            }

            // Récupérer le plus grand ID restant
            String maxIdQuery = "SELECT MAX(id) FROM employees";
            PreparedStatement maxIdPs = conn.prepareStatement(maxIdQuery);
            ResultSet rs = maxIdPs.executeQuery();
            int nextAutoIncrement = 1; // Valeur par défaut si la table est vide

            if (rs.next() && rs.getObject(1) != null) {
                nextAutoIncrement = rs.getInt(1) + 1;
            }

            // Mettre à jour l'auto-incrémentation
            String resetAutoIncrement = "ALTER TABLE employees AUTO_INCREMENT = ?";
            PreparedStatement resetStatement = conn.prepareStatement(resetAutoIncrement);
            resetStatement.setInt(1, nextAutoIncrement);
            resetStatement.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de l'employé: " + e.getMessage());
        }
    }

    // Mettre à jour les informations d'un employé
    @Override
    public void updateEmployee(int id, String name, String department, double salary) {
        try {
            String requete = "UPDATE employees SET name = ?, department = ?, salary = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(requete);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, department);
            preparedStatement.setDouble(3, salary);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            System.out.println("Employé modifié avec succès");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification de l'employé: " + e.getMessage());
        }
    }

    // Afficher la table des employés
    @Override
    public void showTable() {
        try {
            String requete = "SELECT * FROM employees";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);

            DefaultTableModel model = (DefaultTableModel) Interface.tableau.getModel();
            model.setRowCount(0);

            // Ajouter les employés au modèle de table
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");
                double salary = resultSet.getDouble("salary");
                model.addRow(new Object[]{id, name, department, salary});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'affichage de la table: " + e.getMessage());
        }
    }
}