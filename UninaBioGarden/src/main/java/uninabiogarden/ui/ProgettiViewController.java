package uninabiogarden.ui;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Progetto;

public class ProgettiViewController extends Controller {

  @FXML VBox root;
  
  @FXML TableView<Progetto> tableView;
  @FXML TableColumn<String, String> nomeCol;
  @FXML TableColumn<LocalDate, String> dataInizioCol;
  @FXML TableColumn<LocalDate, String> dataFineCol;

  ObservableList<Progetto> progettiObsList = FXCollections.observableArrayList();

  @Override
  Parent getRoot() {
    return root;
  }

  @FXML private void initialize() {
    tableView.setItems(progettiObsList);
    nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
    dataInizioCol.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
    dataFineCol.setCellValueFactory(new PropertyValueFactory<>("dataFine"));
  }

  @FXML private void back() {
    controllerManager.homeController.openHomeContent();
  }

  void loadProgetti(){
    var list = controllerManager.controllerDAO.loadProgetti();
    progettiObsList.setAll(list);
  }
  
}
