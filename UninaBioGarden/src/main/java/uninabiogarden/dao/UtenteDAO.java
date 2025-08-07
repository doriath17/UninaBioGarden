package uninabiogarden.dao;

import java.sql.SQLException;
import uninabiogarden.controllers.WrongPasswordException;
import uninabiogarden.controllers.WrongUsernameException;
import uninabiogarden.entities.Utente;

public class UtenteDAO {
  static Utente.Builder exists(String username, String password, String table) throws WrongUsernameException, WrongPasswordException {
    var sql = "SELECT * FROM "+table+" WHERE username='"+username+"'";

    try (var conn = Database.connect();
      var stmt = conn.createStatement()){

      var result = stmt.executeQuery(sql);
      result.next();
      if (result.getString(1) == null) {
        throw new WrongUsernameException();
      } else if (!result.getString(2).equals(password)) {
        throw new WrongPasswordException();
      } else {
          Utente.Builder builder = new Utente.Builder(
          result.getString(1),
          result.getString(2),
          result.getString(3),
          result.getString(4),
          result.getDate(5).toLocalDate(),
          result.getString(6),
          result.getString(7)
        );
        return builder
          .numTel(result.getString(8))
          .residenza(result.getString(9));
      }
    } catch (SQLException e) {
      throw new WrongUsernameException();
    }
  }
}
