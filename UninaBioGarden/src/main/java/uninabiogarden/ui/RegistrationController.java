package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class RegistrationController extends Controller {
    @FXML VBox root;

    @Override
    public Parent getRoot() {
        return root;
    }

    @FXML private void openLoginView() {
       controllerManager.openLoginView();
    }

    @FXML private void openHomeView() {
        controllerManager.openProprietarioHomeView();
    }


}
