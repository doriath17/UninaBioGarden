package uninabiogarden.ui;

import javafx.scene.layout.VBox;

public abstract class ContentController extends Controller {

  Controller activeContent;

  abstract VBox getContentRoot();

  void setActiveContent(Controller controller) {
    activeContent = controller;
    getContentRoot().getChildren().clear();
    getContentRoot().getChildren().add(controller.getRoot());
  }


}
