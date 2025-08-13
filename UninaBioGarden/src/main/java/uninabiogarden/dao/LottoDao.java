package uninabiogarden.dao;

import java.sql.SQLException;

import uninabiogarden.core.Database;
import uninabiogarden.entities.Lotto;
import uninabiogarden.exceptions.ConnectionFailedException;

public class LottoDao extends DaoBase {

  public LottoDao(Database database) {
    super(database);
  }

  public void update(Lotto lottoToUpdate) throws SQLException, ConnectionFailedException {
    var sql = "UPDATE lotto SET indirizzo=?, codice_lotto=?, estensione=?, nome_orto=? WHERE id_lotto=?";
    try (var conn = database.getConnection();
      var stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, lottoToUpdate.getIndirizzo());
      stmt.setString(2, lottoToUpdate.getCodice());
      stmt.setDouble(3, lottoToUpdate.getEstensione());
      stmt.setString(4, lottoToUpdate.getOrto());
      stmt.setLong(5, lottoToUpdate.getId());
      stmt.executeUpdate();
    }
  }

  public void delete(Lotto lotto) throws SQLException, ConnectionFailedException {
    var sql = "DELETE FROM lotto WHERE id_lotto='"+lotto.getId()+"'";
    try (var conn = database.getConnection();
      var stmt = conn.createStatement()) {
      stmt.executeUpdate(sql);
    }
  }
  
  public void insert(Lotto lottoToInsert) throws SQLException, ConnectionFailedException {
    var sql = "INSERT INTO lotto (indirizzo, codice_lotto, estensione, nome_orto, username_prop) VALUES (?, ?, ?, ?, ?)";

    try (var conn = database.getConnection();
      var stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, lottoToInsert.getIndirizzo());
      stmt.setString(2, lottoToInsert.getCodice());
      stmt.setDouble(3, lottoToInsert.getEstensione());
      stmt.setString(4, lottoToInsert.getOrto());
      stmt.setString(5, lottoToInsert.getProprietario().getUsername());
      stmt.executeUpdate();
    }
  }
  
}
