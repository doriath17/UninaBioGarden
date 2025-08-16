package uninabiogarden.service;

import java.sql.SQLException;
import java.util.List;

import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.InvalidUtenteFieldException;
import uninabiogarden.exceptions.NoDataFoundException;
import uninabiogarden.exceptions.InvalidUtenteFieldException.InvalidUtenteField;

public class ProprietarioService {

  private static final ProprietarioService instance = new ProprietarioService();
  private final LoginService loginService = LoginService.getInstance(); 
  private final ProprietarioDao proprietarioDao = ProprietarioDao.getInstance();

  private ProprietarioService() {}

  public static ProprietarioService getInstance() {
    return instance;
  }

  private void checkUsername(String username) throws InvalidUtenteFieldException {
    if (username == null || username.isEmpty()) {
      throw new InvalidUtenteFieldException(
        "Nome mancante", InvalidUtenteField.USERNAME
      );
    }
    if (username.length() < 6) {
      throw new InvalidUtenteFieldException("Username deve essere almeno 6 caratteri", InvalidUtenteField.USERNAME);
    }
    if (username.matches("^[a-zA-Z][a-zA-Z0-9_-]{6,29}$")){
      throw new InvalidUtenteFieldException(
        "Username non valido", InvalidUtenteField.USERNAME
      );  
    }
  }

  private void checkPassword(String password) throws InvalidUtenteFieldException {
    if (password == null || password.isEmpty()) {
      throw new InvalidUtenteFieldException(
        "Password mancante",
        InvalidUtenteFieldException.InvalidUtenteField.PASSWORD
      );
    }
    if (password.matches("^[a-zA-Z][a-zA-Z0-9_-]{6,29}$")){
      throw new InvalidUtenteFieldException(
        "Username non valido",
        InvalidUtenteFieldException.InvalidUtenteField.PASSWORD
      );  
    }
  }

  private void checkNome(String nome) throws InvalidUtenteFieldException {
    if (nome == null || nome.isEmpty()) {
      throw new InvalidUtenteFieldException(
        "Nome mancante",
        InvalidUtenteFieldException.InvalidUtenteField.NOME
      );
    }
  }

  public void checkBasic(Proprietario proprietarioToCheck) throws InvalidUtenteFieldException {
    checkUsername(proprietarioToCheck.getUsername());
    checkNome(proprietarioToCheck.getNome());
  }

  public List<Lotto> requestLotti() throws ConnectionFailedException, NoDataFoundException {
    return proprietarioDao.findAllLotti(loginService.getUsername());
  }

  public List<Progetto> requestProgetti() throws ConnectionFailedException, NoDataFoundException {
    return proprietarioDao.findAllProgetti(loginService.getLoggedInProprietario());
  }

  public List<Lotto> requestAvailableLotti() throws SQLException, ConnectionFailedException {
    return proprietarioDao.findAvailableLotti(loginService.getLoggedInProprietario().getUsername());
  }



}
