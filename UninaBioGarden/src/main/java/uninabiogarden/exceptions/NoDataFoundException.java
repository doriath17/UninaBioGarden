package uninabiogarden.exceptions;

public class NoDataFoundException extends Exception{
  public static final String msg = "Nessun dato trovato";

  public NoDataFoundException() {
    super(NoDataFoundException.msg);
  }
}
