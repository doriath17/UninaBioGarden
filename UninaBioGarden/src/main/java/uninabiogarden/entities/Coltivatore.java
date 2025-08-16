package uninabiogarden.entities;

import java.time.LocalDate;

public class Coltivatore extends Utente {
  public Coltivatore(String username, String password, String email, String nome, String cognome, LocalDate bday, String nationality, String numTel, String residenza) {
    super(username, password, email, nome, cognome, bday, nationality, numTel, residenza);
  }
}
