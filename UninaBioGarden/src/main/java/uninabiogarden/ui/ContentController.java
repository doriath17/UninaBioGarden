package uninabiogarden.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public abstract class ContentController extends Controller {
    Parent activeContent;

    abstract VBox getContentRoot();

    void setActiveContent(Parent newContent) {
        getContentRoot().getChildren().remove(activeContent);
        getContentRoot().getChildren().add(newContent);
        this.activeContent = newContent;
    }

}
