package uninabiogarden.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.EmptyValueException;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.WrongDataFineException;

public class ProgettoDAO {
  public static List<Progetto> findAll(Proprietario user) {

    var sql = "SELECT * " + 
      "FROM progetto AS prog " + 
      "JOIN proprietario AS prop ON prog.username_prop=prop.username " +
      "JOIN lotto ON prog.id_lotto=lotto.id_lotto " +
      "WHERE prop.username='"+user.getUsername()+"'";

    var progetti = new ArrayList<Progetto>();

    try (var conn = Database.connect();
      var stmt = conn.createStatement()) {

      var result = stmt.executeQuery(sql);
      
      while (result.next()) {
        var dataFine = result.getDate(4);

        var lotto = new Lotto(
          result.getInt(17),
          result.getString(18),
          result.getInt(19),
          result.getDouble(20),
          result.getString(21)
        );

        var prog = new Progetto(
          result.getInt(1),
          result.getString(2),
          result.getDate(3).toLocalDate(),
          (dataFine != null ? dataFine.toLocalDate() : null),
          result.getString(5),
          user,
          lotto
        );
        
        progetti.add(prog);
      }

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    } catch (WrongDataFineException | EmptyValueException e) {
      System.err.println(e.getMessage());
      System.exit(1); // error in the database
    }

    return progetti;
  }
}
