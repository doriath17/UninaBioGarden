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

    //Qua volendo si puo creare anche prima un proprietario, e poi lo si passa alla funzione
    public boolean addProprietario(String username, String email, String password, String nome, String cognome, Date data_di_nascita, String residenza, String nazionalità, String num_telefono) {
        String query = "INSERT INTO proprietario (username, email, password, nome, cognome, data_di_nascita, residenza, nazionalità, num_telefono) VALUES (?, ?, ?, ? , ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = databaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, nome);
            ps.setString(5, cognome);
            ps.setDate(6, data_di_nascita);
            ps.setString(7, residenza);
            ps.setString(8, nazionalità);
            ps.setString(9, num_telefono);
            
            int a = ps.executeUpdate();
            return a > 0;

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

    // public boolean addProprietario (Proprietario proprietario){

    // }


}
