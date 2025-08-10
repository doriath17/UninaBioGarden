package uninabiogarden.entities;

import java.time.LocalDate;

import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.exceptions.EmptyValueException;
import uninabiogarden.exceptions.WrongDataFineException;

public class Progetto {
  int id;
  String nome;
  LocalDate dataInizio;
  LocalDate dataFine;
  String descrizione;
  Proprietario proprietario;
  Lotto lotto;

  private Progetto(String nome, LocalDate dataInizio, LocalDate dataFine, String descrizione, Proprietario proprietario, Lotto lotto)  {
    this.nome = nome;
    this.dataInizio = dataInizio;
    this.dataFine = dataFine;
    this.descrizione = descrizione;
    this.proprietario = proprietario;
    this.lotto = lotto;
  }

  public static Progetto createFromDB(int id, String nome, LocalDate dataInizio, LocalDate dataFine, String descrizione,  Proprietario proprietario, Lotto lotto) {
    var p = new Progetto(nome, dataInizio, dataFine, descrizione, proprietario, lotto);
    p.setId(id);
    return p;
  }

  public static Progetto createFromDto(ProgettoDto dto, Proprietario proprietario, Lotto lotto) throws WrongDataFineException, EmptyValueException {
    Constraint.checkNotEmptyValue(dto.nome());
    Constraint.checkNotEmptyValue(dto.dataInizio());
    Constraint.checkDataFine(dto.dataInizio(), dto.dataFine());
    // Constraint.checkNotEmptyValue(proprietario);
    // Constraint.checkNotEmptyValue(lotto);

    return new Progetto(
      dto.nome(),
      dto.dataInizio(),
      dto.dataFine(),
      dto.descrizione(),
      proprietario,
      lotto
    );
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
