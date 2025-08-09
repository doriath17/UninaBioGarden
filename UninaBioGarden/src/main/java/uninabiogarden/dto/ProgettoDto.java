package uninabiogarden.dto;

import java.time.LocalDate;

public record ProgettoDto (
  int id,
  String nome,
  LocalDate dataInizio,
  LocalDate dataFine, 
  String descrizione, 
  UtenteDto proprietario,
  LottoDto lotto
) {}
