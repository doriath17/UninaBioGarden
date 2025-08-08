package uninabiogarden.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.Lotto;

public class LottoDAO {

  public static List<Lotto> findAll(String username) {
    var sql = "SELECT * FROM lotto WHERE username_prop='" + username + "'";

    var list = new ArrayList<Lotto>();

    try (var conn = Database.connect();
         var stmt = conn.createStatement()) {
      var result = stmt.executeQuery(sql);

      while (result.next()) {
        list.add(new Lotto(
          result.getInt(1),
          result.getString(2),
          result.getInt(3),
          result.getDouble(4),
          result.getString(5)
        ));
      }

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    return list;
  }
  
}
