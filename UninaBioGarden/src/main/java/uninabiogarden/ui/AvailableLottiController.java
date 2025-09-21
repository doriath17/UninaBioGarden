package uninabiogarden.ui;

import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Lotto;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.MissingFieldException;
import uninabiogarden.exceptions.NoDataFoundException;

public class AvailableLottiController extends ControllerBase {

  @FXML VBox root;
  @FXML TableView<Lotto> availableLottiTableView;
  @FXML TableColumn<String, String> indirizzoCol;
  @FXML TableColumn<Integer, String> codiceCol;

  ObservableList<Lotto> availableLottiObsList = 
    FXCollections.observableArrayList();
  int selectedIndex;

  @Override
  VBox getRoot() {
    return root;
  }

  @FXML void initialize() {
    availableLottiTableView.setItems(availableLottiObsList);
    indirizzoCol.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
    codiceCol.setCellValueFactory(new PropertyValueFactory<>("codice"));

    availableLottiTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lotto>() {
      @Override
      public void changed(ObservableValue<? extends Lotto> observable, Lotto oldValue, Lotto newValue) {
        if (newValue != null) {
          selectedIndex = availableLottiTableView.getSelectionModel().getSelectedIndex();
          mainController.fillLottoForm(newValue);
        }
      }
    });
  }

  void init() {
    clearSelection();
    loadAvailableLotti();
  }

  void loadAvailableLotti() {
    try {
      availableLottiObsList.setAll(
        mainController.requestAvailableLotti()
      );
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      mainController.showErrorMessageProgetto("Errore dal database durante il caricamento dei lotti disponibili");
    } catch (NoDataFoundException | ConnectionFailedException e) {
      mainController.showErrorMessageProgetto(e.getMessage());
    }
  }

  void clearSelection() {
    availableLottiTableView.getSelectionModel().clearSelection();
  }

  Lotto getSelectedLotto() throws MissingFieldException {
    try {
      return availableLottiObsList.get(availableLottiTableView.getSelectionModel().getSelectedIndex());
    } catch (IndexOutOfBoundsException e) {
      throw new MissingFieldException("Lotto non selezionato");
    }
  }
  
}
