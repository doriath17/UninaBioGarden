package uninabiogarden.entities;

import java.time.LocalDate;

import uninabiogarden.dto.ColtivatoreDto;

public class Coltivatore extends Utente {
  private Coltivatore(String username, String password, String nome, String cognome, LocalDate bday, String nationality, String email, String numTel, String residenza) {
    super(username, password, nome, cognome, bday, nationality, email, numTel, residenza);
  }

  public static Coltivatore createFromDB(String username, String password, String nome, String cognome, LocalDate bday, String nationality, String email, String numTel, String residenza) {
    return new Coltivatore(username, password, nome, cognome, bday, nationality, email, numTel, residenza);
  }

  public static Coltivatore createFromDto(ColtivatoreDto dto) {
    return new Coltivatore(
      dto.username(),
      dto.password(),
      dto.nome(),
      dto.cognome(),
      dto.bday(),
      dto.nationality(),
      dto.email(),
      dto.numTel(),
      dto.residenza()
    );
  }

}
