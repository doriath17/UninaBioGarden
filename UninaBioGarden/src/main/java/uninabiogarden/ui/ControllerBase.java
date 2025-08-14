package uninabiogarden.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public abstract class ControllerBase {
  abstract VBox getRoot();

  public static ControllerBase loadController(String fxmlFileName) {
    try {
      FXMLLoader loader = new FXMLLoader(ControllerBase.class.getResource(fxmlFileName));
      loader.load();
      var c = (ControllerBase) loader.getController();
      return c;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw new RuntimeException("Failed to load " + fxmlFileName);
    }
  }

  ControllerBase loadContent(String fxmlFileName){
    var c = ControllerBase.loadController(fxmlFileName);
    return c;
  }

}
