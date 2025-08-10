package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ProprietarioHomeController extends ControllerBase {
  @FXML VBox root;
  HomeController homeController;

  @Override
  VBox getRoot() {
    return root;
  }

  @FXML private void logout() {
  }

  @FXML private void openLottiView() {
    homeController.openLottiView();
  }

  @FXML private void openProgettiView() {
    homeController.openProgettiView();
  }

}
