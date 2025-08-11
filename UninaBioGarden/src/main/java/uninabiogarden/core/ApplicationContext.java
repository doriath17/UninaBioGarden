package uninabiogarden.core;

import java.sql.Connection;
import java.sql.SQLException;

import uninabiogarden.dao.ColtivatoreDao;
import uninabiogarden.dao.LottoDao;
import uninabiogarden.dao.ProgettoDao;
import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.service.ColtivatoreService;
import uninabiogarden.service.ProgettoService;
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
  private ApplicationState appState;
  Database database;

  // DAOs
  private final ProprietarioDao proprietarioDao;
  private final ColtivatoreDao coltivatoreDao;
  private final LottoDao lottoDao;
  private final ProgettoDao progettoDao;

  // SERVICES
  private final ProprietarioService proprietarioService;
  private final ColtivatoreService coltivatoreService;
  private final ProgettoService progettoService;


  public ApplicationContext() {
    // init session
    session = new LoginSession();

    // init connection
    database = new Database();

    // init application state
    this.appState = new ApplicationState();

    // init dao classes
    this.proprietarioDao = new ProprietarioDao(database);
    this.coltivatoreDao = new ColtivatoreDao(database);
    this.lottoDao = new LottoDao(database);
    this.progettoDao = new ProgettoDao(database);

    // init services
    proprietarioService = new ProprietarioService(proprietarioDao);
    coltivatoreService = new ColtivatoreService(coltivatoreDao);
    progettoService = new ProgettoService(progettoDao);
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

  public ProgettoService getProgettoService() {
    return progettoService;
  }

}
