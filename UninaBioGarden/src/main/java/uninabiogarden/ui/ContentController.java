package uninabiogarden.ui;

import javafx.scene.layout.VBox;

public abstract class ContentController extends ControllerBase {

  ControllerBase activeContent;

  abstract VBox getContentRoot();

  void setActiveContent(ControllerBase controller) {
    activeContent = controller;
    getContentRoot().getChildren().clear();
    getContentRoot().getChildren().add(controller.getRoot());
  }
}
