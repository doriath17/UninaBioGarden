package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uninabiogarden.dao.DatabaseManager;
import uninabiogarden.dao.ProprietarioDAO;

public class LoginController extends Controller {
  @FXML VBox root;
  @FXML Button btnRegistration;
  @FXML TextField emailField;
  @FXML TextField passwordField;

  public Parent getRoot() {
      return root;
  }

  @FXML private void openRegistrationView() {
      controllerManager.openRegistrationView();
  }

  @FXML private void openHomeView(){
    String email = emailField.getText();
    String password = passwordField.getText();

    // System.out.println("C H E C K I N G    L O G I N");
    // System.out.println("Email: " + email);
    // System.out.println("Password: " + password);

    // System.out.println(DatabaseManager.proprietarioDAO.checkProprietarioExists(email, password));

    if( email.isEmpty() || password.isEmpty()) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Login Error");
        alert.setHeaderText("Missing Credentials");
        alert.setContentText("Please enter both email and password.");
        alert.showAndWait();

    } else{
        
        email = email.trim();
        password = password.trim();

        if(DatabaseManager.proprietarioDAO.checkProprietarioExists(email, password)) {
            controllerManager.openProprietarioHomeView();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("The email or password you entered is incorrect.");
            alert.showAndWait();
        }
    }
    
  }
}
