package uninabiogarden.service;

import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Utente;

public class ColtivatoreService implements UtenteService {
  
  Coltivatore user;

  @Override
  public Utente getUtente() {
    return user;
  }

  public ColtivatoreService(Coltivatore user) {
    this.user = user;
  }

}
