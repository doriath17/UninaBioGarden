package uninabiogarden.entities;

import uninabiogarden.dto.LottoDto;

public class Lotto {
  
  int id;
  String indirizzo;
  int codice;
  Double estensione;
  String orto;

  private Lotto(int id, String indirizzo, int codice, Double estensione, String orto) {
    this.id = id;
    this.indirizzo = indirizzo;
    this.codice = codice;
    this.estensione = estensione;
    this.orto = orto;
  }

  public static Lotto createFromDB(int id, String indirizzo, int codice, Double estensione, String orto) {
    return new Lotto(id, indirizzo, codice, estensione, orto);
  }

  public static Lotto createFromDto(LottoDto dto) {
    return new Lotto(
      dto.id(),
      dto.indirizzo(),
      dto.codice(),
      dto.estensione(),
      dto.orto()
    );
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public int getCodice() {
    return codice;
  }

  public void setCodice(int codice) {
    this.codice = codice;
  }

  public Double getEstensione() {
    return estensione;
  }

  public void setEstensione(Double estensione) {
    this.estensione = estensione;
  }

  public String getOrto() {
    return orto;
  }

  public void setOrto(String orto) {
    this.orto = orto;
  }

}
