module uninabiogarden {
    requires javafx.controls;
    requires javafx.fxml;

    opens uninabiogarden to javafx.fxml;
    exports uninabiogarden;
}
