package uninabiogarden;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LoginController extends Controller {
  @FXML VBox root;
  @FXML Button btnRegistration;

  public Parent getRoot() {
      return root;
  }

  @FXML private void openRegistrationView() {
      controllerManager.openRegistrationView();
  }

  @FXML private void openHomeView(){
      controllerManager.openProprietarioHomeView();
  }
}
