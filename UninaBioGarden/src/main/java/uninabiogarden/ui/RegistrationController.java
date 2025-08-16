package uninabiogarden.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
import uninabiogarden.exceptions.InvalidUtenteFieldException;
import uninabiogarden.exceptions.InvalidUtenteFieldException.InvalidUtenteField;
import uninabiogarden.service.ProprietarioService;
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
  @FXML ChoiceBox nationalityChoiceBox;

  @FXML Label usernameErrorLabel;
  @FXML Label passwordErrorLabel;
  @FXML Label emailErrorLabel;
  @FXML Label nomeErrorLabel;
  @FXML Label cognomeErrorLabel;
  @FXML Label numTelErrorLabel;
  @FXML Label residenceErrorLabel;

  @FXML Label usernameInfoLabel;
  @FXML Label passwordInfoLabel;

 
  // dependencies
  MainController mainController;
  RegistrationService registrationService = RegistrationService.getInstance();


  private static final String[] fieldRules = {
    """
    Regole username:
    - lettere permesse: [a-z] e [A-Z]
    - deve iniziare e finire con una lettera
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

    try {
      var newUtente = getFormData();
      if (proprietarioCheckBox.isSelected()) {
        registerProprietario((Proprietario) newUtente);
      } else {
        registerColtivatore((Coltivatore) newUtente);
      }
    } catch (InvalidUtenteFieldException e) {
      showErrorMessage(e);
    }

  }

  private void registerProprietario(Proprietario newProprietario) throws InvalidUtenteFieldException {
    registrationService.checkBasic(newProprietario);
  }

  private void registerColtivatore(Coltivatore newColtivatore) throws InvalidUtenteFieldException {
    registrationService.checkBasic(newColtivatore);
  }

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
      case InvalidUtenteField.NOME:
        nomeErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.COGNOME:
        cognomeErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.NUMTEL:
        numTelErrorLabel.setText(e.getMessage());
        break;
      case InvalidUtenteField.RESIDENZA:
        residenceErrorLabel.setText(e.getMessage());
        break;
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
    if (proprietarioCheckBox.isSelected()) {
      return new Proprietario(
        usernameField.getText(),
        passwordField.getText(),
        emailField.getText(),
        nomeField.getText(),
        cognomeField.getText(),
        bdayPicker.getValue(),
        (String) nationalityChoiceBox.getValue(),
        numTelField.getText(),
        residenceField.getText()
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
        numTelField.getText(),
        residenceField.getText()
      );
    }
  }

  ///
  /// 
  /// 
  /// Utility
  /// 
  /// 
  /// 
  
  void clearErrorMessages() {
    usernameErrorLabel.setText("");;
    passwordErrorLabel.setText("");;
    emailErrorLabel.setText("");;
    nomeErrorLabel.setText("");;
    cognomeErrorLabel.setText("");;
    numTelErrorLabel.setText("");;
    residenceErrorLabel.setText("");;
  }

  void setTooltip(Label label, String fieldRule) {
    Tooltip t = new Tooltip(fieldRule);
    t.setShowDelay(Duration.millis(500));
    label.setTooltip(t);
  }

}
