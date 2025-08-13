package uninabiogarden.entities;

import java.time.LocalDate;

public class Coltivatore extends Utente {
  public Coltivatore(String username, String password, String nome, String cognome, LocalDate bday, String nationality, String email, String numTel, String residenza) {
    super(username, password, nome, cognome, bday, nationality, email, numTel, residenza);
  }
}
