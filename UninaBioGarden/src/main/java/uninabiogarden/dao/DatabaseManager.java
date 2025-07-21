package uninabiogarden.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/UninaBioGarden";
    private static final String USER = "postgres";
    private static final String PASSWORD = "110502";
    
    private static DatabaseManager instance;
    private Connection connection;

    public static ProprietarioDAO proprietarioDAO= new ProprietarioDAO();
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}