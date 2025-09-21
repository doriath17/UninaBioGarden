package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomeController extends ContentController {

  @FXML VBox root;
  @FXML VBox contentRoot;
  @FXML Label usernameLabel;

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

  void clearUIContent() {
    usernameLabel.setText("");
    mainController.clearPropUIContent();
  }

  @FXML private void logout(){
    mainController.logout();
  }

  @SuppressWarnings("incomplete-switch")
  void openHomeContent() {
    if (usernameLabel.getText().equals("") || usernameLabel.getText() == null){
      usernameLabel.setText(mainController.getUsername());
    }
    setActiveContent(mainController.getHomeContent());
  }

  // void openLottiView() {
  //   try {
  //     getLottiController().clearUIContent();
  //     getLottiController().loadLotti();
  //   } catch (ConnectionFailedException e) {
  //     getLottiController().showErrorMessage(e.getMessage());
  //   } catch (NoDataFoundException e) {
  //     getLottiController().showErrorMessage(e.getMessage());
  //   }
  //   setActiveContent(getLottiController());
  // }

  // void openProgettiView() {
  //   try {
  //     getProgettiController().clearUIContent();
  //     getProgettiController().loadProgetti();
  //   } catch (ConnectionFailedException e) {
  //     getProgettiController().showErrorMessage(e.getMessage());
  //   } catch (NoDataFoundException e) {
  //     getProgettiController().showErrorMessage(e.getMessage());
  //   }
  //   setActiveContent(getProgettiController());
  // }

}
