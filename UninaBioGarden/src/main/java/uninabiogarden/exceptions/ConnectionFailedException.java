package uninabiogarden.exceptions;

public class ConnectionFailedException extends Exception {
  public static String msg = "Connessione al database fallita";

  public ConnectionFailedException() {
    super(ConnectionFailedException.msg);
  }

  public ConnectionFailedException(String msg) {
    super(ConnectionFailedException.msg + " -- " + msg);
  }
}
