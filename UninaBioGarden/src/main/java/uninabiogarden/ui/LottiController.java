package uninabiogarden.ui;

import java.sql.SQLException;
import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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

  @FXML Label formLabel;
  @FXML Button newButton;

  @FXML TextField indirizzoField;
  @FXML TextField codiceField;
  @FXML TextField estensioneField;
  @FXML TextField ortoField;

  @FXML Button deleteButton;
  @FXML Button updateButton;
  @FXML Button addButton;

  @FXML Label errorLabel;

  HomeController homeController;

  Parent activeView;
  
  @FXML TableView<Lotto> tableView;
  @FXML TableColumn<String, String> indirizzoCol;
  @FXML TableColumn<Integer, String> codiceCol;
  @FXML TableColumn<Double, String> estensioneCol;
  @FXML TableColumn<String, String> ortoCol;

  ObservableList<Lotto> lotti = FXCollections.observableArrayList();

  boolean formForSelection = true;
  
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
          if (formForSelection){
            clearMessage();
            fillForm(newValue);
          }
        }
      }
    });

    addButton.setDisable(true);

    ControllerUtility.addTextLimiter(indirizzoField, 80);
    ControllerUtility.addCodiceLottoFilter(codiceField);
    ControllerUtility.addEstensioneLottoFilter(estensioneField);
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
    try {
      var lottoToDelete = lotti.get(index);
      context.getLottoService().delete(lottoToDelete);
      lotti.remove(index);
      showSuccessMessage("Lotto cancellato!");
    } catch (ConnectionFailedException e) {
      showErrorMessage(e.getMessage());
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      showErrorMessage("Errore dal database durante la cancellazione");
    }
  }

  @FXML public void add() {
    try {
      var lotto = getFormData();
      lotto.setProprietario(context.getAppState().getLoggedInProprietario());
      context.getLottoService().insert(lotto);
      lotti.add(lotto);
      showSuccessMessage("Nuovo lotto inserito!");
    } catch (MissingFieldException | FormatException | ConnectionFailedException e) {
      showErrorMessage(e.getMessage());
    } catch (SQLException e) {
      if (e.getSQLState().equals("23505")) {
        showErrorMessage("Un lotto con questo indirizzo e codice esiste già");
      } else {
        System.err.println(e.getSQLState());
        System.err.println(e.getMessage());
        e.printStackTrace();
        showErrorMessage("Errore dal database durante l'inserimento");
      }
    }
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

  void toggleForm() {
    clearUIContent();
    if (formForSelection){
      formForSelection = false;
      formLabel.setText("Creazione Nuovo Lotto");
      newButton.setText("Seleziona");
      addButton.setDisable(false);
      deleteButton.setDisable(true);
      updateButton.setDisable(true);
    } else {
      formForSelection = true;
      formLabel.setText("Lotto Selezionato");
      newButton.setText("New");
      addButton.setDisable(true);
      deleteButton.setDisable(false);
      updateButton.setDisable(false);
    }
  }

  @FXML void toggleAddLottoView() {
    errorLabel.setText("");
    if ("New".equals(newButton.getText())){
      toggleForm();
    } else {
      toggleForm();
    }
    clearSelection();
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
