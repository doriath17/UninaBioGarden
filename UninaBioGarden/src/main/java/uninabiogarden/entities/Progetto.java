package uninabiogarden.entities;

import java.time.LocalDate;

import uninabiogarden.dto.ProgettoDto;
import uninabiogarden.exceptions.EmptyValueException;
import uninabiogarden.exceptions.WrongDataFineException;
import uninabiogarden.service.Constraint;

public class Progetto {
  Long id;
  String nome;
  LocalDate dataInizio;
  LocalDate dataFine;
  String descrizione;
  Proprietario proprietario; // considera rimuovere questo campo
  Lotto lotto;

  private Progetto(String nome, LocalDate dataInizio, LocalDate dataFine, String descrizione, Proprietario proprietario, Lotto lotto)  {
    this.nome = nome;
    this.dataInizio = dataInizio;
    this.dataFine = dataFine;
    this.descrizione = descrizione;
    this.proprietario = proprietario;
    this.lotto = lotto;
  }

  public static Progetto createFromDB(Long id, String nome, LocalDate dataInizio, LocalDate dataFine, String descrizione,  Proprietario prop, Long id_lotto) {
    var lotto = prop.findLottoById(prop.getLotti(), id_lotto);
    if (lotto == null) {
      System.err.println("Problems in findAllLotti of ProprietarioDao class: not all lotti were found");
      System.exit(1);
    }
    var p = new Progetto(nome, dataInizio, dataFine, descrizione, prop, lotto);
    p.setId(id);
    return p;
  }

  public static Progetto createFromDto(ProgettoDto dto, Proprietario proprietario, Lotto lotto) throws WrongDataFineException, EmptyValueException {

    // validate dto
    // use dto data to create object

    // Constraint.checkNotEmptyValue(dto.nome());
    // Constraint.checkNotEmptyValue(dto.dataInizio());
    // Constraint.checkDataFine(dto.dataInizio(), dto.dataFine());
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
