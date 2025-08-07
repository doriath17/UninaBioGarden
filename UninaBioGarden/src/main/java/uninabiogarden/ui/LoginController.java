package uninabiogarden.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uninabiogarden.controllers.WrongPasswordException;
import uninabiogarden.controllers.WrongUsernameException;

public class LoginController extends Controller {
  @FXML VBox root;
  @FXML TextField usernameField;
  @FXML PasswordField passwordField;
  @FXML Label errorLabel;
  @FXML CheckBox propCheckBox;
  @FXML CheckBox coltCheckBox;

  @SuppressWarnings("exports")
  public Parent getRoot() {
    return root;
  }

  @FXML private void initialize() {
    // mutua esclusione dei checkbox
    propCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue && coltCheckBox.isSelected()) {
          coltCheckBox.setSelected(false);
        }
      }
    });
    coltCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue && propCheckBox.isSelected()) {
          propCheckBox.setSelected(false);
        }
      }
    });
  }

  @FXML private void login() {
    System.out.println(propCheckBox.isSelected());
    System.out.println(coltCheckBox.isSelected());
    try {
      if (propCheckBox.isSelected()) {
        controllerManager.loginProprietario(usernameField.getText(), passwordField.getText());
      } else if (coltCheckBox.isSelected()) {
        controllerManager.loginColtivatore(usernameField.getText(), passwordField.getText());
      } else {
        errorLabel.setText("Must select either Proprietario or Coltivatore");
      }
    } catch(WrongUsernameException e) {
      errorLabel.setText(WrongUsernameException.msg);
    } catch(WrongPasswordException e) {
      errorLabel.setText(WrongPasswordException.msg);
    }

  }

  @FXML private void openRegistrationView() {
      controllerManager.openRegistrationView();
  }

  void clear() {
    usernameField.setText("");
    passwordField.setText("");
    propCheckBox.setSelected(false);
    coltCheckBox.setSelected(false);
    errorLabel.setText("");
  }

  // @FXML private void openHomeView(){
  //   String email = usernameField.getText();
  //   String password = passwordField.getText();

  //   controllerManager.openProprietarioHomeView();

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
    
  // }
}
