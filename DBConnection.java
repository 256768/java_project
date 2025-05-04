import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {

    private static volatile Connection dbConnection;

    private DBConnection() {}

    public static Connection getDBConnection() {
        if (dbConnection == null) {
            synchronized (DBConnection.class) {
                if (dbConnection == null) {
                    try {
                    
                        File dir = new File("db");
                        if (!dir.exists()) {
                            dir.mkdirs();  
                        }

                        Class.forName("org.sqlite.JDBC");
                        dbConnection = DriverManager.getConnection("jdbc:sqlite:db/user.db");
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return dbConnection;
    }

    public static void closeConnection() {
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setupUserTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS user (
                id INTEGER PRIMARY KEY NOT NULL,
                firstname VARCHAR(50),
                lastname VARCHAR(50),
                birthyear INTEGER,
                type VARCHAR(20),
                average INTEGER
            );
        """;

        try (PreparedStatement pstmt = getDBConnection().prepareStatement(createTableSQL)) {
            pstmt.executeUpdate();
            System.out.println("Tabel 'user' already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating table 'user':");
            e.printStackTrace();
        }
    }
}
