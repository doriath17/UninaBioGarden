package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Utente;
import java.time.LocalDate;

public class RegistrationController extends Controller {

@FXML VBox root;
// @FXML private DatePicker birthField;
// @FXML private TextField capField;
// @FXML private TextField numPhoneField;
// @FXML private TextField emailField;
// @FXML private TextField nameField;
// @FXML private ChoiceBox<String> nationalityField;
// @FXML private TextField passwordField;
// @FXML private TextField residenceField;
// @FXML private TextField surnameField;
// @FXML private TextField usernameField;

public static class RawUtente {
  public final String username;
  public final String password;
  public final String nome;
  public final String cognome;
  public final LocalDate bday;
  public final String nationality;
  public final String email;
  public final String numTel;
  public final String residenza;

  private RawUtente(String username, String password, String nome, String cognome, LocalDate bday, String nationality, String email, String numTel, String residenza) {
    this.username = username;
    this.password = password;
    this.nome = nome;
    this.cognome = cognome;
    this.bday = bday;
    this.nationality = nationality;
    this.email = email;
    this.numTel = numTel;
    this.residenza = residenza;
  }


}

@Override
public Parent getRoot() {
    return root;
}

@FXML private void initialize() {
  fillNationalityField();
  // nationalityField.setValue("Italian");
}

@FXML private void returnToLogin() {
  controllerManager.logout();
}

@FXML private void openHomeView() {

    // var utenteBuilder = new Utente.Builder(
    //   usernameField.getText(),
    //   passwordField.getText(),
    //   nameField.getText(),
    //   surnameField.getText(),
    //   birthField.getValue(),
    //   nationalityField.getValue(),
    //   emailField.getText()
    // );
    // utenteBuilder
    //     .numTel(numPhoneField.getText())
    //     .residenza(residenceField.getText());

    // controllerManager.controllerDAO.addProprietario(utenteBuilder);

    controllerManager.openProprietarioHomeView();

//        if (checkFields()){
//            sanitizeFields();
//            if(DatabaseManager.proprietarioDAO.addProprietario(usernameField.getText(), emailField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), java.sql.Date.valueOf(birthField.getValue()), residenceField.getText(), nationalityField.getValue(), numPhoneField.getText())) {
//            } else {
//                Alert alert = new Alert(AlertType.ERROR);
//                alert.setTitle("Registration Error");
//                alert.setHeaderText("Registration Failed");
//                alert.setContentText("An error occurred while registering. Please try again.");
//                alert.showAndWait();
//            }
//        }
}

private void sanitizeFields() {
    // usernameField.setText(usernameField.getText().trim());
    // emailField.setText(emailField.getText().trim());
    // passwordField.setText(passwordField.getText().trim());
    // nameField.setText(nameField.getText().trim());
    // surnameField.setText(surnameField.getText().trim());
    // residenceField.setText(residenceField.getText().trim());
    // capField.setText(capField.getText().trim());
    // numPhoneField.setText(numPhoneField.getText().trim());
    // // birthField
}

// private boolean checkFields() {
    // if (usernameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty() || nameField.getText().isEmpty() || surnameField.getText().isEmpty() || birthField.getValue() == null || capField.getText().isEmpty() || nationalityField.getValue() == null) {
    //     Alert alert = new Alert(AlertType.WARNING);
    //     alert.setTitle("Registration Error");
    //     alert.setHeaderText("Missing Credentials");
    //     alert.setContentText("One of the field you entered is empty.");
    //     alert.showAndWait();
    //     return false;
    // } else{
    //     return true;
    // }
// }

void fillNationalityField() {
    String[] nationalities = {"Italian", "American", "French", "Spanish", "German", "Chinese", "Japanese", "Indian", "Brazilian", "Russian", "Other"};
    // nationalityField.getItems().addAll(nationalities);
}

}
