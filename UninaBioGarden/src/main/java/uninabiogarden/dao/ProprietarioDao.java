package uninabiogarden.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.NoDataFoundException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class ProprietarioDao {

  private static final ProprietarioDao instance = new ProprietarioDao();
  private final Database database = Database.getInstance();

  private ProprietarioDao() {}

  public static ProprietarioDao getInstance() {
    return instance;
  }

  // public Proprietario authenticate(String username, String password) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException {
  //   return (Proprietario) UtenteDao.authenticate(database, username, password, "proprietario");
  // }

  public Long insert(Proprietario newProprietario) throws SQLException, ConnectionFailedException {
    return UtenteDao.insert(database, newProprietario, "proprietario");
  }

  public List<Lotto> findAllLotti(Long id) throws ConnectionFailedException, NoDataFoundException {
    var sql = "SELECT * FROM lotto WHERE id_prop=" + id ;

    var list = new ArrayList<Lotto>();

    try (var conn = database.getConnection();
      var stmt = conn.createStatement()) {
      var result = stmt.executeQuery(sql);

      while (result.next()) {
        list.add(new Lotto(
          result.getLong(1),
          result.getString(2),
          result.getString(3),
          result.getDouble(4),
          result.getString(5)
        ));
      }

      if (list.isEmpty()) {
        throw new NoDataFoundException();
      }

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    return list;
  }

  public List<Progetto> findAllProgetti(Proprietario proprietario) throws ConnectionFailedException, NoDataFoundException {
    var sql = "SELECT progetti_utente.*, lotto.* " +
            "FROM (SELECT * FROM progetto WHERE id_prop='"+proprietario.getId()+"') AS progetti_utente " +
            "NATURAL JOIN lotto";

    var progetti = new ArrayList<Progetto>();

    try (var conn = database.getConnection();
      var stmt = conn.createStatement()) {

      var result = stmt.executeQuery(sql);
      
      while (result.next()) {
        // index of id_lotto is 17
        var dataFine = result.getDate(4);

        var lotto = new Lotto(
          result.getLong(8),
          result.getString(9),
          result.getString(10),
          result.getDouble(11),
          result.getString(12)
        );

        var prog = new Progetto(
          result.getLong(1),
          result.getString(2),
          result.getDate(3).toLocalDate(),
          (dataFine != null ? dataFine.toLocalDate() : null),
          result.getString(5),
          proprietario,
          lotto
        );
        
        progetti.add(prog);
      }

      if (progetti.isEmpty()) {
        throw new NoDataFoundException();
      }

    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }

    return progetti;
  }

  public List<Lotto> findAvailableLotti(Long id) throws SQLException, ConnectionFailedException, NoDataFoundException {
    List<Lotto> lotti = new ArrayList<>();

    var sql = """
        (
          SELECT *
          FROM lotto AS lotti_utente
          WHERE id_prop = ?
        )

        EXCEPT 

        (
          SELECT lotti_utente.*
          FROM (SELECT * FROM progetto WHERE id_prop=? AND data_fine IS NULL) AS progetti_utente
          JOIN (SELECT * FROM lotto WHERE id_prop=?) AS lotti_utente ON progetti_utente.id_lotto = lotti_utente.id_lotto
        )
        """;

    try (var conn = database.getConnection();
      var stmt = conn.prepareStatement(sql)){
      stmt.setLong(1, id);
      stmt.setLong(2, id);
      stmt.setLong(3, id);
      var result = stmt.executeQuery();

      while (result.next()) {
        lotti.add(new Lotto(
          result.getLong(1),
          result.getString(2),
          result.getString(3),
          result.getDouble(4),
          result.getString(5)
        ));
      }

    }

    if (lotti.isEmpty()) {
      throw new NoDataFoundException();
    }

    return lotti;
  }
}
