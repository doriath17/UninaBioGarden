package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uninabiogarden.service.ColtivatoreService;
import uninabiogarden.service.ProprietarioService;
import uninabiogarden.service.UtenteService;

public class MainController extends ContentController {

  @FXML VBox root;
  @FXML VBox contentRoot;

  Parent activeView;

  LoginController         loginController;
  HomeController          homeController;
  RegistrationController  registrationController;

  @Override
  VBox getRoot(){
    return root;
  }

  @Override 
  VBox getContentRoot() {
    return contentRoot;
  }

  @SuppressWarnings("exports")
  public void show(Stage stage) {
    Scene scene = new Scene(root);
    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

    openLoginView();

    stage.setScene(scene);
    stage.show();
  }

  void openLoginView() {
    if (loginController == null){
      loginController = (LoginController) loadContent("LoginView.fxml");
    }
    setActiveContent(loginController);
  }

  void openHomeView(UtenteService service) {
    if (homeController == null) {
      homeController = (HomeController) loadContent("HomeView.fxml");
    }
    if (service instanceof ProprietarioService) {
      homeController.propService = (ProprietarioService) service;
    } else {
      homeController.coltService = (ColtivatoreService) service;
    }
    homeController.openHomeContent();
    setActiveContent(homeController);
  }

  void openRegistrationView() {
    if (registrationController == null) {
      registrationController = (RegistrationController) ContentController.loadController("RegistrationView.fxml");
      registrationController.setParent(this);
    }
    setActiveContent(registrationController);
  }

}
