package uninabiogarden.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import uninabiogarden.dao.ColtivatoreDao;
import uninabiogarden.dao.ProprietarioDao;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.InvalidUtenteFieldException;
import uninabiogarden.exceptions.InvalidUtenteFieldException.InvalidUtenteField;

public class RegistrationService {

  private static final RegistrationService instance = new RegistrationService();
  ProprietarioDao proprietarioDao = ProprietarioDao.getInstance();
  ColtivatoreDao coltivatoreDao = ColtivatoreDao.getInstance();
  private final LoginService loginService = LoginService.getInstance();

  private RegistrationService() {}

  public static RegistrationService getInstance() {return instance;}

  public void checkBasic(Utente utenteToCheck) throws InvalidUtenteFieldException {
    checkUsername(utenteToCheck.getUsername());
    checkPassword(utenteToCheck.getPassword());
    checkNome(utenteToCheck.getNome());
    checkCognome(utenteToCheck.getCognome());
    checkBday(utenteToCheck.getBday());
    checkNationality(utenteToCheck.getNationality());
    checkNumTel(utenteToCheck.getNumTel());
    checkResidenza(utenteToCheck.getResidenza());
  }

  public String registerProprietario(Proprietario newProprietario) throws InvalidUtenteFieldException, SQLException, ConnectionFailedException {
    checkBasic(newProprietario);
    return proprietarioDao.insert(newProprietario);
  }

  public String registerColtivatore(Coltivatore newColtivatore) throws InvalidUtenteFieldException, SQLException, ConnectionFailedException {
    checkBasic(newColtivatore);
    return coltivatoreDao.insert(newColtivatore);
  }

  ///
  /// 
  /// 
  /// Validation methods
  /// 
  /// 
  /// 

  private void checkUsername(String username) throws InvalidUtenteFieldException {
    if (username == null || username.isEmpty()) {
      throw new InvalidUtenteFieldException(
        "Nome mancante", InvalidUtenteField.USERNAME
      );
    }
    if (!Character.isAlphabetic(username.charAt(0)) || !Character.isAlphabetic(username.charAt(username.length()-1))) {
      throw new InvalidUtenteFieldException("Username deve iniziare e finire con una lettera", InvalidUtenteField.USERNAME);
    }
    if (username.length() < 6) {
      throw new InvalidUtenteFieldException("Username deve essere almeno 6 caratteri", InvalidUtenteField.USERNAME);
    }
    if (username.length() > 30) {
      throw new InvalidUtenteFieldException("Username troppo lungo", InvalidUtenteField.USERNAME);
    }
    if (!username.matches("^[a-zA-Z][a-zA-Z0-9_-]{4,28}[a-zA-Z]$")){
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
    if (password.length() < 8) {
      throw new InvalidUtenteFieldException("Password deve essere almeno 8 caratteri", InvalidUtenteField.PASSWORD);
    }
    if (password.length() > 60) {
      throw new InvalidUtenteFieldException("Password troppo lunga", InvalidUtenteField.PASSWORD);
    }
    if (!password.matches("^[a-zA-Z0-9_!@#$%^&*+=\\?-]{1,60}$")){
      throw new InvalidUtenteFieldException(
        "Password non valida",
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

  private void checkCognome(String cognome) throws InvalidUtenteFieldException {
    if (cognome == null || cognome.isEmpty()) {
      throw new InvalidUtenteFieldException(
        "Nome mancante",
        InvalidUtenteFieldException.InvalidUtenteField.COGNOME
      );
    }
  }

  private void checkBday(LocalDate date) throws InvalidUtenteFieldException {
    if (date == null) {
      throw new InvalidUtenteFieldException("Data di nascita mancante", InvalidUtenteField.BDAY);
    }
    if (Period.between(date, LocalDate.now()).getYears() < 18) {
      throw new InvalidUtenteFieldException("Utente non maggiorenne", InvalidUtenteField.BDAY);
    }
  }

  private void checkNationality(String nationality) throws InvalidUtenteFieldException {
    if (nationality == null || nationality.isEmpty()) {
      throw new InvalidUtenteFieldException("Nazionalità mancante", InvalidUtenteField.NATIONALITY);
    }
    if (nationality.length() > 30) {
      throw new InvalidUtenteFieldException("Nazionalità troppo lunga", InvalidUtenteField.NATIONALITY);
    }
  }

  private void checkNumTel(String numTel) throws InvalidUtenteFieldException {
    if (numTel != null && !numTel.matches("^[0-9]{10}$")){
      throw new InvalidUtenteFieldException("Numero telefonico non valido", InvalidUtenteField.NUMTEL);
    }
  }

  private void checkResidenza(String residenza) throws InvalidUtenteFieldException {
    if (residenza != null && residenza.length() > 50) {
      throw new InvalidUtenteFieldException("Residenza troppo lunga", InvalidUtenteField.RESIDENZA);
    }
  }

}
