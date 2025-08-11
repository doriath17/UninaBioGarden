package uninabiogarden.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import uninabiogarden.core.Database;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.NoDataFoundException;

public class ProgettoDao extends DaoBase {

  public ProgettoDao(Database db) {
    super(db);
  }

  public Progetto read(Long id_progetto, Proprietario prop) throws SQLException, ConnectionFailedException, NoDataFoundException {
    var sql = """
        SELECT *
        FROM progetto AS prog
        JOIN lotto ON prog.id_lotto = lotto.id_lotto
        JOIN proprietario AS prop ON prog.username_prop = prop.username
        WHERE id_progetto = 
        """ + id_progetto;

    try (var conn = database.getConnection();
         var stmt = conn.createStatement()) {
      var result = stmt.executeQuery(sql);

      if (result.first()) {
        var dataFine = result.getDate(4);

        return Progetto.createFromDB(
          id_progetto,
          result.getString(2),
          result.getDate(3).toLocalDate(),
          (dataFine != null ? dataFine.toLocalDate() : null),
          result.getString(5),
          prop,
          result.getLong(7)
        );
      } else {
        throw new NoDataFoundException();
      }
    }

  }

  public void update(Long id_progetto, String nome, LocalDate dataInizio, LocalDate dataFine, String descrizione) throws ConnectionFailedException {
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
      stmt.setString(1, nome);
      stmt.setDate(2, Date.valueOf(dataInizio));
      stmt.setDate(3, Date.valueOf(dataFine));
      stmt.setString(4, descrizione);
      stmt.setLong(5, id_progetto);

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

  public void insert(Progetto newProgetto, Proprietario prop) throws SQLException, ConnectionFailedException {
    var sql = """
      INSERT INTO progetto VALUES 
      (?, ?, ?, ?, ?, ?, ?)
      """;
    try (var conn = database.getConnection();
      var stmt = conn.prepareStatement(sql)){
      stmt.setLong(1, newProgetto.getId());
      stmt.setString(2, newProgetto.getNome());
      stmt.setDate(3, Date.valueOf(newProgetto.getDataInizio()));
      stmt.setDate(4, Date.valueOf(newProgetto.getDataFine()));
      stmt.setString(5, newProgetto.getDescrizione());
      stmt.setLong(6, newProgetto.getLotto().getId());

      stmt.executeUpdate();
    }
  }


}
