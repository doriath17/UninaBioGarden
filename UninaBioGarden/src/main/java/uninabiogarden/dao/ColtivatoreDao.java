package uninabiogarden.dao;

import java.sql.*;

import uninabiogarden.entities.Coltivatore;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class ColtivatoreDao {

  private static final ColtivatoreDao instance = new ColtivatoreDao();
  private final Database database = Database.getInstance();

  private ColtivatoreDao() {}

  public static ColtivatoreDao getInstance() {
    return instance;
  }

  public Coltivatore authenticate(String username, String password) throws WrongUsernameException, WrongPasswordException, ConnectionFailedException {
    return (Coltivatore) UtenteDao.authenticate(database, username, password, "coltivatore");
  }

  public boolean add(Coltivatore coltivatore) throws ConnectionFailedException {
    var sql = "INSERT INTO coltivatore * " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (var conn = database.getConnection();
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

  public String insert(Coltivatore newColtivatore) throws SQLException, ConnectionFailedException {
    return UtenteDao.insert(database, newColtivatore, "coltivatore");
  }

}
