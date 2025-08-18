package uninabiogarden.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class UtenteDao {

  static Utente authenticate(Database database, String username, String password, String utenteType) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException {
    var sql = "SELECT * FROM utente WHERE username='"+username+"'";

    try (var conn = database.getConnection();
      var stmt = conn.createStatement()){

      var result = stmt.executeQuery(sql);
      result.next();
      if (result.getString("id_utente") == null) {
        throw new WrongUsernameException();
      } else if (!result.getString("password").equals(password)) {
        throw new WrongPasswordException();
      } else {
        Utente utente = null; 

        if (utenteType.equals("proprietario")) {
          utente = new Proprietario(
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
          utente = new Coltivatore(
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

        utente.setId(result.getLong("id_utente"));
        return utente;
      }
    } catch (SQLException e) {
      throw new WrongUsernameException();
    }
  }

  static Long insert(Database database, Utente newUtente, String utenteType) throws SQLException, ConnectionFailedException {
    var sql = 
      "INSERT INTO utente (username, password, email, nome, cognome, bday, nazionalita, num_tel, residenza, u_type) VALUES " +
      "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?::utente_type)";

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
      stmt.setString(10, utenteType);
      var rows = stmt.executeUpdate();
      if (rows > 0) {
        var result = stmt.getGeneratedKeys();
        result.next();
        return result.getLong("id_utente");
      } else {
        System.err.println("Inserimento fallito");
        System.exit(1);
      }
    }

    return null;
  }
}
