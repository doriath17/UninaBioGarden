package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class LottiController extends Controller {
  @FXML VBox root;
  @FXML VBox contentRoot;
  Parent activeView;
  
  @Override
  public Parent getRoot() {
    return root;
  }

  public void setActiveContent(Parent newContent) {
    contentRoot.getChildren().remove(activeView);
    contentRoot.getChildren().add(newContent);
    this.activeView = newContent;
  }

  
}
