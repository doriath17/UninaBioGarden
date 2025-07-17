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

    @FXML void openLoginView() {
        controllerManager.openLoginView();
    }

    @FXML void openLottiView() {
        controllerManager.openLottiView();
    }

    @FXML void openProgettiView() {
        controllerManager.openProgettiView();
    }
}
