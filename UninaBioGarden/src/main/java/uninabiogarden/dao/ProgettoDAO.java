package uninabiogarden.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.Progetto;

public class ProgettoDAO {
  public static List<Progetto> findAll(String username) {
    var sql = "SELECT * FROM progetto WHERE username_prop='"+username+"'";
    var progetti = new ArrayList<Progetto>();

    try (var conn = Database.connect();
      var stmt = conn.createStatement()) {

      var result = stmt.executeQuery(sql);
      
      while (result.next()) {
        var dataFine = result.getDate(4);
        progetti.add(new Progetto(
          result.getInt(1),
          result.getString(2),
          result.getDate(3).toLocalDate(),
          (dataFine != null ? dataFine.toLocalDate() : null),
          result.getString(5),
          result.getString(6),
          result.getInt(7)
        ));
      }

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    for (var p : progetti) {
      System.out.println(p.getId() + "," + p.getNome());
    }

    return progetti;
  }
}
