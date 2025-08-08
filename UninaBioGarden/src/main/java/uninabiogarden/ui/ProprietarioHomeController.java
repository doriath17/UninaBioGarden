package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public class ProprietarioHomeController extends Controller {
  @FXML HBox root;

  @Override
  Parent getRoot() {
    return root;
  }

  @FXML private void logout() {
    controllerManager.logout();
  }

  @FXML private void openLottiView() {
    controllerManager.homeController.openLottiView();
  }

  @FXML private void openProgettiView() {
    controllerManager.homeController.openProgettiView();
  }

}
