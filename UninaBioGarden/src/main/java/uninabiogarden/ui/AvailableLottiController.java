package uninabiogarden.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Lotto;

public class AvailableLottiController extends ControllerBase {

  @FXML VBox root;
  @FXML TableView<Lotto> tableView;
  @FXML TableColumn<String, String> indirizzoCol;
  @FXML TableColumn<Integer, String> codiceCol;

  ProgettiViewController progettiViewController;

  ObservableList<Lotto> availableLotti = FXCollections.observableArrayList();

  @Override
  VBox getRoot() {
    return root;
  }

  @FXML void initialize() {
    tableView.setItems(availableLotti);
    indirizzoCol.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
    codiceCol.setCellValueFactory(new PropertyValueFactory<>("codice"));
  }
  
}
