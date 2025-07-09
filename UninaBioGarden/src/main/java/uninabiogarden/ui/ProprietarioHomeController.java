package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ProprietarioHomeController extends Controller {
    @FXML VBox root;

    @Override
    Parent getRoot() {
        return root;
    }

    @FXML private void openLoginView() {
        controllerManager.openLoginView();
    }
}
