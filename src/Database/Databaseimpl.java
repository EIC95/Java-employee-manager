package Database;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import UI.Interface;

public class Databaseimpl implements Database {
    private Connection conn;

    public Databaseimpl() {
        try {
            String url = "jdbc:sqlite:./employees.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connexion réussie à la base de données");

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

    @Override
    public void deleteEmployee(int id) {
        try {
            String requete = "DELETE FROM employees WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(requete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Employé supprimé avec succès");

            String updateQuery = "UPDATE employees SET id = id - 1 WHERE id > ?";
            PreparedStatement updatePs = conn.prepareStatement(updateQuery);
            updatePs.setInt(1, id);
            updatePs.executeUpdate();

            String checkEmptyQuery = "SELECT COUNT(*) AS rowcount FROM employees";
            Statement checkStmt = conn.createStatement();
            ResultSet checkRs = checkStmt.executeQuery(checkEmptyQuery);
            checkRs.next();
            int count = checkRs.getInt("rowcount");
            if (count == 0) {
                String resetAutoincrementQuery = "DELETE FROM sqlite_sequence WHERE name='employees'";
                Statement resetStmt = conn.createStatement();
                resetStmt.executeUpdate(resetAutoincrementQuery);
            }

            String resetAutoincrementQuery = "DELETE FROM sqlite_sequence WHERE name='employees'";
            Statement statement = conn.createStatement();
            statement.executeUpdate(resetAutoincrementQuery);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de l'employé: " + e.getMessage());
        }
    }

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

    @Override
    public void showTable() {
        try {
            String requete = "SELECT * FROM employees";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);

            DefaultTableModel model = (DefaultTableModel) Interface.tableau.getModel();
            model.setRowCount(0);

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
