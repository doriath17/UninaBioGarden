package uninabiogarden.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uninabiogarden.controllers.ControllerDAO;

public class ControllerManager {

  ControllerDAO controllerDAO;
  Stage stage;

  MainController mainController;
  Controller loginController;
  Controller registrationController;
  Controller proprietarioHomeController;

  public ControllerManager(ControllerDAO controllerDAO) {
    this.controllerDAO = controllerDAO;
    loadControllers();
    openLoginView();
  }

  public void show(Stage stage) {
    this.stage = stage;
    Scene scene = new Scene(mainController.getRoot());
    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
  }

  void loadControllers() {
    mainController = (MainController) loadController("Main");
    loginController = loadController("Login");
    registrationController = loadController("Registration");
    proprietarioHomeController = loadController("ProprietarioHome");
  }

  private Controller loadController(String viewName) {
    String fxmlFileName = viewName + "View.fxml";
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
      loader.load();
      Controller c = loader.getController();
      c.setControllerManager(this);
      return c;
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw new RuntimeException("Failed to load " + fxmlFileName);
    }
  }

  void openLoginView() {
    mainController.setActiveContent(loginController.getRoot());
  }

  void openRegistrationView() {
    mainController.setActiveContent(registrationController.getRoot());
  }

  void openProprietarioHomeView() {
    mainController.setActiveContent(proprietarioHomeController.getRoot());
  }

}
