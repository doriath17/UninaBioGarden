package uninabiogarden.service;

import uninabiogarden.dao.ColtivatoreDao;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class ColtivatoreService {
  
  ColtivatoreDao coltivatoreDao;

  public ColtivatoreService(ColtivatoreDao coltivatoreDao) {
    this.coltivatoreDao = coltivatoreDao;
  }

  public Coltivatore authenticate(String username, String password) throws WrongUsernameException, WrongPasswordException, ConnectionFailedException {
    return (Coltivatore) coltivatoreDao.authenticate(username, password);
  }


}
