module uninabiogarden {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;

    opens uninabiogarden to javafx.fxml;
    opens uninabiogarden.ui to javafx.fxml;
    opens uninabiogarden.entities to javafx.base;
    
    exports uninabiogarden;
    exports uninabiogarden.ui;
    exports uninabiogarden.dao;
    exports uninabiogarden.entities;
}
