package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ColtivatoreHomeController extends ColtivatoreController {

  @FXML VBox root;

  @Override
  VBox getRoot() {
    return root;
  }

  @FXML private void logout() {
    // controllerManager.logout();
  }
  
}
