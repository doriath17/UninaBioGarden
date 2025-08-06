package uninabiogarden.controllers;

import uninabiogarden.dao.ProprietarioDAO;
import uninabiogarden.entities.Utente;

public class ControllerDAO {

  Utente user = null;

  public void addProprietario(Utente.Builder proprietarioBuilder) {
    var proprietario = proprietarioBuilder.buildProprietario();
    ProprietarioDAO.add(proprietario);
  }

  public void login(String username, String password) throws LoginException {
    var p = ProprietarioDAO.exists(username, password);
    if (p == null) {
      throw new LoginException();
    } else {
      user = p;
    }
  }

}
