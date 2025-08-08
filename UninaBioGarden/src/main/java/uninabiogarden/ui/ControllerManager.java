package uninabiogarden.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uninabiogarden.controllers.ControllerDAO;
import uninabiogarden.controllers.WrongPasswordException;
import uninabiogarden.controllers.WrongUsernameException;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;

public class ControllerManager {

  ControllerDAO controllerDAO;
  Stage stage;

  MainController mainController;
  LoginController loginController;
  Controller registrationController;
  HomeController homeController;
  Controller propHomeController;
  Controller coltHomeController;
  Controller lottiController;

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
    // registrationController = loadController("Registration");
    homeController = (HomeController)  loadController("Home");
    propHomeController = loadController("ProprietarioHome");
    coltHomeController = loadController("ColtivatoreHome");
    lottiController = loadController("Lotti");
  }

  Controller loadController(String viewName) {
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

  public void loginProprietario(String username, String password) throws WrongPasswordException, WrongUsernameException {
    controllerDAO.loginProprietario(username, password);
    openHomeView();
  }

  void loginColtivatore(String username, String password) throws WrongPasswordException, WrongUsernameException {
    controllerDAO.loginColtivatore(username, password);
    openHomeView();
  }

  void openRegistrationView() {
    mainController.setActiveContent(registrationController.getRoot());
  }

  public void openHomeView() {
    homeController.openHomeContent();
    mainController.setActiveContent(homeController.getRoot());
  }

  void openProprietarioHomeView() {
    mainController.setActiveContent(propHomeController.getRoot());
  }

  void openColtivatoreHomeView() {
    mainController.setActiveContent(coltHomeController.getRoot());
  }

  public void openLottiView() {
    mainController.setActiveContent(lottiController.getRoot());
  }

}
