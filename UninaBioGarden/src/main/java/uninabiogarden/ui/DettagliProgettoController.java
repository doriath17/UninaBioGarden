package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public class DettagliProgettoController extends Controller {
    @FXML
    HBox root;

    @Override
    Parent getRoot() {
        return root;
    }

    @FXML void goBackToProgetti() {
        controllerManager.progettiController.setActiveContent(controllerManager.progettiListController.getRoot());
    }

}
