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
  
  private Utente utente;

  private LoginService() {}

  public static LoginService getInstance() {
    return instance;
  }

  public Utente login(String username, String password) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException {
    this.utente = UtenteDao.authenticate(username, password);
    return utente;
  }

  public void logout() {
    utente = null;
  }

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
