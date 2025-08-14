package uninabiogarden;

import javafx.application.Application;
import javafx.stage.Stage;
import uninabiogarden.ui.ControllerBase;
import uninabiogarden.ui.MainController;

/**
 * JavaFX App
 */
public class App extends Application {

  private MainController mainController;

  @Override
  public void init() {
    try {
      mainController = (MainController) ControllerBase.loadController("MainView.fxml");
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void start(Stage stage) {
    mainController.show(stage);
    mainController.simulate();
  }

  public static void main(String[] args) {
    launch();
  }

}