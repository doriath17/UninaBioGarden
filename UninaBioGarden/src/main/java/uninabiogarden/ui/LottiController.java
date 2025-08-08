package uninabiogarden.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Lotto;

public class LottiController extends Controller {
  @FXML VBox root;
  @FXML VBox contentRoot;

  Parent activeView;
  
  @FXML TableView<Lotto> tableView;
  @FXML TableColumn<String, String> indirizzoCol;
  @FXML TableColumn<Integer, String> codiceCol;
  @FXML TableColumn<Double, String> estensioneCol;
  @FXML TableColumn<String, String> ortoCol;

  ObservableList<Lotto> lottoObservableList = FXCollections.observableArrayList();
  
  @Override
  public Parent getRoot() {
    return root;
  }

  public void setActiveContent(Parent newContent) {
    contentRoot.getChildren().remove(activeView);
    contentRoot.getChildren().add(newContent);
    this.activeView = newContent;
  }

  @FXML private void initialize() {
    tableView.setItems(lottoObservableList);
    indirizzoCol.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
    codiceCol.setCellValueFactory(new PropertyValueFactory<>("codice"));
    estensioneCol.setCellValueFactory(new PropertyValueFactory<>("estensione"));
    ortoCol.setCellValueFactory(new PropertyValueFactory<>("orto"));
  }

  void loadLotti(){
    var list = controllerManager.controllerDAO.loadLotti();
    lottoObservableList.setAll(list);
  }

  @FXML private void back(){
    controllerManager.homeController.openHomeContent();    
  }
  
}
