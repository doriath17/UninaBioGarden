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
import uninabiogarden.service.ProprietarioService;

public class AvailableLottiController extends ControllerBase {

  @FXML VBox root;
  @FXML TableView<Lotto> tableView;
  @FXML TableColumn<String, String> indirizzoCol;
  @FXML TableColumn<Integer, String> codiceCol;

  // dependencies
  ProgettiViewController progettiViewController;
  ProprietarioService proprietarioService = ProprietarioService.getInstance();

  ObservableList<Lotto> availableLotti = 
    FXCollections.observableArrayList();
  int selectedIndex;

  @Override
  VBox getRoot() {
    return root;
  }

  @FXML void initialize() {
    tableView.setItems(availableLotti);
    indirizzoCol.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
    codiceCol.setCellValueFactory(new PropertyValueFactory<>("codice"));

    tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lotto>() {
      @Override
      public void changed(ObservableValue<? extends Lotto> observable, Lotto oldValue, Lotto newValue) {
        if (newValue != null) {
          selectedIndex = tableView.getSelectionModel().getSelectedIndex();
          progettiViewController.fillLottoForm(newValue);
        }
      }
    });
  }

  void loadAvailableLotti() {
    try {
      availableLotti.setAll(
        proprietarioService.requestAvailableLotti()
      );
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      progettiViewController.showErrorMessage("Errore dal database durante il caricamento dei lotti disponibili");
    } catch (NoDataFoundException | ConnectionFailedException e) {
      progettiViewController.showErrorMessage(e.getMessage());
    }
  }

  void clearSelection() {
    tableView.getSelectionModel().clearSelection();
  }

  Lotto getSelectedLotto() throws MissingFieldException {
    try {
      return availableLotti.get(tableView.getSelectionModel().getSelectedIndex());
    } catch (IndexOutOfBoundsException e) {
      throw new MissingFieldException("Lotto non selezionato");
    }
  }
  
}
