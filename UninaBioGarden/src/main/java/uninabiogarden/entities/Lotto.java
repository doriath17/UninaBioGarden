package uninabiogarden.entities;

public class Lotto {
  
  private Long id;
  private String indirizzo;
  private String codice;
  private Double estensione;
  private String orto;

  public Lotto(Long id, String indirizzo, String codice, Double estensione, String orto) {
    this.id = id;
    this.indirizzo = indirizzo;
    this.codice = codice;
    this.estensione = estensione;
    this.orto = orto;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public String getCodice() {
    return codice;
  }

  public void setCodice(String codice) {
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
