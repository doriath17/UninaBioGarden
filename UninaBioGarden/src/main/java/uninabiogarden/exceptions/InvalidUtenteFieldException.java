package uninabiogarden.exceptions;

import uninabiogarden.entities.Utente;

public class InvalidUtenteFieldException extends Exception {

  public enum InvalidUtenteField {
    USERNAME,
    PASSWORD,
    NOME,
    COGNOME,
    EMAIL,
    BDAY,
    NATIONALITY,
    NUMTEL,
    RESIDENZA;
  }

  @SuppressWarnings("unused")
  private final InvalidUtenteField inv;

  public InvalidUtenteFieldException(String msg, InvalidUtenteField inv) {
    super(msg);
    this.inv = inv;
  }

  public InvalidUtenteField getInv() {
    return inv;
  }

}
