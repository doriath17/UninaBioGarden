package uninabiogarden;

import javafx.application.Application;
import javafx.stage.Stage;
import uninabiogarden.controllers.ControllerDAO;
import uninabiogarden.ui.ControllerManager;

/**
 * JavaFX App
 */
public class App extends Application {

  ControllerManager viewManager;
  ControllerDAO controllerDAO;

  @Override
  public void init() {
    controllerDAO = new ControllerDAO();
    viewManager = new ControllerManager(controllerDAO);
  }

  @Override
  public void start(Stage stage) {
    viewManager.show(stage);
  }

  public static void main(String[] args) {
    launch();
  }

}