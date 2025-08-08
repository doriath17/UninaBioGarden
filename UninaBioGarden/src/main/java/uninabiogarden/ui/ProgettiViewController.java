package uninabiogarden.ui;

import java.time.LocalDate;

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
    nomeField.setText(null);
    dataInizioField.setValue(null);
    dataFineField.setValue(null);
    descrizioneField.setText(null);
    indirizzoLottoField.setText(null);
    codiceLottoField.setText(null);
  }

  @FXML private void back() {
    controllerManager.homeController.openHomeContent();
  }

  void loadProgetti(){
    var list = controllerManager.controllerDAO.loadProgetti();
    progettiObsList.setAll(list);
  }
  
}
