// Importez les classes nécessaires
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestH2 {
    public static void main(String[] args) {
        String url = "jdbc:h2:mem:testdb";
        String user = "sa";
        String password = "";

        try {
            System.out.println("Connexion à la base de données...");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connecté !");

            System.out.println("Création de la table...");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE clients ("
                    + "id INT PRIMARY KEY,"
                    + "nom VARCHAR(50),"
                    + "email VARCHAR(100)"
                    + ")");
            System.out.println("Table créée !");

            System.out.println("Insertion des données...");
            stmt.execute("INSERT INTO clients VALUES (1, 'Jean Dupont', 'jean@email.com')");
            stmt.execute("INSERT INTO clients VALUES (2, 'Marie Martin', 'marie@email.com')");
            System.out.println("Données insérées !");

            System.out.println("\nLecture des données :");
            ResultSet rs = stmt.executeQuery("SELECT * FROM clients");
            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("id")
                                + ", Nom: " + rs.getString("nom")
                                + ", Email: " + rs.getString("email")
                );
            }

            rs.close();
            stmt.close();
            conn.close();
            System.out.println("\nConnexions fermées !");

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}