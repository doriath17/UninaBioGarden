package uninabiogarden.service;

import java.sql.SQLException;
import java.util.List;

import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.NoDataFoundException;

public class ProprietarioService {

  private static final ProprietarioService instance = new ProprietarioService();
  private final LoginService loginService = LoginService.getInstance(); 
  private final ProprietarioDao proprietarioDao = ProprietarioDao.getInstance();

  private ProprietarioService() {}

  public static ProprietarioService getInstance() {
    return instance;
  }

  public List<Lotto> requestLotti() throws ConnectionFailedException, NoDataFoundException {
    return proprietarioDao.findAllLotti(loginService.getId());
  }

  public List<Progetto> requestProgetti() throws ConnectionFailedException, NoDataFoundException {
    return proprietarioDao.findAllProgetti(loginService.getLoggedInProprietario());
  }

  public List<Lotto> requestAvailableLotti() throws SQLException, ConnectionFailedException, NoDataFoundException {
    return proprietarioDao.findAvailableLotti(loginService.getId());
  }



}
