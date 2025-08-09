package uninabiogarden.ui;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Progetto;

public class ProgettiViewController extends Controller {

  @FXML VBox root;
  
  @FXML TableView<Progetto> tableView;
  @FXML TableColumn<String, String> nomeCol;
  @FXML TableColumn<LocalDate, String> dataInizioCol;

  // FORM
  @FXML TextField nomeField;
  @FXML DatePicker dataInizioField;
  @FXML DatePicker dataFineField;
  @FXML TextField indirizzoLottoField;
  @FXML TextField codiceLottoField;
  @FXML TextArea descrizioneField;

  Progetto selectedProgetto;

  @FXML TableColumn<LocalDate, String> dataFineCol;

  ObservableList<Progetto> progettiObsList = FXCollections.observableArrayList();

  @Override
  Parent getRoot() {
    return root;
  }

  @Override
  void close() {
    clearForm();
  }

  @FXML private void initialize() {
    tableView.setItems(progettiObsList);
    nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
    dataInizioCol.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
    dataFineCol.setCellValueFactory(new PropertyValueFactory<>("dataFine"));

    tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Progetto>() {
      @Override
      public void changed(ObservableValue<? extends Progetto> observable, Progetto oldValue, Progetto newValue) {
        if (newValue != null) {
          fillForm(newValue);
        }
      }
    });

    addTextLimiter(nomeField, 50);
    addTextLimiter(descrizioneField, 200);
    addTextLimiter(indirizzoLottoField, 80);
    UnaryOperator<Change> positiveIntegerFilter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty()) {
        return change;
      }
      if (newText.matches("0|[1-9][0-9]*")) { 
        return change;
      }
      return null;
    };
    codiceLottoField.setTextFormatter(new TextFormatter<>(positiveIntegerFilter));

  }

  public static void addTextLimiter(TextInputControl textInputControl, int maxLength) {
    UnaryOperator<Change> maxLengthFilter = change -> {
      String newText = change.getControlNewText();
      if (newText.isEmpty()) {
        return change;
      }
      if (newText.length() <= maxLength) {
        return change;
      }
      return null;
    };
    textInputControl.setTextFormatter(new TextFormatter<>(maxLengthFilter));
  }

  void fillForm(Progetto value) {
    selectedProgetto = value;
    nomeField.setText(value.getNome());
    dataInizioField.setValue(value.getDataInizio());
    dataFineField.setValue(value.getDataFine());
    descrizioneField.setText(value.getDescrizione());
    indirizzoLottoField.setText(value.getLotto().getIndirizzo());
    codiceLottoField.setText(""+value.getLotto().getCodice());
  }

  void clearForm() {
    nomeField.setText("");
    dataInizioField.setValue(null);
    dataFineField.setValue(null);
    descrizioneField.setText("");
    indirizzoLottoField.setText("");
    codiceLottoField.setText("");
  }

  @FXML private void back() {
    controllerManager.homeController.openHomeContent();
  }

  void loadProgetti(){
    var list = controllerManager.userService.loadProgetti();
    progettiObsList.setAll(list);
  }

  @FXML private void update() {
    
  }

  @FXML private void add() {
    
  }
  
}
