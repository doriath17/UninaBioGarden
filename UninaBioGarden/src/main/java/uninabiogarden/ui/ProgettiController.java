package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ProgettiController extends ContentController {

    @FXML VBox root;
    @FXML VBox contentRoot;

    @Override
    VBox getContentRoot() {
        return contentRoot;
    }

    @Override
    Parent getRoot() {
        return root;
    }

    @FXML void goBack() {
        if (activeContent == controllerManager.progettiListController.getRoot()) {
            controllerManager.openProprietarioHomeView();
        } else {
            openProgettiListView();
        }
    }

    @FXML void openProgettiListView() {
        setActiveContent(controllerManager.progettiListController.getRoot());
    }

}
