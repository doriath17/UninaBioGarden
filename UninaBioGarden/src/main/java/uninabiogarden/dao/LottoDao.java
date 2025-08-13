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
  
  
}
