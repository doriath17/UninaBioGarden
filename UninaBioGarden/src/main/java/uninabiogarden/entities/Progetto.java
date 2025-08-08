package uninabiogarden.entities;

import java.time.LocalDate;

public class Progetto {
  int id;
  String nome;
  LocalDate data_inizio;
  LocalDate data_fine;
  String descrizione;
  String username_prop;
  int id_lotto;

  public static class Builder {
    int id;
    String nome;
    LocalDate data_inizio;
    LocalDate data_fine;
    String descrizione;
    String username_prop;
    int id_lotto;

    public Builder(int id, String nome, LocalDate data_inizio, LocalDate data_fine, String descrizione,
        String username_prop, int id_lotto) {
      this.id = id;
      this.nome = nome;
      this.data_inizio = data_inizio;
      this.data_fine = data_fine;
      this.descrizione = descrizione;
      this.username_prop = username_prop;
      this.id_lotto = id_lotto;
    }

    public Progetto build(){
      return new Progetto(id, nome, data_inizio, data_fine, descrizione, username_prop, id_lotto);
    }
  }

  public Progetto(int id, String nome, LocalDate data_inizio, LocalDate data_fine, String descrizione,
      String username_prop, int id_lotto) {
    this.id = id;
    this.nome = nome;
    this.data_inizio = data_inizio;
    this.data_fine = data_fine;
    this.descrizione = descrizione;
    this.username_prop = username_prop;
    this.id_lotto = id_lotto;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public LocalDate getData_inizio() {
    return data_inizio;
  }

  public void setData_inizio(LocalDate data_inizio) {
    this.data_inizio = data_inizio;
  }

  public LocalDate getData_fine() {
    return data_fine;
  }

  public void setData_fine(LocalDate data_fine) {
    this.data_fine = data_fine;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public String getUsername_prop() {
    return username_prop;
  }

  public void setUsername_prop(String username_prop) {
    this.username_prop = username_prop;
  }

  public int getId_lotto() {
    return id_lotto;
  }

  public void setId_lotto(int id_lotto) {
    this.id_lotto = id_lotto;
  }
}
