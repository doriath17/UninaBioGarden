package uninabiogarden.entities;

import java.time.LocalDate;

public abstract class Utente {

  private Long id;
  private String username;
  private String password;
  private String email;
  private String nome;
  private String cognome;
  private LocalDate bday;
  private String nationality;
  private String numTel;
  private String residenza;

  protected Utente(String username, String password, String email, String nome, String cognome, LocalDate bday, String nationality,  String numTel, String residenza) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.nome = nome;
    this.cognome = cognome;
    this.bday = bday;
    this.nationality = nationality;
    this.numTel = numTel;
    this.residenza = residenza;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public LocalDate getBday() {
    return bday;
  }

  public void setBday(LocalDate bday) {
    this.bday = bday;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNumTel() {
    return numTel;
  }

  public void setNumTel(String numTel) {
    this.numTel = numTel;
  }

  public String getResidenza() {
    return residenza;
  }

  public void setResidenza(String residenza) {
    this.residenza = residenza;
  }

}
