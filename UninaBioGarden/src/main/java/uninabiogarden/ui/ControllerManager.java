package uninabiogarden.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uninabiogarden.controllers.ControllerDAO;
import uninabiogarden.controllers.WrongPasswordException;
import uninabiogarden.controllers.WrongUsernameException;

public class ControllerManager {

  ControllerDAO controllerDAO;
  Stage stage;

  MainController mainController;
  LoginController loginController;
  Controller registrationController;
  Controller propHomeController;
  Controller coltHomeController;

  public ControllerManager(ControllerDAO controllerDAO) {
    this.controllerDAO = controllerDAO;
    loadControllers();
    mainController.setActiveContent(loginController.getRoot());
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
    loginController = (LoginController) loadController("Login");
    registrationController = loadController("Registration");
    propHomeController = loadController("ProprietarioHome");
    coltHomeController = loadController("ColtivatoreHome");
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

  void logout() {
    controllerDAO.logout();
    loginController.clear();
    mainController.setActiveContent(loginController.getRoot());
  }

  void loginProprietario(String username, String password) throws WrongPasswordException, WrongUsernameException {
    controllerDAO.loginProprietario(username, password);
    openProprietarioHomeView();
  }

  void loginColtivatore(String username, String password) throws WrongPasswordException, WrongUsernameException {
    controllerDAO.loginColtivatore(username, password);
    openColtivatoreHomeView();
  }

  void openRegistrationView() {
    mainController.setActiveContent(registrationController.getRoot());
  }

  void openProprietarioHomeView() {
    mainController.setActiveContent(propHomeController.getRoot());
  }

  void openColtivatoreHomeView() {
    mainController.setActiveContent(coltHomeController.getRoot());
  }


}
