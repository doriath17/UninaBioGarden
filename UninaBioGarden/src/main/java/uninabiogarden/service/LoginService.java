package uninabiogarden.service;

import uninabiogarden.dao.ColtivatoreDao;
import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class LoginService {

  public static ProprietarioService loginProprietario(String username, String password) throws WrongPasswordException, WrongUsernameException {
    return new ProprietarioService(ProprietarioDao.exists(username, password));
  }

  public static ColtivatoreService loginColtivatore(String username, String password) throws WrongPasswordException, WrongUsernameException {
    return new ColtivatoreService(ColtivatoreDao.exists(username, password));
  }

  public static void autoLogin() {
    try {
      loginProprietario("proprietario1", "pass123");
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

}
