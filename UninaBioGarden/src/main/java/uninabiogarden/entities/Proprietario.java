package uninabiogarden.entities;

import java.time.LocalDate;

public class Proprietario extends Utente {

  public Proprietario(String username, String password, String email, String nome, String cognome, LocalDate bday, String nationality, String numTel, String residenza) {
    super(username, password, email, nome, cognome, bday, nationality, numTel, residenza);
  }

  // public static Proprietario createFromDB(String username, String password, String nome, String cognome, LocalDate bday, String nationality, String email, String numTel, String residenza) {
  //   return new Proprietario(username, password, nome, cognome, bday, nationality, email, numTel, residenza);
  // }

  // public static Proprietario createFromDto(ProprietarioDto dto) {
  //   return new Proprietario(
  //     dto.username(),
  //     dto.password(),
  //     dto.nome(),
  //     dto.cognome(),
  //     dto.bday(),
  //     dto.nationality(),
  //     dto.email(),
  //     dto.numTel(),
  //     dto.residenza()
  //   );
  // }

  // public void addProgetto(Progetto newProgetto) {
  //   progetti.add(newProgetto);
  //   // availableLotti.remove(newProgetto.getIdLotto());
  // }

  /// Questo metodo è necessario per evitare duplicazione di dati.
  /// Se sia il Proprietario e un Progetto mantengono un 
  /// oggetto Lotto che rappresenta lo stesso lotto hai duplicazione
  /// e questo è un problema che rende difficile la consistenza dello
  /// stato dell'app difficile da gestire.
  /// 
  /// Pertanto si preferisce avere nel sistema un solo oggetto lotto
  /// che rappresenta l'oggetto vero presente nel db.
  /// 
  /// In questo modo si rispecchia il fatto che nel db (e quindi
  /// nel minimondo) ci sia soltanto un lotto (attualmente caricato
  /// nella lista di lotti del proprietario loggato).
  // public Lotto findLottoById(List<Lotto> lotti, Long id_lotto) {
  //   for (var lotto : lotti) {
  //     if (lotto.getId() == id_lotto) {
  //       return lotto;
  //     }
  //   }
  //   return null;
  // }

  // public List<Lotto> getLotti() {
  //   return lotti;
  // }

  // public void setLotti(List<Lotto> lotti) {
  //   this.lotti = lotti;
  // }

  // public List<Progetto> getProgetti() {
  //   return progetti;
  // }

  // public void setProgetti(List<Progetto> progetti) {
  //   this.progetti = progetti;
  // }

  // public boolean hasAvailableLotti() {
  //   return availableLotti.isEmpty();
  // }

  // public List<Lotto> getAvailableLotti() {
  //   return availableLotti;
  // }

  // public void setAvailableLotti(List<Long> ids) {
  //   for (var id : ids) {
  //     availableLotti.add(findLottoById(lotti, id));
  //   }
  // }


}
