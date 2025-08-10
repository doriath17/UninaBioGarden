package uninabiogarden.core;

import java.sql.Connection;
import java.sql.SQLException;

import uninabiogarden.dao.ColtivatoreDao;
import uninabiogarden.dao.LottoDao;
import uninabiogarden.dao.ProgettoDao;
import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.service.ColtivatoreService;
import uninabiogarden.service.ProprietarioService;

/**
 * Lo scopo di questa classe è quello di concentrare 
 * in un unico luogo tutto i componenti principali 
 * dell'intero sistema. 
 * In questo modo questa classe può essere utilizzata 
 * per accedervi e per questo motivo può essere utilizzata
 * come una dependency injection.
 * Lo stato di questa classe è praticamente statico e 
 * determinato allo startup del sistema.
 */

public class ApplicationContext {
  private final LoginSession session;
  private Connection connection;
  private ApplicationState appState;

  // DAOs
  private final ProprietarioDao proprietarioDao;
  private final ColtivatoreDao coltivatoreDao;
  private final LottoDao lottoDao;
  private final ProgettoDao progettoDao;

  // SERVICES
  private final ProprietarioService proprietarioService;
  private final ColtivatoreService coltivatoreService;


  public ApplicationContext() throws ConnectionFailedException {
    try {
      // init session
      session = new LoginSession();

      // init connection
      this.getConnection();

      // init application state
      this.appState = new ApplicationState();

      // init dao classes
      this.proprietarioDao = new ProprietarioDao(this);
      this.coltivatoreDao = new ColtivatoreDao(this);
      this.lottoDao = new LottoDao(this);
      this.progettoDao = new ProgettoDao(this);

      // init services
      proprietarioService = new ProprietarioService(proprietarioDao);
      coltivatoreService = new ColtivatoreService(coltivatoreDao);
    } catch (ConnectionFailedException e) {
      System.err.println(e.getMessage());
      throw e;
    }
  }

  public Connection getConnection() throws ConnectionFailedException {
    try {
      if (connection == null || connection.isClosed()) {
        connection = Database.connect();
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      throw new ConnectionFailedException();
    }
    return connection;
  }

  public LoginSession getSession() {
    return session;
  }

  public ProprietarioDao getProprietarioDao() {
    return proprietarioDao;
  }

  public ColtivatoreDao getColtivatoreDao() {
    return coltivatoreDao;
  }

  public LottoDao getLottoDao() {
    return lottoDao;
  }

  public ProgettoDao getProgettoDao() {
    return progettoDao;
  }

  public ProprietarioService getProprietarioService() {
    return proprietarioService;
  }

  public ColtivatoreService getColtivatoreService() {
    return coltivatoreService;
  }

  public ApplicationState getAppState() {
    return appState;
  }

}
