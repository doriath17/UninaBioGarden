package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uninabiogarden.core.LoginSession.UserType;
import uninabiogarden.exceptions.ConnectionFailedException;
import uninabiogarden.exceptions.NoDataFoundException;
import uninabiogarden.service.ColtivatoreService;
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

  MainController mainController;

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
    context.getSession().logout();
    context.getAppState().clear();
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
      usernameLabel.setText(context.getSession().getUsername());
    }

    switch (context.getSession().getCurrType()) {
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
      getLottiController().loadLotti();
    } catch (ConnectionFailedException e) {
      e.printStackTrace();
    } catch (NoDataFoundException e) {
      e.printStackTrace();
    }

    setActiveContent(getLottiController());
  }

  void openProgettiView() {
    try {
      getProgettiController().loadProgetti();
      getProgettiController().getAvailableLottiController().loadAvailableLotti();
    } catch (ConnectionFailedException e) {
      e.printStackTrace();
    } catch (NoDataFoundException e) {
      e.printStackTrace();
    }
    
    setActiveContent(getProgettiController());
  }

}
