package uninabiogarden.core;

public class LoginSession {
  private String currUsername;
  private UserType currType;

  public enum UserType {
    PROPRIETARIO,
    COLTIVATORE,
    NONE
  };

  public LoginSession() {
    this.currUsername = null;
    this.currType = UserType.NONE;
  }

  public String getUsername() {
    return this.currUsername;
  }

  public UserType getCurrType() {
    return currType;
  }

  public void login(String username, UserType type) {
    this.currUsername = username;
    this.currType = type;
  }

  public void logout() {
    this.currUsername = null;
    this.currType = UserType.NONE;
  }

  public boolean isProprietarioSession() {
    return this.currType == UserType.PROPRIETARIO;
  }

  public boolean isColotivatoreSession() {
    return this.currType == UserType.COLTIVATORE;
  }

  
}
