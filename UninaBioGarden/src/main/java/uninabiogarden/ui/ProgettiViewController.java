package uninabiogarden.ui;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.MissingFieldException;
import uninabiogarden.exceptions.NoDataFoundException;
import uninabiogarden.exceptions.WrongDataFineException;
import uninabiogarden.service.LoginService;
import uninabiogarden.service.ProgettoService;
import uninabiogarden.service.ProprietarioService;

public class ProgettiViewController extends ControllerBase {

  @FXML VBox root;
  
  @FXML VBox tableViewRoot;
  @FXML VBox progettiTableView;
  
  @FXML TableView<Progetto> tableView;
  @FXML TableColumn<String, String> nomeCol;
  @FXML TableColumn<LocalDate, String> dataInizioCol;
  @FXML TableColumn<LocalDate, String> dataFineCol;
  
  // FORM
  @FXML TextField nomeField;
  @FXML DatePicker dataInizioField;
  @FXML DatePicker dataFineField;
  @FXML TextField indirizzoLottoField;
  @FXML TextField codiceLottoField;
  @FXML TextArea descrizioneField;
  
  // dependencies
  HomeController homeController;
  AvailableLottiController availableLottiController;
  LoginService loginService = LoginService.getInstance();
  ProprietarioService proprietarioService = ProprietarioService.getInstance();
  ProgettoService progettoService = ProgettoService.getInstance();

  @FXML Label errorLabel;

  ObservableList<Progetto> progettiObsList = FXCollections.observableArrayList();

  @Override
  VBox getRoot() {
    return root;
  }

  void close() {
    clearForm();
  }

  int selectedIndex;

  ///
  /// 
  /// 
  /// Initialization
  /// 
  /// 
  /// 

  @FXML void initialize() {
    tableView.setItems(progettiObsList);
    nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
    dataInizioCol.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
    dataFineCol.setCellValueFactory(new PropertyValueFactory<>("dataFine"));

    tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Progetto>() {
      @Override
      public void changed(ObservableValue<? extends Progetto> observable, Progetto oldValue, Progetto newValue) {
        if (newValue != null) {
          clearMessage();
          clearForm();
          selectedIndex = tableView.getSelectionModel().getSelectedIndex();
          fillForm(newValue);
        }
      }
    });

    ControllerUtility.addTextLimiter(nomeField, 50);
    ControllerUtility.addTextLimiter(descrizioneField, 200);
    ControllerUtility.addTextLimiter(indirizzoLottoField, 80);
    ControllerUtility.addCodiceLottoFilter(codiceLottoField);

