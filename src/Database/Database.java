package Database;

public interface Database {
    void addEmployee(String nom, String departement, double salaire);
    void deleteEmployee(int id);
    void updateEmployee(int id, String nom, String departement, double salaire);
    void showTable();
}
