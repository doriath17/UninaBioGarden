package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uninabiogarden.service.ColtivatoreService;
import uninabiogarden.service.ProprietarioService;

public class HomeController extends ContentController {

  ProprietarioService propService;
  ColtivatoreService coltService;

  @FXML VBox root;
  @FXML VBox contentRoot;

  @FXML Label usernameLabel;

  Parent activeView;
  Controller activeController;
  ProprietarioHomeController  propHomeController;
  ColtivatoreHomeController   coltHomeController;
  LottiController             lottiController;
  ProgettiViewController      progettiViewController;

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

  @FXML private void logout(){
    usernameLabel.setText("");
    // controllerManager.logout();
  }

  String getUsername() {
    if (propService != null){
      return propService.getUtente().getUsername();
    } else {
      return coltService.getUtente().getUsername();
    }
  }

  void openHomeContent() {
    System.out.println("openHomeContent");
    if (usernameLabel.getText().equals("") || usernameLabel.getText() == null){
      usernameLabel.setText(getUsername());
    }
    if (propService != null) {
      if (propHomeController == null) {
        propHomeController = (ProprietarioHomeController) loadContent("ProprietarioHomeView.fxml");
        propHomeController.propService = propService;
      }
      setActiveContent(propHomeController);
    } else {
      if (coltHomeController == null) {
        coltHomeController = (ColtivatoreHomeController) loadContent("ColtivatoreHomeView.fxml");
        coltHomeController.coltService = coltService;
      }
      setActiveContent(coltHomeController);
    }
  }

  void openLottiView() {
    if (lottiController == null) {
      lottiController = (LottiController) loadContent("LottiView.fxml");
      lottiController.propService = propService;
    }
    lottiController.loadLotti();
    setActiveContent(lottiController);
  }

  void openProgettiView() {
    if (progettiViewController == null) {
      progettiViewController = (ProgettiViewController) loadContent("ProgettiView.fxml");
      progettiViewController.propService = propService;
    }
    progettiViewController.loadProgetti();
    setActiveContent(progettiViewController);
  }

}
