package uninabiogarden.ui;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.entities.Utente;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.InvalidUtenteFieldException;
import uninabiogarden.exceptions.InvalidUtenteFieldException.InvalidUtenteField;
import uninabiogarden.exceptions.WrongPasswordException;
import uninabiogarden.exceptions.WrongUsernameException;
import uninabiogarden.service.LoginService;
import uninabiogarden.service.RegistrationService;

public class RegistrationController extends ControllerBase {

  @FXML VBox root;

  @FXML CheckBox proprietarioCheckBox;
  @FXML CheckBox coltivatoreCheckBox;

  @FXML TextField usernameField;
  @FXML TextField passwordField;
  @FXML TextField emailField;
  @FXML TextField nomeField;
  @FXML TextField cognomeField;
  @FXML TextField numTelField;
  @FXML TextField residenceField;

  @FXML DatePicker bdayPicker;
  @FXML ChoiceBox<String> nationalityChoiceBox;

  @FXML Label usernameErrorLabel;
  @FXML Label passwordErrorLabel;
  @FXML Label emailErrorLabel;
  @FXML Label nomeErrorLabel;
  @FXML Label cognomeErrorLabel;
  @FXML Label bdayErrorLabel;
  @FXML Label nationalityErrorLabel;
  @FXML Label numTelErrorLabel;
  @FXML Label residenceErrorLabel;
  @FXML Label errorLabel;

  @FXML Label usernameInfoLabel;
  @FXML Label passwordInfoLabel;
 
  // dependencies
  MainController mainController;
  RegistrationService registrationService = RegistrationService.getInstance();
  LoginService loginService = LoginService.getInstance();

  private static final String[] fieldRules = {
    """
    Regole username:
    - lettere permesse: [a-z] e [A-Z]
    - deve iniziare con una lettera
    - caratteri speciali: - _
    - può contenere numeri [0-9]
    - lunghezza minima di 6 caratteri
    - lunghezza massima di 30 caratteri
    """,
    """
    Regole password:
    - lettere permesse: [a-z] e [A-Z]
    - può contenere numeri [0-9]
    - caratteri speciali: ! @ # $ % ^ & * _ - + = ?
    - lunghezza minima di 8 caratteri
    - lunghezza massima di 60 caratteri
    """,
  };

  private final List<String> nationalities = Arrays.asList(
    "Italiana",
    "Americana",
    "Cinese",
    "Tedesca",
    "Francese",
    "Spagnola",
    "Inglese",
    "Russa",
    "Giapponese",
    "Brasiliana",
    "Indiana",
    "Messicana",
    "Canadese",
    "Australiana"
  );
  
  private final ObservableList<String> nationalitiesObsList = FXCollections.observableArrayList(nationalities);

  @SuppressWarnings("exports")
  @Override
  public VBox getRoot() {
    return root;
  }

