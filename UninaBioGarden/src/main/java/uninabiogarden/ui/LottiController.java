package uninabiogarden.ui;


import java.sql.SQLException;
import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.FormatException;
import uninabiogarden.exceptions.MissingFieldException;
import uninabiogarden.exceptions.NoDataFoundException;

public class LottiController extends ControllerBase {
  @FXML VBox root;
  @FXML VBox contentRoot;

  @FXML TextField indirizzoField;
  @FXML TextField codiceField;
  @FXML TextField estensioneField;
  @FXML TextField ortoField;

  @FXML Label errorLabel;

  HomeController homeController;

  Parent activeView;
  
  @FXML TableView<Lotto> tableView;
  @FXML TableColumn<String, String> indirizzoCol;
  @FXML TableColumn<Integer, String> codiceCol;
  @FXML TableColumn<Double, String> estensioneCol;
  @FXML TableColumn<String, String> ortoCol;

  ObservableList<Lotto> lotti = FXCollections.observableArrayList();
  
  @SuppressWarnings("exports")
  @Override
  public VBox getRoot() {
    return root;
  }

  @FXML private void initialize() {
    tableView.setItems(lotti);
    indirizzoCol.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
    codiceCol.setCellValueFactory(new PropertyValueFactory<>("codice"));
    estensioneCol.setCellValueFactory(new PropertyValueFactory<>("estensione"));
    ortoCol.setCellValueFactory(new PropertyValueFactory<>("orto"));

    tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lotto>() {
      @Override
      public void changed(ObservableValue<? extends Lotto> observable, Lotto oldValue, Lotto newValue) {
        if (newValue != null) {
          fillForm(newValue);
        }
      }
    });

    ControllerUtility.addTextLimiter(indirizzoField, 80);
    addCodiceLottoFilter(codiceField);
    addEstensioneLottoFilter(estensioneField);
    ControllerUtility.addTextLimiter(ortoField, 80);
  }

  ///
  /// 
  /// 
  /// CRUD
  /// 
  /// 
  /// 

  @FXML public void update() {
    var index = tableView.getSelectionModel().getSelectedIndex();
    if (index == -1) {
      showErrorMessage("Nessun lotto selezionato");
      return;
    }
    try {
      var lottoToUpdate = getFormData();
      lottoToUpdate.setId(tableView.getSelectionModel().getSelectedItem().getId());
      context.getLottoService().update(lottoToUpdate);
      lotti.set(index, lottoToUpdate);
      showSuccessMessage("Lotto aggiornato!");
    } catch (MissingFieldException | FormatException | ConnectionFailedException e) {
      showErrorMessage(e.getMessage());
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      showErrorMessage("Errore dal database durante l'aggiornamento");
    }
  }

  @FXML public void delete() {
    var index = tableView.getSelectionModel().getSelectedIndex();
    if (index == -1) {
      showErrorMessage("Nessun lotto selezionato");
      return;
    }
    var lottoToDelete = lotti.get(index);
    try {
      context.getLottoService().delete(lottoToDelete);
    } catch (ConnectionFailedException e) {
      showErrorMessage(e.getMessage());
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      showErrorMessage("Errore dal database durante la cancellazione");
    } 
    showSuccessMessage("Lotto cancellato!");
  }

  @FXML public void add() {
    
  }

  ///
  /// 
  /// 
  /// Form
  /// 
  /// 
  /// 

  void fillForm(Lotto selectedLotto) {
    indirizzoField.setText(selectedLotto.getIndirizzo());
    codiceField.setText("" + selectedLotto.getCodice());
    estensioneField.setText("" + selectedLotto.getEstensione());
    ortoField.setText("" + selectedLotto.getOrto());
  }

  void clearForm() {
    indirizzoField.setText("");
    codiceField.setText("");
    estensioneField.setText("");
    ortoField.setText("");
  }

  Lotto getFormData() {
    return new Lotto(
      null, 
      indirizzoField.getText(),
      codiceField.getText(),
      (estensioneField.getText().isEmpty() ? null : Double.parseDouble(estensioneField.getText())),
      ortoField.getText()
    ); 
  }

  ///
  /// 
  /// 
  /// Transitions
  /// 
  /// 
  /// 

  @FXML private void back(){
    homeController.openHomeContent();
    clearUIContent();
  }

  ///
  /// 
  /// 
  /// Utility
  /// 
  /// 
  /// 

  void loadLotti() 
  throws ConnectionFailedException, NoDataFoundException {
    lotti.setAll(
      context.getProprietarioService().requestLottiFor(
        context.getSession().getUsername()
      )
    );
  }

  void clearUIContent() {
    clearMessage();
    clearForm();
    clearMessage();
  }

  public static void addCodiceLottoFilter(TextField textField) {
    UnaryOperator<Change> filter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty()) {
        return change;
      }
      if (newText.matches("^[0-9]{1,10}$")) { 
        return change;
      }
      return null;
    };
    textField.setTextFormatter(new TextFormatter<>(filter));
  }

  public static void addEstensioneLottoFilter(TextField textField) {
    UnaryOperator<Change> filter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty()) {
        return change;
      }
      if (newText.matches("^([1-9][0-9]{0,10})(\\.[0-9]{0,3})?$")) {
        return change;
      }
      return null;
    };
    textField.setTextFormatter(new TextFormatter<>(filter));
  }

  void showErrorMessage(String message) {
    errorLabel.setStyle("-fx-text-fill: rgba(136, 0, 0, 1)");
    errorLabel.setText(message);
  }

  void showSuccessMessage(String message) {
    errorLabel.setStyle("-fx-text-fill: rgba(0, 143, 59, 1)");
    errorLabel.setText(message);
  }

  void clearMessage() {
    errorLabel.setText("");
  }

  void clearSelection() {
    tableView.getSelectionModel().clearSelection();
  }

  
}
