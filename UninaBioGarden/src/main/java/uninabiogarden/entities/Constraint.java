package uninabiogarden.entities;

import java.time.LocalDate;

public class Constraint {
  public static void checkDataFine(LocalDate dataInizio, LocalDate dataFine) throws WrongDataFineException {
    if (dataFine != null && dataFine.isBefore(dataFine)) {
      throw new WrongDataFineException();
    }
  }

  public static void checkNotEmptyValue(Object value) throws EmptyValueException {
    if (value == null) {
      throw new EmptyValueException();
    }
    if (value instanceof String && ((String)value).isEmpty()) {
      throw new EmptyValueException();
    }
  }
}
