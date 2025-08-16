package uninabiogarden.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
