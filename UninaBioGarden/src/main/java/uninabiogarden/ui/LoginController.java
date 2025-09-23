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
  // @FXML CheckBox propCheckBox;
  // @FXML CheckBox coltCheckBox;

  @SuppressWarnings("exports")
  public VBox getRoot() {
    return root;
  }

  @FXML private void initialize() {
    // mutua esclusione dei checkbox
    // propCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
    //   @Override
    //   public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    //     if (newValue && coltCheckBox.isSelected()) {
    //       coltCheckBox.setSelected(false);
    //     }
    //   }
    // });
    // coltCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
    //   @Override
    //   public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
    //     if (newValue && propCheckBox.isSelected()) {
    //       propCheckBox.setSelected(false);
    //     }
    //   }
    // });
  }

  @FXML void login() {
    try {
      var username = usernameField.getText();
      var password = passwordField.getText();

      mainController.login(username, password);

      // if (propCheckBox.isSelected()) {
      //   mainController.loginProprietario(username, password);
      //   clear();
      // } else if (coltCheckBox.isSelected()) {
      //   mainController.loginColtivatore(username, password);
      //   clear();
      // } else {
      //   errorLabel.setText("Seleziona Proprietario o Coltivatore");
      // }
    } catch(WrongUsernameException e) {
      errorLabel.setText(WrongUsernameException.msg);
    } catch(WrongPasswordException e) {
      errorLabel.setText(WrongPasswordException.msg);
    } catch(ConnectionFailedException e) {
      errorLabel.setText(e.getMessage());
    }
    // catch(SQLException e) {
    //   errorLabel.setText("Errore durante il caricamento dati");
    // }
  }

  @FXML private void openRegistrationView() {
    mainController.openRegistrationView();
  }

  void clear() {
    usernameField.setText("");
    passwordField.setText("");
    // propCheckBox.setSelected(false);
    // coltCheckBox.setSelected(false);
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
