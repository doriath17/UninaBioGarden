package uninabiogarden.exceptions;

public class NoDataFoundException extends Exception{
  public static String msg = "no data found";

  public NoDataFoundException() {
    super(NoDataFoundException.msg);
  }
}
