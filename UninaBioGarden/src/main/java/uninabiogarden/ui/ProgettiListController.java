package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ProgettiListController extends Controller {
    @FXML VBox root;

    @Override
    Parent getRoot() {
        return root;
    }

    @FXML void openDettagliProgettoView() {
        controllerManager.progettiController.setActiveContent(controllerManager.dettagliProgettoController.getRoot());
    }
}
