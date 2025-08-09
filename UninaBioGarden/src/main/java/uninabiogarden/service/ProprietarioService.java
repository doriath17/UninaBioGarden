package uninabiogarden.service;

import java.util.List;

import uninabiogarden.dao.LottoDao;
import uninabiogarden.dao.ProgettoDao;
import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;

public class ProprietarioService implements UtenteService {
  Proprietario user;

  @Override
  public Utente getUtente() {
    return user;
  }

  public ProprietarioService(Proprietario user) {
    this.user = user;
  }

  public List<Lotto> loadLotti(){
    if (user.getLotti() == null) {
      user.setLotti(LottoDao.findAll(user.getUsername()));
    }
    return user.getLotti();
  }

  public List<Progetto> loadProgetti(){
    if (user.getProgetti() == null) {
      user.setProgetti(ProgettoDao.findAll((Proprietario) user));
    }
    return user.getProgetti();
  }
}
