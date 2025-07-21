module uninabiogarden {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens uninabiogarden to javafx.fxml;
    opens uninabiogarden.ui to javafx.fxml;
    opens uninabiogarden.dao to javafx.dao;
    
    exports uninabiogarden;
    exports uninabiogarden.ui;
    exports uninabiogarden.dao;
}
