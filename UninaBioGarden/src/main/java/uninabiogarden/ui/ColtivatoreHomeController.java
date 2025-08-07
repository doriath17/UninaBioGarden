package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ColtivatoreHomeController extends Controller {

  @FXML VBox root;

  @Override
  Parent getRoot() {
    return root;
  }

  @FXML private void logout() {
    controllerManager.logout();
  }
  
}