    addButton.setDisable(true);
  }

  ///
  /// 
  /// 
  /// CRUD operations
  /// 
  /// 
  /// 

  @FXML void update() {
    errorLabel.setText("");

    Progetto p = tableView.getSelectionModel().selectedItemProperty().get();
    if (p != null) {

      Progetto progettoToUpdate = new Progetto(p);
      progettoToUpdate.setNome(nomeField.getText());
      progettoToUpdate.setDataInizio(dataInizioField.getValue());
      progettoToUpdate.setDataFine(dataFineField.getValue());
      progettoToUpdate.setDescrizione(descrizioneField.getText());

      try {
        progettoService.update(progettoToUpdate);
        progettiObsList.set(selectedIndex, progettoToUpdate);
        showSuccessMessage("Progetto aggiornato!");
      } catch (ConnectionFailedException | WrongDataFineException | MissingFieldException e) {
        showErrorMessage(e.getMessage());
      } catch (SQLException e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
        showErrorMessage("Errore dal database durante l'aggiornamento");
      }
    } else {
      showErrorMessage("Nessun progetto selezionato");
    }
  }

  @FXML void delete() {
    errorLabel.setText("");

    Progetto p = tableView.getSelectionModel().selectedItemProperty().get();
    if (p != null) {
      try {
        progettoService.delete(p.getId());
        progettiObsList.remove(selectedIndex);
        if (!p.isTerminated()) {
          getAvailableLottiController().availableLotti.add(p.getLotto());
        }
        showSuccessMessage("Progetto cancellato!");
      } catch (ConnectionFailedException e) {
        showErrorMessage(e.getMessage());
      } catch (SQLException e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
        showErrorMessage("Errore dal database durante la cancellazione");
      }
    } else {
      showErrorMessage("Nessun progetto selezionato");
    }
  }

  @FXML void add() {
    errorLabel.setText("");

    try {
      var lotto = availableLottiController.getSelectedLotto();
      var newProgetto = getFormData();
      newProgetto.setLotto(lotto);

      var newID = progettoService.create(newProgetto);
      newProgetto.setId(newID);

      progettiObsList.add(newProgetto);
      getAvailableLottiController().availableLotti.remove(lotto);
      showSuccessMessage("Nuovo progetto inserito!");
    } catch (MissingFieldException | WrongDataFineException | ConnectionFailedException e) {
      showErrorMessage(e.getMessage());
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      showErrorMessage("Errore dal database durante l'inserimento");
    } 
  }

  ///
  /// 
  /// 
  /// Form utility methods
  /// 
  /// 
  /// 
  
  Progetto getFormData() {
    return new Progetto(
      null, // the id do not exist yet
      nomeField.getText(),
      dataInizioField.getValue(),
      dataFineField.getValue(),
      descrizioneField.getText(),
      loginService.getLoggedInProprietario(),
      null
    );
  }

  void fillLottoForm(Lotto lotto) {
    indirizzoLottoField.setText(lotto.getIndirizzo());
    codiceLottoField.setText(lotto.getCodice());
  }

  void fillForm(Progetto value) {
    nomeField.setText(value.getNome());
    dataInizioField.setValue(value.getDataInizio());
    dataFineField.setValue(value.getDataFine());
    descrizioneField.setText(value.getDescrizione());
    fillLottoForm(value.getLotto());
  }

  void clearForm() {
    nomeField.setText("");
    dataInizioField.setValue(null);
    dataFineField.setValue(null);
    descrizioneField.setText("");
    indirizzoLottoField.setText("");
    codiceLottoField.setText("");
  }

  void clearSelection() {
    if ("New".equals(newButton.getText())) {
      tableView.getSelectionModel().clearSelection();
    } else {
      getAvailableLottiController().clearSelection();
    }
    clearForm();
  }

  ///
  /// 
  /// 
  /// Handling the transitions
  /// 
  /// 
  /// 

  @FXML void back() {
    if ("Seleziona".equals(newButton.getText())) {
      toggleAddProgettoView();
    }
    homeController.openHomeContent();
  }

  @FXML Button newButton;
  @FXML Button deleteButton;
  @FXML Button updateButton;
  @FXML Button addButton;

  void toggleButtons(boolean toggle) {
    if (toggle){
      newButton.setText("Seleziona");
      addButton.setDisable(false);
      deleteButton.setDisable(true);
      updateButton.setDisable(true);
    } else {
      newButton.setText("New");
      addButton.setDisable(true);
      deleteButton.setDisable(false);
      updateButton.setDisable(false);
    }
  }

  @FXML void toggleAddProgettoView() {
    errorLabel.setText("");
    if ("New".equals(newButton.getText())){
      showAvailableLottiTable();
      dataFineField.setEditable(false);
      toggleButtons(true);
    } else {
      showProgettiTable();
      dataFineField.setEditable(true);
      toggleButtons(false);
    }
    clearSelection();
  }

  void clearUIContent() {
    clearMessage();
    clearSelection();
    clearForm();
  }

  AvailableLottiController getAvailableLottiController() {
    if (availableLottiController == null){
      availableLottiController = (AvailableLottiController) loadContent("AvailableLotti.fxml");
      availableLottiController.progettiViewController = this;
    }
    return availableLottiController; 
  }

  void showProgettiTable() {
    tableViewRoot.getChildren().remove(
      getAvailableLottiController().getRoot()
    );
    tableViewRoot.getChildren().add(progettiTableView);
  }

  void showAvailableLottiTable() {
    getAvailableLottiController().loadAvailableLotti();
    tableViewRoot.getChildren().remove(progettiTableView);
    tableViewRoot.getChildren().add(
      getAvailableLottiController().getRoot()
    );
  }

  ///
  /// 
  /// 
  /// Utility methods
  /// 
  /// 
  /// 
  /// 
  
  void loadProgetti() throws ConnectionFailedException, NoDataFoundException {
    progettiObsList.setAll(proprietarioService.requestProgetti());
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

}
