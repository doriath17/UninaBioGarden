package uninabiogarden;

import javafx.application.Application;
import javafx.stage.Stage;
import uninabiogarden.core.ApplicationContext;
import uninabiogarden.ui.ControllerBase;
import uninabiogarden.ui.MainController;

/**
 * JavaFX App
 */
public class App extends Application {

  private ApplicationContext context;
  private MainController mainController;

  @Override
  public void init() {
    try {
      context = new ApplicationContext();
      mainController = (MainController) ControllerBase.loadController("MainView.fxml", context);
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