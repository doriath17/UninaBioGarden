package uninabiogarden.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.InsertFailedException;
import uninabiogarden.exceptions.NoDataFoundException;

public class ProgettoDao {

  private static final ProgettoDao instance = new ProgettoDao();
  private final Database database = Database.getInstance();

  private ProgettoDao() {}

  public static ProgettoDao getInstance() {
    return instance;
  }

  // public Progetto read(Long id_progetto, Proprietario prop) throws SQLException, ConnectionFailedException, NoDataFoundException {
  //   var sql = """
  //       SELECT *
  //       FROM progetto AS prog
  //       JOIN lotto ON prog.id_lotto = lotto.id_lotto
  //       JOIN proprietario AS prop ON prog.username_prop = prop.username
  //       WHERE id_progetto = 
  //       """ + id_progetto;

  //   try (var conn = database.getConnection();
  //        var stmt = conn.createStatement()) {
  //     var result = stmt.executeQuery(sql);

  //     if (result.first()) {
  //       var dataFine = result.getDate(4);

  //       return new Progetto();

  //       // return new Progetto(
  //       //   id_progetto,
  //       //   result.getString(2),
  //       //   result.getDate(3).toLocalDate(),
  //       //   (dataFine != null ? dataFine.toLocalDate() : null),
  //       //   result.getString(5),
  //       //   prop.getUsername(),
  //       //   result.getLong(7)
  //       // );
  //     } else {
  //       throw new NoDataFoundException();
  //     }
  //   }
  // }

  public void update(Progetto toUpdate) throws ConnectionFailedException {
    var sql = """
      UPDATE 
        progetto
      SET
        nome=?, data_inizio=?, data_fine=?, descrizione=?
      WHERE
        id_progetto=?  
      """;

    try (var conn = database.getConnection();
      var stmt = conn.prepareStatement(sql)){

      stmt.setString(1, toUpdate.getNome());
      stmt.setDate(2, Date.valueOf(toUpdate.getDataInizio()));
      stmt.setDate(3, toSqlDate(toUpdate.getDataFine()));
      stmt.setString(4, toUpdate.getDescrizione());
      stmt.setLong(5, toUpdate.getId());

      stmt.executeUpdate();
    } catch(SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  public void delete(Long id_progetto) throws ConnectionFailedException, SQLException {
    var sql = "DELETE FROM progetto WHERE id_progetto="+id_progetto;
    try (var conn = database.getConnection();
      var stmt = conn.createStatement()) {
      stmt.executeUpdate(sql);
    }
  }

  public Long insert(Progetto newProgetto) throws SQLException, ConnectionFailedException {
    var sql = """
      INSERT INTO progetto (nome, data_inizio, data_fine, descrizione, id_prop, id_lotto) VALUES 
      (?, ?, ?, ?, ?, ?)
      """;
    try (var conn = database.getConnection();
      var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
      stmt.setString(1, newProgetto.getNome());
      stmt.setDate(2, Date.valueOf(newProgetto.getDataInizio()));

      stmt.setDate(3, toSqlDate(newProgetto.getDataFine()));
      stmt.setString(4, newProgetto.getDescrizione());
      stmt.setLong(5, newProgetto.getProprietario().getId());
      stmt.setLong(6, newProgetto.getLotto().getId());

      var rows = stmt.executeUpdate();

      if (rows > 0) {
        var result = stmt.getGeneratedKeys();
        result.next();
        return result.getLong(1);
      } else {
        System.err.println("Inserimento fallito");
        System.exit(1);
      }
    }
    return null;
  }

  private Date toSqlDate(LocalDate dataFine) {
    return dataFine != null ? Date.valueOf(dataFine) : null;
  }

}
