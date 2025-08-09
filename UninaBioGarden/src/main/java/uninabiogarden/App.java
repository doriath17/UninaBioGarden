package uninabiogarden;

import javafx.application.Application;
import javafx.stage.Stage;
import uninabiogarden.service.LoginService;
import uninabiogarden.ui.Controller;
import uninabiogarden.ui.MainController;

/**
 * JavaFX App
 */
public class App extends Application {

  MainController mainController;

  void simulate() {
    try {
      // mainController.loginProprietario("proprietario1", "pass123");
      // viewManager.openHomeView();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  @Override
  public void init() {
    mainController = (MainController) Controller.loadController("MainView.fxml");
    // simulate();
  }

  @Override
  public void start(Stage stage) {
    mainController.show(stage);
  }

  public static void main(String[] args) {
    launch();
  }

}