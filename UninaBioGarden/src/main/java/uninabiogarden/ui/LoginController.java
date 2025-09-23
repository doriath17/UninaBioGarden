package uninabiogarden.ui;

import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class LoginController extends ControllerBase {
  @FXML VBox root;
  @FXML TextField usernameField;
  @FXML PasswordField passwordField;
  @FXML Label errorLabel;

  @SuppressWarnings("exports")
  public VBox getRoot() {
    return root;
  }

  @FXML void login() {
    try {
      var username = usernameField.getText();
      var password = passwordField.getText();
      mainController.login(username, password);
    } catch(WrongUsernameException e) {
      errorLabel.setText(WrongUsernameException.msg);
    } catch(WrongPasswordException e) {
      errorLabel.setText(WrongPasswordException.msg);
    } catch(ConnectionFailedException e) {
      errorLabel.setText(e.getMessage());
    }
  }

  @FXML private void openRegistrationView() {
    mainController.openRegistrationView();
  }

  void clear() {
    usernameField.setText("");
    passwordField.setText("");
    errorLabel.setText("");
  }
}
