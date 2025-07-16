package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class LottiController extends ContentController {

    @FXML VBox root;
    @FXML VBox contentRoot;

    @Override
    Parent getRoot() {
        return root;
    }

    @Override
    VBox getContentRoot() {
        return contentRoot;
    }

    @FXML private void goBack() {
        if (activeContent == controllerManager.lottiListController.getRoot()){
            controllerManager.openProprietarioHomeView();
        } else {    // active content is AddingLottoView
            setActiveContent(controllerManager.lottiListController.getRoot());
        }
    }

    @FXML private void openLottiListView() {
        setActiveContent(controllerManager.lottiListController.getRoot());
    }

    @FXML private void openAddingLottoView() {
        setActiveContent(controllerManager.addingLottoController.getRoot());
    }

}
