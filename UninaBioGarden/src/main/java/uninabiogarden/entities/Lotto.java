package uninabiogarden.entities;

public class Lotto {
  
  int id;
  String indirizzo;
  int codice;
  Double estensione;
  String orto;

  public static class Builder {
    final int id;
    final String indirizzo;
    final int codice;
    final Double estensione;
    final String orto;

    public Builder(int id, String indirizzo, int codice, Double estensione, String orto) {
      this.id = id;
      this.indirizzo = indirizzo;
      this.codice = codice;
      this.estensione = estensione;
      this.orto = orto;
    }

    public Lotto build() {
      return new Lotto(id, indirizzo, codice, estensione, orto);
    }

  }

  public Lotto(int id, String indirizzo, int codice, Double estensione, String orto) {
    this.id = id;
    this.indirizzo = indirizzo;
    this.codice = codice;
    this.estensione = estensione;
    this.orto = orto;
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
