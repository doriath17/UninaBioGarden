package uninabiogarden.service;

import java.sql.SQLException;
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

  public Proprietario authenticate(String username, String password) 
    throws ConnectionFailedException,
    WrongUsernameException,
    WrongPasswordException, SQLException
  {
    var p = proprietarioDao.authenticate(username, password);
    try {
      p.setLotti(this.requestLottiFor(p.getUsername()));
      p.setProgetti(this.requestProgettiFor(p));
      p.setAvailableLotti(this.requestAvailableLottiIdsFor(username));
    } catch (NoDataFoundException e) {
      System.err.println("when trying to authenticate proprietario: " + e.getMessage());
    }
    return p;
  }

  public List<Lotto> requestLottiFor(String username) throws ConnectionFailedException, NoDataFoundException{
    return proprietarioDao.findAllLotti(username);
  }

  public List<Progetto> requestProgettiFor(Proprietario proprietario) throws ConnectionFailedException, NoDataFoundException {
    return proprietarioDao.findAllProgetti(proprietario);
  }

  public List<Long> requestAvailableLottiIdsFor(String username) throws SQLException, ConnectionFailedException {
    return proprietarioDao.findAvailableLottiIds(username);
  }

}
