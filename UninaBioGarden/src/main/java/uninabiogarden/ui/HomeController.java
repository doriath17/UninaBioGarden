package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uninabiogarden.service.LoginService.UserType;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.NoDataFoundException;
import uninabiogarden.service.ColtivatoreService;
import uninabiogarden.service.LoginService;
import uninabiogarden.service.ProprietarioService;

public class HomeController extends ContentController {

  ProprietarioService propService;
  ColtivatoreService coltService;

  @FXML VBox root;
  @FXML VBox contentRoot;

  @FXML Label usernameLabel;

  ProprietarioHomeController  propHomeController;
  ColtivatoreHomeController   coltHomeController;
  LottiController             lottiController;
  ProgettiViewController      progettiViewController;

  // dependencies
  MainController mainController;
  LoginService loginService = LoginService.getInstance();

  @SuppressWarnings("exports")
  @Override
  public VBox getRoot(){
    return root;
  }

  @SuppressWarnings("exports")
  @Override
  public VBox getContentRoot(){
    return contentRoot;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  /// 
  /// 
  /// Getters per i controller 
  /// (lo scopo e quello di evitare di non inizializzare) 
  /// 
  /// 

  ProprietarioHomeController getProprietarioHomeController() {
    if (propHomeController == null) {
      propHomeController = (ProprietarioHomeController) loadContent("ProprietarioHomeView.fxml");
      propHomeController.homeController = this;
    }
    return propHomeController;
  }

  ColtivatoreHomeController getColtivatoreHomeController() {
    if (coltHomeController == null) {
      coltHomeController = (ColtivatoreHomeController) loadContent("ColtivatoreHomeView.fxml");
      coltHomeController.homeController = this;
    }
    return coltHomeController;
  }

  LottiController getLottiController() {
    if (lottiController == null) {
      lottiController = (LottiController) loadContent("LottiView.fxml");
      lottiController.homeController = this;
    }
    return lottiController;
  }

  ProgettiViewController getProgettiController() {
    if (progettiViewController == null) {
      progettiViewController = (ProgettiViewController) loadContent("ProgettiView.fxml");
      progettiViewController.homeController = this;
    }
    return progettiViewController;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  /// 
  /// 
  /// Metodi per il logout
  /// 
  /// 

  private void clearState() {
    loginService.logout();
  }

  private void clearUIContent() {
    usernameLabel.setText("");
    getLottiController().clearUIContent();
    getProgettiController().clearUIContent();
  }

  @FXML private void logout(){
    clearState();
    clearUIContent();
    mainController.openLoginView();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  /// 
  /// 
  /// Metodi fare open di qualche view
  /// 
  /// 

  @SuppressWarnings("incomplete-switch")
  void openHomeContent() {
    if (usernameLabel.getText().equals("") || usernameLabel.getText() == null){
      usernameLabel.setText(loginService.getUsername());
    }

    switch (loginService.getLoggedInType()) {
      case UserType.PROPRIETARIO: 
        setActiveContent(getProprietarioHomeController());
        break;
      case UserType.COLTIVATORE: 
        setActiveContent(getColtivatoreHomeController());
        break;
    }
  }

  void openLottiView() {
    try {
      getLottiController().clearUIContent();
      getLottiController().loadLotti();
    } catch (ConnectionFailedException e) {
      getLottiController().showErrorMessage(e.getMessage());
    } catch (NoDataFoundException e) {
      getLottiController().showErrorMessage(e.getMessage());
    }
    setActiveContent(getLottiController());
  }

  void openProgettiView() {
    try {
      getProgettiController().clearUIContent();
      getProgettiController().loadProgetti();
    } catch (ConnectionFailedException e) {
      getProgettiController().showErrorMessage(e.getMessage());
    } catch (NoDataFoundException e) {
      getProgettiController().showErrorMessage(e.getMessage());
    }
    setActiveContent(getProgettiController());
  }

}
