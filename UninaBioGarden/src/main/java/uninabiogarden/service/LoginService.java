package uninabiogarden.service;

import java.security.SecureRandom;
import java.sql.SQLException;

import uninabiogarden.dao.ColtivatoreDao;
import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.dao.UtenteDao;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class LoginService {
  private static final LoginService instance = new LoginService();
  // private final ProprietarioDao proprietarioDao = ProprietarioDao.getInstance();
  // private final ColtivatoreDao coltivatoreDao = ColtivatoreDao.getInstance();


  private Utente utente;
  // private Proprietario loggedInProprietario;
  // private Coltivatore loggedInColtivatre;
  // private UserType loggedInType = UserType.NONE;

  // public enum UserType {
  //   PROPRIETARIO,
  //   COLTIVATORE,
  //   NONE
  // };

  private LoginService() {}

  public static LoginService getInstance() {
    return instance;
  }

  public Utente login(String username, String password) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException {
    this.utente = UtenteDao.authenticate(username, password);
    return utente;
  }

  // public Proprietario authenticateProprietario(String username, String password) 
  //   throws ConnectionFailedException,
  //   WrongUsernameException,
  //   WrongPasswordException, SQLException
  // {
  //   loggedInProprietario = proprietarioDao.authenticate(username, password);
  //   loggedInType = UserType.PROPRIETARIO;
  //   return loggedInProprietario;
  // }

  // public Coltivatore authenticateColtivatore(String username, String password) 
  //   throws ConnectionFailedException,
  //   WrongUsernameException,
  //   WrongPasswordException, SQLException
  // {
  //   loggedInColtivatre = coltivatoreDao.authenticate(username, password);
  //   loggedInType = UserType.COLTIVATORE;
  //   return loggedInColtivatre;
  // }

  public void logout() {
    utente = null;
    // loggedInColtivatre = null;
    // loggedInProprietario = null;
    // loggedInType = UserType.NONE;
  }

  // public Proprietario getLoggedInProprietario() {
  //   return loggedInProprietario;
  // }

  // public Coltivatore getLoggedInColtivatre() {
  //   return loggedInColtivatre;
  // }

  // public UserType getLoggedInType() {
  //   return loggedInType;
  // }

  public Utente getLoggedUtente() {
    return utente;
  }

  public String getUsername() {
    return utente.getUsername();
  }

  public Long getId() {
    return utente.getId();
  }

  public boolean isProprietarioSession() {
    if (utente instanceof Proprietario) {
      return true;
    }
    return false;
  }

  public boolean isColotivatoreSession() {
    return !isProprietarioSession();
  }

}
