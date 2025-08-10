package uninabiogarden.dto;

import java.time.LocalDate;

public record ColtivatoreDto(
  String username,
  String password,
  String nome,
  String cognome,
  LocalDate bday,
  String nationality,
  String email,
  String numTel,
  String residenza
) {}
