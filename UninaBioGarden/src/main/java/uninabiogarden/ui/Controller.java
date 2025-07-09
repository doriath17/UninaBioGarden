package uninabiogarden.ui;

import javafx.scene.Parent;

public abstract class Controller {
    ControllerManager controllerManager;

    void setControllerManager(ControllerManager controllerManager){
        this.controllerManager = controllerManager;
    }

    abstract Parent getRoot();
}