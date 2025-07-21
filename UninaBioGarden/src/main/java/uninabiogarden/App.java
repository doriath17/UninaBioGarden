package uninabiogarden;

import javafx.application.Application;
import javafx.stage.Stage;
import uninabiogarden.dao.DatabaseManager;
import uninabiogarden.ui.ControllerManager;

/**
 * JavaFX App
 */
public class App extends Application {

    ControllerManager viewManager;
    DatabaseManager databaseManager;

    @Override
    public void init() {
        viewManager = new ControllerManager();
        databaseManager = DatabaseManager.getInstance();
    }

    @Override
    public void start(Stage stage) {
        viewManager.show(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}