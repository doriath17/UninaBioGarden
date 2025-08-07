package uninabiogarden.entities;

import java.time.LocalDate;

public abstract class Utente {

  private String username;
  private String password;
  private String nome;
  private String cognome;
  private LocalDate bday;
  private String nationality;
  private String email;
  private String numTel;
  private String residenza;

  public static class Builder {
    final String username;
    final String password;
    final String nome;
    final String cognome;
    final LocalDate bday;
    final String nationality;
    final String email;
    String numTel;
    String residenza;

    public Builder(String username, String password, String nome, String cognome, LocalDate bday, String nationality, String email) {
      this.username = username;
      this.password = password;
      this.nome = nome;
      this.cognome = cognome;
      this.bday = bday;
      this.nationality = nationality;
      this.email = email;
    }

    public Builder numTel(String numTel) {
      this.numTel = numTel;
      return this;
    }

    public Builder residenza(String residenza) {
      this.residenza = residenza;
      return this;
    }

    public Proprietario buildProprietario() {
      return new Proprietario(username, password, nome, cognome, bday, nationality, email, numTel, residenza);
    }

    public Coltivatore buildColtivatore() {
      return new Coltivatore(username, password, nome, cognome, bday, nationality, email, numTel, residenza);
    }
  }

  public Utente(String username, String password, String nome, String cognome, LocalDate bday, String nationality, String email, String numTel, String residenza) {
    this.username = username;
    this.password = password;
    this.nome = nome;
    this.cognome = cognome;
    this.bday = bday;
    this.nationality = nationality;
    this.email = email;
    this.numTel = numTel;
    this.residenza = residenza;
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
