package uninabiogarden.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerManager {

  Stage stage;
  ContentController mainController;
  Controller loginController;
  Controller registrationController;
  Controller proprietarioHomeController;

  ContentController lottiController;
  Controller lottiListController;
  Controller addingLottoController;

  ContentController progettiController;
  Controller progettiListController;
  Controller dettagliProgettoController;

  public ControllerManager() {
    loadControllers();
    openLoginView();
  }

  public void show(Stage stage) {
    this.stage = stage;
    Scene scene = new Scene(mainController.getRoot());
    stage.setScene(scene);
    stage.show();
  }

  void loadControllers() {
    mainController = (ContentController) loadController("Home");
    loginController = loadController("Login");
    registrationController = loadController("Registration");
    proprietarioHomeController = loadController("ProprietarioHome");

    lottiListController = loadController("LottiList");
    addingLottoController = loadController("AddingLotto");
    lottiController = (ContentController) loadController("Lotti");
    lottiController.setActiveContent(lottiListController.getRoot());

    progettiController = (ContentController) loadController("Progetti");
    progettiListController = loadController("ProgettiList");
    progettiController.setActiveContent(progettiListController.getRoot());
    dettagliProgettoController = loadController("DettagliProgetto");
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

  void openLottiView() {
    mainController.setActiveContent(lottiController.getRoot());
  }

  void openProgettiView() {
    mainController.setActiveContent(progettiController.getRoot());
  }

}
