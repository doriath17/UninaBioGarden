package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uninabiogarden.entities.Coltivatore;
import uninabiogarden.entities.Proprietario;

public class HomeController extends Controller {

  @FXML VBox root;
  @FXML HBox contentRoot;

  @FXML Label usernameLabel;

  Parent activeView;
  ProprietarioHomeController  propHomeController;
  ColtivatoreHomeController   coltHomeController;
  LottiController             lottiController;
  ProgettiViewController       progettiViewController;

  @Override
  public Parent getRoot(){
    return root;
  }

  public void setActiveContent(Parent newContent) {
    contentRoot.getChildren().remove(activeView);
    contentRoot.getChildren().add(newContent);
    this.activeView = newContent;
  }

  @FXML private void logout(){
    usernameLabel.setText("");
    controllerManager.logout();
  }

  void openHomeContent() {
    if (usernameLabel.getText().equals("") || usernameLabel.getText() == null){
      usernameLabel.setText(controllerManager.controllerDAO.getUser().getUsername());
    }
    if (controllerManager.controllerDAO.getUser() instanceof Proprietario) {
      if (propHomeController == null) {
        propHomeController = (ProprietarioHomeController) controllerManager.loadController("ProprietarioHome");
      }
      setActiveContent(propHomeController.getRoot());
    } else if (controllerManager.controllerDAO.getUser() instanceof Coltivatore) {
      if (coltHomeController == null) {
        coltHomeController = (ColtivatoreHomeController) controllerManager.loadController("ColtivatoreHome");
      }
      setActiveContent(coltHomeController.getRoot());
    }
  }

  void openLottiView() {
    if (lottiController == null) {
      lottiController = (LottiController) controllerManager.loadController("Lotti");
    }
    lottiController.loadLotti();
    setActiveContent(lottiController.getRoot());
  }

  void openProgettiView() {
    if (progettiViewController == null) {
      progettiViewController = (ProgettiViewController) controllerManager.loadController("Progetti");
    }
    progettiViewController.loadProgetti();
    setActiveContent(progettiViewController.getRoot());
  }

}
