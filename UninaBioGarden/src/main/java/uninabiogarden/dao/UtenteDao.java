package uninabiogarden.dao;

import java.sql.SQLException;

import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class UtenteDao {
  static Utente exists(String username, String password, String table) throws WrongUsernameException, WrongPasswordException {
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
        if (table.equals("proprietario")){
          return new Proprietario(
            result.getString(1),
            result.getString(2),
            result.getString(3),
            result.getString(4),
            result.getDate(5).toLocalDate(),
            result.getString(6),
            result.getString(7),
            result.getString(8),
            result.getString(9)
          );
        } else {
          return new Coltivatore(
            result.getString(1),
            result.getString(2),
            result.getString(3),
            result.getString(4),
            result.getDate(5).toLocalDate(),
            result.getString(6),
            result.getString(7),
            result.getString(8),
            result.getString(9)
          );
        }
      }
    } catch (SQLException e) {
      throw new WrongUsernameException();
    }
  }
}
