package uninabiogarden;

import javafx.application.Application;
import javafx.stage.Stage;
import uninabiogarden.service.UserService;
import uninabiogarden.ui.ControllerManager;

/**
 * JavaFX App
 */
public class App extends Application {

  ControllerManager viewManager;
  UserService controllerDAO;

  void simulate() {
    try {
      viewManager.loginProprietario("proprietario1", "pass123");
      viewManager.openHomeView();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  @Override
  public void init() {
    controllerDAO = new UserService();
    viewManager = new ControllerManager(controllerDAO);
    simulate();
  }

  @Override
  public void start(Stage stage) {
    viewManager.show(stage);
  }

  public static void main(String[] args) {
    launch();
  }

}