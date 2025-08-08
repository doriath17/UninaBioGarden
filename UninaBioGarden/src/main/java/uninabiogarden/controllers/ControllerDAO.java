package uninabiogarden.controllers;

import java.util.List;

import uninabiogarden.dao.ColtivatoreDAO;
import uninabiogarden.dao.LottoDAO;
import uninabiogarden.dao.ProgettoDAO;
import uninabiogarden.dao.ProprietarioDAO;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Utente;

public class ControllerDAO {

  Utente user;
  List<Lotto> lotti;
  List<Progetto> progetti;

  public Utente getUser() {
    return user;
  }

  public void addProprietario(Utente.Builder proprietarioBuilder) {
    var proprietario = proprietarioBuilder.buildProprietario();
    ProprietarioDAO.add(proprietario);
  }

  private void login(String username, String password, boolean userType) throws WrongPasswordException, WrongUsernameException {
    if (userType) {
      user = ProprietarioDAO.exists(username, password);
    } else {
      user = ColtivatoreDAO.exists(username, password);
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

  public void logout() {
    user = null;
    lotti = null;
  }

  public List<Lotto> loadLotti(){
    if (lotti == null) {
      lotti = LottoDAO.findAll(user.getUsername());
    }
    return lotti;
  }

  public List<Progetto> loadProgetti(){
    if (progetti == null) {
      progetti = ProgettoDAO.findAll(user.getUsername());
    }
    return progetti;
  }

}
