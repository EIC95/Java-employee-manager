package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import Database.*;

public class Interface {
    public Interface() {
        Database db = new Databaseimpl();
        db.showTable();

        JFrame frame = new JFrame("Gestion des employés");
        frame.setSize(720, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(titreNom);
        inputPanel.add(champNom);
        inputPanel.add(titreDepartement);
        inputPanel.add(champDepartement);
        inputPanel.add(titreSalaire);
        inputPanel.add(champSalaire);
        panel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(230, 230, 230));
        bouttonAjouter.setBackground(new Color(90, 144, 201));
        bouttonAjouter.setForeground(Color.WHITE);
        bouttonSupprimer.setBackground(new Color(173, 7, 23));
        bouttonSupprimer.setForeground(Color.WHITE);
        bouttonMaj.setBackground(new Color(90, 144, 201));
        bouttonMaj.setForeground(Color.WHITE);
        bouttonEffacer.setBackground(new Color(108, 117, 125));
        bouttonEffacer.setForeground(Color.WHITE);
        buttonPanel.add(bouttonAjouter);
        buttonPanel.add(bouttonSupprimer);
        buttonPanel.add(bouttonMaj);
        buttonPanel.add(bouttonEffacer);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(tableau);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tableau.setFont(new Font("Arial", Font.PLAIN, 14));
        tableau.setRowHeight(25);
        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableau.getTableHeader().setBackground(new Color(90, 144, 201));
        tableau.getTableHeader().setForeground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.EAST);

        frame.add(panel);

        bouttonAjouter.addActionListener(e -> {
            String name = champNom.getText();
            String departement = (String) champDepartement.getSelectedItem();
            double salaire = Double.parseDouble(champSalaire.getText().replaceAll("\\s+", ""));
            db.addEmployee(name, departement, salaire);
            model.setRowCount(0);
            db.showTable();
        });

        bouttonEffacer.addActionListener(e -> {
            champNom.setText("");
            champDepartement.setSelectedIndex(0);
            champSalaire.setText("");
        });

        bouttonSupprimer.addActionListener(e -> {
            int row = tableau.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé à supprimer");
            } else {
                int id = (int) tableau.getValueAt(row, 0);
                db.deleteEmployee(id);
                model.setRowCount(0);
                db.showTable();
            }
        });

        bouttonMaj.addActionListener(e -> {
            int row = tableau.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé à modifier");
            } else {
                int id = (int) tableau.getValueAt(row, 0);
                String name = champNom.getText();
                String departement = (String) champDepartement.getSelectedItem();
                double salaire = Double.parseDouble(champSalaire.getText());
                db.updateEmployee(id, name, departement, salaire);
                model.setRowCount(0);
                db.showTable();
            }
        });
    }

    JLabel titreNom = new JLabel("Nom : ");
    public static JTextField champNom = new JTextField();

    JLabel titreDepartement = new JLabel("Département : ");
    public static String[] departements = {"Informatique", "Ressources Humaines", "Comptabilité", "Marketing"};
    public static JComboBox<String> champDepartement = new JComboBox<>(departements);

    JLabel titreSalaire = new JLabel("Salaire : ");
    public static JTextField champSalaire = new JTextField();

    JButton bouttonAjouter = new JButton("Ajouter");
    JButton bouttonSupprimer = new JButton("Supprimer");
    JButton bouttonMaj = new JButton("Modifier");
    JButton bouttonEffacer = new JButton("Effacer");

    public static String[] colomnes = {"Id", "Nom", "Département", "Salaire"};
    public static DefaultTableModel model = new DefaultTableModel(colomnes, 0);
    public static JTable tableau = new JTable(model);
}
