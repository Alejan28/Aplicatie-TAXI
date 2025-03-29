module com.example.examenpractic_map {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.examenpractic_map to javafx.fxml;
    exports com.example.examenpractic_map;
    opens com.example.examenpractic_map.Controller to javafx.fxml;
    opens com.example.examenpractic_map.Domain to javafx.base;
    exports com.example.examenpractic_map.Controller;
}