  @FXML private void initialize() {
    proprietarioCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue && coltivatoreCheckBox.isSelected()) {
          coltivatoreCheckBox.setSelected(false);
        }
      }
    });
    coltivatoreCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue && proprietarioCheckBox.isSelected()) {
          proprietarioCheckBox.setSelected(false);
        }
      }
    });

    setTooltip(usernameInfoLabel, fieldRules[0]);
    setTooltip(passwordInfoLabel, fieldRules[1]);

    ControllerUtility.addDigitsFilter(numTelField);
    ControllerUtility.addTextLimiter(usernameField, 80);
    ControllerUtility.addTextLimiter(passwordField, 60);

    nationalityChoiceBox.setItems(nationalitiesObsList);
    nationalityChoiceBox.setValue(nationalitiesObsList.get(0));

    bdayPicker.setValue(LocalDate.now().minusYears(20));
  }
  
  ///
  /// 
  /// 
  /// Transitions
  /// 
  /// 
  /// 

  @FXML private void returnToLogin() {
    clearErrorMessages();
    mainController.openLoginView();
  }

  @FXML private void register() {
    clearErrorMessages();

    if (!proprietarioCheckBox.isSelected() && !coltivatoreCheckBox.isSelected()) {
      errorLabel.setText("Selezionare Proprietario o Coltivatore");
      return;
    }

    try {
      var newUtente = getFormData();
      if (proprietarioCheckBox.isSelected()) {
        mainController.registerProprietario((Proprietario) newUtente);
      } else {
        mainController.registerColtivatore((Coltivatore) newUtente);
      }
    } catch (InvalidUtenteFieldException e) {
      showErrorMessage(e);
    } catch (ConnectionFailedException e) {
      errorLabel.setText(e.getMessage());
    } catch (SQLException e) {
      if (e.getSQLState().equals("23505")) {
        errorLabel.setText("Utente già esistente");
      } else {
        System.err.println(e.getSQLState());
        System.err.println(e.getMessage());
        e.printStackTrace();
        errorLabel.setText("Errore dal database durante la registrazione");
      }
    } catch (WrongUsernameException | WrongPasswordException e) {
      errorLabel.setText("Impossibile fare il login");
      System.err.println(e.getMessage());
      e.printStackTrace();
    } 
  }

  ///
  /// 
  /// 
  /// Form
  /// 
  /// 
  /// 
  
  Utente getFormData() {
    var numTel = numTelField.getText().isEmpty() ? null : numTelField.getText();
    var residence = residenceField.getText().isEmpty() ? null : residenceField.getText();

    if (proprietarioCheckBox.isSelected()) {
      return new Proprietario(
        usernameField.getText(),
        passwordField.getText(),
        emailField.getText(),
        nomeField.getText(),
        cognomeField.getText(),
        bdayPicker.getValue(),
        (String) nationalityChoiceBox.getValue(),
        numTel,
        residence
      );
    } else {
      return new Coltivatore(
        usernameField.getText(),
        passwordField.getText(),
        emailField.getText(),
        nomeField.getText(),
        cognomeField.getText(),
        bdayPicker.getValue(),
        (String) nationalityChoiceBox.getValue(),
        numTel,
        residence
      );
    }
  }

  ///
  /// 
  /// 
  /// Utilities
  /// 
  /// 
  /// 
  
  @SuppressWarnings("incomplete-switch")
  private void showErrorMessage(InvalidUtenteFieldException e) {
    switch (e.getInv()) {
      case InvalidUtenteField.USERNAME:
        usernameErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.PASSWORD:
        passwordErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.EMAIL:
        emailErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.NOME:
        nomeErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.COGNOME:
        cognomeErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.BDAY: 
        bdayErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.NATIONALITY:
        nationalityErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.NUMTEL:
        numTelErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.RESIDENZA:
        residenceErrorLabel.setText(e.getMessage());
        break;
    }
  }

  void clearUIContent() {
    clearErrorMessages();
    clearFields();
  }

  void clearFields() {
    usernameField.setText("");
    passwordField.setText("");
    emailField.setText("");
    nomeField.setText("");
    cognomeField.setText("");
    bdayPicker.setValue(LocalDate.now().minusYears(20));
    nationalityChoiceBox.setValue(nationalitiesObsList.get(0));
    numTelField.setText("");
    residenceField.setText("");
  }

  void clearErrorMessages() {
    usernameErrorLabel.setText("");;
    passwordErrorLabel.setText("");;
    emailErrorLabel.setText("");;
    nomeErrorLabel.setText("");;
    cognomeErrorLabel.setText("");;
    numTelErrorLabel.setText("");;
    residenceErrorLabel.setText("");
    errorLabel.setText("");
  }

  void setTooltip(Label label, String fieldRule) {
    Tooltip t = new Tooltip(fieldRule);
    t.setShowDelay(Duration.millis(500));
    label.setTooltip(t);
  }

  void simulate() {
    usernameField.setText("aleeeeeeeee");
    passwordField.setText("oleeeeeeeee");
    emailField.setText("alexthegreat17@example.com");
    nomeField.setText("Alessandro");
    cognomeField.setText("The Great");
    bdayPicker.setValue(LocalDate.now().minusYears(20));
    numTelField.setText("1234567890");
    residenceField.setText("The world is the residence i conquered.");
  }

  void showSuccessMessage(String message) {
    errorLabel.setStyle("-fx-text-fill: rgba(0, 143, 59, 1)");
    errorLabel.setText(message);
  }

}
