package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class HomeController extends ContentController {
  @FXML VBox root;
  @FXML VBox contentRoot;

  @Override
  public Parent getRoot() {
    return root;
  }

  @Override
  VBox getContentRoot() {
    return contentRoot;
  }

}
