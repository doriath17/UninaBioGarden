package uninabiogarden.entities;

import java.time.LocalDate;

public class Proprietario extends Utente {

  public Proprietario(String username, String password, String email, String nome, String cognome, LocalDate bday, String nationality, String numTel, String residenza) {
    super(username, password, email, nome, cognome, bday, nationality, numTel, residenza);
  }

}
