module uninabiogarden {
    requires javafx.controls;
    requires javafx.fxml;

    opens uninabiogarden to javafx.fxml;
    exports uninabiogarden;
    exports uninabiogarden.ui;
    opens uninabiogarden.ui to javafx.fxml;
}
