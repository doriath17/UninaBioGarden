package uninabiogarden.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import uninabiogarden.core.ApplicationContext;
import uninabiogarden.core.Database;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.NoDataFoundException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class ProprietarioDao extends DaoBase {

  public ProprietarioDao(ApplicationContext context) {
    super(context);
  }

  public Proprietario authenticate(String username, String password) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException {
    return (Proprietario) UtenteDao.authenticate(context, username, password, "proprietario");
  }

  public List<Lotto> findAllLotti(String username) throws ConnectionFailedException, NoDataFoundException {
    var sql = "SELECT * FROM lotto WHERE username_prop='" + username + "'";

    var list = new ArrayList<Lotto>();

    try (var conn = context.getConnection();
      var stmt = conn.createStatement()) {
      var result = stmt.executeQuery(sql);

      while (result.next()) {
        list.add(Lotto.createFromDB(
          result.getInt(1),
          result.getString(2),
          result.getInt(3),
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

  public static boolean add(Proprietario proprietario) {
    var sql = "INSERT INTO Proprietario * " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (var conn = Database.connect();
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

  /// Questo metodo è necessario per evitare duplicazione
  /// di dati. 
  /// Se sia il Proprietario che un Progetto mantengono un 
  /// oggetto Lotto che rappresenta lo stesso lotto hai duplicazione
  /// e questo è un problema ed è difficile da gestire la consistenza
  /// dei dati successivamente.
  /// 
  /// In questo modo si rispecchia il fatto che nel db (e quindi
  /// nel minimondo) ci sia soltanto un lotto (attualmente caricato
  /// nella lista di lotti del proprietario loggato).
  private Lotto findLottoById(List<Lotto> lotti, int id) {
    for (var lotto : lotti) {
      if (lotto.getId() == id) {
        return lotto;
      }
    }
    return null;
  }

  public List<Progetto> findAllProgetti(Proprietario proprietario) throws ConnectionFailedException, NoDataFoundException {

    var sql = "SELECT * " + 
      "FROM progetto AS prog " + 
      "JOIN proprietario AS prop ON prog.username_prop=prop.username " +
      "JOIN lotto ON prog.id_lotto=lotto.id_lotto " +
      "WHERE prop.username='"+proprietario.getUsername()+"'";

    var progetti = new ArrayList<Progetto>();

    try (var conn = context.getConnection();
      var stmt = conn.createStatement()) {

      var result = stmt.executeQuery(sql);
      
      while (result.next()) {
        // index of id_lotto is 17
        var lotto = findLottoById(proprietario.getLotti(), result.getInt("id_lotto"));
        if (lotto == null) {
          System.err.println("Problems in findAllLotti of ProprietarioDao class: not all lotti were found");
          System.exit(1);
        }
        var dataFine = result.getDate(4);

        var prog = Progetto.createFromDB(
          result.getInt(1),
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
