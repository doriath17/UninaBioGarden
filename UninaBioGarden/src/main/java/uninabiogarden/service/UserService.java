package uninabiogarden.service;

import java.util.List;

import uninabiogarden.dao.ColtivatoreDao;
import uninabiogarden.dao.LottoDao;
import uninabiogarden.dao.ProgettoDao;
import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class UserService {

  Utente user;
  List<Lotto> lotti;
  List<Progetto> progetti;

  public Utente getUser() {
    return user;
  }

  public void logout() {
    user = null;
    lotti = null;
    progetti = null;
  }

  private void login(String username, String password, boolean userType) throws WrongPasswordException, WrongUsernameException {
    if (userType) {
      user = ProprietarioDao.exists(username, password);
    } else {
      user = ColtivatoreDao.exists(username, password);
    }
  }

  public void loginProprietario(String username, String password) throws WrongPasswordException, WrongUsernameException {
    login(username, password, true);
  }

  public void loginColtivatore(String username, String password) throws WrongPasswordException, WrongUsernameException {
    login(username, password, false);
  }

  public void autoLogin() {
    try {
      loginProprietario("proprietario1", "pass123");
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public List<Lotto> loadLotti(){
    if (lotti == null) {
      lotti = LottoDao.findAll(user.getUsername());
    }
    return lotti;
  }

  public List<Progetto> loadProgetti(){
    if (progetti == null) {
      progetti = ProgettoDao.findAll((Proprietario) user);
    }
    return progetti;
  }

}
