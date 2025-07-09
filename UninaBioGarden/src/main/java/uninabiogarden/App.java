package uninabiogarden;

import javafx.application.Application;
import javafx.stage.Stage;
import uninabiogarden.ui.ControllerManager;

/**
 * JavaFX App
 */
public class App extends Application {

    ControllerManager viewManager;

    @Override
    public void init() {
        viewManager = new ControllerManager();
    }

    @Override
    public void start(Stage stage) {
        viewManager.show(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}