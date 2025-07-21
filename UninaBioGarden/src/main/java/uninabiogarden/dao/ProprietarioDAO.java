package uninabiogarden.dao;

import java.sql.*;
import uninabiogarden.entities.Proprietario;

public class ProprietarioDAO {
    private DatabaseManager databaseManager;
    
    public ProprietarioDAO() {
        databaseManager = DatabaseManager.getInstance();
    }

    public boolean checkProprietarioExists(String email, String password) {
        String query = "SELECT 1 FROM proprietario WHERE email = ? AND password = ?";
        Connection conn = null;
        try {
            conn= databaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
            
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
