package uninabiogarden.dao;

import java.sql.*;

import uninabiogarden.entities.Coltivatore;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class ColtivatoreDao {

  public static Coltivatore exists(String username, String password) throws WrongUsernameException, WrongPasswordException {
    return (Coltivatore) UtenteDao.exists(username, password, "coltivatore");
  }

  public static boolean add(Coltivatore coltivatore) {
    var sql = "INSERT INTO coltivatore * " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (var conn = Database.connect();
      var stmt = conn.prepareStatement(sql)){
      stmt.setString(1, coltivatore.getUsername());
      stmt.setString(2, coltivatore.getPassword());
      stmt.setString(3, coltivatore.getNome());
      stmt.setString(4, coltivatore.getCognome());
      stmt.setDate(5,  Date.valueOf(coltivatore.getBday()));
      stmt.setString(6, coltivatore.getNationality());
      stmt.setString(7, coltivatore.getEmail());
      stmt.setString(8, coltivatore.getNumTel());
      stmt.setString(9, coltivatore.getResidenza());

      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return false;
    }
    return true;
  }

}
