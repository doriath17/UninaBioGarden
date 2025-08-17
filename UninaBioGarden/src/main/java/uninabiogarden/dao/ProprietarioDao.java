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

  public Proprietario authenticate(String username, String password) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException {
    return (Proprietario) UtenteDao.authenticate(database, username, password, "proprietario");
  }

  public String insert(Proprietario newProprietario) throws SQLException, ConnectionFailedException {
    return UtenteDao.insert(database, newProprietario, "proprietario");
  }

  public List<Lotto> findAllLotti(String username) throws ConnectionFailedException, NoDataFoundException {
    var sql = "SELECT * FROM lotto WHERE username_prop='" + username + "'";

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

  public boolean add(Proprietario proprietario) throws ConnectionFailedException {
    var sql = "INSERT INTO Proprietario * " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (var conn = database.getConnection();
      var stmt = conn.prepareStatement(sql)){
      stmt.setString(1, proprietario.getUsername());
      stmt.setString(2, proprietario.getPassword());
      stmt.setString(3, proprietario.getNome());
      stmt.setString(4, proprietario.getCognome());
      stmt.setDate(5,  Date.valueOf(proprietario.getBday()));
      stmt.setString(6, proprietario.getNationality());
      stmt.setString(7, proprietario.getEmail());
      stmt.setString(8, proprietario.getNumTel());
      stmt.setString(9, proprietario.getResidenza());

      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return false;
    }
    return true;
  }

  public List<Progetto> findAllProgetti(Proprietario proprietario) throws ConnectionFailedException, NoDataFoundException {
    var sql = "SELECT progetti_utente.*, lotto.* " +
            "FROM (SELECT * FROM progetto WHERE username_prop='"+proprietario.getUsername()+"') AS progetti_utente " +
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

  public List<Lotto> findAvailableLotti(String username) throws SQLException, ConnectionFailedException {
    var lotti = new ArrayList<Lotto>();

    var sql = """
        (
          SELECT *
          FROM lotto AS lotti_utente
          WHERE username_prop = ?
        )

        EXCEPT 

        (
          SELECT lotti_utente.*
          FROM (SELECT * FROM progetto WHERE username_prop=? AND data_fine IS NULL) AS progetti_utente
          JOIN (SELECT * FROM lotto WHERE username_prop=?) AS lotti_utente ON progetti_utente.id_lotto = lotti_utente.id_lotto
        )
        """;

    try (var conn = database.getConnection();
      var stmt = conn.prepareStatement(sql)){
      stmt.setString(1, username);
      stmt.setString(2, username);
      stmt.setString(3, username);
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

    return lotti;
  }

//    public boolean checkProprietarioExists(String email, String password) {
//        String query = "SELECT 1 FROM proprietario WHERE email = ? AND password = ?";
//        Connection conn = null;
//        try {
//            conn = Database.connect();
//            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setString(1, email);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//            return rs.next();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

//    //Qua volendo si puo creare anche prima un proprietario, e poi lo si passa alla funzione
//    public boolean addProprietario(String username, String email, String password, String nome, String cognome, Date data_di_nascita, String residenza, String nazionalità, String num_telefono) {
//        String query = "INSERT INTO proprietario (username, email, password, nome, cognome, data_di_nascita, residenza, nazionalità, num_telefono) VALUES (?, ?, ?, ? , ?, ?, ?, ?, ?)";
//        Connection conn = null;
//        try {
//            conn = databaseManager.getConnection();
//            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setString(1, username);
//            ps.setString(2, email);
//            ps.setString(3, password);
//            ps.setString(4, nome);
//            ps.setString(5, cognome);
//            ps.setDate(6, data_di_nascita);
//            ps.setString(7, residenza);
//            ps.setString(8, nazionalità);
//            ps.setString(9, num_telefono);
//
//            int a = ps.executeUpdate();
//            return a > 0;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    // public boolean addProprietario (Proprietario proprietario){

    // }

}
