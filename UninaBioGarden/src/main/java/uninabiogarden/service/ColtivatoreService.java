package uninabiogarden.service;

import uninabiogarden.dao.ColtivatoreDao;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class ColtivatoreService {
  
  private static final ColtivatoreService instance = new ColtivatoreService();
  private final ColtivatoreDao coltivatoreDao = ColtivatoreDao.getInstance();

  private ColtivatoreService() {}

  public static ColtivatoreService getInstance() {
    return instance;
  }
}
