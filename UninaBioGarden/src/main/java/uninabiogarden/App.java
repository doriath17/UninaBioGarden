package uninabiogarden;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    ViewManager viewManager;

    @Override
    public void init() {
        viewManager = new ViewManager();
    }

    @Override
    public void start(Stage stage) {
        viewManager.show(stage);

    }

    public static void main(String[] args) {
        launch();
    }

}