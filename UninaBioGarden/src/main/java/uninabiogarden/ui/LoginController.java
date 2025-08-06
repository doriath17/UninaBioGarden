package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uninabiogarden.controllers.LoginException;

public class LoginController extends Controller {
  @FXML VBox root;
  @FXML TextField usernameField;
  @FXML PasswordField passwordField;
  @FXML Label errorLabel;

  public Parent getRoot() {
      return root;
  }

  @FXML private void login() {
    try {
      controllerManager.controllerDAO.login(usernameField.getText(), passwordField.getText());
      controllerManager.openProprietarioHomeView();
    } catch(LoginException e) {
      errorLabel.setText("Wrong username or password");
    }
  }

  @FXML private void openRegistrationView() {
      controllerManager.openRegistrationView();
  }

  @FXML private void openHomeView(){
    String email = usernameField.getText();
    String password = passwordField.getText();

    controllerManager.openProprietarioHomeView();

    // System.out.println("C H E C K I N G    L O G I N");
    // System.out.println("Email: " + email);
    // System.out.println("Password: " + password);

    // System.out.println(DatabaseManager.proprietarioDAO.checkProprietarioExists(email, password));

//    if( email.isEmpty() || password.isEmpty()) {
//        Alert alert = new Alert(AlertType.WARNING);
//        alert.setTitle("Login Error");
//        alert.setHeaderText("Missing Credentials");
//        alert.setContentText("Please enter both email and password.");
//        alert.showAndWait();
//
//    } else{
//
//        email = email.trim();
//        password = password.trim();
//
//        if(DatabaseManager.proprietarioDAO.checkProprietarioExists(email, password)) {
//            controllerManager.openProprietarioHomeView();
//        } else {
//            Alert alert = new Alert(AlertType.ERROR);
//            alert.setTitle("Login Error");
//            alert.setHeaderText("Invalid Credentials");
//            alert.setContentText("The email or password you entered is incorrect.");
//            alert.showAndWait();
//        }
//    }
    
  }
}
