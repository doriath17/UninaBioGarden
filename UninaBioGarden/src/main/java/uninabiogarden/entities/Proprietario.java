package uninabiogarden.entities;

import java.time.LocalDate;
import java.util.List;

public class Proprietario extends Utente {

  public Proprietario(String username, String password, String nome, String cognome, LocalDate bday, String nationality, String email, String numTel, String residenza) {
    super(username, password, nome, cognome, bday, nationality, email, numTel, residenza);
  }

  @Override 
  public String toString() {
    return "{username: " + this.getUsername() + "}";
  }
}
