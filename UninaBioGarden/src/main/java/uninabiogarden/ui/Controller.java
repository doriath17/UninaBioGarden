package uninabiogarden.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public abstract class Controller {
  Controller parent;

  abstract VBox getRoot();

  public void setParent(Controller parent) {
    this.parent = parent;
  }

  public static Controller loadController(String fxmlFileName) {
    try {
      FXMLLoader loader = new FXMLLoader(Controller.class.getResource(fxmlFileName));
      loader.load();
      return loader.getController();
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw new RuntimeException("Failed to load " + fxmlFileName);
    }
  }

  Controller loadContent(String fxmlFileName){
    var c = loadController(fxmlFileName);
    c.parent = this;
    return c;
  }

}