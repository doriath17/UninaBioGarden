package uninabiogarden.ui;


import java.util.function.UnaryOperator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Lotto;
import uninabiogarden.entities.Progetto;
import uninabiogarden.entities.Proprietario;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.NoDataFoundException;

public class LottiController extends ControllerBase {
  @FXML VBox root;
  @FXML VBox contentRoot;

  @FXML TextField indirizzoField;
  @FXML TextField codiceField;
  @FXML TextField estensioneField;
  @FXML TextField ortoField;

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
    ControllerUtility.addPositiveIntegerFilter(codiceField);
    ControllerUtility.addDoubleFilter(estensioneField);
    ControllerUtility.addTextLimiter(ortoField, 80);
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

  ///
  /// 
  /// 
  /// Transitions
  /// 
  /// 
  /// 

  @FXML private void back(){
    homeController.openHomeContent();
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

  void clear() {
    clearForm();
    lotti.clear();
  }
  
}
