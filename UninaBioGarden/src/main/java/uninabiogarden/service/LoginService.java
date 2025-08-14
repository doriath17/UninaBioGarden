package uninabiogarden.service;

import java.security.SecureRandom;
import java.sql.SQLException;

import uninabiogarden.dao.ColtivatoreDao;
import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class LoginService {
  private static final LoginService instance = new LoginService();
  private final ProprietarioDao proprietarioDao = ProprietarioDao.getInstance();
  private final ColtivatoreDao coltivatoreDao = ColtivatoreDao.getInstance();


  private Proprietario loggedInProprietario = null;
  private Coltivatore loggedInColtivatre = null;
  private UserType loggedInType = UserType.NONE;

  public enum UserType {
    PROPRIETARIO,
    COLTIVATORE,
    NONE
  };

  private LoginService() {}

  public static LoginService getInstance() {
    return instance;
  }

  public Proprietario authenticateProprietario(String username, String password) 
    throws ConnectionFailedException,
    WrongUsernameException,
    WrongPasswordException, SQLException
  {
    loggedInProprietario = proprietarioDao.authenticate(username, password);
    loggedInType = UserType.PROPRIETARIO;
    return loggedInProprietario;
  }

  public Coltivatore authenticateColtivatore(String username, String password) 
    throws ConnectionFailedException,
    WrongUsernameException,
    WrongPasswordException, SQLException
  {
    loggedInColtivatre = coltivatoreDao.authenticate(username, password);
    loggedInType = UserType.COLTIVATORE;
    return loggedInColtivatre;
  }

  public void logout() {
    loggedInColtivatre = null;
    loggedInProprietario = null;
    loggedInType = UserType.NONE;
  }

  public Proprietario getLoggedInProprietario() {
    return loggedInProprietario;
  }

  public Coltivatore getLoggedInColtivatre() {
    return loggedInColtivatre;
  }

  public UserType getLoggedInType() {
    return loggedInType;
  }

  public String getUsername() {
    switch (loggedInType) {
      case UserType.PROPRIETARIO:
        return loggedInProprietario.getUsername();        
      case UserType.COLTIVATORE:
        loggedInColtivatre.getUsername();
    }
    return null;
  }

  public boolean isProprietarioSession() {
    return this.loggedInType == UserType.PROPRIETARIO;
  }

  public boolean isColotivatoreSession() {
    return this.loggedInType == UserType.COLTIVATORE;
  }

}
