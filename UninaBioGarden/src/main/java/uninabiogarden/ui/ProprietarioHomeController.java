package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ProprietarioHomeController extends ProprietarioController {
  @FXML VBox root;

  @Override
  VBox getRoot() {
    return root;
  }

  @FXML private void logout() {
    // controllerManager.logout();
  }

  @FXML private void openLottiView() {
    ((HomeController)parent).openLottiView();
  }

  @FXML private void openProgettiView() {
    ((HomeController)parent).openProgettiView();
  }

}
