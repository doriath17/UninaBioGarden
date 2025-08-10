package uninabiogarden.service;

import java.util.List;

import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.WrongUsernameException;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.NoDataFoundException;
import uninabiogarden.exceptions.WrongPasswordException;

public class ProprietarioService {
  ProprietarioDao proprietarioDao;

  public ProprietarioService(ProprietarioDao  proprietarioDao) {
    this.proprietarioDao = proprietarioDao;
  }

  public Proprietario authenticate(String username, String password) throws ConnectionFailedException, WrongUsernameException, WrongPasswordException {
    return proprietarioDao.authenticate(username, password);
  }

  public List<Lotto> requestLottiFor(String username) throws ConnectionFailedException, NoDataFoundException{
    return proprietarioDao.findAllLotti(username);
  }

  public List<Progetto> requestProgettiFor(String username) throws ConnectionFailedException, NoDataFoundException {
    return proprietarioDao.findAllProgetti(username);
  }

}
