package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController extends ContentController {

  public void simulate() {
    openRegistrationView();
    // getRegistrationController().simulate();

    getLoginController().usernameField.setText("alefus");
    getLoginController().passwordField.setText("alefus17");
    getLoginController().propCheckBox.setSelected(true);
    getLoginController().login();
    // getHomeController().openLottiView();
  }

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

  ////////////////////////////////////////////////////////////////////////////////////////////////
  /// 
  /// 
  /// Getters per i controller 
  /// (lo scopo e quello di evitare di non inizializzare) 
  /// 
  /// 

  LoginController getLoginController() {
    if (loginController == null){
      loginController = (LoginController) loadContent("LoginView.fxml");
      loginController.mainController = this;
    }
    return loginController; 
  }

  HomeController getHomeController() {
    if (homeController == null) {
      homeController = (HomeController) loadContent("HomeView.fxml");
      homeController.mainController = this;
    }
    return homeController;
  }

  RegistrationController getRegistrationController() {
    if (registrationController == null) {
      registrationController = (RegistrationController)loadContent("RegistrationView.fxml");
      registrationController.mainController = this;
    }
    return registrationController;
  }

  void openLoginView() {
    setActiveContent(getLoginController());
  }

  void openHomeView() {
    getHomeController().openHomeContent();
    setActiveContent(homeController);
    getLoginController().clear();
  }

  void openRegistrationView() {
    getRegistrationController().clearUIContent();
    setActiveContent(getRegistrationController());
  }

}
