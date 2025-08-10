package uninabiogarden.exceptions;

public class ConnectionFailedException extends Exception {
  public static String msg = "connection failed";

  public ConnectionFailedException() {
    super(ConnectionFailedException.msg);
  }

  public ConnectionFailedException(String msg) {
    super(ConnectionFailedException.msg + " -- " + msg);
  }
}
