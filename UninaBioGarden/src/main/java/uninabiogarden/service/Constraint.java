package uninabiogarden.service;

import java.time.LocalDate;

public class Constraint {
  public static boolean checkDataFine(LocalDate dataInizio, LocalDate dataFine)  {
    if (dataFine != null && dataFine.isBefore(dataInizio)) {
      return true;
    }
    return false;
  }

  public static boolean checkNotEmptyValue(Object value){
    if (value == null || (value instanceof String && ((String)value).isEmpty())) {
      return true;
    }
    return false;
  }
}
