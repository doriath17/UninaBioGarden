package uninabiogarden.entities;

import java.time.LocalDate;

public class Progetto {
  Long id;
  String nome;
  LocalDate dataInizio;
  LocalDate dataFine;
  String descrizione;
  Proprietario proprietario; // considera rimuovere questo campo
  Lotto lotto;

  public Progetto() {

  }

  public Progetto(Progetto source) {
    this.id = source.id;
    this.nome = source.nome;
    this.dataInizio = source.dataInizio;
    this.dataFine = source.dataFine;
    this.descrizione = source.descrizione;
    this.proprietario = source.proprietario;
    this.lotto = source.lotto;
  }

  public Progetto(Long id, String nome, LocalDate dataInizio, LocalDate dataFine, String descrizione, Proprietario proprietario, Lotto lotto)  {
    this.id = id;
    this.nome = nome;
    this.dataInizio = dataInizio;
    this.dataFine = dataFine;
    this.descrizione = descrizione;
    this.proprietario = proprietario;
    this.lotto = lotto;
  }

  public boolean isTerminated() {
    return dataFine != null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public LocalDate getDataInizio() {
    return dataInizio;
  }

  public void setDataInizio(LocalDate data_inizio) {
    this.dataInizio = data_inizio;
  }

  public LocalDate getDataFine() {
    return dataFine;
  }

  public void setDataFine(LocalDate dataFine) {
    this.dataFine = dataFine;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Proprietario getProprietario() {
    return proprietario;
  }

  public void setProprietario(Proprietario prop) {
    this.proprietario = prop;
  }

  public Lotto getLotto() {
    return lotto;
  }

  public void setLotto(Lotto lotto) {
    this.lotto = lotto;
  }
}
