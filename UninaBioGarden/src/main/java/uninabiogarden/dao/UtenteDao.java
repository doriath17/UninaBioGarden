package uninabiogarden.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class UtenteDao {

  static Utente authenticate(Database database, String username, String password, String table) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException {
    var sql = "SELECT * FROM "+table+" WHERE username='"+username+"'";

    try (var conn = database.getConnection();
      var stmt = conn.createStatement()){

      var result = stmt.executeQuery(sql);
      result.next();
      if (result.getString(1) == null) {
        throw new WrongUsernameException();
      } else if (!result.getString(2).equals(password)) {
        throw new WrongPasswordException();
      } else {
        if (table.equals("proprietario")) {
          return new Proprietario(
            result.getString("username"),
            result.getString("password"),
            result.getString("email"),
            result.getString("nome"),
            result.getString("cognome"),
            result.getDate("bday").toLocalDate(),
            result.getString("nazionalita"),
            result.getString("num_tel"),
            result.getString("residenza")
          );
        } else {
          return new Coltivatore(
            result.getString("username"),
            result.getString("password"),
            result.getString("email"),
            result.getString("nome"),
            result.getString("cognome"),
            result.getDate("bday").toLocalDate(),
            result.getString("nazionalita"),
            result.getString("num_tel"),
            result.getString("residenza")
          );
        }
      }
    } catch (SQLException e) {
      throw new WrongUsernameException();
    }
  }

  static String insert(Database database, Utente newUtente, String table) throws SQLException, ConnectionFailedException {
    var sql = 
      "INSERT INTO "+table+" (username, password, email, nome, cognome, bday, nazionalita, num_tel, residenza) VALUES " +
      "(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (var conn = database.getConnection();
      var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, newUtente.getUsername());
      stmt.setString(2, newUtente.getPassword());
      stmt.setString(3, newUtente.getEmail());
      stmt.setString(4, newUtente.getNome());
      stmt.setString(5, newUtente.getCognome());
      stmt.setDate(6, Date.valueOf(newUtente.getBday()));
      stmt.setString(7, newUtente.getNationality());
      stmt.setString(8, newUtente.getNumTel());
      stmt.setString(9, newUtente.getResidenza());
      var rows = stmt.executeUpdate();
      if (rows > 0) {
        var result = stmt.getGeneratedKeys();
        result.next();
        return result.getString(1);
      } else {
        System.err.println("Inserimento fallito");
        System.exit(1);
      }
    }

    return null;
  }
}
