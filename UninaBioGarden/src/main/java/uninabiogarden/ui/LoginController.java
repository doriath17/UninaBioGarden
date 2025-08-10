package uninabiogarden.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uninabiogarden.core.LoginSession.UserType;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;

public class LoginController extends ControllerBase {
  @FXML VBox root;
  @FXML TextField usernameField;
  @FXML PasswordField passwordField;
  @FXML Label errorLabel;
  @FXML CheckBox propCheckBox;
  @FXML CheckBox coltCheckBox;

  MainController mainController;

  @SuppressWarnings("exports")
  public VBox getRoot() {
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

  private void loginProprietario()
    throws ConnectionFailedException,
    WrongUsernameException,
    WrongPasswordException
  {
    Proprietario p = context.getProprietarioService().authenticate(usernameField.getText(), passwordField.getText());
    context.getSession().login(p.getUsername(), UserType.PROPRIETARIO);
    context.getAppState().setLoggedInProprietario(p);
  }

  private void loginColtivatore() 
    throws ConnectionFailedException,
    WrongUsernameException,
    WrongPasswordException
  {
    Coltivatore c = context.getColtivatoreService().authenticate(usernameField.getText(), passwordField.getText());
    context.getSession().login(c.getUsername(), UserType.COLTIVATORE);
    context.getAppState().setLoggedInColtivatore(c);
  }

  @FXML private void login() {
    try {
      if (propCheckBox.isSelected()) {
        loginProprietario();
      } else if (coltCheckBox.isSelected()) {
        loginColtivatore();
      } else {
        errorLabel.setText("Must select either Proprietario or Coltivatore");
      }
      mainController.openHomeView();
    } catch(WrongUsernameException e) {
      errorLabel.setText(WrongUsernameException.msg);
    } catch(WrongPasswordException e) {
      errorLabel.setText(WrongPasswordException.msg);
    } catch(ConnectionFailedException e) {
      errorLabel.setText(e.getMessage());
    }
  }

  @FXML private void openRegistrationView() {
    ((MainController)parent).openRegistrationView();
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
