package uninabiogarden.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String dbUrl = "jdbc:postgresql://localhost:5432/UninaBioGarden";
    private static final String dbUser = "ubg_user";
    private static final String dbPassword = "101010";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException  e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}