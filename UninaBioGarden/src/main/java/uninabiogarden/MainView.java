package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class MainView {
  @FXML VBox mainView;
  @FXML Parent activeView;

  void setActiveView(Parent view) {
    mainView.getChildren().add(view);
    this.activeView = view;
  }

}
