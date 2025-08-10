package uninabiogarden.entities;

import java.time.LocalDate;
import java.util.List;

import uninabiogarden.dto.ProprietarioDto;

public class Proprietario extends Utente {

  List<Lotto> lotti;
  List<Progetto> progetti;

  private Proprietario(String username, String password, String nome, String cognome, LocalDate bday, String nationality, String email, String numTel, String residenza) {
    super(username, password, nome, cognome, bday, nationality, email, numTel, residenza);
  }

  public static Proprietario createFromDB(String username, String password, String nome, String cognome, LocalDate bday, String nationality, String email, String numTel, String residenza) {
    return new Proprietario(username, password, nome, cognome, bday, nationality, email, numTel, residenza);
  }

  public static Proprietario createFromDto(ProprietarioDto dto) {
    return new Proprietario(
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

  public List<Lotto> getLotti() {
    return lotti;
  }

  public void setLotti(List<Lotto> lotti) {
    this.lotti = lotti;
  }

  public List<Progetto> getProgetti() {
    return progetti;
  }

  public void setProgetti(List<Progetto> progetti) {
    this.progetti = progetti;
  }

}
