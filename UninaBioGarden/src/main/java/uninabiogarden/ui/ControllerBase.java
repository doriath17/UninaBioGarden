package uninabiogarden.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import uninabiogarden.core.ApplicationContext;

public abstract class ControllerBase {
  protected ApplicationContext context;
  protected ControllerBase parent;

  abstract VBox getRoot();

  public void setParent(ControllerBase parent) {
    this.parent = parent;
  }

  public static ControllerBase loadController(String fxmlFileName, ApplicationContext context) {
    try {
      FXMLLoader loader = new FXMLLoader(ControllerBase.class.getResource(fxmlFileName));
      loader.load();
      var c = (ControllerBase) loader.getController();
      c.context = context;
      return c;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw new RuntimeException("Failed to load " + fxmlFileName);
    }
  }

  ControllerBase loadContent(String fxmlFileName){
    var c = ControllerBase.loadController(fxmlFileName, context);
    c.parent = this;
    return c;
  }

}
