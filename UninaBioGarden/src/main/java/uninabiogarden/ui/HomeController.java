package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomeController extends Controller {

  @FXML VBox root;
  @FXML HBox contentRoot;

  Parent activeView;
  LottiController lottiController;

  @Override
  public Parent getRoot(){
    return root;
  }

  public void setActiveContent(Parent newContent) {
    contentRoot.getChildren().remove(activeView);
    contentRoot.getChildren().add(newContent);
    this.activeView = newContent;
  }

  @FXML private void logout(){
    controllerManager.logout();
  }

  void openLottiView() {
    if (lottiController == null) {
      lottiController = (LottiController) controllerManager.loadController("Lotti");
    }
    setActiveContent(lottiController.getRoot());
  }

}
