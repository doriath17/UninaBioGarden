package uninabiogarden.service;

import uninabiogarden.entities.Utente;
import uninabiogarden.exceptions.InvalidUtenteFieldException;
import uninabiogarden.exceptions.InvalidUtenteFieldException.InvalidUtenteField;

public class RegistrationService {

  private static final RegistrationService instance = new RegistrationService();
  private final LoginService loginService = LoginService.getInstance();

  private RegistrationService() {}

  public static RegistrationService getInstance() {return instance;}

  public void checkBasic(Utente utenteToCheck) throws InvalidUtenteFieldException {
    checkUsername(utenteToCheck.getUsername());
    checkPassword(utenteToCheck.getPassword());
    checkNome(utenteToCheck.getNome());
    checkCognome(utenteToCheck.getCognome());
  }

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
    if (!password.matches("^[a-zA-Z0-9_-!@#$%^&*_-+=?]{1,60}$")){
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

}
